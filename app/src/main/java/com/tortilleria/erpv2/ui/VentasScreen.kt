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
    val tipo: String,
    val cliente: String = "Turi"
)

object VentasRepo {
    val historial = mutableStateListOf<VentaHistorial>()
    var totalHoy by mutableStateOf(0.0)
    fun tickets() = historial.filter { it.tipo.startsWith("TICKET") }
    fun cobradas() = historial.filter { it.tipo == "COBRADA" }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VentasScreen(onBack: () -> Unit, onVerHistorial: () -> Unit) {
    val catalogo = InventarioRepo.productos.filter { it.apareceEnCatalogo }
    // Agrupa por categoria si existe, si no todo a General
    val categorias = catalogo.groupBy { 
        try { (it as? Any)?.let { p -> 
            val f = p.javaClass.getDeclaredField("categoria"); f.isAccessible=true; f.get(p) as? String
        } ?: "General" } catch (e: Exception) { "General" }
    }

    var producto by remember { mutableStateOf(catalogo.firstOrNull()) }
    var variante by remember { mutableStateOf(producto?.variantes?.firstOrNull()) }
    var kilosTxt by remember { mutableStateOf("") }
    var precioTxt by remember { mutableStateOf((variante?.precio ?: producto?.precioVenta ?: 0.0).toString()) }
    var tab by remember { mutableStateOf(0) }

    val kilos = kilosTxt.toDoubleOrNull() ?: 0.0
    val precio = precioTxt.toDoubleOrNull() ?: (variante?.precio ?: producto?.precioVenta ?: 0.0)
    val total = kilos * precio

    LaunchedEffect(producto) {
        variante = producto?.variantes?.firstOrNull()
        precioTxt = (variante?.precio ?: producto?.precioVenta ?: 0.0).toString()
    }
    LaunchedEffect(variante) {
        precioTxt = (variante?.precio ?: producto?.precioVenta ?: 0.0).toString()
    }

    if (catalogo.isEmpty()) {
        Column(Modifier.fillMaxSize().padding(16.dp)) {
            Button(onClick = onBack) { Text("← Menú") }
            Text("Activa el check 'Aparece en catálogo' en Inventario", modifier = Modifier.padding(top = 16.dp))
        }
        return
    }

    LazyColumn(Modifier.fillMaxSize().padding(12.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        item {
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Button(onClick = onBack) { Text("← Menú") }
                Text("Hoy: $${VentasRepo.totalHoy}", fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 10.dp))
            }
            Text("Tortilleria ERP v2", fontWeight = FontWeight.Bold, fontSize = 22.sp, modifier = Modifier.padding(top = 8.dp))
        }

        item {
            Card(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant), modifier = Modifier.fillMaxWidth()) {
                Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text("Nueva Venta", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                    if (producto != null) {
                        Text("Producto: ${producto!!.nombre} ${variante?.nombre ?: ""} - $${precio}/kg", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = MaterialTheme.colorScheme.primary)
                    }
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        OutlinedTextField(value = kilosTxt, onValueChange = { kilosTxt = it.replace(",", ".") }, label = { Text("Kilos") }, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number), modifier = Modifier.weight(1f))
                        OutlinedTextField(value = precioTxt, onValueChange = { precioTxt = it.replace("$", "").replace(",", ".") }, label = { Text("Precio") }, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number), modifier = Modifier.weight(1f))
                    }
                    Text("Total: $${"%.2f".format(total)}", fontWeight = FontWeight.Bold, fontSize = 22.sp)
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
                        OutlinedButton(onClick = {
                            if (kilos > 0 && producto != null) {
                                VentasRepo.historial.add(0, VentaHistorial("${producto!!.nombre} ${variante?.nombre ?: ""} - ${kilos}kg", kilos, total, "TICKET: ${producto!!.nombre}"))
                                kilosTxt = ""
                            }
                        }, modifier = Modifier.weight(1f)) { Text("Guardar Ticket") }
                        Button(onClick = {
                            if (kilos > 0 && producto != null) {
                                VentasRepo.historial.add(0, VentaHistorial("${producto!!.nombre} ${variante?.nombre ?: ""} - ${kilos}kg", kilos, total, "COBRADA"))
                                VentasRepo.totalHoy += total
                                val idx = InventarioRepo.productos.indexOfFirst { it.nombre == producto!!.nombre }
                                if (idx >= 0) {
                                    val act = InventarioRepo.productos[idx]
                                    InventarioRepo.productos[idx] = act.copy(stock = (act.stock - kilos).coerceAtLeast(0.0))
                                }
                                kilosTxt = ""
                            }
                        }, modifier = Modifier.weight(1f)) { Text("Cobrar") }
                    }
                }
            }
        }

        // GRID POR CATEGORIAS - 2 columnas
        categorias.forEach { (cat, prods) ->
            item { Text(cat.uppercase(), fontWeight = FontWeight.Bold, fontSize = 14.sp, modifier = Modifier.padding(top = 8.dp)) }
            item {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    prods.chunked(2).forEach { fila ->
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
                            fila.forEach { p ->
                                ElevatedCard(
                                    onClick = { producto = p; variante = p.variantes.firstOrNull() },
                                    modifier = Modifier.weight(1f),
                                    colors = CardDefaults.elevatedCardColors(containerColor = if (producto?.nombre == p.nombre) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surface)
                                ) {
                                    Column(Modifier.padding(10.dp)) {
                                        Text(p.nombre, fontWeight = FontWeight.Bold, fontSize = 13.sp, maxLines = 1)
                                        if (p.variantes.isNotEmpty()) {
                                            p.variantes.take(3).forEach { v ->
                                                FilterChip(
                                                    selected = producto?.nombre == p.nombre && variante?.nombre == v.nombre,
                                                    onClick = { producto = p; variante = v },
                                                    label = { Text("${v.nombre} $${v.precio}", fontSize = 11.sp) },
                                                    modifier = Modifier.padding(top = 2.dp)
                                                )
                                            }
                                        } else {
                                            Text("$${p.precioVenta}/kg", fontSize = 11.sp)
                                        }
                                    }
                                }
                            }
                            if (fila.size == 1) Spacer(Modifier.weight(1f))
                        }
                    }
                }
            }
        }

        item {
            TabRow(selectedTabIndex = tab) {
                Tab(selected = tab == 0, onClick = { tab = 0 }, text = { Text("Tickets (${VentasRepo.tickets().size})") })
                Tab(selected = tab == 1, onClick = { tab = 1 }, text = { Text("Cobrados (${VentasRepo.cobradas().size})") })
            }
            Spacer(Modifier.height(8.dp))
            val lista = if (tab == 0) VentasRepo.tickets() else VentasRepo.cobradas()
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                lista.forEach { v ->
                    Card(
                        colors = CardDefaults.cardColors(containerColor = if (tab == 0) MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.6f) else MaterialTheme.colorScheme.surfaceVariant),
                        modifier = Modifier.fillMaxWidth(),
                        onClick = {
                            if (tab == 0) {
                                VentasRepo.historial.remove(v)
                                VentasRepo.historial.add(0, v.copy(tipo = "COBRADA"))
                                VentasRepo.totalHoy += v.total
                            }
                        }
                    ) {
                        Row(Modifier.padding(12.dp).fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Column(Modifier.weight(1f)) {
                                Text(v.nombre, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                                Text(if (tab == 0) "TICKET - Toca para cobrar" else "COBRADA", fontSize = 11.sp)
                            }
                            Text("$${v.total}", fontWeight = FontWeight.Bold)
                        }
                    }
                }
                if (lista.isEmpty()) Text(if (tab == 0) "No hay tickets guardados" else "No hay cobros hoy", fontSize = 12.sp, modifier = Modifier.padding(8.dp))
            }
        }
    }
}
