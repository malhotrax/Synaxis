package com.synaxis.android.chatapp.core.common.util

import androidx.compose.runtime.IntState
import androidx.compose.ui.unit.IntSize
import java.time.Instant
import java.time.ZoneId


object DateTimeUtil {
   private val zone = ZoneId.systemDefault()

   fun String.isoToMillis(): Long = Instant.parse(this).toEpochMilli()

   fun Long.toIso(): String = Instant.ofEpochMilli(this).toString()

}