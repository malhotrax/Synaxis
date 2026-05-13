package com.synaxis.android.chatapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.synaxis.android.chatapp.navigation.NavigationRoot
import com.synaxis.android.chatapp.ui.theme.SynaxisTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SynaxisTheme {
                NavigationRoot()
            }
        }
    }
}