@file:OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)
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

data class Insumo(var nombre: String, var stock: Double, var unidad: String, var minimo: Double)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InventarioScreen(onBack: () -> Unit) {
    var insumos by remember { mutableStateOf(listOf(
        Insumo("Maíz", 250.0, "kg", 50.0),
        Insumo("Harina", 100.0, "kg", 20.0),
        Insumo("Gas", 80.0, "%", 20.0),
        Insumo("Papel / Bolsa", 500.0, "pzas", 100.0),
        Insumo("Cal", 15.0, "kg", 5.0)
    ))}

    var showAdd by remember { mutableStateOf<Insumo?>(null) }
    var cantidadInput by remember { mutableStateOf("") }
    var esEntrada by remember { mutableStateOf(true) }

    Column(Modifier.fillMaxSize().padding(12.dp)) {
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Button(onClick = onBack) { Text("← Menú") }
            Text("INVENTARIO", fontWeight = FontWeight.Bold, fontSize = 18.sp, modifier=Modifier.padding(top=8.dp))
        }
        Spacer(Modifier.height(12.dp))
        Text("Insumos - Toca para ajustar", fontWeight = FontWeight.Bold)

        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp), modifier=Modifier.padding(top=8.dp)) {
            items(insumos) { item ->
                val bajo = item.stock <= item.minimo
                ElevatedCard(
                    colors = CardDefaults.elevatedCardColors(containerColor = if(bajo) MaterialTheme.colorScheme.errorContainer else MaterialTheme.colorScheme.surface),
                    onClick = { showAdd = item; cantidadInput="" },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(Modifier.padding(14.dp).fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Column {
                            Text(item.nombre, fontWeight=FontWeight.Bold, fontSize=16.sp)
                            Text("${item.stock} ${item.unidad} | Mín: ${item.minimo}", fontSize=12.sp)
                            if(bajo) Text("⚠️ BAJO STOCK", color=MaterialTheme.colorScheme.error, fontSize=11.sp, fontWeight=FontWeight.Bold)
                        }
                        Text("Ajustar", fontSize=12.sp)
                    }
                }
            }
        }
    }

    if (showAdd != null) {
        val item = showAdd!!
        AlertDialog(onDismissRequest = { showAdd=null },
            title = { Text(item.nombre) },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("Stock actual: ${item.stock} ${item.unidad}")
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        FilterChip(selected=esEntrada, onClick={esEntrada=true}, label={Text("Entrada +")})
                        FilterChip(selected=!esEntrada, onClick={esEntrada=false}, label={Text("Salida -")})
                    }
                    OutlinedTextField(
                        value=cantidadInput,
                        onValueChange={ cantidadInput=it.replace(",",".") },
                        label={ Text("Cantidad (${item.unidad})") },
                        keyboardOptions = KeyboardOptions(keyboardType=KeyboardType.Number),
                        singleLine=true
                    )
                }
            },
            confirmButton = {
                Button(onClick={
                    val cant = cantidadInput.toDoubleOrNull() ?: 0.0
                    if(cant>0){
                        insumos = insumos.map {
                            if(it.nombre==item.nombre) it.copy(stock = if(esEntrada) it.stock+cant else (it.stock-cant).coerceAtLeast(0.0))
                            else it
                        }
                    }
                    showAdd=null
                }){ Text("Guardar") }
            },
            dismissButton = { TextButton(onClick={showAdd=null}){ Text("Cancelar") } }
        )
    }
}
