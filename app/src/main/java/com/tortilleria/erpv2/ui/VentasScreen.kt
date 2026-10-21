package com.tortilleria.erpv2.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
    val precioUnit: Double,
    val total: Double = cantidad * precioUnit,
    val extras: List<String> = emptyList()
)

object VentasRepo {
    val historialTickets = mutableStateListOf<List<TicketItem>>()
    val historialCobros = mutableStateListOf<List<TicketItem>>()
    var totalHoy by mutableStateOf(0.0)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VentasScreen(onBack: () -> Unit, onVerHistorial: () -> Unit) {
    val catalogo = InventarioRepo.productos.filter { it.apareceEnCatalogo }
    val categorias = catalogo.groupBy { 
        try { it.javaClass.getDeclaredField("categoria").let { f-> f.isAccessible=true; (f.get(it) as? String)?.ifBlank{ "Tortilleria" } ?: "Tortilleria" } } 
        catch(e:Exception){ "Tortilleria" } 
    }.toMutableMap().apply { 
        // categorias fijas de tu tablet para que se vea igual
        if (!containsKey("Lo más frecuente")) put("Lo más frecuente", catalogo.take(10))
    }
    var categoriaSel by remember { mutableStateOf("Lo más frecuente") }
    var carrito by remember { mutableStateOf(listOf<TicketItem>()) }
    
    // flujo de seleccion
    var prodSel by remember { mutableStateOf<com.tortilleria.erpv2.data.Insumo?>(null) }
    var showCantidad by remember { mutableStateOf(false) }
    var showVariantes by remember { mutableStateOf(false) }
    var cantidadTmp by remember { mutableStateOf(1.0) }
    var varianteTmp by remember { mutableStateOf<com.tortilleria.erpv2.data.VarianteProducto?>(null) }
    var extrasTmp by remember { mutableStateOf(listOf<String>()) }

    // flujo de cobro
    var showCobro by remember { mutableStateOf(false) }
    var efectivoRecibido by remember { mutableStateOf("") }
    var cobroFinalizado by remember { mutableStateOf(false) }

    val totalCarrito = carrito.sumOf { it.total }

    if (catalogo.isEmpty()) {
        Column(Modifier.fillMaxSize().padding(16.dp)){ Button(onClick=onBack){ Text("← Menú") }; Text("Activa productos en Inventario") }
        return
    }

    Row(Modifier.fillMaxSize()) {
        // IZQUIERDA - CUADRICULA COMO TU TABLET
        Column(Modifier.weight(0.65f).fillMaxHeight()) {
            // header verde
            Surface(color = MaterialTheme.colorScheme.primary, modifier = Modifier.fillMaxWidth()) {
                Row(Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
                    Row(verticalAlignment = Alignment.CenterVertically) { 
                        IconButton(onClick=onBack){ Text("☰", color = MaterialTheme.colorScheme.onPrimary) }
                        Text(categoriaSel, color = MaterialTheme.colorScheme.onPrimary, fontWeight = FontWeight.Bold)
                    }
                    Text("Hoy: $${VentasRepo.totalHoy}", color = MaterialTheme.colorScheme.onPrimary, fontSize = 12.sp)
                }
            }
            // grid
            val listaCat = categorias[categoriaSel] ?: catalogo
            LazyVerticalGrid(columns = GridCells.Fixed(3), modifier = Modifier.weight(1f).padding(4.dp), verticalArrangement = Arrangement.spacedBy(4.dp), horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                items(listaCat) { p ->
                    ElevatedCard(onClick = {
                        prodSel = p
                        cantidadTmp = 1.0
                        varianteTmp = p.variantes.firstOrNull()
                        extrasTmp = emptyList()
                        showCantidad = true
                    }, modifier = Modifier.height(90.dp)) {
                        Box(Modifier.fillMaxSize().padding(6.dp), contentAlignment = Alignment.BottomStart) {
                            Text(p.nombre, fontWeight = FontWeight.Bold, fontSize = 12.sp, maxLines = 2)
                        }
                    }
                }
            }
            // paginacion categorias abajo como tu foto
            ScrollableTabRow(selectedTabIndex = categorias.keys.indexOf(categoriaSel).coerceAtLeast(0)) {
                categorias.keys.forEach { cat ->
                    Tab(selected = cat == categoriaSel, onClick = { categoriaSel = cat }, text = { Text(cat, fontSize = 11.sp) })
                }
            }
        }

        // DERECHA - TICKET
        Column(Modifier.weight(0.35f).fillMaxHeight().padding(8.dp)) {
            Text("Ticket", fontWeight = FontWeight.Bold, fontSize = 18.sp)
            Text("Venta en Mostrador", fontSize = 12.sp, color = MaterialTheme.colorScheme.secondary)
            Divider(Modifier.padding(vertical = 6.dp))
            LazyColumn(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                items(carrito.size) { idx ->
                    val it = carrito[idx]
                    Column {
                        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text("${it.productoNombre} x ${it.cantidad}", fontSize = 12.sp, fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
                            Text("$${it.total}", fontSize = 12.sp)
                        }
                        if (it.varianteNombre.isNotBlank()) Text(it.varianteNombre, fontSize = 11.sp)
                        it.extras.forEach { ex -> Text(ex, fontSize = 11.sp) }
                    }
                }
            }
            Divider()
            Row(Modifier.fillMaxWidth().padding(vertical = 8.dp), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("Total", fontWeight = FontWeight.Bold); Text("$${"%.2f".format(totalCarrito)}", fontWeight = FontWeight.Bold)
            }
            Row(horizontalArrangement = Arrangement.spacedBy(6.dp), modifier = Modifier.fillMaxWidth()) {
                OutlinedButton(onClick = {
                    if (carrito.isNotEmpty()) { VentasRepo.historialTickets.add(carrito); carrito = emptyList() }
                }, modifier = Modifier.weight(1f)) { Text("GUARDAR", fontSize = 12.sp) }
                Button(onClick = { if (carrito.isNotEmpty()) { efectivoRecibido = totalCarrito.toString(); cobroFinalizado = false; showCobro = true } }, modifier = Modifier.weight(1f), colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)) {
                    Text("COBRAR", fontSize = 12.sp)
                }
            }
        }
    }

    // DIALOG CANTIDAD - como foto 2
    if (showCantidad && prodSel != null) {
        AlertDialog(onDismissRequest = { showCantidad = false },
            title = { Text(prodSel!!.nombre) },
            text = {
                Column {
                    Text("Cantidad", fontWeight = FontWeight.Bold)
                    Text("${cantidadTmp}", fontSize = 32.sp, modifier = Modifier.align(Alignment.End).padding(12.dp))
                    // teclado numerico
                    val keys = listOf(listOf("1","2","3"), listOf("4","5","6"), listOf("7","8","9"), listOf(".","0","⌫"))
                    keys.forEach { row ->
                        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                            row.forEach { k ->
                                TextButton(onClick = {
                                    if (k == "⌫") { val s = cantidadTmp.toString(); cantidadTmp = s.dropLast(1).toDoubleOrNull() ?: 0.0 }
                                    else { val s = if (cantidadTmp == 0.0) "" else cantidadTmp.toString().removeSuffix(".0"); val ns = (s + k).toDoubleOrNull(); if (ns != null) cantidadTmp = ns }
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

    // DIALOG VARIANTES - como foto 3
    if (showVariantes && prodSel != null) {
        AlertDialog(onDismissRequest = { showVariantes = false },
            title = { Text("${prodSel!!.nombre} $${varianteTmp?.precio ?: prodSel!!.precioVenta}") },
            text = {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    item { Text("Variantes", color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold, fontSize = 12.sp) }
                    item {
                        val vars = prodSel!!.variantes
                        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                            vars.chunked(2).forEach { fila ->
                                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                    fila.forEach { v ->
                                        OutlinedCard(onClick = { varianteTmp = v }, modifier = Modifier.weight(1f), colors = CardDefaults.outlinedCardColors(containerColor = if (varianteTmp?.nombre == v.nombre) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surface)) {
                                            Row(Modifier.padding(10.dp).fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                                Text(v.nombre, fontSize = 12.sp); Text("$${v.precio}", fontSize = 12.sp)
                                            }
                                        }
                                    }
                                    if (fila.size == 1) Spacer(Modifier.weight(1f))
                                }
                            }
                        }
                    }
                    item { Text("Extras / Papel", color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold, fontSize = 12.sp, modifier = Modifier.padding(top=8.dp)) }
                    item {
                        val extras = catalogo.filter { it.nombre.contains("Papel", true) || it.nombre.contains("Bolsa", true) }
                        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                            extras.take(6).chunked(2).forEach { fila ->
                                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                    fila.forEach { ex ->
                                        FilterChip(selected = extrasTmp.contains(ex.nombre), onClick = {
                                            extrasTmp = if (extrasTmp.contains(ex.nombre)) extrasTmp - ex.nombre else extrasTmp + ex.nombre
                                        }, label = { Text("${ex.nombre} $${ex.precioVenta}", fontSize = 11.sp) }, modifier = Modifier.weight(1f))
                                    }
                                    if (fila.size == 1) Spacer(Modifier.weight(1f))
                                }
                            }
                        }
                    }
                    item {
                        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                            IconButton(onClick = { if (cantidadTmp > 1) cantidadTmp-- }) { Text("−") }
                            Text("%.3f".format(cantidadTmp))
                            IconButton(onClick = { cantidadTmp++ }) { Text("+") }
                        }
                    }
                }
            },
            confirmButton = {
                Button(onClick = {
                    val precio = varianteTmp?.precio ?: prodSel!!.precioVenta
                    val item = TicketItem(prodSel!!.nombre, varianteTmp?.nombre ?: "", cantidadTmp, precio, cantidadTmp * precio, extrasTmp)
                    carrito = carrito + item
                    showVariantes = false
                }) { Text("GUARDAR") }
            },
            dismissButton = { TextButton(onClick = { showVariantes = false }) { Text("X") } }
        )
    }

    // DIALOG COBRO - como fotos 4 y 5
    if (showCobro) {
        val total = totalCarrito
        val efectivo = efectivoRecibido.toDoubleOrNull() ?: 0.0
        val cambio = (efectivo - total).coerceAtLeast(0.0)
        AlertDialog(onDismissRequest = { showCobro = false },
            title = { if (!cobroFinalizado) Text("$${"%.2f".format(total)}", fontSize = 28.sp, fontWeight = FontWeight.Bold) else Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) { Column{ Text("$${"%.2f".format(efectivo)}", fontWeight = FontWeight.Bold); Text("Total pagado", fontSize = 11.sp) }; Column{ Text("$${"%.2f".format(cambio)}", fontWeight = FontWeight.Bold); Text("Cambio", fontSize = 11.sp) } } },
            text = {
                if (!cobroFinalizado) {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        if (total > 0) Text("Importe total adeudado", fontSize = 12.sp)
                        OutlinedTextField(value = efectivoRecibido, onValueChange = { efectivoRecibido = it.replace(",",".") }, label = { Text("Efectivo recibido") }, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number), modifier = Modifier.fillMaxWidth())
                        Row(horizontalArrangement = Arrangement.spacedBy(6.dp), modifier = Modifier.fillMaxWidth()) {
                            listOf(40.0,50.0,100.0,200.0).forEach { v ->
                                OutlinedButton(onClick = { efectivoRecibido = v.toString() }, modifier = Modifier.weight(1f)) { Text("$${v.toInt()}.00", fontSize = 11.sp) }
                            }
                        }
                        OutlinedButton(onClick = { /* tarjeta */ }, modifier = Modifier.fillMaxWidth()) { Text("POR TARJETA") }
                    }
                } else {
                    Column { Text("¡Cobro listo!", fontWeight = FontWeight.Bold) }
                }
            },
            confirmButton = {
                if (!cobroFinalizado) {
                    Button(onClick = { if (efectivo >= total) cobroFinalizado = true }) { Text("COBRAR") }
                } else {
                    Button(onClick = {
                        // guarda cobro, descuenta stock, limpia
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
                        cobroFinalizado = false
                    }, modifier = Modifier.fillMaxWidth()) { Text("✓ NUEVA VENTA") }
                }
            },
            dismissButton = { TextButton(onClick = { showCobro = false }) { Text("Cerrar") } }
        )
    }
}
