package com.tortilleria.erpv2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import com.tortilleria.erpv2.ui.HistorialScreen
import com.tortilleria.erpv2.ui.VentasScreen
import com.tortilleria.erpv2.ui.theme.TortilleriaERPv2Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TortilleriaERPv2Theme {
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
