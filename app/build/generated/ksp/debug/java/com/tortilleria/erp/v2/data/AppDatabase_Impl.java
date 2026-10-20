package com.tortilleria.erp.v2.data;

import androidx.annotation.NonNull;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomOpenHelper;
import androidx.room.RoomOpenHelper.Delegate;
import androidx.room.RoomOpenHelper.ValidationResult;
import androidx.room.migration.AutoMigrationSpec;
import androidx.room.migration.Migration;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.room.util.TableInfo.Column;
import androidx.room.util.TableInfo.ForeignKey;
import androidx.room.util.TableInfo.Index;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import androidx.sqlite.db.SupportSQLiteOpenHelper.Callback;
import androidx.sqlite.db.SupportSQLiteOpenHelper.Configuration;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class AppDatabase_Impl extends AppDatabase {
  private volatile EmpleadoDao _empleadoDao;

  private volatile ProduccionDao _produccionDao;

  private volatile VentaDao _ventaDao;

  private volatile CajaDao _cajaDao;

  private volatile ProductoDao _productoDao;

  @Override
  protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration configuration) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(configuration, new RoomOpenHelper.Delegate(1) {
      @Override
      public void createAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("CREATE TABLE IF NOT EXISTS `roles` (`id` TEXT NOT NULL, `nombre` TEXT NOT NULL, `permisos` TEXT NOT NULL, `esSistema` INTEGER NOT NULL, `activo` INTEGER NOT NULL, `fechaDia` TEXT NOT NULL, PRIMARY KEY(`id`))");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `empleados` (`id` TEXT NOT NULL, `rolId` TEXT NOT NULL, `nombre` TEXT NOT NULL, `telefono` TEXT NOT NULL, `pagoPorBola` REAL NOT NULL, `activo` INTEGER NOT NULL, `qrCode` TEXT NOT NULL, `fechaLong` INTEGER NOT NULL, `fechaDia` TEXT NOT NULL, `semanaId` TEXT NOT NULL, `mesId` TEXT NOT NULL, `creadoPorId` TEXT NOT NULL, PRIMARY KEY(`id`))");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `produccion` (`id` TEXT NOT NULL, `empleadoId` TEXT NOT NULL, `empleadoNombreSnapshot` TEXT NOT NULL, `cantidad` REAL NOT NULL, `turno` TEXT NOT NULL, `fechaLong` INTEGER NOT NULL, `fechaDia` TEXT NOT NULL, `semanaId` TEXT NOT NULL, `mesId` TEXT NOT NULL, `entregadoPorId` TEXT NOT NULL, `entregadoPorNombreSnapshot` TEXT NOT NULL, PRIMARY KEY(`id`))");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `categorias` (`id` TEXT NOT NULL, `nombre` TEXT NOT NULL, `tipo` TEXT NOT NULL, `activo` INTEGER NOT NULL, PRIMARY KEY(`id`))");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `proveedores` (`id` TEXT NOT NULL, `nombre` TEXT NOT NULL, `telefono` TEXT NOT NULL, `saldoDeuda` REAL NOT NULL, `activo` INTEGER NOT NULL, `fechaDia` TEXT NOT NULL, PRIMARY KEY(`id`))");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `productos` (`id` TEXT NOT NULL, `nombre` TEXT NOT NULL, `categoriaId` TEXT NOT NULL, `proveedorId` TEXT NOT NULL, `unidadBase` TEXT NOT NULL, `precioBase` REAL NOT NULL, `activo` INTEGER NOT NULL, PRIMARY KEY(`id`))");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `variantes` (`id` TEXT NOT NULL, `productoId` TEXT NOT NULL, `nombreVariante` TEXT NOT NULL, `sku` TEXT NOT NULL, `precioVenta` REAL NOT NULL, `precioMayoreo` REAL NOT NULL, `costo` REAL NOT NULL, `stock` REAL NOT NULL, PRIMARY KEY(`id`))");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `clientes` (`id` TEXT NOT NULL, `nombre` TEXT NOT NULL, `telefono` TEXT NOT NULL, `saldoFiado` REAL NOT NULL, `limiteCredito` REAL NOT NULL, PRIMARY KEY(`id`))");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `compras` (`id` TEXT NOT NULL, `proveedorId` TEXT NOT NULL, `proveedorNombreSnapshot` TEXT NOT NULL, `fechaLong` INTEGER NOT NULL, `fechaDia` TEXT NOT NULL, `semanaId` TEXT NOT NULL, `total` REAL NOT NULL, `pagado` REAL NOT NULL, `estado` TEXT NOT NULL, PRIMARY KEY(`id`))");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `ventas` (`id` TEXT NOT NULL, `folioGlobal` INTEGER NOT NULL, `folioDiario` TEXT NOT NULL, `folioSemanal` TEXT NOT NULL, `fechaLong` INTEGER NOT NULL, `fechaDia` TEXT NOT NULL, `semanaId` TEXT NOT NULL, `clienteId` TEXT NOT NULL, `clienteNombreSnapshot` TEXT NOT NULL, `usuarioId` TEXT NOT NULL, `total` REAL NOT NULL, `metodoPago` TEXT NOT NULL, `estado` TEXT NOT NULL, PRIMARY KEY(`id`))");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `venta_items` (`id` TEXT NOT NULL, `ventaId` TEXT NOT NULL, `productoId` TEXT NOT NULL, `varianteId` TEXT NOT NULL, `nombreSnapshot` TEXT NOT NULL, `cantidad` REAL NOT NULL, `precioUnitarioSnapshot` REAL NOT NULL, `subtotal` REAL NOT NULL, PRIMARY KEY(`id`))");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `caja_movimientos` (`id` TEXT NOT NULL, `fechaLong` INTEGER NOT NULL, `fechaDia` TEXT NOT NULL, `semanaId` TEXT NOT NULL, `tipo` TEXT NOT NULL, `categoria` TEXT NOT NULL, `monto` REAL NOT NULL, `motivo` TEXT NOT NULL, `referenciaId` TEXT NOT NULL, `usuarioId` TEXT NOT NULL, PRIMARY KEY(`id`))");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `gastos` (`id` TEXT NOT NULL, `fechaLong` INTEGER NOT NULL, `fechaDia` TEXT NOT NULL, `semanaId` TEXT NOT NULL, `categoria` TEXT NOT NULL, `monto` REAL NOT NULL, `descripcion` TEXT NOT NULL, PRIMARY KEY(`id`))");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `mermas` (`id` TEXT NOT NULL, `fechaLong` INTEGER NOT NULL, `fechaDia` TEXT NOT NULL, `semanaId` TEXT NOT NULL, `productoId` TEXT NOT NULL, `varianteId` TEXT NOT NULL, `cantidad` REAL NOT NULL, `motivo` TEXT NOT NULL, PRIMARY KEY(`id`))");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `cierres_dia` (`id` TEXT NOT NULL, `fechaDia` TEXT NOT NULL, `semanaId` TEXT NOT NULL, `totalVentas` REAL NOT NULL, `totalGastos` REAL NOT NULL, `saldoFinal` REAL NOT NULL, `cerradoPorId` TEXT NOT NULL, PRIMARY KEY(`id`))");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `cierres_semana` (`id` TEXT NOT NULL, `semanaId` TEXT NOT NULL, `totalProduccion` REAL NOT NULL, `totalVentas` REAL NOT NULL, `utilidad` REAL NOT NULL, PRIMARY KEY(`id`))");
        _db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        _db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, '9cd1eec4595d299fde98ac067881b5ba')");
      }

      @Override
      public void dropAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("DROP TABLE IF EXISTS `roles`");
        _db.execSQL("DROP TABLE IF EXISTS `empleados`");
        _db.execSQL("DROP TABLE IF EXISTS `produccion`");
        _db.execSQL("DROP TABLE IF EXISTS `categorias`");
        _db.execSQL("DROP TABLE IF EXISTS `proveedores`");
        _db.execSQL("DROP TABLE IF EXISTS `productos`");
        _db.execSQL("DROP TABLE IF EXISTS `variantes`");
        _db.execSQL("DROP TABLE IF EXISTS `clientes`");
        _db.execSQL("DROP TABLE IF EXISTS `compras`");
        _db.execSQL("DROP TABLE IF EXISTS `ventas`");
        _db.execSQL("DROP TABLE IF EXISTS `venta_items`");
        _db.execSQL("DROP TABLE IF EXISTS `caja_movimientos`");
        _db.execSQL("DROP TABLE IF EXISTS `gastos`");
        _db.execSQL("DROP TABLE IF EXISTS `mermas`");
        _db.execSQL("DROP TABLE IF EXISTS `cierres_dia`");
        _db.execSQL("DROP TABLE IF EXISTS `cierres_semana`");
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onDestructiveMigration(_db);
          }
        }
      }

      @Override
      public void onCreate(SupportSQLiteDatabase _db) {
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onCreate(_db);
          }
        }
      }

      @Override
      public void onOpen(SupportSQLiteDatabase _db) {
        mDatabase = _db;
        internalInitInvalidationTracker(_db);
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onOpen(_db);
          }
        }
      }

      @Override
      public void onPreMigrate(SupportSQLiteDatabase _db) {
        DBUtil.dropFtsSyncTriggers(_db);
      }

      @Override
      public void onPostMigrate(SupportSQLiteDatabase _db) {
      }

      @Override
      public RoomOpenHelper.ValidationResult onValidateSchema(SupportSQLiteDatabase _db) {
        final HashMap<String, TableInfo.Column> _columnsRoles = new HashMap<String, TableInfo.Column>(6);
        _columnsRoles.put("id", new TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRoles.put("nombre", new TableInfo.Column("nombre", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRoles.put("permisos", new TableInfo.Column("permisos", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRoles.put("esSistema", new TableInfo.Column("esSistema", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRoles.put("activo", new TableInfo.Column("activo", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRoles.put("fechaDia", new TableInfo.Column("fechaDia", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysRoles = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesRoles = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoRoles = new TableInfo("roles", _columnsRoles, _foreignKeysRoles, _indicesRoles);
        final TableInfo _existingRoles = TableInfo.read(_db, "roles");
        if (! _infoRoles.equals(_existingRoles)) {
          return new RoomOpenHelper.ValidationResult(false, "roles(com.tortilleria.erp.v2.data.Rol).\n"
                  + " Expected:\n" + _infoRoles + "\n"
                  + " Found:\n" + _existingRoles);
        }
        final HashMap<String, TableInfo.Column> _columnsEmpleados = new HashMap<String, TableInfo.Column>(12);
        _columnsEmpleados.put("id", new TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsEmpleados.put("rolId", new TableInfo.Column("rolId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsEmpleados.put("nombre", new TableInfo.Column("nombre", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsEmpleados.put("telefono", new TableInfo.Column("telefono", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsEmpleados.put("pagoPorBola", new TableInfo.Column("pagoPorBola", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsEmpleados.put("activo", new TableInfo.Column("activo", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsEmpleados.put("qrCode", new TableInfo.Column("qrCode", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsEmpleados.put("fechaLong", new TableInfo.Column("fechaLong", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsEmpleados.put("fechaDia", new TableInfo.Column("fechaDia", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsEmpleados.put("semanaId", new TableInfo.Column("semanaId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsEmpleados.put("mesId", new TableInfo.Column("mesId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsEmpleados.put("creadoPorId", new TableInfo.Column("creadoPorId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysEmpleados = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesEmpleados = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoEmpleados = new TableInfo("empleados", _columnsEmpleados, _foreignKeysEmpleados, _indicesEmpleados);
        final TableInfo _existingEmpleados = TableInfo.read(_db, "empleados");
        if (! _infoEmpleados.equals(_existingEmpleados)) {
          return new RoomOpenHelper.ValidationResult(false, "empleados(com.tortilleria.erp.v2.data.Empleado).\n"
                  + " Expected:\n" + _infoEmpleados + "\n"
                  + " Found:\n" + _existingEmpleados);
        }
        final HashMap<String, TableInfo.Column> _columnsProduccion = new HashMap<String, TableInfo.Column>(11);
        _columnsProduccion.put("id", new TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProduccion.put("empleadoId", new TableInfo.Column("empleadoId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProduccion.put("empleadoNombreSnapshot", new TableInfo.Column("empleadoNombreSnapshot", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProduccion.put("cantidad", new TableInfo.Column("cantidad", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProduccion.put("turno", new TableInfo.Column("turno", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProduccion.put("fechaLong", new TableInfo.Column("fechaLong", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProduccion.put("fechaDia", new TableInfo.Column("fechaDia", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProduccion.put("semanaId", new TableInfo.Column("semanaId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProduccion.put("mesId", new TableInfo.Column("mesId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProduccion.put("entregadoPorId", new TableInfo.Column("entregadoPorId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProduccion.put("entregadoPorNombreSnapshot", new TableInfo.Column("entregadoPorNombreSnapshot", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysProduccion = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesProduccion = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoProduccion = new TableInfo("produccion", _columnsProduccion, _foreignKeysProduccion, _indicesProduccion);
        final TableInfo _existingProduccion = TableInfo.read(_db, "produccion");
        if (! _infoProduccion.equals(_existingProduccion)) {
          return new RoomOpenHelper.ValidationResult(false, "produccion(com.tortilleria.erp.v2.data.RegistroBola).\n"
                  + " Expected:\n" + _infoProduccion + "\n"
                  + " Found:\n" + _existingProduccion);
        }
        final HashMap<String, TableInfo.Column> _columnsCategorias = new HashMap<String, TableInfo.Column>(4);
        _columnsCategorias.put("id", new TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCategorias.put("nombre", new TableInfo.Column("nombre", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCategorias.put("tipo", new TableInfo.Column("tipo", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCategorias.put("activo", new TableInfo.Column("activo", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysCategorias = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesCategorias = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoCategorias = new TableInfo("categorias", _columnsCategorias, _foreignKeysCategorias, _indicesCategorias);
        final TableInfo _existingCategorias = TableInfo.read(_db, "categorias");
        if (! _infoCategorias.equals(_existingCategorias)) {
          return new RoomOpenHelper.ValidationResult(false, "categorias(com.tortilleria.erp.v2.data.Categoria).\n"
                  + " Expected:\n" + _infoCategorias + "\n"
                  + " Found:\n" + _existingCategorias);
        }
        final HashMap<String, TableInfo.Column> _columnsProveedores = new HashMap<String, TableInfo.Column>(6);
        _columnsProveedores.put("id", new TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProveedores.put("nombre", new TableInfo.Column("nombre", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProveedores.put("telefono", new TableInfo.Column("telefono", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProveedores.put("saldoDeuda", new TableInfo.Column("saldoDeuda", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProveedores.put("activo", new TableInfo.Column("activo", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProveedores.put("fechaDia", new TableInfo.Column("fechaDia", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysProveedores = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesProveedores = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoProveedores = new TableInfo("proveedores", _columnsProveedores, _foreignKeysProveedores, _indicesProveedores);
        final TableInfo _existingProveedores = TableInfo.read(_db, "proveedores");
        if (! _infoProveedores.equals(_existingProveedores)) {
          return new RoomOpenHelper.ValidationResult(false, "proveedores(com.tortilleria.erp.v2.data.Proveedor).\n"
                  + " Expected:\n" + _infoProveedores + "\n"
                  + " Found:\n" + _existingProveedores);
        }
        final HashMap<String, TableInfo.Column> _columnsProductos = new HashMap<String, TableInfo.Column>(7);
        _columnsProductos.put("id", new TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProductos.put("nombre", new TableInfo.Column("nombre", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProductos.put("categoriaId", new TableInfo.Column("categoriaId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProductos.put("proveedorId", new TableInfo.Column("proveedorId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProductos.put("unidadBase", new TableInfo.Column("unidadBase", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProductos.put("precioBase", new TableInfo.Column("precioBase", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProductos.put("activo", new TableInfo.Column("activo", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysProductos = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesProductos = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoProductos = new TableInfo("productos", _columnsProductos, _foreignKeysProductos, _indicesProductos);
        final TableInfo _existingProductos = TableInfo.read(_db, "productos");
        if (! _infoProductos.equals(_existingProductos)) {
          return new RoomOpenHelper.ValidationResult(false, "productos(com.tortilleria.erp.v2.data.Producto).\n"
                  + " Expected:\n" + _infoProductos + "\n"
                  + " Found:\n" + _existingProductos);
        }
        final HashMap<String, TableInfo.Column> _columnsVariantes = new HashMap<String, TableInfo.Column>(8);
        _columnsVariantes.put("id", new TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVariantes.put("productoId", new TableInfo.Column("productoId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVariantes.put("nombreVariante", new TableInfo.Column("nombreVariante", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVariantes.put("sku", new TableInfo.Column("sku", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVariantes.put("precioVenta", new TableInfo.Column("precioVenta", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVariantes.put("precioMayoreo", new TableInfo.Column("precioMayoreo", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVariantes.put("costo", new TableInfo.Column("costo", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVariantes.put("stock", new TableInfo.Column("stock", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysVariantes = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesVariantes = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoVariantes = new TableInfo("variantes", _columnsVariantes, _foreignKeysVariantes, _indicesVariantes);
        final TableInfo _existingVariantes = TableInfo.read(_db, "variantes");
        if (! _infoVariantes.equals(_existingVariantes)) {
          return new RoomOpenHelper.ValidationResult(false, "variantes(com.tortilleria.erp.v2.data.VarianteProducto).\n"
                  + " Expected:\n" + _infoVariantes + "\n"
                  + " Found:\n" + _existingVariantes);
        }
        final HashMap<String, TableInfo.Column> _columnsClientes = new HashMap<String, TableInfo.Column>(5);
        _columnsClientes.put("id", new TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsClientes.put("nombre", new TableInfo.Column("nombre", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsClientes.put("telefono", new TableInfo.Column("telefono", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsClientes.put("saldoFiado", new TableInfo.Column("saldoFiado", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsClientes.put("limiteCredito", new TableInfo.Column("limiteCredito", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysClientes = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesClientes = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoClientes = new TableInfo("clientes", _columnsClientes, _foreignKeysClientes, _indicesClientes);
        final TableInfo _existingClientes = TableInfo.read(_db, "clientes");
        if (! _infoClientes.equals(_existingClientes)) {
          return new RoomOpenHelper.ValidationResult(false, "clientes(com.tortilleria.erp.v2.data.Cliente).\n"
                  + " Expected:\n" + _infoClientes + "\n"
                  + " Found:\n" + _existingClientes);
        }
        final HashMap<String, TableInfo.Column> _columnsCompras = new HashMap<String, TableInfo.Column>(9);
        _columnsCompras.put("id", new TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCompras.put("proveedorId", new TableInfo.Column("proveedorId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCompras.put("proveedorNombreSnapshot", new TableInfo.Column("proveedorNombreSnapshot", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCompras.put("fechaLong", new TableInfo.Column("fechaLong", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCompras.put("fechaDia", new TableInfo.Column("fechaDia", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCompras.put("semanaId", new TableInfo.Column("semanaId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCompras.put("total", new TableInfo.Column("total", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCompras.put("pagado", new TableInfo.Column("pagado", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCompras.put("estado", new TableInfo.Column("estado", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysCompras = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesCompras = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoCompras = new TableInfo("compras", _columnsCompras, _foreignKeysCompras, _indicesCompras);
        final TableInfo _existingCompras = TableInfo.read(_db, "compras");
        if (! _infoCompras.equals(_existingCompras)) {
          return new RoomOpenHelper.ValidationResult(false, "compras(com.tortilleria.erp.v2.data.CompraProveedor).\n"
                  + " Expected:\n" + _infoCompras + "\n"
                  + " Found:\n" + _existingCompras);
        }
        final HashMap<String, TableInfo.Column> _columnsVentas = new HashMap<String, TableInfo.Column>(13);
        _columnsVentas.put("id", new TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVentas.put("folioGlobal", new TableInfo.Column("folioGlobal", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVentas.put("folioDiario", new TableInfo.Column("folioDiario", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVentas.put("folioSemanal", new TableInfo.Column("folioSemanal", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVentas.put("fechaLong", new TableInfo.Column("fechaLong", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVentas.put("fechaDia", new TableInfo.Column("fechaDia", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVentas.put("semanaId", new TableInfo.Column("semanaId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVentas.put("clienteId", new TableInfo.Column("clienteId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVentas.put("clienteNombreSnapshot", new TableInfo.Column("clienteNombreSnapshot", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVentas.put("usuarioId", new TableInfo.Column("usuarioId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVentas.put("total", new TableInfo.Column("total", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVentas.put("metodoPago", new TableInfo.Column("metodoPago", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVentas.put("estado", new TableInfo.Column("estado", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysVentas = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesVentas = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoVentas = new TableInfo("ventas", _columnsVentas, _foreignKeysVentas, _indicesVentas);
        final TableInfo _existingVentas = TableInfo.read(_db, "ventas");
        if (! _infoVentas.equals(_existingVentas)) {
          return new RoomOpenHelper.ValidationResult(false, "ventas(com.tortilleria.erp.v2.data.Venta).\n"
                  + " Expected:\n" + _infoVentas + "\n"
                  + " Found:\n" + _existingVentas);
        }
        final HashMap<String, TableInfo.Column> _columnsVentaItems = new HashMap<String, TableInfo.Column>(8);
        _columnsVentaItems.put("id", new TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVentaItems.put("ventaId", new TableInfo.Column("ventaId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVentaItems.put("productoId", new TableInfo.Column("productoId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVentaItems.put("varianteId", new TableInfo.Column("varianteId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVentaItems.put("nombreSnapshot", new TableInfo.Column("nombreSnapshot", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVentaItems.put("cantidad", new TableInfo.Column("cantidad", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVentaItems.put("precioUnitarioSnapshot", new TableInfo.Column("precioUnitarioSnapshot", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVentaItems.put("subtotal", new TableInfo.Column("subtotal", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysVentaItems = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesVentaItems = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoVentaItems = new TableInfo("venta_items", _columnsVentaItems, _foreignKeysVentaItems, _indicesVentaItems);
        final TableInfo _existingVentaItems = TableInfo.read(_db, "venta_items");
        if (! _infoVentaItems.equals(_existingVentaItems)) {
          return new RoomOpenHelper.ValidationResult(false, "venta_items(com.tortilleria.erp.v2.data.VentaItem).\n"
                  + " Expected:\n" + _infoVentaItems + "\n"
                  + " Found:\n" + _existingVentaItems);
        }
        final HashMap<String, TableInfo.Column> _columnsCajaMovimientos = new HashMap<String, TableInfo.Column>(10);
        _columnsCajaMovimientos.put("id", new TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCajaMovimientos.put("fechaLong", new TableInfo.Column("fechaLong", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCajaMovimientos.put("fechaDia", new TableInfo.Column("fechaDia", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCajaMovimientos.put("semanaId", new TableInfo.Column("semanaId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCajaMovimientos.put("tipo", new TableInfo.Column("tipo", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCajaMovimientos.put("categoria", new TableInfo.Column("categoria", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCajaMovimientos.put("monto", new TableInfo.Column("monto", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCajaMovimientos.put("motivo", new TableInfo.Column("motivo", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCajaMovimientos.put("referenciaId", new TableInfo.Column("referenciaId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCajaMovimientos.put("usuarioId", new TableInfo.Column("usuarioId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysCajaMovimientos = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesCajaMovimientos = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoCajaMovimientos = new TableInfo("caja_movimientos", _columnsCajaMovimientos, _foreignKeysCajaMovimientos, _indicesCajaMovimientos);
        final TableInfo _existingCajaMovimientos = TableInfo.read(_db, "caja_movimientos");
        if (! _infoCajaMovimientos.equals(_existingCajaMovimientos)) {
          return new RoomOpenHelper.ValidationResult(false, "caja_movimientos(com.tortilleria.erp.v2.data.MovimientoCaja).\n"
                  + " Expected:\n" + _infoCajaMovimientos + "\n"
                  + " Found:\n" + _existingCajaMovimientos);
        }
        final HashMap<String, TableInfo.Column> _columnsGastos = new HashMap<String, TableInfo.Column>(7);
        _columnsGastos.put("id", new TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsGastos.put("fechaLong", new TableInfo.Column("fechaLong", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsGastos.put("fechaDia", new TableInfo.Column("fechaDia", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsGastos.put("semanaId", new TableInfo.Column("semanaId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsGastos.put("categoria", new TableInfo.Column("categoria", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsGastos.put("monto", new TableInfo.Column("monto", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsGastos.put("descripcion", new TableInfo.Column("descripcion", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysGastos = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesGastos = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoGastos = new TableInfo("gastos", _columnsGastos, _foreignKeysGastos, _indicesGastos);
        final TableInfo _existingGastos = TableInfo.read(_db, "gastos");
        if (! _infoGastos.equals(_existingGastos)) {
          return new RoomOpenHelper.ValidationResult(false, "gastos(com.tortilleria.erp.v2.data.Gasto).\n"
                  + " Expected:\n" + _infoGastos + "\n"
                  + " Found:\n" + _existingGastos);
        }
        final HashMap<String, TableInfo.Column> _columnsMermas = new HashMap<String, TableInfo.Column>(8);
        _columnsMermas.put("id", new TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMermas.put("fechaLong", new TableInfo.Column("fechaLong", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMermas.put("fechaDia", new TableInfo.Column("fechaDia", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMermas.put("semanaId", new TableInfo.Column("semanaId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMermas.put("productoId", new TableInfo.Column("productoId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMermas.put("varianteId", new TableInfo.Column("varianteId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMermas.put("cantidad", new TableInfo.Column("cantidad", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMermas.put("motivo", new TableInfo.Column("motivo", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysMermas = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesMermas = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoMermas = new TableInfo("mermas", _columnsMermas, _foreignKeysMermas, _indicesMermas);
        final TableInfo _existingMermas = TableInfo.read(_db, "mermas");
        if (! _infoMermas.equals(_existingMermas)) {
          return new RoomOpenHelper.ValidationResult(false, "mermas(com.tortilleria.erp.v2.data.Merma).\n"
                  + " Expected:\n" + _infoMermas + "\n"
                  + " Found:\n" + _existingMermas);
        }
        final HashMap<String, TableInfo.Column> _columnsCierresDia = new HashMap<String, TableInfo.Column>(7);
        _columnsCierresDia.put("id", new TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCierresDia.put("fechaDia", new TableInfo.Column("fechaDia", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCierresDia.put("semanaId", new TableInfo.Column("semanaId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCierresDia.put("totalVentas", new TableInfo.Column("totalVentas", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCierresDia.put("totalGastos", new TableInfo.Column("totalGastos", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCierresDia.put("saldoFinal", new TableInfo.Column("saldoFinal", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCierresDia.put("cerradoPorId", new TableInfo.Column("cerradoPorId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysCierresDia = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesCierresDia = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoCierresDia = new TableInfo("cierres_dia", _columnsCierresDia, _foreignKeysCierresDia, _indicesCierresDia);
        final TableInfo _existingCierresDia = TableInfo.read(_db, "cierres_dia");
        if (! _infoCierresDia.equals(_existingCierresDia)) {
          return new RoomOpenHelper.ValidationResult(false, "cierres_dia(com.tortilleria.erp.v2.data.CierreDia).\n"
                  + " Expected:\n" + _infoCierresDia + "\n"
                  + " Found:\n" + _existingCierresDia);
        }
        final HashMap<String, TableInfo.Column> _columnsCierresSemana = new HashMap<String, TableInfo.Column>(5);
        _columnsCierresSemana.put("id", new TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCierresSemana.put("semanaId", new TableInfo.Column("semanaId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCierresSemana.put("totalProduccion", new TableInfo.Column("totalProduccion", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCierresSemana.put("totalVentas", new TableInfo.Column("totalVentas", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCierresSemana.put("utilidad", new TableInfo.Column("utilidad", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysCierresSemana = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesCierresSemana = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoCierresSemana = new TableInfo("cierres_semana", _columnsCierresSemana, _foreignKeysCierresSemana, _indicesCierresSemana);
        final TableInfo _existingCierresSemana = TableInfo.read(_db, "cierres_semana");
        if (! _infoCierresSemana.equals(_existingCierresSemana)) {
          return new RoomOpenHelper.ValidationResult(false, "cierres_semana(com.tortilleria.erp.v2.data.CierreSemana).\n"
                  + " Expected:\n" + _infoCierresSemana + "\n"
                  + " Found:\n" + _existingCierresSemana);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "9cd1eec4595d299fde98ac067881b5ba", "1e352a73926b6d1aa6d99e4cca3ee0d9");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(configuration.context)
        .name(configuration.name)
        .callback(_openCallback)
        .build();
    final SupportSQLiteOpenHelper _helper = configuration.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "roles","empleados","produccion","categorias","proveedores","productos","variantes","clientes","compras","ventas","venta_items","caja_movimientos","gastos","mermas","cierres_dia","cierres_semana");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    try {
      super.beginTransaction();
      _db.execSQL("DELETE FROM `roles`");
      _db.execSQL("DELETE FROM `empleados`");
      _db.execSQL("DELETE FROM `produccion`");
      _db.execSQL("DELETE FROM `categorias`");
      _db.execSQL("DELETE FROM `proveedores`");
      _db.execSQL("DELETE FROM `productos`");
      _db.execSQL("DELETE FROM `variantes`");
      _db.execSQL("DELETE FROM `clientes`");
      _db.execSQL("DELETE FROM `compras`");
      _db.execSQL("DELETE FROM `ventas`");
      _db.execSQL("DELETE FROM `venta_items`");
      _db.execSQL("DELETE FROM `caja_movimientos`");
      _db.execSQL("DELETE FROM `gastos`");
      _db.execSQL("DELETE FROM `mermas`");
      _db.execSQL("DELETE FROM `cierres_dia`");
      _db.execSQL("DELETE FROM `cierres_semana`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  protected Map<Class<?>, List<Class<?>>> getRequiredTypeConverters() {
    final HashMap<Class<?>, List<Class<?>>> _typeConvertersMap = new HashMap<Class<?>, List<Class<?>>>();
    _typeConvertersMap.put(EmpleadoDao.class, EmpleadoDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(ProduccionDao.class, ProduccionDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(VentaDao.class, VentaDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(CajaDao.class, CajaDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(ProductoDao.class, ProductoDao_Impl.getRequiredConverters());
    return _typeConvertersMap;
  }

  @Override
  public Set<Class<? extends AutoMigrationSpec>> getRequiredAutoMigrationSpecs() {
    final HashSet<Class<? extends AutoMigrationSpec>> _autoMigrationSpecsSet = new HashSet<Class<? extends AutoMigrationSpec>>();
    return _autoMigrationSpecsSet;
  }

  @Override
  public List<Migration> getAutoMigrations(
      @NonNull Map<Class<? extends AutoMigrationSpec>, AutoMigrationSpec> autoMigrationSpecsMap) {
    return Arrays.asList();
  }

  @Override
  public EmpleadoDao empleadoDao() {
    if (_empleadoDao != null) {
      return _empleadoDao;
    } else {
      synchronized(this) {
        if(_empleadoDao == null) {
          _empleadoDao = new EmpleadoDao_Impl(this);
        }
        return _empleadoDao;
      }
    }
  }

  @Override
  public ProduccionDao produccionDao() {
    if (_produccionDao != null) {
      return _produccionDao;
    } else {
      synchronized(this) {
        if(_produccionDao == null) {
          _produccionDao = new ProduccionDao_Impl(this);
        }
        return _produccionDao;
      }
    }
  }

  @Override
  public VentaDao ventaDao() {
    if (_ventaDao != null) {
      return _ventaDao;
    } else {
      synchronized(this) {
        if(_ventaDao == null) {
          _ventaDao = new VentaDao_Impl(this);
        }
        return _ventaDao;
      }
    }
  }

  @Override
  public CajaDao cajaDao() {
    if (_cajaDao != null) {
      return _cajaDao;
    } else {
      synchronized(this) {
        if(_cajaDao == null) {
          _cajaDao = new CajaDao_Impl(this);
        }
        return _cajaDao;
      }
    }
  }

  @Override
  public ProductoDao productoDao() {
    if (_productoDao != null) {
      return _productoDao;
    } else {
      synchronized(this) {
        if(_productoDao == null) {
          _productoDao = new ProductoDao_Impl(this);
        }
        return _productoDao;
      }
    }
  }
}
