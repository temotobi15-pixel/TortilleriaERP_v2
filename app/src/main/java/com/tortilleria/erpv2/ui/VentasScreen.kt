package com.tortilleria.erpv2.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tortilleria.erpv2.data.InventarioRepo

data class TicketItem(
    val productoNombre: String,
    val varianteNombre: String,
    val cantidad: Double,
    val precioUnit: Double
) {
    val total: Double get() = cantidad * precioUnit
}

object VentasRepo {
    val historialTickets = mutableStateListOf<List<TicketItem>>()
    val historialCobros = mutableStateListOf<List<TicketItem>>()
    var totalHoy by mutableStateOf(0.0)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VentasScreen(onBack: () -> Unit, onVerHistorial: () -> Unit) {
    val catalogo = InventarioRepo.productos.filter { it.apareceEnCatalogo }
    var categoriaSel by remember { mutableStateOf("Lo más frecuente") }
    var carrito by remember { mutableStateOf(listOf<TicketItem>()) }

    var prodSel by remember { mutableStateOf(catalogo.firstOrNull()) }
    var varianteSel by remember { mutableStateOf(prodSel?.variantes?.firstOrNull()) }
    var cantidadTmp by remember { mutableStateOf(1.0) }
    var showCantidad by remember { mutableStateOf(false) }
    var showVariantes by remember { mutableStateOf(false) }

    var showCobro by remember { mutableStateOf(false) }
    var efectivoTxt by remember { mutableStateOf("") }
    var cobroOk by remember { mutableStateOf(false) }

    val totalCarrito = carrito.sumOf { it.total }

    if (catalogo.isEmpty()) {
        Column(Modifier.fillMaxSize().padding(16.dp)) {
            Button(onClick = onBack) { Text("← Menú") }
            Text("Activa check 'Aparece en catálogo' en Inventario")
        }
        return
    }

    // Lista visible = todo el catalogo por ahora para asegurar build, categorias solo visual
    val listaVisible = catalogo
    val categoriasLista = listOf("Lo más frecuente", "Tortilleria", "Mostrador", "Granel y Otros", "Dulces y Botanas", "Frutas y Verduras")

    Row(Modifier.fillMaxSize()) {
        Column(Modifier.weight(0.62f).fillMaxHeight()) {
            Surface(color = MaterialTheme.colorScheme.primary, modifier = Modifier.fillMaxWidth()) {
                Row(Modifier.padding(10.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(categoriaSel, color = MaterialTheme.colorScheme.onPrimary, fontWeight = FontWeight.Bold)
                    Text("Hoy: $${VentasRepo.totalHoy}", color = MaterialTheme.colorScheme.onPrimary, fontSize = 12.sp)
                }
            }
            LazyVerticalGrid(columns = GridCells.Fixed(3), modifier = Modifier.weight(1f).padding(4.dp), verticalArrangement = Arrangement.spacedBy(4.dp), horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                items(listaVisible) { p ->
                    ElevatedCard(onClick = {
                        prodSel = p
                        varianteSel = p.variantes.firstOrNull()
                        cantidadTmp = 1.0
                        showCantidad = true
                    }, modifier = Modifier.height(88.dp)) {
                        Box(Modifier.fillMaxSize().padding(6.dp), contentAlignment = Alignment.BottomStart) {
                            Text(p.nombre, fontWeight = FontWeight.Bold, fontSize = 11.sp, maxLines = 2)
                        }
                    }
                }
            }
            ScrollableTabRow(selectedTabIndex = categoriasLista.indexOf(categoriaSel).coerceAtLeast(0)) {
                categoriasLista.forEach { cat ->
                    Tab(selected = cat == categoriaSel, onClick = { categoriaSel = cat }, text = { Text(cat, fontSize = 10.sp) })
                }
            }
        }

        Column(Modifier.weight(0.38f).fillMaxHeight().padding(8.dp)) {
            Text("Ticket", fontWeight = FontWeight.Bold, fontSize = 16.sp)
            Text("Venta en Mostrador", fontSize = 11.sp)
            Divider(Modifier.padding(vertical = 4.dp))
            LazyColumn(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                items(carrito.size) { i ->
                    val it = carrito[i]
                    Column {
                        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text("${it.productoNombre} x ${it.cantidad}", fontSize = 11.sp, fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
                            Text("$${"%.0f".format(it.total)}", fontSize = 11.sp)
                        }
                        if (it.varianteNombre.isNotBlank()) Text(it.varianteNombre, fontSize = 10.sp)
                    }
                }
            }
            Divider()
            Row(Modifier.fillMaxWidth().padding(vertical = 6.dp), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("Total", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                Text("$${"%.2f".format(totalCarrito)}", fontWeight = FontWeight.Bold, fontSize = 14.sp)
            }
            Row(horizontalArrangement = Arrangement.spacedBy(4.dp), modifier = Modifier.fillMaxWidth()) {
                OutlinedButton(onClick = { if (carrito.isNotEmpty()) { VentasRepo.historialTickets.add(carrito); carrito = emptyList() } }, modifier = Modifier.weight(1f)) { Text("GUARDAR", fontSize = 10.sp) }
                Button(onClick = { if (carrito.isNotEmpty()) { efectivoTxt = totalCarrito.toString(); cobroOk = false; showCobro = true } }, modifier = Modifier.weight(1f)) { Text("COBRAR", fontSize = 10.sp) }
            }
        }
    }

    if (showCantidad && prodSel != null) {
        AlertDialog(onDismissRequest = { showCantidad = false },
            title = { Text(prodSel!!.nombre) },
            text = {
                Column {
                    Text("Cantidad", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                    Text("%.3f".format(cantidadTmp), fontSize = 28.sp, modifier = Modifier.align(Alignment.End).padding(8.dp))
                    val filas = listOf(listOf("1","2","3"), listOf("4","5","6"), listOf("7","8","9"), listOf("0",".","⌫"))
                    filas.forEach { fila ->
                        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                            fila.forEach { k ->
                                TextButton(onClick = {
                                    if (k == "⌫") {
                                        val s = cantidadTmp.toString()
                                        cantidadTmp = if (s.length <= 1) 0.0 else s.dropLast(1).toDoubleOrNull() ?: 0.0
                                    } else {
                                        val cur = if (cantidadTmp == 0.0) "" else cantidadTmp.toString().trimEnd('0').trimEnd('.')
                                        val nuevo = (cur + k).toDoubleOrNull()
                                        if (nuevo != null) cantidadTmp = nuevo
                                    }
                                }) { Text(k, fontSize = 20.sp) }
                            }
                        }
                    }
                }
            },
            confirmButton = { TextButton(onClick = { showCantidad = false; showVariantes = true }) { Text("ACEPTAR") } },
            dismissButton = { TextButton(onClick = { showCantidad = false }) { Text("X") } }
        )
    }

    if (showVariantes && prodSel != null) {
        val p = prodSel!!
        AlertDialog(onDismissRequest = { showVariantes = false },
            title = { Text("${p.nombre} $${varianteSel?.precio ?: p.precioVenta}") },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("Variantes", color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                    p.variantes.chunked(2).forEach { fila ->
                        Row(horizontalArrangement = Arrangement.spacedBy(6.dp), modifier = Modifier.fillMaxWidth()) {
                            fila.forEach { v ->
                                OutlinedCard(onClick = { varianteSel = v }, modifier = Modifier.weight(1f), colors = CardDefaults.outlinedCardColors(containerColor = if (varianteSel?.nombre == v.nombre) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surface)) {
                                    Row(Modifier.padding(8.dp).fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                        Text(v.nombre, fontSize = 11.sp); Text("$${v.precio}", fontSize = 11.sp)
                                    }
                                }
                            }
                            if (fila.size == 1) Spacer(Modifier.weight(1f))
                        }
                    }
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth().padding(top = 8.dp)) {
                        IconButton(onClick = { if (cantidadTmp > 1) cantidadTmp-- }) { Text("−") }
                        Text("%.3f".format(cantidadTmp))
                        IconButton(onClick = { cantidadTmp++ }) { Text("+") }
                    }
                }
            },
            confirmButton = {
                Button(onClick = {
                    val precio = varianteSel?.precio ?: p.precioVenta
                    val item = TicketItem(p.nombre, varianteSel?.nombre ?: "", cantidadTmp, precio)
                    carrito = carrito + item
                    showVariantes = false
                }) { Text("GUARDAR") }
            },
            dismissButton = { TextButton(onClick = { showVariantes = false }) { Text("X") } }
        )
    }

    if (showCobro) {
        val total = totalCarrito
        val efectivo = efectivoTxt.toDoubleOrNull() ?: 0.0
        val cambio = (efectivo - total).coerceAtLeast(0.0)
        AlertDialog(onDismissRequest = { showCobro = false },
            title = {
                if (!cobroOk) Text("$${"%.2f".format(total)}", fontWeight = FontWeight.Bold, fontSize = 26.sp)
                else Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Column { Text("$${"%.2f".format(efectivo)}", fontWeight = FontWeight.Bold, fontSize = 18.sp); Text("Total pagado", fontSize = 10.sp) }
                    Column { Text("$${"%.2f".format(cambio)}", fontWeight = FontWeight.Bold, fontSize = 18.sp); Text("Cambio", fontSize = 10.sp) }
                }
            },
            text = {
                if (!cobroOk) {
                    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        Text("Importe total adeudado", fontSize = 11.sp)
                        OutlinedTextField(value = efectivoTxt, onValueChange = { efectivoTxt = it.replace(",", ".") }, label = { Text("Efectivo recibido") }, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number), modifier = Modifier.fillMaxWidth())
                        Row(horizontalArrangement = Arrangement.spacedBy(4.dp), modifier = Modifier.fillMaxWidth()) {
                            listOf(40.0, 50.0, 100.0, 200.0).forEach { v ->
                                OutlinedButton(onClick = { efectivoTxt = v.toString() }, modifier = Modifier.weight(1f)) { Text("$${v.toInt()}", fontSize = 10.sp) }
                            }
                        }
                        OutlinedButton(onClick = {}, modifier = Modifier.fillMaxWidth()) { Text("POR TARJETA") }
                    }
                } else { Text("¡Listo para nueva venta!") }
            },
            confirmButton = {
                if (!cobroOk) {
                    Button(onClick = { if (efectivo >= total) cobroOk = true }) { Text("COBRAR") }
                } else {
                    Button(onClick = {
                        VentasRepo.historialCobros.add(carrito)
                        VentasRepo.totalHoy += total
                        carrito.forEach { ti ->
                            val idx = InventarioRepo.productos.indexOfFirst { it.nombre == ti.productoNombre }
                            if (idx >= 0) {
                                val act = InventarioRepo.productos[idx]
                                InventarioRepo.productos[idx] = act.copy(stock = (act.stock - ti.cantidad).coerceAtLeast(0.0))
                            }
                        }
                        carrito = emptyList()
                        showCobro = false
                        cobroOk = false
                    }, modifier = Modifier.fillMaxWidth()) { Text("✓ NUEVA VENTA") }
                }
            },
            dismissButton = { TextButton(onClick = { showCobro = false }) { Text("Cerrar") } }
        )
    }
}
