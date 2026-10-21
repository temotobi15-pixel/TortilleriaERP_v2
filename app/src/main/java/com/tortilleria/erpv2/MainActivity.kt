package com.tortilleria.erpv2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import com.tortilleria.erpv2.ui.*

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
                            onProduccion = { ruta = "produccion" },
                            onInventario = { ruta = "inventario" },
                            onClientes = { ruta = "clientes" },
                            onReportes = { ruta = "reportes" }
                        )
                        "ventas" -> VentasScreen(onBack = { ruta="menu" }, onVerHistorial = { ruta="historial" })
                        "inventario" -> InventarioScreen(onBack = { ruta="menu" })
                        "historial" -> HistorialScreen(onBack = { ruta="menu" })
                        "produccion" -> InventarioScreen(onBack = { ruta="menu" }) // temporal, luego modulo real
                        "clientes" -> HistorialScreen(onBack = { ruta="menu" })
                        "reportes" -> HistorialScreen(onBack = { ruta="menu" })
                    }
                }
            }
        }
    }
}
