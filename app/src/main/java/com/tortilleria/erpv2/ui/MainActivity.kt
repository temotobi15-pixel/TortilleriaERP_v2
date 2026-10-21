package com.tortilleria.erpv2.ui
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.*
import androidx.compose.runtime.*
import com.tortilleria.erpv2.data.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val db = AppDatabase.getInstance(this)
        setContent {
            MaterialTheme {
                var texto by remember { mutableStateOf("Tortilleria ERP v2 - Base Congelada OK") }
                Surface {
                    Text(texto)
                }
            }
        }
    }
}
