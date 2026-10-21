package com.tortilleria.erpv2.data

import androidx.compose.runtime.mutableStateListOf
import com.tortilleria.erpv2.ui.Insumo
import com.tortilleria.erpv2.ui.VarianteProducto

object InventarioRepo {
    val productos = mutableStateListOf(
        Insumo(1,"Maíz", "MATERIA PRIMA", 250.0, "kg", 50.0, 12.0, 0.0, false, listOf(), "Granel y Otros"),
        Insumo(2,"Harina", "MATERIA PRIMA", 100.0, "kg", 20.0, 15.0, 0.0, false, listOf(), "Granel y Otros"),
        Insumo(3,"Tortilla", "PRODUCTO TERMINADO", 0.0, "kg", 10.0, 14.0, 28.0, true, listOf(VarianteProducto("Normal",28.0), VarianteProducto("Amarilla",30.0)), "Tortilleria"),
        Insumo(4,"Masa", "PRODUCTO TERMINADO", 0.0, "kg", 5.0, 10.0, 20.0, true, listOf(VarianteProducto("Normal",20.0)), "Tortilleria")
    )
    var nextId = 5
    fun getCatalogoVentas() = productos.filter { it.apareceEnCatalogo }
}
