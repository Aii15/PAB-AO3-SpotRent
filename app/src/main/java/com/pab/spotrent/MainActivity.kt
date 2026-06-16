package com.pab.spotrent

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.pab.spotrent.ui.navigation.MainNavigation
import com.pab.spotrent.ui.theme.SpotRentTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SpotRentTheme {
                MainNavigation()
            }
        }
    }
}
