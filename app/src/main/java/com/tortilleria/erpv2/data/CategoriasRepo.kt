package com.tortilleria.erpv2.data
import androidx.compose.runtime.mutableStateListOf

object CategoriasRepo {
    val categorias = mutableStateListOf(
        "Lo más frecuente",
        "Tortilleria",
        "Mostrador",
        "Granel y Otros",
        "Dulces y Botanas",
        "Frutas y Verduras"
    )
    fun agregar(nombre: String) {
        val n = nombre.trim()
        if (n.isNotEmpty() && !categorias.any { it.equals(n, true) }) {
            categorias.add(n)
        }
    }
    fun eliminar(nombre: String) {
        if (categorias.size > 1) categorias.remove(nombre)
    }
}
