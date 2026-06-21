package com.synaxis.android.chatapp.core.socket


object SocketEvents {

    const val JOIN_ROOM = "join:chat"
    const val LEAVE_ROOM = "leave:chat"
    const val MESSAGE_RECEIVED = "message:new"
    const val MESSAGE_DELETED = "message:deleted"
    const val MESSAGE_UPDATED = "message:updated"

    const val SEND_MESSAGE = "message:send"
    const val UPDATE_MESSAGE = "message:update"
    const val DELETE_MESSAGE = "message:delete"
}