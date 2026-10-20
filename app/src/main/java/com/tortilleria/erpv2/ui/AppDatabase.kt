package com.tortilleria.erpv2.data

import android.content.Context
import androidx.room.*

@Dao
interface EmpleadoDao {
    @Query("SELECT * FROM empleados WHERE activo = 1 ORDER BY nombre ASC")
    fun getActivos(): kotlinx.coroutines.flow.Flow<List<Empleado>>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(empleado: Empleado)
    @Query("SELECT * FROM empleados WHERE id = :id LIMIT 1")
    suspend fun getById(id: String): Empleado?
}

@Dao
interface ProduccionDao {
    @Query("SELECT * FROM produccion WHERE fechaDia = :fechaDia ORDER BY fechaLong DESC")
    fun getPorDia(fechaDia: String): kotlinx.coroutines.flow.Flow<List<RegistroBola>>
    @Query("SELECT * FROM produccion WHERE semanaId = :semanaId ORDER BY fechaLong DESC")
    fun getPorSemana(semanaId: String): kotlinx.coroutines.flow.Flow<List<RegistroBola>>
    @Query("SELECT SUM(cantidad) FROM produccion WHERE empleadoId = :empleadoId AND semanaId = :semanaId")
    suspend fun totalPorEmpleadoSemana(empleadoId: String, semanaId: String): Double?
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(registro: RegistroBola)
}

@Dao
interface VentaDao {
    @Query("SELECT * FROM ventas WHERE fechaDia = :fechaDia ORDER BY fechaLong DESC")
    fun getPorDia(fechaDia: String): kotlinx.coroutines.flow.Flow<List<Venta>>
    @Query("SELECT * FROM ventas WHERE semanaId = :semanaId ORDER BY folioGlobal DESC")
    fun getPorSemana(semanaId: String): kotlinx.coroutines.flow.Flow<List<Venta>>
    @Query("SELECT MAX(folioGlobal) FROM ventas")
    suspend fun getMaxFolioGlobal(): Int?
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(venta: Venta)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItems(items: List<VentaItem>)
}

@Dao
interface CajaDao {
    @Query("SELECT * FROM caja_movimientos WHERE fechaDia = :fechaDia ORDER BY fechaLong DESC")
    fun getPorDia(fechaDia: String): kotlinx.coroutines.flow.Flow<List<MovimientoCaja>>
    @Query("SELECT SUM(monto) FROM caja_movimientos WHERE fechaDia = :fechaDia AND tipo = :tipo")
    suspend fun totalPorTipoDia(fechaDia: String, tipo: String): Double?
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(mov: MovimientoCaja)
}

@Dao
interface ProductoDao {
    @Query("SELECT * FROM productos WHERE activo = 1")
    fun getAll(): kotlinx.coroutines.flow.Flow<List<Producto>>
    @Query("SELECT * FROM variantes WHERE productoId = :productoId AND stock > 0")
    fun getVariantes(productoId: String): kotlinx.coroutines.flow.Flow<List<VarianteProducto>>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProducto(p: Producto)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVariante(v: VarianteProducto)
}

@Database(
    entities = [
        Rol::class, Empleado::class, RegistroBola::class, Categoria::class,
        Proveedor::class, Producto::class, VarianteProducto::class, Cliente::class,
        CompraProveedor::class, Venta::class, VentaItem::class, MovimientoCaja::class,
        Gasto::class, Merma::class, CierreDia::class, CierreSemana::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun empleadoDao(): EmpleadoDao
    abstract fun produccionDao(): ProduccionDao
    abstract fun ventaDao(): VentaDao
    abstract fun cajaDao(): CajaDao
    abstract fun productoDao(): ProductoDao

    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null
        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, "tortilleria_erp_v2.db")
                    .fallbackToDestructiveMigration()
                    .build().also { INSTANCE = it }
            }
        }
    }
}

