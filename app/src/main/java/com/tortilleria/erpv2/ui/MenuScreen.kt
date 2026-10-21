package com.tortilleria.erpv2.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MenuScreen(onVentas: () -> Unit, onHistorial: () -> Unit, onInventario: () -> Unit) {
    Column(Modifier.fillMaxSize().padding(24.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Text("Tortillería ERP v2", fontSize = 28.sp, fontWeight = FontWeight.Bold)
        Text("¿Qué quieres hacer hoy?")
        Spacer(Modifier.height(10.dp))
        ElevatedButton(onClick = onVentas, modifier = Modifier.fillMaxWidth().height(70.dp)) { Text("1. VENTAS - Punto de venta", fontSize = 18.sp) }
        ElevatedButton(onClick = onHistorial, modifier = Modifier.fillMaxWidth().height(70.dp)) { Text("2. HISTORIAL DE TICKETS", fontSize = 18.sp) }
        OutlinedButton(onClick = onInventario, modifier = Modifier.fillMaxWidth().height(60.dp)) { Text("3. INVENTARIO") }
    }
}
