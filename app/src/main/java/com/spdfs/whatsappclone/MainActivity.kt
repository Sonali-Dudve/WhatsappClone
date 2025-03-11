package com.spdfs.whatsappclone

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType.Companion.Text
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.spdfs.whatsappclone.screens.UserListScreen
import com.spdfs.whatsappclone.ui.theme.WhatsappCloneTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WhatsappCloneTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    requestPermission(context = this)
                    UserListScreen(context = this)
                }
            }
        }
    }

    private fun requestPermission(context: Context) {
        if (ContextCompat.checkSelfPermission(
                context,
                android.Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                (context as Activity), arrayOf(android.Manifest.permission.CAMERA),
                100
            )
        }
    }

}


