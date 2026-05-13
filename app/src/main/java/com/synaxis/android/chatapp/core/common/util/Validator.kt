package com.synaxis.android.chatapp.core.common.util

import android.util.Patterns
import javax.inject.Singleton

@Singleton
object Validator {
    
    fun isEmailValid(email: String): Boolean {
        return email.isNotBlank() && Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
    
    fun isUsernameValid(username: String): Boolean {
        return username.isNotBlank()
    }
    
    fun isPasswordValid(password: String): Boolean {
        return password.length >= 6
    } 
    
    fun isFullNameValid(fullName: String): Boolean {
        return fullName.isNotBlank()
    }
}
