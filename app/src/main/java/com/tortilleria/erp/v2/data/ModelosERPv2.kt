package com.tortilleria.erp.v2.data

import androidx.room.*
import java.util.UUID

object FechaUtils {
    fun ahoraLong(): Long = System.currentTimeMillis()
    fun fechaDia(): String = java.time.LocalDate.now().toString()
    fun semanaId(): String {
        val fecha = java.time.LocalDate.now()
        val week = fecha.get(java.time.temporal.WeekFields.ISO.weekOfYear())
        return "${fecha.year}-W${String.format("%02d", week)}"
    }
    fun mesId(): String {
        val f = java.time.LocalDate.now()
        return "${f.year}-${String.format("%02d", f.monthValue)}"
    }
}

enum class TipoMovimientoCaja { INGRESO, RETIRO, VENTA, GASTO, MERMA, COMPRA, PAGO_PROVEEDOR }
enum class MetodoPago { EFECTIVO, TRANSFERENCIA, FIADO }
enum class EstadoVenta { PAGADA, FIADA, CANCELADA }
enum class TurnoProduccion { MANANA, TARDE, MIXTO }

@Entity(tableName = "roles")
data class Rol(@PrimaryKey val id: String = UUID.randomUUID().toString(), val nombre: String = "", val permisos: String = "", val esSistema: Boolean = false, val activo: Boolean = true, val fechaDia: String = "")

@Entity(tableName = "empleados")
data class Empleado(@PrimaryKey val id: String = UUID.randomUUID().toString(), val rolId: String = "", val nombre: String = "", val telefono: String = "", val pagoPorBola: Double = 30.0, val activo: Boolean = true, val qrCode: String = "", val fechaLong: Long = System.currentTimeMillis(), val fechaDia: String = "", val semanaId: String = "", val mesId: String = "", val creadoPorId: String = "admin")

@Entity(tableName = "produccion")
data class RegistroBola(@PrimaryKey val id: String = UUID.randomUUID().toString(), val empleadoId: String = "", val empleadoNombreSnapshot: String = "", val cantidad: Double = 0.0, val turno: String = "MANANA", val fechaLong: Long = System.currentTimeMillis(), val fechaDia: String = "", val semanaId: String = "", val mesId: String = "", val entregadoPorId: String = "", val entregadoPorNombreSnapshot: String = "Admin")

@Entity(tableName = "categorias")
data class Categoria(@PrimaryKey val id: String = UUID.randomUUID().toString(), val nombre: String = "", val tipo: String = "PRODUCTO_FINAL", val activo: Boolean = true)

@Entity(tableName = "proveedores")
data class Proveedor(@PrimaryKey val id: String = UUID.randomUUID().toString(), val nombre: String = "", val telefono: String = "", val saldoDeuda: Double = 0.0, val activo: Boolean = true, val fechaDia: String = "")

@Entity(tableName = "productos")
data class Producto(@PrimaryKey val id: String = UUID.randomUUID().toString(), val nombre: String = "", val categoriaId: String = "", val proveedorId: String = "", val unidadBase: String = "pz", val precioBase: Double = 0.0, val activo: Boolean = true)

@Entity(tableName = "variantes")
data class VarianteProducto(@PrimaryKey val id: String = UUID.randomUUID().toString(), val productoId: String = "", val nombreVariante: String = "", val sku: String = "", val precioVenta: Double = 0.0, val precioMayoreo: Double = 0.0, val costo: Double = 0.0, val stock: Double = 0.0)

@Entity(tableName = "clientes")
data class Cliente(@PrimaryKey val id: String = UUID.randomUUID().toString(), val nombre: String = "PUBLICO GENERAL", val telefono: String = "", val saldoFiado: Double = 0.0, val limiteCredito: Double = 1000.0)

@Entity(tableName = "compras")
data class CompraProveedor(@PrimaryKey val id: String = UUID.randomUUID().toString(), val proveedorId: String = "", val proveedorNombreSnapshot: String = "", val fechaLong: Long = System.currentTimeMillis(), val fechaDia: String = "", val semanaId: String = "", val total: Double = 0.0, val pagado: Double = 0.0, val estado: String = "PENDIENTE")

@Entity(tableName = "ventas")
data class Venta(@PrimaryKey val id: String = UUID.randomUUID().toString(), val folioGlobal: Int = 0, val folioDiario: String = "", val folioSemanal: String = "", val fechaLong: Long = System.currentTimeMillis(), val fechaDia: String = "", val semanaId: String = "", val clienteId: String = "", val clienteNombreSnapshot: String = "PUBLICO GENERAL", val usuarioId: String = "", val total: Double = 0.0, val metodoPago: String = "EFECTIVO", val estado: String = "PAGADA")

@Entity(tableName = "venta_items")
data class VentaItem(@PrimaryKey val id: String = UUID.randomUUID().toString(), val ventaId: String = "", val productoId: String = "", val varianteId: String = "", val nombreSnapshot: String = "", val cantidad: Double = 0.0, val precioUnitarioSnapshot: Double = 0.0, val subtotal: Double = 0.0)

@Entity(tableName = "caja_movimientos")
data class MovimientoCaja(@PrimaryKey val id: String = UUID.randomUUID().toString(), val fechaLong: Long = System.currentTimeMillis(), val fechaDia: String = "", val semanaId: String = "", val tipo: String = "VENTA", val categoria: String = "", val monto: Double = 0.0, val motivo: String = "", val referenciaId: String = "", val usuarioId: String = "")

@Entity(tableName = "gastos")
data class Gasto(@PrimaryKey val id: String = UUID.randomUUID().toString(), val fechaLong: Long = System.currentTimeMillis(), val fechaDia: String = "", val semanaId: String = "", val categoria: String = "VARIOS", val monto: Double = 0.0, val descripcion: String = "")

@Entity(tableName = "mermas")
data class Merma(@PrimaryKey val id: String = UUID.randomUUID().toString(), val fechaLong: Long = System.currentTimeMillis(), val fechaDia: String = "", val semanaId: String = "", val productoId: String = "", val varianteId: String = "", val cantidad: Double = 0.0, val motivo: String = "QUEMADO")

@Entity(tableName = "cierres_dia")
data class CierreDia(@PrimaryKey val id: String = UUID.randomUUID().toString(), val fechaDia: String = "", val semanaId: String = "", val totalVentas: Double = 0.0, val totalGastos: Double = 0.0, val saldoFinal: Double = 0.0, val cerradoPorId: String = "")

@Entity(tableName = "cierres_semana")
data class CierreSemana(@PrimaryKey val id: String = UUID.randomUUID().toString(), val semanaId: String = "", val totalProduccion: Double = 0.0, val totalVentas: Double = 0.0, val utilidad: Double = 0.0)
