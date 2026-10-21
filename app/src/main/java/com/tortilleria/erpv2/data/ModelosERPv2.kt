package com.tortilleria.erpv2.data

import androidx.room.*

@Entity(tableName = "ventas")
data class Venta(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val producto: String,
    val variante: String,
    val kilos: Double,
    val precioKg: Double,
    val total: Double,
    val tipo: String = "COBRADA", // COBRADA o TICKET
    val cliente: String = "",
    val fecha: Long = System.currentTimeMillis()
)

@Dao
interface VentaDao {
    @Query("SELECT * FROM ventas ORDER BY fecha DESC")
    suspend fun obtenerTodas(): List<Venta>
    @Query("SELECT SUM(total) FROM ventas WHERE tipo='COBRADA' AND date(fecha/1000, 'unixepoch','localtime') = date('now','localtime')")
    suspend fun totalHoy(): Double?
    @Insert
    suspend fun insertar(venta: Venta)
}

data class ProductoVariante(val sku: String, val nombre: String, val precio: Double)
data class Producto(val id: String, val nombre: String, val precioBase: Double, val variantes: List<ProductoVariante>)

object CatalogoFijo {
    val productos = listOf(
        Producto("TORT", "Tortilla", 34.0, listOf(
            ProductoVariante("TORT-BASE", "Tortilla (Normal)", 34.0),
            ProductoVariante("TORT-NOPAL", "Nopal", 40.0),
            ProductoVariante("TORT-TACO", "Taco", 45.0),
            ProductoVariante("TORT-AZUL", "Azul", 42.0)
        )),
        Producto("MASA", "Masa", 20.0, listOf(
            ProductoVariante("MASA-BASE", "Masa Normal", 20.0),
            ProductoVariante("MASA-TAMAL", "Masa para Tamal", 26.0)
        ))
    )
}
