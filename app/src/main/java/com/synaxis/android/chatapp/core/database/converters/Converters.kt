package com.synaxis.android.chatapp.core.database.converters

import androidx.room.TypeConverter
import com.synaxis.android.chatapp.core.common.chat.ChatMember
import kotlinx.serialization.json.Json


class Converters   {

    @TypeConverter
    fun membersToString(members: List<ChatMember>): String {
        return Json.encodeToString(members)
    }

    @TypeConverter
    fun stringToMembers(string: String): List<ChatMember> {
        return Json.decodeFromString<List<ChatMember>>(string)
    }

}