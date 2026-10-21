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
import com.tortilleria.erpv2.data.AppDatabase
import com.tortilleria.erpv2.data.Ticket
import kotlinx.coroutines.launch

@Composable
fun HistorialScreen(onBack: () -> Unit) {
    val context = LocalContext.current
    val db = remember { AppDatabase.getInstance(context) }
    val scope = rememberCoroutineScope()
    var tab by remember { mutableStateOf(0) }
    var porCobrar by remember { mutableStateOf(listOf<Ticket>()) }
    var cobrados by remember { mutableStateOf(listOf<Ticket>()) }

    fun cargar() {
        scope.launch {
            porCobrar = db.ticketDao().obtenerPorCobrar()
            cobrados = db.ticketDao().obtenerCobrados()
        }
    }
    LaunchedEffect(Unit) { cargar() }

    Column(Modifier.fillMaxSize().padding(12.dp)) {
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Button(onClick = onBack) { Text("← Ventas") }
            Text("Tickets", fontWeight = FontWeight.Bold)
        }
        TabRow(selectedTabIndex = tab) {
            Tab(selected = tab==0, onClick = { tab=0 }, text = { Text("Por Cobrar (${porCobrar.size})") })
            Tab(selected = tab==1, onClick = { tab=1 }, text = { Text("Cobrados (${cobrados.size})") })
        }
        val lista = if(tab==0) porCobrar else cobrados
        LazyColumn(verticalArrangement = Arrangement.spacedBy(6.dp), modifier = Modifier.padding(top=8.dp)) {
            items(lista) { t ->
                Card(Modifier.fillMaxWidth()) {
                    Row(Modifier.padding(12.dp).fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Column { Text(if(t.cliente.isNotBlank()) t.cliente else "Ticket #${t.id}", fontWeight=FontWeight.Bold); Text("$${t.total} - ${java.util.Date(t.fecha)}", fontWeight=FontWeight.Light) }
                        if(tab==0) {
                            Button(onClick = { scope.launch { db.ticketDao().marcarCobrado(t.id); cargar() } }) { Text("Cobrar") }
                        }
                    }
                }
            }
        }
    }
}
