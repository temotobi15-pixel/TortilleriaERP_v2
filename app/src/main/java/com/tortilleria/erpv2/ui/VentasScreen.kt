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

    var cliente by remember { mutableStateOf("") }
    var kilos by remember { mutableStateOf("") }
    var ventas by remember { mutableStateOf(listOf<Venta>()) }
    var totalHoy by remember { mutableStateOf(0.0) }

    val producto = CatalogoFijo.productos[0]
    var varianteSeleccionada by remember { mutableStateOf(producto.variantes[0]) }
    var showMenu by remember { mutableStateOf(false) }

    fun cargar() {
        scope.launch {
            ventas = db.ventaDao().obtenerTodas()
            totalHoy = db.ventaDao().totalHoy() ?: 0.0
        }
    }
    LaunchedEffect(Unit) { cargar() }

    val kilosD = kilos.toDoubleOrNull() ?: 0.0
    val totalCalc = kilosD * varianteSeleccionada.precio

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Button(onClick = onBack) { Text("← Menú") }
            Text("Hoy: $${String.format("%.2f", totalHoy)}", fontWeight = FontWeight.Bold)
        }
        Spacer(Modifier.height(12.dp))
        Card(modifier = Modifier.fillMaxWidth()) {
            Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text("Nueva Venta - ${producto.nombre}", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                OutlinedTextField(value = cliente, onValueChange = { cliente = it }, label = { Text("Cliente / Tienda") }, modifier = Modifier.fillMaxWidth())

                Box {
                    OutlinedButton(onClick = { showMenu = true }, modifier = Modifier.fillMaxWidth()) {
                        Text("${varianteSeleccionada.nombre} - $${varianteSeleccionada.precio}/kg")
                    }
                    DropdownMenu(expanded = showMenu, onDismissRequest = { showMenu = false }) {
                        producto.variantes.forEach { v ->
                            DropdownMenuItem(text = { Text("${v.nombre} - $${v.precio}") }, onClick = { varianteSeleccionada = v; showMenu = false })
                        }
                    }
                }

                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(value = kilos, onValueChange = { kilos = it }, label = { Text("Kilos") }, modifier = Modifier.weight(1f))
                    OutlinedTextField(value = "$${varianteSeleccionada.precio}", onValueChange = {}, readOnly = true, label = { Text("Precio") }, modifier = Modifier.weight(1f))
                }

                Text("Total: $${String.format("%.2f", totalCalc)}", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                Button(onClick = {
                    if (cliente.isBlank() || kilosD <= 0) return@Button
                    scope.launch {
                        db.ventaDao().insertar(Venta(cliente = cliente, producto = producto.nombre, variante = varianteSeleccionada.nombre, kilos = kilosD, precioKg = varianteSeleccionada.precio, total = totalCalc))
                        cliente = ""; kilos = ""; cargar()
                    }
                }, modifier = Modifier.fillMaxWidth()) { Text("Guardar Venta") }
            }
        }
        Spacer(Modifier.height(10.dp))
        Text("Historial:", fontWeight = FontWeight.Bold)
        LazyColumn(verticalArrangement = Arrangement.spacedBy(6.dp)) {
            items(ventas) { v ->
                Card(modifier = Modifier.fillMaxWidth()) {
                    Row(Modifier.padding(12.dp).fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Column { Text(v.cliente, fontWeight = FontWeight.Bold); Text("${v.variante} - ${v.kilos}kg") }
                        Text("$${v.total}", fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}
