package com.example.mobproject

import NavigationGraph
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.graphics.Color
import com.example.mobproject.ui.theme.MobProjectTheme
import dagger.hilt.android.AndroidEntryPoint
import com.google.firebase.FirebaseApp

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MobProjectTheme {
                Surface(
                    color = MaterialTheme.colorScheme.background,
                    contentColor = Color.White
                ) {
                    NavigationGraph()
                }
            }
        }
    }
}
