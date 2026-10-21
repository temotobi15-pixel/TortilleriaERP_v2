package com.tortilleria.erpv2.data

import androidx.room.*

@Entity(tableName = "ventas")
data class Venta(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val cliente: String,
    val kilosTortilla: Double,
    val kilosMasa: Double,
    val precioKg: Double,
    val total: Double,
    val fecha: Long = System.currentTimeMillis(),
    val tipoPago: String = "Efectivo"
)

@Dao
interface VentaDao {
    @Query("SELECT * FROM ventas ORDER BY fecha DESC")
    suspend fun obtenerTodas(): List<Venta>

    @Query("SELECT SUM(total) FROM ventas WHERE date(fecha/1000, 'unixepoch','localtime') = date('now','localtime')")
    suspend fun totalHoy(): Double?

    @Insert
    suspend fun insertar(venta: Venta)

    @Delete
    suspend fun borrar(venta: Venta)
}
