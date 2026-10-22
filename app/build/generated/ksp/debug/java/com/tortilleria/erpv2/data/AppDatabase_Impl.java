package com.tortilleria.erpv2.data;

import androidx.annotation.NonNull;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.room.RoomOpenHelper;
import androidx.room.migration.AutoMigrationSpec;
import androidx.room.migration.Migration;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class AppDatabase_Impl extends AppDatabase {
  private volatile TicketDao _ticketDao;

  private volatile EmpleadoDao _empleadoDao;

  private volatile RegistroBolaDao _registroBolaDao;

  @Override
  @NonNull
  protected SupportSQLiteOpenHelper createOpenHelper(@NonNull final DatabaseConfiguration config) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(config, new RoomOpenHelper.Delegate(6) {
      @Override
      public void createAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS `tickets` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `total` REAL NOT NULL, `tipo` TEXT NOT NULL, `cliente` TEXT NOT NULL, `fecha` INTEGER NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `ticket_detalle` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `ticketId` INTEGER NOT NULL, `producto` TEXT NOT NULL, `variante` TEXT NOT NULL, `kilos` REAL NOT NULL, `precioKg` REAL NOT NULL, `total` REAL NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `empleados` (`id` TEXT NOT NULL, `nombre` TEXT NOT NULL, `rolId` TEXT NOT NULL, `rolNombreSnapshot` TEXT NOT NULL, `pagoPorBola` REAL NOT NULL, `telefono` TEXT NOT NULL, `qrToken` TEXT NOT NULL, `qrActivo` INTEGER NOT NULL, `activo` INTEGER NOT NULL, `fechaLong` INTEGER NOT NULL, `fechaDia` TEXT NOT NULL, `semanaId` TEXT NOT NULL, PRIMARY KEY(`id`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS `registro_bolas` (`id` TEXT NOT NULL, `empleadoId` TEXT NOT NULL, `empleadoNombreSnapshot` TEXT NOT NULL, `kg` REAL NOT NULL, `bolas` REAL NOT NULL, `pagoPorBolaSnapshot` REAL NOT NULL, `ganado` REAL NOT NULL, `hora` TEXT NOT NULL, `entregado` INTEGER NOT NULL, `entregadoPorId` TEXT NOT NULL, `fechaLong` INTEGER NOT NULL, `fechaDia` TEXT NOT NULL, `semanaId` TEXT NOT NULL, PRIMARY KEY(`id`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, '38bb084d4f2baf24f6e66c078a0911ac')");
      }

      @Override
      public void dropAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS `tickets`");
        db.execSQL("DROP TABLE IF EXISTS `ticket_detalle`");
        db.execSQL("DROP TABLE IF EXISTS `empleados`");
        db.execSQL("DROP TABLE IF EXISTS `registro_bolas`");
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onDestructiveMigration(db);
          }
        }
      }

      @Override
      public void onCreate(@NonNull final SupportSQLiteDatabase db) {
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onCreate(db);
          }
        }
      }

      @Override
      public void onOpen(@NonNull final SupportSQLiteDatabase db) {
        mDatabase = db;
        internalInitInvalidationTracker(db);
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onOpen(db);
          }
        }
      }

      @Override
      public void onPreMigrate(@NonNull final SupportSQLiteDatabase db) {
        DBUtil.dropFtsSyncTriggers(db);
      }

      @Override
      public void onPostMigrate(@NonNull final SupportSQLiteDatabase db) {
      }

      @Override
      @NonNull
      public RoomOpenHelper.ValidationResult onValidateSchema(
          @NonNull final SupportSQLiteDatabase db) {
        final HashMap<String, TableInfo.Column> _columnsTickets = new HashMap<String, TableInfo.Column>(5);
        _columnsTickets.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTickets.put("total", new TableInfo.Column("total", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTickets.put("tipo", new TableInfo.Column("tipo", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTickets.put("cliente", new TableInfo.Column("cliente", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTickets.put("fecha", new TableInfo.Column("fecha", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysTickets = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesTickets = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoTickets = new TableInfo("tickets", _columnsTickets, _foreignKeysTickets, _indicesTickets);
        final TableInfo _existingTickets = TableInfo.read(db, "tickets");
        if (!_infoTickets.equals(_existingTickets)) {
          return new RoomOpenHelper.ValidationResult(false, "tickets(com.tortilleria.erpv2.data.Ticket).\n"
                  + " Expected:\n" + _infoTickets + "\n"
                  + " Found:\n" + _existingTickets);
        }
        final HashMap<String, TableInfo.Column> _columnsTicketDetalle = new HashMap<String, TableInfo.Column>(7);
        _columnsTicketDetalle.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTicketDetalle.put("ticketId", new TableInfo.Column("ticketId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTicketDetalle.put("producto", new TableInfo.Column("producto", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTicketDetalle.put("variante", new TableInfo.Column("variante", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTicketDetalle.put("kilos", new TableInfo.Column("kilos", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTicketDetalle.put("precioKg", new TableInfo.Column("precioKg", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTicketDetalle.put("total", new TableInfo.Column("total", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysTicketDetalle = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesTicketDetalle = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoTicketDetalle = new TableInfo("ticket_detalle", _columnsTicketDetalle, _foreignKeysTicketDetalle, _indicesTicketDetalle);
        final TableInfo _existingTicketDetalle = TableInfo.read(db, "ticket_detalle");
        if (!_infoTicketDetalle.equals(_existingTicketDetalle)) {
          return new RoomOpenHelper.ValidationResult(false, "ticket_detalle(com.tortilleria.erpv2.data.TicketDetalle).\n"
                  + " Expected:\n" + _infoTicketDetalle + "\n"
                  + " Found:\n" + _existingTicketDetalle);
        }
        final HashMap<String, TableInfo.Column> _columnsEmpleados = new HashMap<String, TableInfo.Column>(12);
        _columnsEmpleados.put("id", new TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsEmpleados.put("nombre", new TableInfo.Column("nombre", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsEmpleados.put("rolId", new TableInfo.Column("rolId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsEmpleados.put("rolNombreSnapshot", new TableInfo.Column("rolNombreSnapshot", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsEmpleados.put("pagoPorBola", new TableInfo.Column("pagoPorBola", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsEmpleados.put("telefono", new TableInfo.Column("telefono", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsEmpleados.put("qrToken", new TableInfo.Column("qrToken", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsEmpleados.put("qrActivo", new TableInfo.Column("qrActivo", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsEmpleados.put("activo", new TableInfo.Column("activo", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsEmpleados.put("fechaLong", new TableInfo.Column("fechaLong", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsEmpleados.put("fechaDia", new TableInfo.Column("fechaDia", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsEmpleados.put("semanaId", new TableInfo.Column("semanaId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysEmpleados = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesEmpleados = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoEmpleados = new TableInfo("empleados", _columnsEmpleados, _foreignKeysEmpleados, _indicesEmpleados);
        final TableInfo _existingEmpleados = TableInfo.read(db, "empleados");
        if (!_infoEmpleados.equals(_existingEmpleados)) {
          return new RoomOpenHelper.ValidationResult(false, "empleados(com.tortilleria.erpv2.data.Empleado).\n"
                  + " Expected:\n" + _infoEmpleados + "\n"
                  + " Found:\n" + _existingEmpleados);
        }
        final HashMap<String, TableInfo.Column> _columnsRegistroBolas = new HashMap<String, TableInfo.Column>(13);
        _columnsRegistroBolas.put("id", new TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRegistroBolas.put("empleadoId", new TableInfo.Column("empleadoId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRegistroBolas.put("empleadoNombreSnapshot", new TableInfo.Column("empleadoNombreSnapshot", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRegistroBolas.put("kg", new TableInfo.Column("kg", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRegistroBolas.put("bolas", new TableInfo.Column("bolas", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRegistroBolas.put("pagoPorBolaSnapshot", new TableInfo.Column("pagoPorBolaSnapshot", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRegistroBolas.put("ganado", new TableInfo.Column("ganado", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRegistroBolas.put("hora", new TableInfo.Column("hora", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRegistroBolas.put("entregado", new TableInfo.Column("entregado", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRegistroBolas.put("entregadoPorId", new TableInfo.Column("entregadoPorId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRegistroBolas.put("fechaLong", new TableInfo.Column("fechaLong", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRegistroBolas.put("fechaDia", new TableInfo.Column("fechaDia", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRegistroBolas.put("semanaId", new TableInfo.Column("semanaId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysRegistroBolas = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesRegistroBolas = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoRegistroBolas = new TableInfo("registro_bolas", _columnsRegistroBolas, _foreignKeysRegistroBolas, _indicesRegistroBolas);
        final TableInfo _existingRegistroBolas = TableInfo.read(db, "registro_bolas");
        if (!_infoRegistroBolas.equals(_existingRegistroBolas)) {
          return new RoomOpenHelper.ValidationResult(false, "registro_bolas(com.tortilleria.erpv2.data.RegistroBola).\n"
                  + " Expected:\n" + _infoRegistroBolas + "\n"
                  + " Found:\n" + _existingRegistroBolas);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "38bb084d4f2baf24f6e66c078a0911ac", "2bc19f7112ca9975a236663eb3fecb4c");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(config.context).name(config.name).callback(_openCallback).build();
    final SupportSQLiteOpenHelper _helper = config.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  @NonNull
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    final HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "tickets","ticket_detalle","empleados","registro_bolas");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    try {
      super.beginTransaction();
      _db.execSQL("DELETE FROM `tickets`");
      _db.execSQL("DELETE FROM `ticket_detalle`");
      _db.execSQL("DELETE FROM `empleados`");
      _db.execSQL("DELETE FROM `registro_bolas`");
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
  @NonNull
  protected Map<Class<?>, List<Class<?>>> getRequiredTypeConverters() {
    final HashMap<Class<?>, List<Class<?>>> _typeConvertersMap = new HashMap<Class<?>, List<Class<?>>>();
    _typeConvertersMap.put(TicketDao.class, TicketDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(EmpleadoDao.class, EmpleadoDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(RegistroBolaDao.class, RegistroBolaDao_Impl.getRequiredConverters());
    return _typeConvertersMap;
  }

  @Override
  @NonNull
  public Set<Class<? extends AutoMigrationSpec>> getRequiredAutoMigrationSpecs() {
    final HashSet<Class<? extends AutoMigrationSpec>> _autoMigrationSpecsSet = new HashSet<Class<? extends AutoMigrationSpec>>();
    return _autoMigrationSpecsSet;
  }

  @Override
  @NonNull
  public List<Migration> getAutoMigrations(
      @NonNull final Map<Class<? extends AutoMigrationSpec>, AutoMigrationSpec> autoMigrationSpecs) {
    final List<Migration> _autoMigrations = new ArrayList<Migration>();
    return _autoMigrations;
  }

  @Override
  public TicketDao ticketDao() {
    if (_ticketDao != null) {
      return _ticketDao;
    } else {
      synchronized(this) {
        if(_ticketDao == null) {
          _ticketDao = new TicketDao_Impl(this);
        }
        return _ticketDao;
      }
    }
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
  public RegistroBolaDao registroBolaDao() {
    if (_registroBolaDao != null) {
      return _registroBolaDao;
    } else {
      synchronized(this) {
        if(_registroBolaDao == null) {
          _registroBolaDao = new RegistroBolaDao_Impl(this);
        }
        return _registroBolaDao;
      }
    }
  }
}
