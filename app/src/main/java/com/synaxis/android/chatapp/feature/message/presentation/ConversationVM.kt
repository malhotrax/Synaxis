package com.synaxis.android.chatapp.feature.message.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.synaxis.android.chatapp.core.common.resource.ApiResult
import com.synaxis.android.chatapp.core.datastore.session.SessionDatasource
import com.synaxis.android.chatapp.feature.chat.domain.model.Chat
import com.synaxis.android.chatapp.feature.message.domain.model.Message
import com.synaxis.android.chatapp.feature.message.domain.use_case.MessageUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel(assistedFactory = ConversationVM.Factory::class)
class ConversationVM @AssistedInject constructor(
    private val messageUseCase: MessageUseCase,
    private val sessionDatasource: SessionDatasource,
    @Assisted private val chat: Chat
) : ViewModel() {

    init {
        viewModelScope.launch {
            val userId = sessionDatasource.userId().first {it != null}
            _state.update { it.copy(userId = userId) }
        }
    }
    private val _state = MutableStateFlow(ConversationState())
    val state = _state.asStateFlow()

    private val _uiState = MutableSharedFlow<ConversationUiEvent>()
    val uiState = _uiState.asSharedFlow()

    val messages: Flow<PagingData<Message>> = messageUseCase
        .getMessages(chat.id)
        .cachedIn(viewModelScope)

    fun onEvent(e: ConversationEvent) {
        when (e) {
            is ConversationEvent.DeleteMessage -> onDeleteMessage(e.id)
            is ConversationEvent.MessageChanged -> onMessageChanged(e.message)
            is ConversationEvent.SendMessage -> onSendMessage()
            is ConversationEvent.UpdateMessage -> onUpdateMessage(e.id, e.message)
            ConversationEvent.NavigateBack -> onNavigateBack()
            ConversationEvent.JoinChat -> onJoinChat()
            ConversationEvent.LeaveChat -> onLeaveChat()
        }
    }

    private fun onJoinChat() {
        messageUseCase.joinChat(chat.id)
    }

    private fun onLeaveChat() {
        messageUseCase.leaveChat(chat.id)
    }
    private fun sendUiEvent(e: ConversationUiEvent) {
        viewModelScope.launch {
            _uiState.emit(e)
        }
    }

    private fun onNavigateBack() {
        sendUiEvent(ConversationUiEvent.NavigateBack)
    }

    private fun onDeleteMessage(id: String) {
        viewModelScope.launch {
            when (val result = messageUseCase.deleteMessage(id)) {
                ApiResult.Empty -> sendUiEvent(ConversationUiEvent.ShowSnackBar(message = "Something went wrong"))
                is ApiResult.Error -> sendUiEvent(
                    ConversationUiEvent.ShowSnackBar(
                        result.message ?: "Unknown error"
                    )
                )

                is ApiResult.Success -> Unit
            }
        }
    }

    private fun onSendMessage() {
        viewModelScope.launch {
            when (val result = messageUseCase.sendMessage(content = _state.value.message.trim(), chatId = chat.id)) {
                ApiResult.Empty -> sendUiEvent(ConversationUiEvent.ShowSnackBar(message = "Something went wrong"))
                is ApiResult.Error -> sendUiEvent(
                    ConversationUiEvent.ShowSnackBar(
                        result.message ?: "Unknown error"
                    )
                )

                is ApiResult.Success -> Unit
            }
        }
    }

    private fun onUpdateMessage(id: String, message: String) {
        viewModelScope.launch {
            when (val result = messageUseCase.updateMessage(id, message)) {
                ApiResult.Empty -> sendUiEvent(ConversationUiEvent.ShowSnackBar(message = "Something went wrong"))
                is ApiResult.Error -> sendUiEvent(
                    ConversationUiEvent.ShowSnackBar(
                        result.message ?: "Unknown error"
                    )
                )

                is ApiResult.Success -> Unit
            }
        }
    }

    private fun onMessageChanged(message: String) {
        _state.update { it.copy(message = message) }
    }

    @AssistedFactory
    interface Factory {
        fun create(chat: Chat): ConversationVM
    }
}