@file:OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)
package com.tortilleria.erpv2.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class VarianteProducto(val nombre: String, val precio: Double)
data class Insumo(
    var id: Int,
    var nombre: String,
    var categoria: String,
    var stock: Double,
    var unidad: String,
    var minimo: Double,
    var costo: Double,
    var precioVenta: Double,
    var apareceEnCatalogo: Boolean,
    var variantes: List<VarianteProducto>
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InventarioScreen(onBack: () -> Unit) {
    var insumos by remember { mutableStateOf(listOf(
        Insumo(1,"Maíz", "MATERIA PRIMA", 250.0, "kg", 50.0, 12.0, 0.0, false, listOf()),
        Insumo(2,"Harina", "MATERIA PRIMA", 100.0, "kg", 20.0, 15.0, 0.0, false, listOf()),
        Insumo(3,"Tortilla", "PRODUCTO TERMINADO", 0.0, "kg", 10.0, 14.0, 28.0, true, listOf(VarianteProducto("Normal",28.0), VarianteProducto("Amarilla",30.0))),
        Insumo(4,"Masa", "PRODUCTO TERMINADO", 0.0, "kg", 5.0, 10.0, 20.0, true, listOf(VarianteProducto("Normal",20.0)))
    ))}
    var nextId by remember { mutableStateOf(5) }

    // Ajuste stock
    var showAjuste by remember { mutableStateOf<Insumo?>(null) }
    var cantidadInput by remember { mutableStateOf("") }
    var esEntrada by remember { mutableStateOf(true) }

    // Crear / Editar
    var showNuevo by remember { mutableStateOf(false) }
    var editando by remember { mutableStateOf<Insumo?>(null) }
    var nNombre by remember { mutableStateOf("") }
    var nCategoria by remember { mutableStateOf("MATERIA PRIMA") }
    var nUnidad by remember { mutableStateOf("kg") }
    var nStock by remember { mutableStateOf("") }
    var nMinimo by remember { mutableStateOf("") }
    var nCosto by remember { mutableStateOf("") }
    var nPrecio by remember { mutableStateOf("") }
    var nAparece by remember { mutableStateOf(true) }
    var nVariantes by remember { mutableStateOf(listOf<VarianteProducto>()) }
    var vNombre by remember { mutableStateOf("") }
    var vPrecio by remember { mutableStateOf("") }

    fun abrirNuevo() {
        editando=null; nNombre=""; nStock=""; nMinimo=""; nCosto=""; nPrecio=""; nUnidad="kg"; nCategoria="MATERIA PRIMA"; nAparece=true; nVariantes=listOf(); vNombre=""; vPrecio=""; showNuevo=true
    }
    fun abrirEditar(item: Insumo) {
        editando=item; nNombre=item.nombre; nCategoria=item.categoria; nUnidad=item.unidad; nStock=item.stock.toString(); nMinimo=item.minimo.toString(); nCosto=item.costo.toString(); nPrecio=item.precioVenta.toString(); nAparece=item.apareceEnCatalogo; nVariantes=item.variantes; showNuevo=true
    }

    Column(Modifier.fillMaxSize().padding(12.dp)) {
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Button(onClick = onBack) { Text("← Menú") }
            Text("INVENTARIO", fontWeight = FontWeight.Bold, fontSize = 18.sp, modifier=Modifier.padding(top=8.dp))
        }
        Spacer(Modifier.height(12.dp))
        Button(onClick = { abrirNuevo() }, modifier=Modifier.fillMaxWidth()) { Text("+ Agregar Producto / Insumo") }
        Spacer(Modifier.height(8.dp))

        LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp), modifier=Modifier.padding(top=8.dp).weight(1f)) {
            items(insumos, key={it.id}) { item ->
                val bajo = item.stock <= item.minimo
                ElevatedCard(
                    colors = CardDefaults.elevatedCardColors(containerColor = if(bajo) MaterialTheme.colorScheme.errorContainer else MaterialTheme.colorScheme.surface),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(Modifier.padding(12.dp)) {
                        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text(item.nombre, fontWeight=FontWeight.Bold, fontSize=16.sp)
                            if(item.apareceEnCatalogo) Badge{ Text("En Ventas") }
                        }
                        Text("${item.categoria} - ${item.stock} ${item.unidad} | Min: ${item.minimo}", fontSize=12.sp)
                        if(item.variantes.isNotEmpty()) Text("Variantes: ${item.variantes.joinToString{ "${it.nombre} $${it.precio}" }}", fontSize=11.sp, fontWeight=FontWeight.SemiBold)
                        Text("Costo: $${item.costo} | Venta: $${item.precioVenta}", fontSize=11.sp)
                        if(bajo) Text("⚠️ BAJO STOCK", color=MaterialTheme.colorScheme.error, fontSize=11.sp, fontWeight=FontWeight.Bold)
                        Spacer(Modifier.height(8.dp))
                        Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            OutlinedButton(onClick={ showAjuste=item; cantidadInput=""; esEntrada=true }, modifier=Modifier.weight(1f)) { Text("Ajustar") }
                            OutlinedButton(onClick={ abrirEditar(item) }, modifier=Modifier.weight(1f)) { Text("Editar") }
                            Button(onClick={ insumos=insumos.filterNot{ it.id==item.id } }, colors=ButtonDefaults.buttonColors(containerColor=MaterialTheme.colorScheme.error), modifier=Modifier.weight(1f)) { Text("Eliminar") }
                        }
                    }
                }
            }
        }
    }

    if (showAjuste != null) {
        val item = showAjuste!!
        AlertDialog(onDismissRequest = { showAjuste=null },
            title = { Text("Ajustar ${item.nombre}") },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("Stock actual: ${item.stock} ${item.unidad}")
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        FilterChip(selected=esEntrada, onClick={esEntrada=true}, label={Text("Entrada +")})
                        FilterChip(selected=!esEntrada, onClick={esEntrada=false}, label={Text("Salida -")})
                    }
                    OutlinedTextField(value=cantidadInput, onValueChange={ cantidadInput=it.replace(",",".") }, label={ Text("Cantidad") }, keyboardOptions=KeyboardOptions(keyboardType=KeyboardType.Number), singleLine=true)
                }
            },
            confirmButton = {
                Button(onClick={
                    val cant = cantidadInput.toDoubleOrNull() ?: 0.0
                    if(cant>0) insumos = insumos.map { if(it.id==item.id) it.copy(stock= if(esEntrada) it.stock+cant else (it.stock-cant).coerceAtLeast(0.0)) else it }
                    showAjuste=null
                }){ Text("Guardar") }
            },
            dismissButton = { TextButton(onClick={showAjuste=null}){ Text("Cancelar") } }
        )
    }

    if (showNuevo) {
        AlertDialog(onDismissRequest = { showNuevo=false },
            title = { Text(if(editando==null) "Nuevo Producto / Insumo" else "Editar ${editando!!.nombre}") },
            text = {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp), modifier=Modifier.heightIn(max=520.dp)) {
                    item { OutlinedTextField(value=nNombre, onValueChange={nNombre=it}, label={Text("Nombre *")}, modifier=Modifier.fillMaxWidth(), singleLine=true) }
                    item { Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        FilterChip(selected=nCategoria=="MATERIA PRIMA", onClick={nCategoria="MATERIA PRIMA"}, label={Text("Mat. Prima")})
                        FilterChip(selected=nCategoria=="PRODUCTO TERMINADO", onClick={nCategoria="PRODUCTO TERMINADO"}, label={Text("Terminado")})
                        FilterChip(selected=nCategoria=="CONSUMIBLE", onClick={nCategoria="CONSUMIBLE"}, label={Text("Consumible")})
                    }}
                    item { Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        FilterChip(selected=nUnidad=="kg", onClick={nUnidad="kg"}, label={Text("kg")})
                        FilterChip(selected=nUnidad=="pzas", onClick={nUnidad="pzas"}, label={Text("pzas")})
                        FilterChip(selected=nUnidad=="%", onClick={nUnidad="%"}, label={Text("%")})
                        FilterChip(selected=nUnidad=="lt", onClick={nUnidad="lt"}, label={Text("lt")})
                    }}
                    item { OutlinedTextField(value=nStock, onValueChange={nStock=it.replace(",",".")}, label={Text("Stock Inicial")}, keyboardOptions=KeyboardOptions(keyboardType=KeyboardType.Number), modifier=Modifier.fillMaxWidth(), singleLine=true) }
                    item { OutlinedTextField(value=nMinimo, onValueChange={nMinimo=it.replace(",",".")}, label={Text("Stock Mínimo *")}, keyboardOptions=KeyboardOptions(keyboardType=KeyboardType.Number), modifier=Modifier.fillMaxWidth(), singleLine=true) }
                    item { OutlinedTextField(value=nCosto, onValueChange={nCosto=it.replace(",",".")}, label={Text("Costo por ${nUnidad}")}, keyboardOptions=KeyboardOptions(keyboardType=KeyboardType.Number), modifier=Modifier.fillMaxWidth(), singleLine=true) }
                    item { OutlinedTextField(value=nPrecio, onValueChange={nPrecio=it.replace(",",".")}, label={Text("Precio Venta base")}, keyboardOptions=KeyboardOptions(keyboardType=KeyboardType.Number), modifier=Modifier.fillMaxWidth(), singleLine=true) }
                    item { 
                        Row(verticalAlignment=Alignment.CenterVertically) {
                            Checkbox(checked=nAparece, onCheckedChange={nAparece=it})
                            Text("Aparece en catálogo de Ventas", fontSize=13.sp)
                        }
                    }
                    item { Divider(); Text("Variantes de producto", fontWeight=FontWeight.Bold, fontSize=14.sp, modifier=Modifier.padding(top=8.dp)) }
                    item {
                        Row(horizontalArrangement=Arrangement.spacedBy(6.dp), verticalAlignment=Alignment.CenterVertically) {
                            OutlinedTextField(value=vNombre, onValueChange={vNombre=it}, label={Text("Ej: Amarilla")}, modifier=Modifier.weight(1f), singleLine=true)
                            OutlinedTextField(value=vPrecio, onValueChange={vPrecio=it.replace(",",".")}, label={Text("$")}, keyboardOptions=KeyboardOptions(keyboardType=KeyboardType.Number), modifier=Modifier.width(90.dp), singleLine=true)
                            Button(onClick={
                                val vp = vPrecio.toDoubleOrNull() ?: 0.0
                                if(vNombre.isNotBlank() && vp>0){ nVariantes=nVariantes+VarianteProducto(vNombre.trim(), vp); vNombre=""; vPrecio="" }
                            }){ Text("+") }
                        }
                    }
                    items(nVariantes) { v ->
                        Row(Modifier.fillMaxWidth(), horizontalArrangement=Arrangement.SpaceBetween, verticalAlignment=Alignment.CenterVertically) {
                            Text("${v.nombre} - $${v.precio}", fontSize=12.sp)
                            TextButton(onClick={ nVariantes=nVariantes.filterNot{ it==v } }){ Text("Quitar") }
                        }
                    }
                }
            },
            confirmButton = {
                Button(enabled=nNombre.isNotBlank(), onClick={
                    if(editando==null){
                        val nuevo = Insumo(nextId, nNombre.trim(), nCategoria, nUnidad=nUnidad, stock=nStock.toDoubleOrNull()?:0.0, minimo=nMinimo.toDoubleOrNull()?:0.0, costo=nCosto.toDoubleOrNull()?:0.0, precioVenta=nPrecio.toDoubleOrNull()?:0.0, apareceEnCatalogo=nAparece, variantes=nVariantes)
                        insumos = insumos + nuevo; nextId++
                    } else {
                        insumos = insumos.map { if(it.id==editando!!.id) it.copy(nombre=nNombre.trim(), categoria=nCategoria, unidad=nUnidad, stock=nStock.toDoubleOrNull()?:it.stock, minimo=nMinimo.toDoubleOrNull()?:it.minimo, costo=nCosto.toDoubleOrNull()?:it.costo, precioVenta=nPrecio.toDoubleOrNull()?:it.precioVenta, apareceEnCatalogo=nAparece, variantes=nVariantes) else it }
                    }
                    showNuevo=false
                }){ Text(if(editando==null) "Agregar" else "Guardar") }
            },
            dismissButton = { TextButton(onClick={showNuevo=false}){ Text("Cancelar") } }
        )
    }
}
