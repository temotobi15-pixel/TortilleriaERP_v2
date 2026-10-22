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
import java.lang.Double;
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
public final class RegistroBolaDao_Impl implements RegistroBolaDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<RegistroBola> __insertionAdapterOfRegistroBola;

  private final SharedSQLiteStatement __preparedStmtOfEliminar;

  public RegistroBolaDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfRegistroBola = new EntityInsertionAdapter<RegistroBola>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `registro_bolas` (`id`,`empleadoId`,`empleadoNombreSnapshot`,`kg`,`bolas`,`pagoPorBolaSnapshot`,`ganado`,`hora`,`entregado`,`entregadoPorId`,`fechaLong`,`fechaDia`,`semanaId`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final RegistroBola entity) {
        statement.bindString(1, entity.getId());
        statement.bindString(2, entity.getEmpleadoId());
        statement.bindString(3, entity.getEmpleadoNombreSnapshot());
        statement.bindDouble(4, entity.getKg());
        statement.bindDouble(5, entity.getBolas());
        statement.bindDouble(6, entity.getPagoPorBolaSnapshot());
        statement.bindDouble(7, entity.getGanado());
        statement.bindString(8, entity.getHora());
        final int _tmp = entity.getEntregado() ? 1 : 0;
        statement.bindLong(9, _tmp);
        statement.bindString(10, entity.getEntregadoPorId());
        statement.bindLong(11, entity.getFechaLong());
        statement.bindString(12, entity.getFechaDia());
        statement.bindString(13, entity.getSemanaId());
      }
    };
    this.__preparedStmtOfEliminar = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM registro_bolas WHERE id=?";
        return _query;
      }
    };
  }

  @Override
  public Object insertar(final RegistroBola r, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfRegistroBola.insert(r);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object eliminar(final String id, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfEliminar.acquire();
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
          __preparedStmtOfEliminar.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object obtenerPorDia(final String fechaDia,
      final Continuation<? super List<RegistroBola>> $completion) {
    final String _sql = "SELECT * FROM registro_bolas WHERE fechaDia=? ORDER BY fechaLong ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, fechaDia);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<RegistroBola>>() {
      @Override
      @NonNull
      public List<RegistroBola> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfEmpleadoId = CursorUtil.getColumnIndexOrThrow(_cursor, "empleadoId");
          final int _cursorIndexOfEmpleadoNombreSnapshot = CursorUtil.getColumnIndexOrThrow(_cursor, "empleadoNombreSnapshot");
          final int _cursorIndexOfKg = CursorUtil.getColumnIndexOrThrow(_cursor, "kg");
          final int _cursorIndexOfBolas = CursorUtil.getColumnIndexOrThrow(_cursor, "bolas");
          final int _cursorIndexOfPagoPorBolaSnapshot = CursorUtil.getColumnIndexOrThrow(_cursor, "pagoPorBolaSnapshot");
          final int _cursorIndexOfGanado = CursorUtil.getColumnIndexOrThrow(_cursor, "ganado");
          final int _cursorIndexOfHora = CursorUtil.getColumnIndexOrThrow(_cursor, "hora");
          final int _cursorIndexOfEntregado = CursorUtil.getColumnIndexOrThrow(_cursor, "entregado");
          final int _cursorIndexOfEntregadoPorId = CursorUtil.getColumnIndexOrThrow(_cursor, "entregadoPorId");
          final int _cursorIndexOfFechaLong = CursorUtil.getColumnIndexOrThrow(_cursor, "fechaLong");
          final int _cursorIndexOfFechaDia = CursorUtil.getColumnIndexOrThrow(_cursor, "fechaDia");
          final int _cursorIndexOfSemanaId = CursorUtil.getColumnIndexOrThrow(_cursor, "semanaId");
          final List<RegistroBola> _result = new ArrayList<RegistroBola>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final RegistroBola _item;
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpEmpleadoId;
            _tmpEmpleadoId = _cursor.getString(_cursorIndexOfEmpleadoId);
            final String _tmpEmpleadoNombreSnapshot;
            _tmpEmpleadoNombreSnapshot = _cursor.getString(_cursorIndexOfEmpleadoNombreSnapshot);
            final double _tmpKg;
            _tmpKg = _cursor.getDouble(_cursorIndexOfKg);
            final double _tmpBolas;
            _tmpBolas = _cursor.getDouble(_cursorIndexOfBolas);
            final double _tmpPagoPorBolaSnapshot;
            _tmpPagoPorBolaSnapshot = _cursor.getDouble(_cursorIndexOfPagoPorBolaSnapshot);
            final double _tmpGanado;
            _tmpGanado = _cursor.getDouble(_cursorIndexOfGanado);
            final String _tmpHora;
            _tmpHora = _cursor.getString(_cursorIndexOfHora);
            final boolean _tmpEntregado;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfEntregado);
            _tmpEntregado = _tmp != 0;
            final String _tmpEntregadoPorId;
            _tmpEntregadoPorId = _cursor.getString(_cursorIndexOfEntregadoPorId);
            final long _tmpFechaLong;
            _tmpFechaLong = _cursor.getLong(_cursorIndexOfFechaLong);
            final String _tmpFechaDia;
            _tmpFechaDia = _cursor.getString(_cursorIndexOfFechaDia);
            final String _tmpSemanaId;
            _tmpSemanaId = _cursor.getString(_cursorIndexOfSemanaId);
            _item = new RegistroBola(_tmpId,_tmpEmpleadoId,_tmpEmpleadoNombreSnapshot,_tmpKg,_tmpBolas,_tmpPagoPorBolaSnapshot,_tmpGanado,_tmpHora,_tmpEntregado,_tmpEntregadoPorId,_tmpFechaLong,_tmpFechaDia,_tmpSemanaId);
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
  public Object obtenerPorSemana(final String semanaId,
      final Continuation<? super List<RegistroBola>> $completion) {
    final String _sql = "SELECT * FROM registro_bolas WHERE semanaId=? ORDER BY fechaLong DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, semanaId);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<RegistroBola>>() {
      @Override
      @NonNull
      public List<RegistroBola> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfEmpleadoId = CursorUtil.getColumnIndexOrThrow(_cursor, "empleadoId");
          final int _cursorIndexOfEmpleadoNombreSnapshot = CursorUtil.getColumnIndexOrThrow(_cursor, "empleadoNombreSnapshot");
          final int _cursorIndexOfKg = CursorUtil.getColumnIndexOrThrow(_cursor, "kg");
          final int _cursorIndexOfBolas = CursorUtil.getColumnIndexOrThrow(_cursor, "bolas");
          final int _cursorIndexOfPagoPorBolaSnapshot = CursorUtil.getColumnIndexOrThrow(_cursor, "pagoPorBolaSnapshot");
          final int _cursorIndexOfGanado = CursorUtil.getColumnIndexOrThrow(_cursor, "ganado");
          final int _cursorIndexOfHora = CursorUtil.getColumnIndexOrThrow(_cursor, "hora");
          final int _cursorIndexOfEntregado = CursorUtil.getColumnIndexOrThrow(_cursor, "entregado");
          final int _cursorIndexOfEntregadoPorId = CursorUtil.getColumnIndexOrThrow(_cursor, "entregadoPorId");
          final int _cursorIndexOfFechaLong = CursorUtil.getColumnIndexOrThrow(_cursor, "fechaLong");
          final int _cursorIndexOfFechaDia = CursorUtil.getColumnIndexOrThrow(_cursor, "fechaDia");
          final int _cursorIndexOfSemanaId = CursorUtil.getColumnIndexOrThrow(_cursor, "semanaId");
          final List<RegistroBola> _result = new ArrayList<RegistroBola>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final RegistroBola _item;
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpEmpleadoId;
            _tmpEmpleadoId = _cursor.getString(_cursorIndexOfEmpleadoId);
            final String _tmpEmpleadoNombreSnapshot;
            _tmpEmpleadoNombreSnapshot = _cursor.getString(_cursorIndexOfEmpleadoNombreSnapshot);
            final double _tmpKg;
            _tmpKg = _cursor.getDouble(_cursorIndexOfKg);
            final double _tmpBolas;
            _tmpBolas = _cursor.getDouble(_cursorIndexOfBolas);
            final double _tmpPagoPorBolaSnapshot;
            _tmpPagoPorBolaSnapshot = _cursor.getDouble(_cursorIndexOfPagoPorBolaSnapshot);
            final double _tmpGanado;
            _tmpGanado = _cursor.getDouble(_cursorIndexOfGanado);
            final String _tmpHora;
            _tmpHora = _cursor.getString(_cursorIndexOfHora);
            final boolean _tmpEntregado;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfEntregado);
            _tmpEntregado = _tmp != 0;
            final String _tmpEntregadoPorId;
            _tmpEntregadoPorId = _cursor.getString(_cursorIndexOfEntregadoPorId);
            final long _tmpFechaLong;
            _tmpFechaLong = _cursor.getLong(_cursorIndexOfFechaLong);
            final String _tmpFechaDia;
            _tmpFechaDia = _cursor.getString(_cursorIndexOfFechaDia);
            final String _tmpSemanaId;
            _tmpSemanaId = _cursor.getString(_cursorIndexOfSemanaId);
            _item = new RegistroBola(_tmpId,_tmpEmpleadoId,_tmpEmpleadoNombreSnapshot,_tmpKg,_tmpBolas,_tmpPagoPorBolaSnapshot,_tmpGanado,_tmpHora,_tmpEntregado,_tmpEntregadoPorId,_tmpFechaLong,_tmpFechaDia,_tmpSemanaId);
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
  public Object obtenerPorEmpleadoYdia(final String empleadoId, final String fechaDia,
      final Continuation<? super List<RegistroBola>> $completion) {
    final String _sql = "SELECT * FROM registro_bolas WHERE empleadoId=? AND fechaDia=?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    _statement.bindString(_argIndex, empleadoId);
    _argIndex = 2;
    _statement.bindString(_argIndex, fechaDia);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<RegistroBola>>() {
      @Override
      @NonNull
      public List<RegistroBola> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfEmpleadoId = CursorUtil.getColumnIndexOrThrow(_cursor, "empleadoId");
          final int _cursorIndexOfEmpleadoNombreSnapshot = CursorUtil.getColumnIndexOrThrow(_cursor, "empleadoNombreSnapshot");
          final int _cursorIndexOfKg = CursorUtil.getColumnIndexOrThrow(_cursor, "kg");
          final int _cursorIndexOfBolas = CursorUtil.getColumnIndexOrThrow(_cursor, "bolas");
          final int _cursorIndexOfPagoPorBolaSnapshot = CursorUtil.getColumnIndexOrThrow(_cursor, "pagoPorBolaSnapshot");
          final int _cursorIndexOfGanado = CursorUtil.getColumnIndexOrThrow(_cursor, "ganado");
          final int _cursorIndexOfHora = CursorUtil.getColumnIndexOrThrow(_cursor, "hora");
          final int _cursorIndexOfEntregado = CursorUtil.getColumnIndexOrThrow(_cursor, "entregado");
          final int _cursorIndexOfEntregadoPorId = CursorUtil.getColumnIndexOrThrow(_cursor, "entregadoPorId");
          final int _cursorIndexOfFechaLong = CursorUtil.getColumnIndexOrThrow(_cursor, "fechaLong");
          final int _cursorIndexOfFechaDia = CursorUtil.getColumnIndexOrThrow(_cursor, "fechaDia");
          final int _cursorIndexOfSemanaId = CursorUtil.getColumnIndexOrThrow(_cursor, "semanaId");
          final List<RegistroBola> _result = new ArrayList<RegistroBola>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final RegistroBola _item;
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpEmpleadoId;
            _tmpEmpleadoId = _cursor.getString(_cursorIndexOfEmpleadoId);
            final String _tmpEmpleadoNombreSnapshot;
            _tmpEmpleadoNombreSnapshot = _cursor.getString(_cursorIndexOfEmpleadoNombreSnapshot);
            final double _tmpKg;
            _tmpKg = _cursor.getDouble(_cursorIndexOfKg);
            final double _tmpBolas;
            _tmpBolas = _cursor.getDouble(_cursorIndexOfBolas);
            final double _tmpPagoPorBolaSnapshot;
            _tmpPagoPorBolaSnapshot = _cursor.getDouble(_cursorIndexOfPagoPorBolaSnapshot);
            final double _tmpGanado;
            _tmpGanado = _cursor.getDouble(_cursorIndexOfGanado);
            final String _tmpHora;
            _tmpHora = _cursor.getString(_cursorIndexOfHora);
            final boolean _tmpEntregado;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfEntregado);
            _tmpEntregado = _tmp != 0;
            final String _tmpEntregadoPorId;
            _tmpEntregadoPorId = _cursor.getString(_cursorIndexOfEntregadoPorId);
            final long _tmpFechaLong;
            _tmpFechaLong = _cursor.getLong(_cursorIndexOfFechaLong);
            final String _tmpFechaDia;
            _tmpFechaDia = _cursor.getString(_cursorIndexOfFechaDia);
            final String _tmpSemanaId;
            _tmpSemanaId = _cursor.getString(_cursorIndexOfSemanaId);
            _item = new RegistroBola(_tmpId,_tmpEmpleadoId,_tmpEmpleadoNombreSnapshot,_tmpKg,_tmpBolas,_tmpPagoPorBolaSnapshot,_tmpGanado,_tmpHora,_tmpEntregado,_tmpEntregadoPorId,_tmpFechaLong,_tmpFechaDia,_tmpSemanaId);
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
  public Object totalKgPorDia(final String fechaDia,
      final Continuation<? super Double> $completion) {
    final String _sql = "SELECT SUM(kg) FROM registro_bolas WHERE fechaDia=?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, fechaDia);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Double>() {
      @Override
      @Nullable
      public Double call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Double _result;
          if (_cursor.moveToFirst()) {
            final Double _tmp;
            if (_cursor.isNull(0)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getDouble(0);
            }
            _result = _tmp;
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

  @Override
  public Object totalBolasPorDia(final String fechaDia,
      final Continuation<? super Double> $completion) {
    final String _sql = "SELECT SUM(bolas) FROM registro_bolas WHERE fechaDia=?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, fechaDia);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Double>() {
      @Override
      @Nullable
      public Double call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Double _result;
          if (_cursor.moveToFirst()) {
            final Double _tmp;
            if (_cursor.isNull(0)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getDouble(0);
            }
            _result = _tmp;
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

  @Override
  public Object totalGanadoPorDia(final String fechaDia,
      final Continuation<? super Double> $completion) {
    final String _sql = "SELECT SUM(ganado) FROM registro_bolas WHERE fechaDia=?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, fechaDia);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Double>() {
      @Override
      @Nullable
      public Double call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Double _result;
          if (_cursor.moveToFirst()) {
            final Double _tmp;
            if (_cursor.isNull(0)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getDouble(0);
            }
            _result = _tmp;
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
