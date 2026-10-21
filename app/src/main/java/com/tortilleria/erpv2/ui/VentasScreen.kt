package com.tortilleria.erpv2.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tortilleria.erpv2.data.InventarioRepo

data class VentaHistorial(
    val nombre: String,
    val kilos: Double,
    val total: Double,
    val tipo: String // TICKET o COBRADA
)

object VentasRepo {
    val historial = mutableStateListOf<VentaHistorial>()
    var totalHoy by mutableStateOf(0.0)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VentasScreen(onBack: () -> Unit, onVerHistorial: () -> Unit) {
    val catalogo = InventarioRepo.productos.filter { it.apareceEnCatalogo }
    if (catalogo.isEmpty()) {
        Column(Modifier.fillMaxSize().padding(16.dp)) {
            Button(onClick = onBack) { Text("← Menú") }
            Text("No hay productos con check 'Aparece en catálogo'. Actívalo en Inventario.", color = MaterialTheme.colorScheme.error, modifier = Modifier.padding(top = 16.dp))
        }
        return
    }

    var producto by remember { mutableStateOf(catalogo.first()) }
    var variante by remember { mutableStateOf(producto.variantes.firstOrNull()) }
    var showProductoDialog by remember { mutableStateOf(false) }
    var showVarianteDialog by remember { mutableStateOf(false) }
    var kilosTxt by remember { mutableStateOf("") }
    var precioTxt by remember { mutableStateOf((variante?.precio ?: producto.precioVenta).toString()) }

    val kilos = kilosTxt.toDoubleOrNull() ?: 0.0
    val precio = precioTxt.toDoubleOrNull() ?: (variante?.precio ?: producto.precioVenta)
    val total = kilos * precio

    // Cuando cambia producto, resetea variante y precio
    LaunchedEffect(producto) {
        variante = producto.variantes.firstOrNull()
        precioTxt = (variante?.precio ?: producto.precioVenta).toString()
    }
    LaunchedEffect(variante) {
        precioTxt = (variante?.precio ?: producto.precioVenta).toString()
    }

    Column(Modifier.fillMaxSize().padding(12.dp)) {
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Button(onClick = onBack) { Text("← Menú") }
            Text("Hoy: $${VentasRepo.totalHoy}", fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 10.dp))
        }
        Text("Tortilleria ERP v2", fontWeight = FontWeight.Bold, fontSize = 22.sp, modifier = Modifier.padding(vertical = 8.dp))

        Card(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant), modifier = Modifier.fillMaxWidth()) {
            Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Text("Nueva Venta", fontWeight = FontWeight.Bold, fontSize = 18.sp)

                OutlinedButton(onClick = { showProductoDialog = true }, modifier = Modifier.fillMaxWidth()) {
                    Text("Producto: ${producto.nombre}")
                }
                OutlinedButton(onClick = { if (producto.variantes.isNotEmpty()) showVarianteDialog = true }, modifier = Modifier.fillMaxWidth()) {
                    val nombreVar = variante?.let { "${producto.nombre} ${it.nombre} - $${it.precio}/kg" } ?: "${producto.nombre} - $${producto.precioVenta}/kg"
                    Text(nombreVar)
                }

                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = kilosTxt,
                        onValueChange = { kilosTxt = it.replace(",", ".") },
                        label = { Text("Kilos") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.weight(1f)
                    )
                    OutlinedTextField(
                        value = "$$precioTxt",
                        onValueChange = { precioTxt = it.replace("$", "").replace(",", ".") },
                        label = { Text("Precio") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.weight(1f)
                    )
                }
                Text("Total: $${"%.2f".format(total)}", fontWeight = FontWeight.Bold, fontSize = 22.sp)

                Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
                    OutlinedButton(
                        onClick = {
                            if (kilos > 0) {
                                val item = VentaHistorial("${producto.nombre} ${variante?.nombre ?: ""} - ${kilos}kg", kilos, total, "TICKET: Turi")
                                VentasRepo.historial.add(0, item)
                                kilosTxt = ""
                            }
                        },
                        modifier = Modifier.weight(1f)
                    ) { Text("Guardar Ticket") }

                    Button(
                        onClick = {
                            if (kilos > 0) {
                                val item = VentaHistorial("${producto.nombre} ${variante?.nombre ?: ""} - ${kilos}kg", kilos, total, "COBRADA")
                                VentasRepo.historial.add(0, item)
                                VentasRepo.totalHoy += total
                                val idx = InventarioRepo.productos.indexOfFirst { it.id == producto.id }
                                if (idx >= 0) {
                                    val act = InventarioRepo.productos[idx]
                                    InventarioRepo.productos[idx] = act.copy(stock = (act.stock - kilos).coerceAtLeast(0.0))
                                }
                                kilosTxt = ""
                            }
                        },
                        modifier = Modifier.weight(1f)
                    ) { Text("Cobrar") }
                }
            }
        }

        Spacer(Modifier.height(12.dp))
        Text("Historial (Cobros y Tickets):", fontWeight = FontWeight.Bold, fontSize = 16.sp)
        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxSize()) {
            items(VentasRepo.historial) { v ->
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = if (v.tipo.startsWith("TICKET")) MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.5f) else MaterialTheme.colorScheme.surfaceVariant
                    ),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(Modifier.padding(12.dp).fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(v.nombre, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                            Text(v.tipo, fontSize = 12.sp)
                        }
                        Text("$${v.total}", fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }

    if (showProductoDialog) {
        AlertDialog(
            onDismissRequest = { showProductoDialog = false },
            title = { Text("Elige Producto") },
            text = {
                LazyColumn {
                    items(catalogo, key = { it.id }) { p ->
                        TextButton(onClick = { producto = p; showProductoDialog = false }, modifier = Modifier.fillMaxWidth()) {
                            Text(p.nombre, modifier = Modifier.fillMaxWidth())
                        }
                    }
                }
            },
            confirmButton = {},
            dismissButton = { TextButton(onClick = { showProductoDialog = false }) { Text("Cerrar") } }
        )
    }

    if (showVarianteDialog) {
        AlertDialog(
            onDismissRequest = { showVarianteDialog = false },
            title = { Text("Elige Variante") },
            text = {
                Column {
                    producto.variantes.forEach { varP ->
                        TextButton(onClick = { variante = varP; showVarianteDialog = false }, modifier = Modifier.fillMaxWidth()) {
                            Text("${varP.nombre} - $${varP.precio}/kg", modifier = Modifier.fillMaxWidth())
                        }
                    }
                }
            },
            confirmButton = {},
            dismissButton = { TextButton(onClick = { showVarianteDialog = false }) { Text("Cerrar") } }
        )
    }
}
