package com.synaxis.android.chatapp.core.common.util

import androidx.compose.runtime.IntState
import androidx.compose.ui.unit.IntSize
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter


object DateTimeUtil {

   private val zone = ZoneId.systemDefault()
   private val timeFormatter = DateTimeFormatter.ofPattern("h:mm a")
   private val dayFormatter = DateTimeFormatter.ofPattern("EEEE")
   private val dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yy")

   fun String.isoToMillis(): Long = Instant.parse(this).toEpochMilli()

   fun Long.toChatTime(): String {
      val messageTime = Instant.ofEpochMilli(this).atZone(zone)
      val today = LocalDate.now(zone)
      val messageDate = messageTime.toLocalDate()

      return when (messageDate) {
          today -> {
              messageTime.format(timeFormatter)
          }
          today.minusDays(1) -> {
              "yesterday"
          }
          else -> {
              messageTime.format(dateFormatter)
          }
      }

   }
   fun Long.toIso(): String = Instant.ofEpochMilli(this).toString()

}