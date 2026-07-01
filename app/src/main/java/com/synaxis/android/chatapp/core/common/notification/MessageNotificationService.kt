package com.synaxis.android.chatapp.core.common.notification

import android.Manifest
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.synaxis.android.chatapp.MainActivity
import com.synaxis.android.chatapp.R
import com.synaxis.android.chatapp.feature.message.data.remote.dto.NotificationMessageDto
import com.synaxis.android.chatapp.feature.message.domain.model.Message
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject


class MessageNotificationService @Inject constructor(
    @param:ApplicationContext private val context: Context
) {

    private val notificationManger  = NotificationManagerCompat.from(context)
    fun showNotification(notificationMessageDto: NotificationMessageDto) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
            ContextCompat.checkSelfPermission(
                context, Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) return
        if (ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) return

        val tapIntent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK  // ← assign it
            putExtra(EXTRA_OPEN_CHAT_ID, notificationMessageDto.messageDto.chatId)
        }
        val pendingIntent = PendingIntent.getActivity(
            context,
            notificationMessageDto.messageDto.chatId.hashCode(),
            tapIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        val notification = NotificationCompat
            .Builder(context, MESSAGE_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_chat)
            .setContentTitle(notificationMessageDto.senderName)
            .setContentIntent(pendingIntent)
            .setContentText(notificationMessageDto.messageDto.text)
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setCategory(NotificationCompat.CATEGORY_MESSAGE)
            .setDefaults(NotificationCompat.DEFAULT_ALL)
            .build()
        notificationManger.notify(notificationMessageDto.messageDto.chatId.hashCode(), notification)
    }

    companion object {
        const val MESSAGE_CHANNEL_ID = "message_notification"
        const val EXTRA_OPEN_CHAT_ID = "extra_open_chat_id"
    }
}