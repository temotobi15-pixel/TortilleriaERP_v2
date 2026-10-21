package com.tortilleria.erpv2.data

import androidx.room.*

@Entity(tableName = "tickets")
data class Ticket(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val total: Double,
    val tipo: String, // COBRADO o POR_COBRAR
    val cliente: String = "",
    val fecha: Long = System.currentTimeMillis()
)

@Entity(tableName = "ticket_detalle")
data class TicketDetalle(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val ticketId: Int,
    val producto: String,
    val variante: String,
    val kilos: Double,
    val precioKg: Double,
    val total: Double
)

@Dao
interface TicketDao {
    @Query("SELECT * FROM tickets ORDER BY fecha DESC")
    suspend fun obtenerTickets(): List<Ticket>
    @Query("SELECT * FROM tickets WHERE tipo='POR_COBRAR' ORDER BY fecha DESC")
    suspend fun obtenerPorCobrar(): List<Ticket>
    @Query("SELECT * FROM tickets WHERE tipo='COBRADO' ORDER BY fecha DESC")
    suspend fun obtenerCobrados(): List<Ticket>
    @Query("SELECT * FROM ticket_detalle WHERE ticketId=:id")
    suspend fun detalleDe(id: Int): List<TicketDetalle>
    @Insert
    suspend fun insertarTicket(t: Ticket): Long
    @Insert
    suspend fun insertarDetalles(detalles: List<TicketDetalle>)
    @Query("SELECT SUM(total) FROM tickets WHERE tipo='COBRADO' AND date(fecha/1000,'unixepoch','localtime') = date('now','localtime')")
    suspend fun totalHoy(): Double?
    @Query("UPDATE tickets SET tipo='COBRADO' WHERE id=:id")
    suspend fun marcarCobrado(id: Int)
}

// --- CATALOGO FIJO EXTENSO ---
data class ProductoVariante(val sku: String, val nombre: String, val precio: Double)
data class Producto(val id: String, val nombre: String, val categoria: String, val precioBase: Double, val variantes: List<ProductoVariante>)

object CatalogoFijo {
    val categorias = listOf("TORTILLA", "MASA", "FRITURA")
    val productos = listOf(
        Producto("TORT", "Tortilla", "TORTILLA", 34.0, listOf(
            ProductoVariante("TORT-BASE", "Normal", 34.0),
            ProductoVariante("TORT-NOPAL", "Nopal", 40.0),
            ProductoVariante("TORT-TACO", "Taco", 45.0),
            ProductoVariante("TORT-AZUL", "Azul", 42.0)
        )),
        Producto("MASA", "Masa", "MASA", 20.0, listOf(
            ProductoVariante("MASA-BASE", "Normal", 20.0),
            ProductoVariante("MASA-TAMAL", "Para Tamal", 26.0),
            ProductoVariante("MASA-AZUL", "Azul", 28.0)
        )),
        Producto("FRIT", "Fritura", "FRITURA", 45.0, listOf(
            ProductoVariante("TOTOPO", "Totopo", 45.0),
            ProductoVariante("TOSTADA", "Tostada", 50.0)
        ))
    )
    fun porCategoria(cat: String) = productos.filter { it.categoria == cat }
}

data class ItemCuenta(val producto: String, val variante: String, val kilos: Double, val precio: Double, val total: Double)
