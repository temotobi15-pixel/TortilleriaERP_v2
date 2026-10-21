package com.tortilleria.erpv2.data

import androidx.room.*

@Entity(tableName = "ventas")
data class Venta(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val cliente: String,
    val producto: String,
    val variante: String,
    val kilos: Double,
    val precioKg: Double,
    val total: Double,
    val fecha: Long = System.currentTimeMillis()
)

@Dao
interface VentaDao {
    @Query("SELECT * FROM ventas ORDER BY fecha DESC")
    suspend fun obtenerTodas(): List<Venta>
    @Query("SELECT SUM(total) FROM ventas WHERE date(fecha/1000, 'unixepoch','localtime') = date('now','localtime')")
    suspend fun totalHoy(): Double?
    @Insert
    suspend fun insertar(venta: Venta)
}

data class ProductoVariante(
    val sku: String,
    val nombre: String,
    val precio: Double
)

data class Producto(
    val id: String,
    val nombre: String,
    val precioBase: Double,
    val variantes: List<ProductoVariante>
)

object CatalogoFijo {
    val productos = listOf(
        Producto(
            id = "TORT",
            nombre = "Tortilla",
            precioBase = 34.0,
            variantes = listOf(
                ProductoVariante("TORT-BASE", "Tortilla (Normal)", 34.0),
                ProductoVariante("TORT-NOPAL", "Nopal", 40.0),
                ProductoVariante("TORT-TACO", "Taco", 45.0),
                ProductoVariante("TORT-AZUL", "Azul", 42.0)
            )
        )
    )
}
