package com.tortilleria.erpv2.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tortilleria.erpv2.data.AppDatabase
import com.tortilleria.erpv2.data.CatalogoFijo
import com.tortilleria.erpv2.data.Venta
import kotlinx.coroutines.launch

@Composable
fun VentasScreen(onBack: () -> Unit) {
    val context = LocalContext.current
    val db = remember { AppDatabase.getInstance(context) }
    val scope = rememberCoroutineScope()

    var kilos by remember { mutableStateOf("") }
    var ventas by remember { mutableStateOf(listOf<Venta>()) }
    var totalHoy by remember { mutableStateOf(0.0) }
    
    var productoIdx by remember { mutableStateOf(0) }
    var showProdMenu by remember { mutableStateOf(false) }
    var showVarMenu by remember { mutableStateOf(false) }
    var showTicketDialog by remember { mutableStateOf(false) }
    var clienteTicket by remember { mutableStateOf("") }

    val producto = CatalogoFijo.productos[productoIdx]
    var varianteSel by remember(productoIdx) { mutableStateOf(producto.variantes[0]) }

    fun cargar() {
        scope.launch {
            ventas = db.ventaDao().obtenerTodas()
            totalHoy = db.ventaDao().totalHoy() ?: 0.0
        }
    }
    LaunchedEffect(Unit) { cargar() }

    val kilosD = kilos.toDoubleOrNull() ?: 0.0
    val totalCalc = kilosD * varianteSel.precio

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Button(onClick = onBack) { Text("← Menú") }
            Text("Hoy: $${String.format("%.2f", totalHoy)}", fontWeight = FontWeight.Bold)
        }
        Spacer(Modifier.height(12.dp))
        Card(modifier = Modifier.fillMaxWidth()) {
            Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text("Nueva Venta", fontWeight = FontWeight.Bold, fontSize = 18.sp)

                // Selector Producto
                Box {
                    OutlinedButton(onClick = { showProdMenu = true }, modifier = Modifier.fillMaxWidth()) {
                        Text("Producto: ${producto.nombre}")
                    }
                    DropdownMenu(expanded = showProdMenu, onDismissRequest = { showProdMenu = false }) {
                        CatalogoFijo.productos.forEachIndexed { idx, p ->
                            DropdownMenuItem(text = { Text(p.nombre) }, onClick = { productoIdx = idx; showProdMenu = false })
                        }
                    }
                }

                // Selector Variante
                Box {
                    OutlinedButton(onClick = { showVarMenu = true }, modifier = Modifier.fillMaxWidth()) {
                        Text("${varianteSel.nombre} - $${varianteSel.precio}/kg")
                    }
                    DropdownMenu(expanded = showVarMenu, onDismissRequest = { showVarMenu = false }) {
                        producto.variantes.forEach { v ->
                            DropdownMenuItem(text = { Text("${v.nombre} - $${v.precio}") }, onClick = { varianteSel = v; showVarMenu = false })
                        }
                    }
                }

                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(value = kilos, onValueChange = { kilos = it }, label = { Text("Kilos") }, modifier = Modifier.weight(1f))
                    OutlinedTextField(value = "$${varianteSel.precio}", onValueChange = {}, readOnly = true, label = { Text("Precio") }, modifier = Modifier.weight(1f))
                }

                Text("Total: $${String.format("%.2f", totalCalc)}", fontSize = 22.sp, fontWeight = FontWeight.Bold)

                Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
                    OutlinedButton(onClick = {
                        if (kilosD <= 0) return@OutlinedButton
                        showTicketDialog = true
                    }, modifier = Modifier.weight(1f)) {
                        Text("Guardar Ticket")
                    }
                    Button(onClick = {
                        if (kilosD <= 0) return@Button
                        scope.launch {
                            db.ventaDao().insertar(Venta(producto = producto.nombre, variante = varianteSel.nombre, kilos = kilosD, precioKg = varianteSel.precio, total = totalCalc, tipo = "COBRADA"))
                            kilos = ""; cargar()
                        }
                    }, modifier = Modifier.weight(1f)) {
                        Text("Cobrar")
                    }
                }
            }
        }
        
        if (showTicketDialog) {
            AlertDialog(onDismissRequest = { showTicketDialog = false },
                title = { Text("Guardar Ticket") },
                text = {
                    Column {
                        Text("Cliente para cobrar después:")
                        Spacer(Modifier.height(8.dp))
                        OutlinedTextField(value = clienteTicket, onValueChange = { clienteTicket = it }, label = { Text("Nombre / Tienda") })
                        Text("Total: $${String.format("%.2f", totalCalc)} - ${producto.nombre} ${varianteSel.nombre}", modifier = Modifier.padding(top=8.dp))
                    }
                },
                confirmButton = {
                    Button(onClick = {
                        scope.launch {
                            db.ventaDao().insertar(Venta(producto = producto.nombre, variante = varianteSel.nombre, kilos = kilosD, precioKg = varianteSel.precio, total = totalCalc, tipo = "TICKET", cliente = clienteTicket))
                            clienteTicket = ""; kilos = ""; showTicketDialog = false; cargar()
                        }
                    }) { Text("Guardar") }
                },
                dismissButton = { TextButton(onClick = { showTicketDialog = false }) { Text("Cancelar") } }
            )
        }

        Spacer(Modifier.height(10.dp))
        Text("Historial (Cobros y Tickets):", fontWeight = FontWeight.Bold)
        LazyColumn(verticalArrangement = Arrangement.spacedBy(6.dp)) {
            items(ventas) { v ->
                Card(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = if(v.tipo=="TICKET") MaterialTheme.colorScheme.errorContainer else MaterialTheme.colorScheme.surfaceVariant)) {
                    Row(Modifier.padding(12.dp).fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Column { 
                            Text("${v.producto} ${v.variante} - ${v.kilos}kg", fontWeight = FontWeight.Bold)
                            Text(if(v.tipo=="TICKET") "TICKET: ${v.cliente}" else "COBRADA", fontSize = 12.sp)
                        }
                        Text("$${v.total}", fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}
