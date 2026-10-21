package com.tortilleria.erpv2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import com.tortilleria.erpv2.ui.HistorialScreen
import com.tortilleria.erpv2.ui.MenuScreen
import com.tortilleria.erpv2.ui.VentasScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface {
                    var ruta by remember { mutableStateOf("menu") }
                    when (ruta) {
                        "menu" -> MenuScreen(
                            onVentas = { ruta = "ventas" },
                            onHistorial = { ruta = "historial" },
                            onInventario = { ruta = "ventas" }
                        )
                        "ventas" -> VentasScreen(
                            onBack = { ruta = "menu" },
                            onVerHistorial = { ruta = "historial" }
                        )
                        "historial" -> HistorialScreen(
                            onBack = { ruta = "menu" }
                        )
                    }
                }
            }
        }
    }
}
