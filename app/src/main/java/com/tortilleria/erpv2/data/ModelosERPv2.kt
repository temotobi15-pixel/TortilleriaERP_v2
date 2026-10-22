package com.tortilleria.erpv2.data

import androidx.room.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt

// ================== TUS TABLAS ESTABLES (NO TOQUE) ==================

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

// ================== NUEVO: MODULO USUARIOS + PRODUCCION TORTILLERAS ==================

@Entity(tableName = "empleados")
data class Empleado(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val nombre: String = "",
    val rolId: String = "TORTILLERA", // TORTILLERA, REPARTIDOR, MOSTRADOR, ADMIN
    val rolNombreSnapshot: String = "Tortillera",
    val pagoPorBola: Double = 8.0, // VARIABLE por empleada - solo aplica si rol=Tortillera
    val telefono: String = "",
    val qrToken: String = "LOGIN:${System.currentTimeMillis()}",
    val qrActivo: Boolean = true,
    val activo: Boolean = true,
    val fechaLong: Long = System.currentTimeMillis(),
    val fechaDia: String = SimpleDateFormat("yyyy-MM-dd", Locale.US).format(Date()),
    val semanaId: String = SimpleDateFormat("yyyy-'W'ww", Locale.US).format(Date())
)

@Entity(tableName = "registro_bolas")
data class RegistroBola(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val empleadoId: String = "",
    val empleadoNombreSnapshot: String = "",
    val kg: Double = 0.0,
    // LOGICA FRACCIONAL: 5kg = 1 bola, 3kg = 0.6, 8kg = 1.6
    val bolas: Double = 0.0, // auto-calculado = kg / 5
    val pagoPorBolaSnapshot: Double = 8.0,
    val ganado: Double = 0.0, // auto-calculado = bolas * pago
    val hora: String = SimpleDateFormat("HH:mm", Locale.US).format(Date()),
    val entregado: Boolean = true,
    val entregadoPorId: String = "",
    val fechaLong: Long = System.currentTimeMillis(),
    val fechaDia: String = SimpleDateFormat("yyyy-MM-dd", Locale.US).format(Date()),
    val semanaId: String = SimpleDateFormat("yyyy-'W'ww", Locale.US).format(Date())
) {
    companion object {
        fun kgToBolas(kg: Double): Double {
            // 5kg = 1 bola -> 12.5kg=2.5, 8kg=1.6, 3kg=0.6
            return ((kg / 5.0) * 10.0).roundToInt() / 10.0
        }
        fun calcularGanado(kg: Double, pagoPorBola: Double): Double {
            return kgToBolas(kg) * pagoPorBola
        }
    }
}

@Dao
interface EmpleadoDao {
    @Query("SELECT * FROM empleados WHERE activo=1 ORDER BY nombre ASC")
    suspend fun obtenerTodos(): List<Empleado>

    @Query("SELECT * FROM empleados WHERE rolId='TORTILLERA' AND activo=1 ORDER BY nombre ASC")
    suspend fun obtenerTortilleras(): List<Empleado>

    @Query("SELECT * FROM empleados WHERE id=:id")
    suspend fun obtenerPorId(id: String): Empleado?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertar(e: Empleado)

    @Query("UPDATE empleados SET activo=0 WHERE id=:id")
    suspend fun desactivar(id: String)

    @Query("DELETE FROM empleados WHERE id=:id")
    suspend fun eliminarDefinitivo(id: String)
}

@Dao
interface RegistroBolaDao {
    @Query("SELECT * FROM registro_bolas WHERE fechaDia=:fechaDia ORDER BY fechaLong ASC")
    suspend fun obtenerPorDia(fechaDia: String): List<RegistroBola>

    @Query("SELECT * FROM registro_bolas WHERE semanaId=:semanaId ORDER BY fechaLong DESC")
    suspend fun obtenerPorSemana(semanaId: String): List<RegistroBola>

    @Query("SELECT * FROM registro_bolas WHERE empleadoId=:empleadoId AND fechaDia=:fechaDia")
    suspend fun obtenerPorEmpleadoYdia(empleadoId: String, fechaDia: String): List<RegistroBola>

    @Query("SELECT SUM(kg) FROM registro_bolas WHERE fechaDia=:fechaDia")
    suspend fun totalKgPorDia(fechaDia: String): Double?

    @Query("SELECT SUM(bolas) FROM registro_bolas WHERE fechaDia=:fechaDia")
    suspend fun totalBolasPorDia(fechaDia: String): Double?

    @Query("SELECT SUM(ganado) FROM registro_bolas WHERE fechaDia=:fechaDia")
    suspend fun totalGanadoPorDia(fechaDia: String): Double?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertar(r: RegistroBola)

    @Query("DELETE FROM registro_bolas WHERE id=:id")
    suspend fun eliminar(id: String)
}

// --- CATALOGO FIJO EXTENSO (TU CODIGO ORIGINAL) ---
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