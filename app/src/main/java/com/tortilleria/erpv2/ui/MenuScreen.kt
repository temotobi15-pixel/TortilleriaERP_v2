package com.tortilleria.erpv2.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MenuScreen(
    onVentas: () -> Unit,
    onProduccion: () -> Unit,
    onInventario: () -> Unit,
    onClientes: () -> Unit,
    onReportes: () -> Unit
) {
    Column(Modifier.fillMaxSize().padding(20.dp), verticalArrangement = Arrangement.spacedBy(14.dp)) {
        Text("Tortillería ERP v2", fontSize = 28.sp, fontWeight = FontWeight.Bold)
        Text("Base OK - Módulos:", fontSize = 18.sp)
        Spacer(Modifier.height(8.dp))

        MenuCard("🛒 Ventas y Pedidos", "Registrar pedidos, tortilla, masa", onVentas)
        MenuCard("🌽 Producción", "Kilos producidos, merma", onProduccion)
        MenuCard("📦 Inventario", "Maíz, harina, gas, papel", onInventario)
        MenuCard("👥 Clientes", "Tiendas, rutas, adeudos", onClientes)
        MenuCard("📊 Reportes", "Corte de caja, utilidad", onReportes)
    }
}

@Composable
private fun MenuCard(titulo: String, subtitulo: String, onClick: () -> Unit) {
    ElevatedCard(onClick = onClick, modifier = Modifier.fillMaxWidth()) {
        Column(Modifier.padding(16.dp)) {
            Text(titulo, fontWeight = FontWeight.Bold, fontSize = 20.sp)
            Text(subtitulo, fontSize = 14.sp)
        }
    }
}
