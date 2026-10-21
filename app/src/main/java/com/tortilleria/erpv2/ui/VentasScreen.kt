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
import com.tortilleria.erpv2.data.Venta
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VentasScreen(onBack: () -> Unit) {
    val context = LocalContext.current
    val db = remember { AppDatabase.getInstance(context) }
    val scope = rememberCoroutineScope()

    var cliente by remember { mutableStateOf("") }
    var kilosTortilla by remember { mutableStateOf("") }
    var kilosMasa by remember { mutableStateOf("") }
    var precio by remember { mutableStateOf("32") }
    var ventas by remember { mutableStateOf(listOf<Venta>()) }
    var totalHoy by remember { mutableStateOf(0.0) }

    fun cargar() {
        scope.launch {
            ventas = db.ventaDao().obtenerTodas()
            totalHoy = db.ventaDao().totalHoy() ?: 0.0
        }
    }

    LaunchedEffect(Unit) { cargar() }

    val totalCalc = (kilosTortilla.toDoubleOrNull() ?: 0.0 + (kilosMasa.toDoubleOrNull() ?: 0.0)) * (precio.toDoubleOrNull() ?: 0.0)

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Button(onClick = onBack) { Text("← Menú") }
            Text("Ventas Hoy: $${String.format("%.2f", totalHoy)}", fontWeight = FontWeight.Bold, fontSize = 16.sp)
        }
        Spacer(Modifier.height(12.dp))
        Card(modifier = Modifier.fillMaxWidth()) {
            Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text("Nueva Venta", fontWeight = FontWeight.Bold)
                OutlinedTextField(value = cliente, onValueChange = { cliente = it }, label = { Text("Cliente / Tienda") }, modifier = Modifier.fillMaxWidth())
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(value = kilosTortilla, onValueChange = { kilosTortilla = it }, label = { Text("Kg Tortilla") }, modifier = Modifier.weight(1f))
                    OutlinedTextField(value = kilosMasa, onValueChange = { kilosMasa = it }, label = { Text("Kg Masa") }, modifier = Modifier.weight(1f))
                }
                OutlinedTextField(value = precio, onValueChange = { precio = it }, label = { Text("Precio x Kg") }, modifier = Modifier.fillMaxWidth())
                Text("Total: $${String.format("%.2f", totalCalc)}", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                Button(onClick = {
                    if (cliente.isBlank()) return@Button
                    scope.launch {
                        db.ventaDao().insertar(Venta(cliente = cliente, kilosTortilla = kilosTortilla.toDoubleOrNull()?:0.0, kilosMasa = kilosMasa.toDoubleOrNull()?:0.0, precioKg = precio.toDoubleOrNull()?:0.0, total = totalCalc))
                        cliente = ""; kilosTortilla = ""; kilosMasa = ""
                        cargar()
                    }
                }, modifier = Modifier.fillMaxWidth()) { Text("Guardar Venta") }
            }
        }
        Spacer(Modifier.height(12.dp))
        Text("Historial:", fontWeight = FontWeight.Bold)
        LazyColumn(verticalArrangement = Arrangement.spacedBy(6.dp)) {
            items(ventas) { v ->
                Card(modifier = Modifier.fillMaxWidth()) {
                    Row(Modifier.padding(12.dp).fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Column { Text(v.cliente, fontWeight = FontWeight.Bold); Text("${v.kilosTortilla}kg tort + ${v.kilosMasa}kg masa") }
                        Text("$${v.total}", fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}
