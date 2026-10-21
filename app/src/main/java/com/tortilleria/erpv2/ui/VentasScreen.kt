package com.tortilleria.erpv2.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tortilleria.erpv2.data.InventarioRepo

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VentasScreen(onBack: () -> Unit, onVerHistorial: () -> Unit) {
    // Esta es la ÚNICA línea que conecta con Inventario - filtra por tu check
    val catalogo = InventarioRepo.productos.filter { it.apareceEnCatalogo }

    Column(Modifier.fillMaxSize().padding(12.dp)) {
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Button(onClick = onBack) { Text("← Menú") }
            TextButton(onClick = onVerHistorial) { Text("Historial") }
        }
        Text("VENTAS", fontWeight = FontWeight.Bold, fontSize = 18.sp, modifier = Modifier.padding(vertical = 8.dp))
        Text("Catálogo (${catalogo.size} productos)", fontWeight = FontWeight.Bold, fontSize = 14.sp)

        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.weight(1f).padding(top = 8.dp)) {
            items(catalogo, key = { it.id }) { prod ->
                ElevatedCard(modifier = Modifier.fillMaxWidth()) {
                    Column(Modifier.padding(12.dp)) {
                        Text(prod.nombre, fontWeight = FontWeight.Bold)
                        Text("${prod.unidad} - $${prod.precioVenta}", fontSize = 12.sp)
                        if (prod.variantes.isNotEmpty()) {
                            Text("Variantes: ${prod.variantes.joinToString { "${it.nombre} $${it.precio}" }}", fontSize = 11.sp)
                        }
                    }
                }
            }
        }
    }
}
