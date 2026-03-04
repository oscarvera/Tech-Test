package com.oscarvera.techtest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.oscarvera.techtest.ui.navigation.AppNavGraph
import com.oscarvera.techtest.ui.theme.TechTestTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TechTestTheme {
                AppNavGraph()
            }
        }
    }
}
