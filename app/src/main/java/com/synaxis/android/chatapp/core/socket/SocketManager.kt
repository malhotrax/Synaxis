package com.synaxis.android.chatapp.core.socket

import android.content.Context
import android.util.Log
import com.synaxis.android.chatapp.feature.message.data.remote.dto.MessageDto
import dagger.hilt.android.qualifiers.ApplicationContext
import io.socket.client.IO
import io.socket.client.Socket
import io.socket.emitter.Emitter
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import java.time.Instant
import java.util.concurrent.atomic.AtomicInteger
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SocketManager @Inject constructor(
    private val baseUrl: String,
    private val json: Json
) {
    private var socket: Socket? = null
    private val _connectionState = MutableStateFlow<ConnectionState>(ConnectionState.Disconnected)
    val connectionState = _connectionState.asStateFlow()


    private val _socketInboundEvents = MutableSharedFlow<SocketInboundEvent>(
        extraBufferCapacity = 64,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val socketInboundEvent = _socketInboundEvents.asSharedFlow()
    private val maxReconnectAttempts = 5

    fun connect(authToken: String) {
        if (socket?.connected() == true) {
            Log.d("SocketManager", "Already connected")
            return
        }

        try {
            val options = IO.Options().apply {
                auth = mapOf("token" to authToken)
                reconnection = true
                reconnectionDelay = 1000
                reconnectionDelayMax = 5000
                reconnectionAttempts = maxReconnectAttempts
                timeout = 10000
            }
            socket = IO.socket(baseUrl, options).apply {
                setupEventListener()
                connect()
            }
            _connectionState.value = ConnectionState.Connecting
        } catch (e: Exception) {
            Log.e("SocketManager", "Connection failed ${e.message}")
            _connectionState.value =
                ConnectionState.Error(e.localizedMessage ?: "Failed to connect with socket")
        }
    }

    fun emit(e: SocketOutboundEvent) {
        val socket = socket ?: return
        when (e) {
            is SocketOutboundEvent.DeleteMessage -> {
                val payload = e.messageId
                socket.emit(SocketEvents.DELETE_MESSAGE,payload )
            }
            is SocketOutboundEvent.SendMessage -> {
                val payload = json.encodeToString<MessageDto>(e.messageDto)
                socket.emit(SocketEvents.SEND_MESSAGE,payload)
            }
            is SocketOutboundEvent.UpdateMessage -> {
                val updateMessageDto = UpdateMessageDto(e.messageId, e.content)
                val payload = json.encodeToString<UpdateMessageDto>(updateMessageDto)
                socket.emit(SocketEvents.UPDATE_MESSAGE, payload)
            }

            is SocketOutboundEvent.JoinChat -> {
                socket.emit(SocketEvents.JOIN_ROOM,e.chatId)
            }
            is SocketOutboundEvent.LeaveChat -> {
                socket.emit(SocketEvents.LEAVE_ROOM, e.chatId)
            }
        }
    }

    fun Socket.setupEventListener() {
        on(Socket.EVENT_CONNECT) {
            Log.d("SocketManager", "Connected to server")
            _connectionState.value = ConnectionState.Connected
        }
        on(Socket.EVENT_DISCONNECT) { args ->
            Log.d("SocketManager", "Disconnected ${args.firstOrNull()?.toString()}")
            _connectionState.value = ConnectionState.Disconnected
        }
        on(Socket.EVENT_CONNECT_ERROR) { args ->
            val error = args.firstOrNull()?.toString() ?: "Connection Error"
            Log.e("SocketManager", "Connection Failed $error")
            _connectionState.value = ConnectionState.Error(error)
        }
        registerEvent(SocketEvents.MESSAGE_RECEIVED) {
            json.decodeFromString<MessageDto>(it).let (SocketInboundEvent::MessageReceived)
        }
        registerEvent(SocketEvents.MESSAGE_DELETED) {
            json.decodeFromString<DeleteMessageDto>(it).let { SocketInboundEvent.MessageDeleted(it.id) }
        }
        registerEvent(SocketEvents.MESSAGE_UPDATED) {
            json.decodeFromString<UpdateMessageDto>(it).let { dto -> SocketInboundEvent.MessageUpdated(messageId = dto.id, content = dto.content, updatedAt = dto.updatedAt) }
        }
    }

    fun Socket.registerEvent(eventName: String, parse: (String) -> SocketInboundEvent) {
        on(eventName) { args ->
           try {
               _socketInboundEvents.tryEmit(parse(args.first().toString()))
           }catch (e: Exception)  {
               Log.e("SocketManager","Parse error $eventName : ${e.message}")
           }
        }
    }

    fun disconnect() {
        socket?.disconnect()
        socket?.off()
        _connectionState.value = ConnectionState.Disconnected
    }

}



@Serializable
data class UpdateMessageDto(
    val id: String,
    val content: String,
    val updatedAt: String = Instant.now().toString()
)

@Serializable
data class DeleteMessageDto(
    val id: String
)