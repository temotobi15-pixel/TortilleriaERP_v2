import com.tortilleria.erpv2.data.InventarioRepo
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
import com.tortilleria.erpv2.data.InventarioRepo

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VentasScreen(onBack: () -> Unit, onVerHistorial: () -> Unit) {
    val catalogo = InventarioRepo.productos.filter { it.apareceEnCatalogo }

    var seleccionado by remember { mutableStateOf<Insumo?>(null) }
    var varianteSeleccionada by remember { mutableStateOf<VarianteProducto?>(null) }
    var cantidad by remember { mutableStateOf("1") }
    var carrito by remember { mutableStateOf(listOf<Triple<Insumo, VarianteProducto?, Double>>()) }

    Column(Modifier.fillMaxSize().padding(12.dp)) {
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Button(onClick=onBack){ Text("← Menú") }
            TextButton(onClick=onVerHistorial){ Text("Historial") }
        }
        Text("VENTAS", fontWeight=FontWeight.Bold, fontSize=18.sp, modifier=Modifier.padding(vertical=8.dp))
        if(catalogo.isEmpty()){
            Text("No hay productos con check 'Aparece en catálogo de Ventas'. Ve a Inventario.", fontSize=13.sp, color=MaterialTheme.colorScheme.error)
        } else {
            Text("Catálogo (${catalogo.size} productos)", fontWeight=FontWeight.Bold, fontSize=14.sp)
        }

        LazyColumn(verticalArrangement=Arrangement.spacedBy(8.dp), modifier=Modifier.weight(1f).padding(top=8.dp)) {
            items(catalogo, key={it.id}) { prod ->
                ElevatedCard(onClick={
                    seleccionado=prod
                    varianteSeleccionada = if(prod.variantes.isNotEmpty()) prod.variantes[0] else null
                    cantidad="1"
                }, modifier=Modifier.fillMaxWidth()) {
                    Column(Modifier.padding(12.dp)) {
                        Text(prod.nombre, fontWeight=FontWeight.Bold)
                        Text("${prod.unidad} - $${prod.precioVenta}", fontSize=12.sp)
                        if(prod.variantes.isNotEmpty()) Text("Variantes: ${prod.variantes.joinToString{ "${it.nombre} $${it.precio}" }}", fontSize=11.sp)
                    }
                }
            }
        }

        if(carrito.isNotEmpty()){
            Divider(Modifier.padding(vertical=6.dp))
            Text("Carrito:", fontWeight=FontWeight.Bold)
            carrito.forEach { (p, v, cant) ->
                val precio = v?.precio ?: p.precioVenta
                Text("${p.nombre} ${v?.nombre?:""} x$cant = $${precio*cant}", fontSize=12.sp)
            }
            val total = carrito.sumOf{ (p,v,c) -> (v?.precio ?: p.precioVenta)*c }
            Text("Total: $${total}", fontWeight=FontWeight.Bold, fontSize=16.sp)
            Button(onClick={ carrito=listOf() }, modifier=Modifier.fillMaxWidth().padding(top=6.dp)){ Text("Cobrar / Guardar Venta") }
        }
    }

    if(seleccionado!=null){
        val prod = seleccionado!!
        AlertDialog(onDismissRequest={seleccionado=null},
            title={Text(prod.nombre)},
            text={
                Column(verticalArrangement=Arrangement.spacedBy(8.dp)) {
                    if(prod.variantes.isNotEmpty()){
                        Text("Elige variante:", fontWeight=FontWeight.Bold, fontSize=12.sp)
                        Row(horizontalArrangement=Arrangement.spacedBy(6.dp)) {
                            prod.variantes.forEach { v ->
                                FilterChip(selected=varianteSeleccionada==v, onClick={varianteSeleccionada=v}, label={Text("${v.nombre} $${v.precio}")})
                            }
                        }
                    }
                    OutlinedTextField(value=cantidad, onValueChange={cantidad=it.replace(",",".")}, label={Text("Cantidad (${prod.unidad})")}, keyboardOptions=KeyboardOptions(keyboardType=KeyboardType.Number), singleLine=true)
                }
            },
            confirmButton={
                Button(onClick={
                    val cant = cantidad.toDoubleOrNull() ?: 1.0
                    carrito = carrito + Triple(prod, varianteSeleccionada, cant)
                    val idx = InventarioRepo.productos.indexOfFirst{ it.id==prod.id }
                    if(idx>=0){
                        val actual = InventarioRepo.productos[idx]
                        InventarioRepo.productos[idx] = actual.copy(stock=(actual.stock-cant).coerceAtLeast(0.0))
                    }
                    seleccionado=null
                }){ Text("Agregar") }
            },
            dismissButton={TextButton(onClick={seleccionado=null}){Text("Cancelar")}}
        )
    }
}
