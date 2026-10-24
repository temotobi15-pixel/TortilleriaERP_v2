package com.tortilleria.erpv2.data

import androidx.room.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt

@Entity(tableName = "tickets")
data class Ticket(@PrimaryKey(autoGenerate = true) val id: Int = 0, val total: Double, val tipo: String, val cliente: String = "", val fecha: Long = System.currentTimeMillis())
@Entity(tableName = "ticket_detalle")
data class TicketDetalle(@PrimaryKey(autoGenerate = true) val id: Int = 0, val ticketId: Int, val producto: String, val variante: String, val kilos: Double, val precioKg: Double, val total: Double)

@Dao
interface TicketDao {
    @Query("SELECT * FROM tickets ORDER BY fecha DESC") suspend fun obtenerTickets(): List<Ticket>
    @Insert suspend fun insertarTicket(t: Ticket): Long
    @Insert suspend fun insertarDetalles(detalles: List<TicketDetalle>)
    @Query("SELECT * FROM ticket_detalle WHERE ticketId=:id") suspend fun detalleDe(id: Int): List<TicketDetalle>
    @Query("UPDATE tickets SET tipo='COBRADO' WHERE id=:id") suspend fun marcarCobrado(id: Int)
}

@Entity(tableName = "empleados")
data class Empleado(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val nombre: String = "", val rolId: String = "TORTILLERA", val rolNombreSnapshot: String = "Tortillera",
    val pagoPorBola: Double = 8.0, val telefono: String = "", val qrToken: String = "LOGIN:${System.currentTimeMillis()}",
    val qrActivo: Boolean = true, val activo: Boolean = true, val fechaLong: Long = System.currentTimeMillis(),
    val fechaDia: String = SimpleDateFormat("yyyy-MM-dd", Locale.US).format(Date()),
    val semanaId: String = SimpleDateFormat("yyyy-'W'ww", Locale.US).format(Date())
)

@Entity(tableName = "registro_bolas")
data class RegistroBola(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val empleadoId: String = "", val empleadoNombreSnapshot: String = "", val kg: Double = 0.0, val bolas: Double = 0.0,
    val pagoPorBolaSnapshot: Double = 8.0, val ganado: Double = 0.0,
    val hora: String = SimpleDateFormat("HH:mm", Locale.US).format(Date()),
    val entregado: Boolean = true, val entregadoPorId: String = "",
    val entregadoPorNombre: String = "Mostrador",
    val fechaLong: Long = System.currentTimeMillis(),
    val fechaDia: String = SimpleDateFormat("yyyy-MM-dd", Locale.US).format(Date()),
    val semanaId: String = SimpleDateFormat("yyyy-'W'ww", Locale.US).format(Date())
) {
    companion object {
        fun kgToBolas(kg: Double): Double = ((kg / 5.0) * 10.0).roundToInt() / 10.0
    }
}

@Dao
interface EmpleadoDao {
    @Query("SELECT * FROM empleados WHERE activo=1 ORDER BY nombre ASC") suspend fun obtenerTodos(): List<Empleado>
    @Query("SELECT * FROM empleados WHERE rolId='TORTILLERA' AND activo=1 ORDER BY nombre ASC") suspend fun obtenerTortilleras(): List<Empleado>
    @Insert(onConflict = OnConflictStrategy.REPLACE) suspend fun insertar(e: Empleado)
    @Query("UPDATE empleados SET activo=0 WHERE id=:id") suspend fun desactivar(id: String)
    @Query("UPDATE empleados SET pagoPorBola = :pago WHERE id=:id") suspend fun actualizarPago(id: String, pago: Double)
}

@Dao
interface RegistroBolaDao {
    @Query("SELECT * FROM registro_bolas WHERE fechaDia=:fechaDia ORDER BY fechaLong ASC") suspend fun obtenerPorDia(fechaDia: String): List<RegistroBola>
    @Insert(onConflict = OnConflictStrategy.REPLACE) suspend fun insertar(r: RegistroBola)
    @Query("DELETE FROM registro_bolas WHERE id=:id") suspend fun eliminar(id: String)
}

data class ProductoVariante(val sku: String, val nombre: String, val precio: Double)
data class Producto(val id: String, val nombre: String, val categoria: String, val precioBase: Double, val variantes: List<ProductoVariante>)
object CatalogoFijo { val productos = listOf<Producto>() }
data class ItemCuenta(val producto: String, val variante: String, val kilos: Double, val precio: Double, val total: Double)