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

data class Insumo(
    var nombre: String,
    var categoria: String,
    var stock: Double,
    var unidad: String,
    var minimo: Double,
    var costo: Double,
    var precioVenta: Double
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InventarioScreen(onBack: () -> Unit) {
    var insumos by remember { mutableStateOf(listOf(
        Insumo("Maíz", "MATERIA PRIMA", 250.0, "kg", 50.0, 12.0, 0.0),
        Insumo("Harina", "MATERIA PRIMA", 100.0, "kg", 20.0, 15.0, 0.0),
        Insumo("Gas", "CONSUMIBLE", 80.0, "%", 20.0, 0.0, 0.0),
        Insumo("Tortilla kg", "PRODUCTO TERMINADO", 0.0, "kg", 10.0, 14.0, 28.0),
        Insumo("Masa kg", "PRODUCTO TERMINADO", 0.0, "kg", 5.0, 10.0, 20.0)
    ))}

    var showAdd by remember { mutableStateOf<Insumo?>(null) }
    var cantidadInput by remember { mutableStateOf("") }
    var esEntrada by remember { mutableStateOf(true) }

    var showNuevo by remember { mutableStateOf(false) }
    var nNombre by remember { mutableStateOf("") }
    var nCategoria by remember { mutableStateOf("MATERIA PRIMA") }
    var nUnidad by remember { mutableStateOf("kg") }
    var nStock by remember { mutableStateOf("") }
    var nMinimo by remember { mutableStateOf("") }
    var nCosto by remember { mutableStateOf("") }
    var nPrecio by remember { mutableStateOf("") }

    Column(Modifier.fillMaxSize().padding(12.dp)) {
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Button(onClick = onBack) { Text("← Menú") }
            Text("INVENTARIO", fontWeight = FontWeight.Bold, fontSize = 18.sp, modifier=Modifier.padding(top=8.dp))
        }
        Spacer(Modifier.height(12.dp))
        Button(onClick = { 
            nNombre=""; nStock=""; nMinimo=""; nCosto=""; nPrecio=""; nUnidad="kg"; nCategoria="MATERIA PRIMA"
            showNuevo=true 
        }, modifier=Modifier.fillMaxWidth()) { Text("+ Agregar Producto / Insumo") }
        
        Spacer(Modifier.height(8.dp))
        Text("Toca un insumo para entrada/salida", fontWeight=FontWeight.Bold, fontSize=12.sp)

        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp), modifier=Modifier.padding(top=8.dp).weight(1f)) {
            items(insumos) { item ->
                val bajo = item.stock <= item.minimo
                ElevatedCard(
                    colors = CardDefaults.elevatedCardColors(containerColor = if(bajo) MaterialTheme.colorScheme.errorContainer else MaterialTheme.colorScheme.surface),
                    onClick = { showAdd = item; cantidadInput="" },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(Modifier.padding(12.dp).fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Column(Modifier.weight(1f)) {
                            Text(item.nombre, fontWeight=FontWeight.Bold, fontSize=16.sp)
                            Text("${item.categoria} - ${item.stock} ${item.unidad}", fontSize=12.sp)
                            Text("Min: ${item.minimo} | Costo: $${item.costo} | Venta: $${item.precioVenta}", fontSize=11.sp)
                            if(bajo) Text("⚠️ BAJO STOCK", color=MaterialTheme.colorScheme.error, fontSize=11.sp, fontWeight=FontWeight.Bold)
                        }
                        Text("Ajustar", fontSize=11.sp)
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

    if (showNuevo) {
        AlertDialog(onDismissRequest = { showNuevo=false },
            title = { Text("Nuevo Producto / Insumo") },
            text = {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    item { OutlinedTextField(value=nNombre, onValueChange={nNombre=it}, label={Text("Nombre * Ej: Maíz")}, modifier=Modifier.fillMaxWidth(), singleLine=true) }
                    item { 
                        Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            FilterChip(selected=nCategoria=="MATERIA PRIMA", onClick={nCategoria="MATERIA PRIMA"}, label={Text("Mat. Prima")})
                            FilterChip(selected=nCategoria=="PRODUCTO TERMINADO", onClick={nCategoria="PRODUCTO TERMINADO"}, label={Text("Terminado")})
                            FilterChip(selected=nCategoria=="CONSUMIBLE", onClick={nCategoria=="CONSUMIBLE"}, label={Text("Consumible")})
                        }
                    }
                    item { 
                        Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            FilterChip(selected=nUnidad=="kg", onClick={nUnidad="kg"}, label={Text("kg")})
                            FilterChip(selected=nUnidad=="pzas", onClick={nUnidad="pzas"}, label={Text("pzas")})
                            FilterChip(selected=nUnidad=="%", onClick={nUnidad="%"}, label={Text("%")})
                            FilterChip(selected=nUnidad=="lt", onClick={nUnidad="lt"}, label={Text("lt")})
                        }
                    }
                    item { OutlinedTextField(value=nStock, onValueChange={nStock=it.replace(",",".")}, label={Text("Stock Inicial")}, keyboardOptions=KeyboardOptions(keyboardType=KeyboardType.Number), modifier=Modifier.fillMaxWidth(), singleLine=true) }
                    item { OutlinedTextField(value=nMinimo, onValueChange={nMinimo=it.replace(",",".")}, label={Text("Stock Mínimo *")}, keyboardOptions=KeyboardOptions(keyboardType=KeyboardType.Number), modifier=Modifier.fillMaxWidth(), singleLine=true) }
                    item { OutlinedTextField(value=nCosto, onValueChange={nCosto=it.replace(",",".")}, label={Text("Costo por ${nUnidad}")}, keyboardOptions=KeyboardOptions(keyboardType=KeyboardType.Number), modifier=Modifier.fillMaxWidth(), singleLine=true) }
                    item { OutlinedTextField(value=nPrecio, onValueChange={nPrecio=it.replace(",",".")}, label={Text("Precio Venta (si aplica)")}, keyboardOptions=KeyboardOptions(keyboardType=KeyboardType.Number), modifier=Modifier.fillMaxWidth(), singleLine=true) }
                }
            },
            confirmButton = {
                Button(enabled=nNombre.isNotBlank(), onClick={
                    val nuevo = Insumo(
                        nombre=nNombre.trim(),
                        categoria=nCategoria,
                        unidad=nUnidad,
                        stock=nStock.toDoubleOrNull()?:0.0,
                        minimo=nMinimo.toDoubleOrNull()?:0.0,
                        costo=nCosto.toDoubleOrNull()?:0.0,
                        precioVenta=nPrecio.toDoubleOrNull()?:0.0
                    )
                    insumos = insumos + nuevo
                    showNuevo=false
                }){ Text("Agregar") }
            },
            dismissButton = { TextButton(onClick={showNuevo=false}){ Text("Cancelar") } }
        )
    }
}
