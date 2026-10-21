package com.tortilleria.erpv2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import com.tortilleria.erpv2.ui.HistorialScreen
import com.tortilleria.erpv2.ui.VentasScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface {
                    var ruta by remember { mutableStateOf("ventas") }
                    when (ruta) {
                        "ventas" -> VentasScreen(
                            onBack = { finish() },
                            onVerHistorial = { ruta = "historial" }
                        )
                        "historial" -> HistorialScreen(
                            onBack = { ruta = "ventas" }
                        )
                    }
                }
            }
        }
    }
}
