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
import com.tortilleria.erpv2.data.ItemCuenta
import com.tortilleria.erpv2.data.Ticket
import com.tortilleria.erpv2.data.TicketDetalle
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VentasScreen(onBack: () -> Unit, onVerHistorial: () -> Unit) {
    val context = LocalContext.current
    val db = remember { AppDatabase.getInstance(context) }
    val scope = rememberCoroutineScope()

    var catSel by remember { mutableStateOf("TORTILLA") }
    var cuenta by remember { mutableStateOf(listOf<ItemCuenta>()) }
    var totalHoy by remember { mutableStateOf(0.0) }
    
    var selVariante by remember { mutableStateOf<Pair<String, com.tortilleria.erpv2.data.ProductoVariante>?>(null) }
    var kilosInput by remember { mutableStateOf("") }
    var showKilosDialog by remember { mutableStateOf(false) }
    var showClienteDialog by remember { mutableStateOf(false) }
    var clienteTicket by remember { mutableStateOf("") }

    fun cargarTotal() { scope.launch { totalHoy = db.ticketDao().totalHoy() ?: 0.0 } }
    LaunchedEffect(Unit) { cargarTotal() }

    val totalCuenta = cuenta.sumOf { it.total }

    Column(Modifier.fillMaxSize().padding(12.dp)) {
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Button(onClick = onBack) { Text("← Menú") }
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedButton(onClick = onVerHistorial) { Text("Ver Tickets") }
                Text("Hoy: $${String.format("%.2f", totalHoy)}", fontWeight = FontWeight.Bold, modifier = Modifier.padding(top=8.dp))
            }
        }

        Spacer(Modifier.height(8.dp))

        Row(Modifier.fillMaxSize(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            // IZQUIERDA - INVENTARIO
            Card(Modifier.weight(1.7f).fillMaxHeight()) {
                Column(Modifier.padding(8.dp)) {
                    Text("Inventario", fontWeight = FontWeight.Bold)
                    Row(horizontalArrangement = Arrangement.spacedBy(6.dp), modifier = Modifier.padding(vertical=6.dp)) {
                        CatalogoFijo.categorias.forEach { cat ->
                            FilterChip(selected = catSel==cat, onClick = { catSel=cat }, label = { Text(cat) })
                        }
                    }
                    LazyColumn(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        items(CatalogoFijo.porCategoria(catSel)) { prod ->
                            Text(prod.nombre, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                            prod.variantes.forEach { v ->
                                ElevatedCard(onClick = { selVariante = prod.nombre to v; kilosInput=""; showKilosDialog=true }, modifier = Modifier.fillMaxWidth()) {
                                    Row(Modifier.padding(10.dp).fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                        Text(v.nombre); Text("$${v.precio}/kg", fontWeight = FontWeight.Bold)
                                    }
                                }
                            }
                            Spacer(Modifier.height(4.dp))
                        }
                    }
                }
            }

            // DERECHA - CUENTA
            Card(Modifier.weight(1f).fillMaxHeight()) {
                Column(Modifier.padding(8.dp).fillMaxHeight()) {
                    Text("Cuenta", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                    Divider()
                    if (cuenta.isEmpty()) {
                        Text("Toca un producto para agregar", modifier = Modifier.padding(20.dp))
                    } else {
                        LazyColumn(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                            items(cuenta) { item ->
                                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                    Column { Text("${item.producto} ${item.variante}", fontSize=12.sp, fontWeight=FontWeight.Bold); Text("${item.kilos}kg x $${item.precio}", fontSize=10.sp) }
                                    Text("$${String.format("%.2f", item.total)}", fontWeight=FontWeight.Bold)
                                }
                            }
                        }
                    }
                    Divider()
                    Text("Total: $${String.format("%.2f", totalCuenta)}", fontSize=20.sp, fontWeight=FontWeight.Bold, modifier=Modifier.padding(vertical=8.dp))
                    Button(onClick = {
                        if(cuenta.isEmpty()) return@Button
                        scope.launch {
                            val id = db.ticketDao().insertarTicket(Ticket(total=totalCuenta, tipo="COBRADO"))
                            db.ticketDao().insertarDetalles(cuenta.map { TicketDetalle(ticketId=id.toInt(), producto=it.producto, variante=it.variante, kilos=it.kilos, precioKg=it.precio, total=it.total) })
                            cuenta = listOf(); cargarTotal()
                        }
                    }, modifier=Modifier.fillMaxWidth()) { Text("Cobrar") }
                    OutlinedButton(onClick = { if(cuenta.isNotEmpty()) showClienteDialog=true }, modifier=Modifier.fillMaxWidth()) { Text("Guardar Ticket") }
                    TextButton(onClick = { cuenta = listOf() }, modifier=Modifier.fillMaxWidth()) { Text("Limpiar") }
                }
            }
        }
    }

    if (showKilosDialog) {
        AlertDialog(onDismissRequest = { showKilosDialog=false },
            title={ Text("${selVariante?.first} ${selVariante?.second?.nombre}") },
            text={
                OutlinedTextField(value=kilosInput, onValueChange={ kilosInput=it }, label={ Text("Kilos (a granel)") })
            },
            confirmButton={
                Button(onClick={
                    val k = kilosInput.toDoubleOrNull() ?: 0.0
                    if(k>0 && selVariante!=null){
                        val v = selVariante!!.second
                        cuenta = cuenta + ItemCuenta(selVariante!!.first, v.nombre, k, v.precio, k*v.precio)
                    }
                    showKilosDialog=false
                }){ Text("Agregar") }
            },
            dismissButton={ TextButton(onClick={showKilosDialog=false}){ Text("Cancelar") } }
        )
    }

    if (showClienteDialog) {
        AlertDialog(onDismissRequest = { showClienteDialog=false },
            title={ Text("Guardar por cobrar") },
            text={
                Column {
                    Text("Total: $${String.format("%.2f", totalCuenta)}")
                    OutlinedTextField(value=clienteTicket, onValueChange={clienteTicket=it}, label={ Text("Cliente") }, modifier=Modifier.padding(top=8.dp))
                }
            },
            confirmButton={
                Button(onClick={
                    scope.launch {
                        val id = db.ticketDao().insertarTicket(Ticket(total=totalCuenta, tipo="POR_COBRAR", cliente=clienteTicket))
                        db.ticketDao().insertarDetalles(cuenta.map { TicketDetalle(ticketId=id.toInt(), producto=it.producto, variante=it.variante, kilos=it.kilos, precioKg=it.precio, total=it.total) })
                        cuenta=listOf(); clienteTicket=""; showClienteDialog=false; cargarTotal()
                    }
                }){ Text("Guardar") }
            },
            dismissButton={ TextButton(onClick={showClienteDialog=false}){ Text("Cancelar") } }
        )
    }
}
