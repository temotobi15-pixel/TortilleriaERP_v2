package com.tortilleria.erpv2.data;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class EmpleadoDao_Impl implements EmpleadoDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Empleado> __insertionAdapterOfEmpleado;

  private final SharedSQLiteStatement __preparedStmtOfDesactivar;

  private final SharedSQLiteStatement __preparedStmtOfEliminarDefinitivo;

  public EmpleadoDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfEmpleado = new EntityInsertionAdapter<Empleado>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `empleados` (`id`,`nombre`,`rolId`,`rolNombreSnapshot`,`pagoPorBola`,`telefono`,`qrToken`,`qrActivo`,`activo`,`fechaLong`,`fechaDia`,`semanaId`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Empleado entity) {
        statement.bindString(1, entity.getId());
        statement.bindString(2, entity.getNombre());
        statement.bindString(3, entity.getRolId());
        statement.bindString(4, entity.getRolNombreSnapshot());
        statement.bindDouble(5, entity.getPagoPorBola());
        statement.bindString(6, entity.getTelefono());
        statement.bindString(7, entity.getQrToken());
        final int _tmp = entity.getQrActivo() ? 1 : 0;
        statement.bindLong(8, _tmp);
        final int _tmp_1 = entity.getActivo() ? 1 : 0;
        statement.bindLong(9, _tmp_1);
        statement.bindLong(10, entity.getFechaLong());
        statement.bindString(11, entity.getFechaDia());
        statement.bindString(12, entity.getSemanaId());
      }
    };
    this.__preparedStmtOfDesactivar = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE empleados SET activo=0 WHERE id=?";
        return _query;
      }
    };
    this.__preparedStmtOfEliminarDefinitivo = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM empleados WHERE id=?";
        return _query;
      }
    };
  }

  @Override
  public Object insertar(final Empleado e, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfEmpleado.insert(e);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object desactivar(final String id, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDesactivar.acquire();
        int _argIndex = 1;
        _stmt.bindString(_argIndex, id);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfDesactivar.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object eliminarDefinitivo(final String id, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfEliminarDefinitivo.acquire();
        int _argIndex = 1;
        _stmt.bindString(_argIndex, id);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfEliminarDefinitivo.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object obtenerTodos(final Continuation<? super List<Empleado>> $completion) {
    final String _sql = "SELECT * FROM empleados WHERE activo=1 ORDER BY nombre ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<Empleado>>() {
      @Override
      @NonNull
      public List<Empleado> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfNombre = CursorUtil.getColumnIndexOrThrow(_cursor, "nombre");
          final int _cursorIndexOfRolId = CursorUtil.getColumnIndexOrThrow(_cursor, "rolId");
          final int _cursorIndexOfRolNombreSnapshot = CursorUtil.getColumnIndexOrThrow(_cursor, "rolNombreSnapshot");
          final int _cursorIndexOfPagoPorBola = CursorUtil.getColumnIndexOrThrow(_cursor, "pagoPorBola");
          final int _cursorIndexOfTelefono = CursorUtil.getColumnIndexOrThrow(_cursor, "telefono");
          final int _cursorIndexOfQrToken = CursorUtil.getColumnIndexOrThrow(_cursor, "qrToken");
          final int _cursorIndexOfQrActivo = CursorUtil.getColumnIndexOrThrow(_cursor, "qrActivo");
          final int _cursorIndexOfActivo = CursorUtil.getColumnIndexOrThrow(_cursor, "activo");
          final int _cursorIndexOfFechaLong = CursorUtil.getColumnIndexOrThrow(_cursor, "fechaLong");
          final int _cursorIndexOfFechaDia = CursorUtil.getColumnIndexOrThrow(_cursor, "fechaDia");
          final int _cursorIndexOfSemanaId = CursorUtil.getColumnIndexOrThrow(_cursor, "semanaId");
          final List<Empleado> _result = new ArrayList<Empleado>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Empleado _item;
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpNombre;
            _tmpNombre = _cursor.getString(_cursorIndexOfNombre);
            final String _tmpRolId;
            _tmpRolId = _cursor.getString(_cursorIndexOfRolId);
            final String _tmpRolNombreSnapshot;
            _tmpRolNombreSnapshot = _cursor.getString(_cursorIndexOfRolNombreSnapshot);
            final double _tmpPagoPorBola;
            _tmpPagoPorBola = _cursor.getDouble(_cursorIndexOfPagoPorBola);
            final String _tmpTelefono;
            _tmpTelefono = _cursor.getString(_cursorIndexOfTelefono);
            final String _tmpQrToken;
            _tmpQrToken = _cursor.getString(_cursorIndexOfQrToken);
            final boolean _tmpQrActivo;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfQrActivo);
            _tmpQrActivo = _tmp != 0;
            final boolean _tmpActivo;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfActivo);
            _tmpActivo = _tmp_1 != 0;
            final long _tmpFechaLong;
            _tmpFechaLong = _cursor.getLong(_cursorIndexOfFechaLong);
            final String _tmpFechaDia;
            _tmpFechaDia = _cursor.getString(_cursorIndexOfFechaDia);
            final String _tmpSemanaId;
            _tmpSemanaId = _cursor.getString(_cursorIndexOfSemanaId);
            _item = new Empleado(_tmpId,_tmpNombre,_tmpRolId,_tmpRolNombreSnapshot,_tmpPagoPorBola,_tmpTelefono,_tmpQrToken,_tmpQrActivo,_tmpActivo,_tmpFechaLong,_tmpFechaDia,_tmpSemanaId);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object obtenerTortilleras(final Continuation<? super List<Empleado>> $completion) {
    final String _sql = "SELECT * FROM empleados WHERE rolId='TORTILLERA' AND activo=1 ORDER BY nombre ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<Empleado>>() {
      @Override
      @NonNull
      public List<Empleado> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfNombre = CursorUtil.getColumnIndexOrThrow(_cursor, "nombre");
          final int _cursorIndexOfRolId = CursorUtil.getColumnIndexOrThrow(_cursor, "rolId");
          final int _cursorIndexOfRolNombreSnapshot = CursorUtil.getColumnIndexOrThrow(_cursor, "rolNombreSnapshot");
          final int _cursorIndexOfPagoPorBola = CursorUtil.getColumnIndexOrThrow(_cursor, "pagoPorBola");
          final int _cursorIndexOfTelefono = CursorUtil.getColumnIndexOrThrow(_cursor, "telefono");
          final int _cursorIndexOfQrToken = CursorUtil.getColumnIndexOrThrow(_cursor, "qrToken");
          final int _cursorIndexOfQrActivo = CursorUtil.getColumnIndexOrThrow(_cursor, "qrActivo");
          final int _cursorIndexOfActivo = CursorUtil.getColumnIndexOrThrow(_cursor, "activo");
          final int _cursorIndexOfFechaLong = CursorUtil.getColumnIndexOrThrow(_cursor, "fechaLong");
          final int _cursorIndexOfFechaDia = CursorUtil.getColumnIndexOrThrow(_cursor, "fechaDia");
          final int _cursorIndexOfSemanaId = CursorUtil.getColumnIndexOrThrow(_cursor, "semanaId");
          final List<Empleado> _result = new ArrayList<Empleado>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Empleado _item;
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpNombre;
            _tmpNombre = _cursor.getString(_cursorIndexOfNombre);
            final String _tmpRolId;
            _tmpRolId = _cursor.getString(_cursorIndexOfRolId);
            final String _tmpRolNombreSnapshot;
            _tmpRolNombreSnapshot = _cursor.getString(_cursorIndexOfRolNombreSnapshot);
            final double _tmpPagoPorBola;
            _tmpPagoPorBola = _cursor.getDouble(_cursorIndexOfPagoPorBola);
            final String _tmpTelefono;
            _tmpTelefono = _cursor.getString(_cursorIndexOfTelefono);
            final String _tmpQrToken;
            _tmpQrToken = _cursor.getString(_cursorIndexOfQrToken);
            final boolean _tmpQrActivo;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfQrActivo);
            _tmpQrActivo = _tmp != 0;
            final boolean _tmpActivo;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfActivo);
            _tmpActivo = _tmp_1 != 0;
            final long _tmpFechaLong;
            _tmpFechaLong = _cursor.getLong(_cursorIndexOfFechaLong);
            final String _tmpFechaDia;
            _tmpFechaDia = _cursor.getString(_cursorIndexOfFechaDia);
            final String _tmpSemanaId;
            _tmpSemanaId = _cursor.getString(_cursorIndexOfSemanaId);
            _item = new Empleado(_tmpId,_tmpNombre,_tmpRolId,_tmpRolNombreSnapshot,_tmpPagoPorBola,_tmpTelefono,_tmpQrToken,_tmpQrActivo,_tmpActivo,_tmpFechaLong,_tmpFechaDia,_tmpSemanaId);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object obtenerPorId(final String id, final Continuation<? super Empleado> $completion) {
    final String _sql = "SELECT * FROM empleados WHERE id=?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, id);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Empleado>() {
      @Override
      @Nullable
      public Empleado call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfNombre = CursorUtil.getColumnIndexOrThrow(_cursor, "nombre");
          final int _cursorIndexOfRolId = CursorUtil.getColumnIndexOrThrow(_cursor, "rolId");
          final int _cursorIndexOfRolNombreSnapshot = CursorUtil.getColumnIndexOrThrow(_cursor, "rolNombreSnapshot");
          final int _cursorIndexOfPagoPorBola = CursorUtil.getColumnIndexOrThrow(_cursor, "pagoPorBola");
          final int _cursorIndexOfTelefono = CursorUtil.getColumnIndexOrThrow(_cursor, "telefono");
          final int _cursorIndexOfQrToken = CursorUtil.getColumnIndexOrThrow(_cursor, "qrToken");
          final int _cursorIndexOfQrActivo = CursorUtil.getColumnIndexOrThrow(_cursor, "qrActivo");
          final int _cursorIndexOfActivo = CursorUtil.getColumnIndexOrThrow(_cursor, "activo");
          final int _cursorIndexOfFechaLong = CursorUtil.getColumnIndexOrThrow(_cursor, "fechaLong");
          final int _cursorIndexOfFechaDia = CursorUtil.getColumnIndexOrThrow(_cursor, "fechaDia");
          final int _cursorIndexOfSemanaId = CursorUtil.getColumnIndexOrThrow(_cursor, "semanaId");
          final Empleado _result;
          if (_cursor.moveToFirst()) {
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpNombre;
            _tmpNombre = _cursor.getString(_cursorIndexOfNombre);
            final String _tmpRolId;
            _tmpRolId = _cursor.getString(_cursorIndexOfRolId);
            final String _tmpRolNombreSnapshot;
            _tmpRolNombreSnapshot = _cursor.getString(_cursorIndexOfRolNombreSnapshot);
            final double _tmpPagoPorBola;
            _tmpPagoPorBola = _cursor.getDouble(_cursorIndexOfPagoPorBola);
            final String _tmpTelefono;
            _tmpTelefono = _cursor.getString(_cursorIndexOfTelefono);
            final String _tmpQrToken;
            _tmpQrToken = _cursor.getString(_cursorIndexOfQrToken);
            final boolean _tmpQrActivo;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfQrActivo);
            _tmpQrActivo = _tmp != 0;
            final boolean _tmpActivo;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfActivo);
            _tmpActivo = _tmp_1 != 0;
            final long _tmpFechaLong;
            _tmpFechaLong = _cursor.getLong(_cursorIndexOfFechaLong);
            final String _tmpFechaDia;
            _tmpFechaDia = _cursor.getString(_cursorIndexOfFechaDia);
            final String _tmpSemanaId;
            _tmpSemanaId = _cursor.getString(_cursorIndexOfSemanaId);
            _result = new Empleado(_tmpId,_tmpNombre,_tmpRolId,_tmpRolNombreSnapshot,_tmpPagoPorBola,_tmpTelefono,_tmpQrToken,_tmpQrActivo,_tmpActivo,_tmpFechaLong,_tmpFechaDia,_tmpSemanaId);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
