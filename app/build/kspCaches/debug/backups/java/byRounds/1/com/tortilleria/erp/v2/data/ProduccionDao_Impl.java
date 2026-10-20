package com.tortilleria.erp.v2.data;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
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
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class ProduccionDao_Impl implements ProduccionDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<RegistroBola> __insertionAdapterOfRegistroBola;

  public ProduccionDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfRegistroBola = new EntityInsertionAdapter<RegistroBola>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR REPLACE INTO `produccion` (`id`,`empleadoId`,`empleadoNombreSnapshot`,`cantidad`,`turno`,`fechaLong`,`fechaDia`,`semanaId`,`mesId`,`entregadoPorId`,`entregadoPorNombreSnapshot`) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, RegistroBola value) {
        if (value.getId() == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindString(1, value.getId());
        }
        if (value.getEmpleadoId() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getEmpleadoId());
        }
        if (value.getEmpleadoNombreSnapshot() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getEmpleadoNombreSnapshot());
        }
        stmt.bindDouble(4, value.getCantidad());
        if (value.getTurno() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getTurno());
        }
        stmt.bindLong(6, value.getFechaLong());
        if (value.getFechaDia() == null) {
          stmt.bindNull(7);
        } else {
          stmt.bindString(7, value.getFechaDia());
        }
        if (value.getSemanaId() == null) {
          stmt.bindNull(8);
        } else {
          stmt.bindString(8, value.getSemanaId());
        }
        if (value.getMesId() == null) {
          stmt.bindNull(9);
        } else {
          stmt.bindString(9, value.getMesId());
        }
        if (value.getEntregadoPorId() == null) {
          stmt.bindNull(10);
        } else {
          stmt.bindString(10, value.getEntregadoPorId());
        }
        if (value.getEntregadoPorNombreSnapshot() == null) {
          stmt.bindNull(11);
        } else {
          stmt.bindString(11, value.getEntregadoPorNombreSnapshot());
        }
      }
    };
  }

  @Override
  public Object insert(final RegistroBola registro, final Continuation<? super Unit> continuation) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfRegistroBola.insert(registro);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, continuation);
  }

  @Override
  public Flow<List<RegistroBola>> getPorDia(final String fechaDia) {
    final String _sql = "SELECT * FROM produccion WHERE fechaDia = ? ORDER BY fechaLong DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (fechaDia == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, fechaDia);
    }
    return CoroutinesRoom.createFlow(__db, false, new String[]{"produccion"}, new Callable<List<RegistroBola>>() {
      @Override
      public List<RegistroBola> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfEmpleadoId = CursorUtil.getColumnIndexOrThrow(_cursor, "empleadoId");
          final int _cursorIndexOfEmpleadoNombreSnapshot = CursorUtil.getColumnIndexOrThrow(_cursor, "empleadoNombreSnapshot");
          final int _cursorIndexOfCantidad = CursorUtil.getColumnIndexOrThrow(_cursor, "cantidad");
          final int _cursorIndexOfTurno = CursorUtil.getColumnIndexOrThrow(_cursor, "turno");
          final int _cursorIndexOfFechaLong = CursorUtil.getColumnIndexOrThrow(_cursor, "fechaLong");
          final int _cursorIndexOfFechaDia = CursorUtil.getColumnIndexOrThrow(_cursor, "fechaDia");
          final int _cursorIndexOfSemanaId = CursorUtil.getColumnIndexOrThrow(_cursor, "semanaId");
          final int _cursorIndexOfMesId = CursorUtil.getColumnIndexOrThrow(_cursor, "mesId");
          final int _cursorIndexOfEntregadoPorId = CursorUtil.getColumnIndexOrThrow(_cursor, "entregadoPorId");
          final int _cursorIndexOfEntregadoPorNombreSnapshot = CursorUtil.getColumnIndexOrThrow(_cursor, "entregadoPorNombreSnapshot");
          final List<RegistroBola> _result = new ArrayList<RegistroBola>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final RegistroBola _item;
            final String _tmpId;
            if (_cursor.isNull(_cursorIndexOfId)) {
              _tmpId = null;
            } else {
              _tmpId = _cursor.getString(_cursorIndexOfId);
            }
            final String _tmpEmpleadoId;
            if (_cursor.isNull(_cursorIndexOfEmpleadoId)) {
              _tmpEmpleadoId = null;
            } else {
              _tmpEmpleadoId = _cursor.getString(_cursorIndexOfEmpleadoId);
            }
            final String _tmpEmpleadoNombreSnapshot;
            if (_cursor.isNull(_cursorIndexOfEmpleadoNombreSnapshot)) {
              _tmpEmpleadoNombreSnapshot = null;
            } else {
              _tmpEmpleadoNombreSnapshot = _cursor.getString(_cursorIndexOfEmpleadoNombreSnapshot);
            }
            final double _tmpCantidad;
            _tmpCantidad = _cursor.getDouble(_cursorIndexOfCantidad);
            final String _tmpTurno;
            if (_cursor.isNull(_cursorIndexOfTurno)) {
              _tmpTurno = null;
            } else {
              _tmpTurno = _cursor.getString(_cursorIndexOfTurno);
            }
            final long _tmpFechaLong;
            _tmpFechaLong = _cursor.getLong(_cursorIndexOfFechaLong);
            final String _tmpFechaDia;
            if (_cursor.isNull(_cursorIndexOfFechaDia)) {
              _tmpFechaDia = null;
            } else {
              _tmpFechaDia = _cursor.getString(_cursorIndexOfFechaDia);
            }
            final String _tmpSemanaId;
            if (_cursor.isNull(_cursorIndexOfSemanaId)) {
              _tmpSemanaId = null;
            } else {
              _tmpSemanaId = _cursor.getString(_cursorIndexOfSemanaId);
            }
            final String _tmpMesId;
            if (_cursor.isNull(_cursorIndexOfMesId)) {
              _tmpMesId = null;
            } else {
              _tmpMesId = _cursor.getString(_cursorIndexOfMesId);
            }
            final String _tmpEntregadoPorId;
            if (_cursor.isNull(_cursorIndexOfEntregadoPorId)) {
              _tmpEntregadoPorId = null;
            } else {
              _tmpEntregadoPorId = _cursor.getString(_cursorIndexOfEntregadoPorId);
            }
            final String _tmpEntregadoPorNombreSnapshot;
            if (_cursor.isNull(_cursorIndexOfEntregadoPorNombreSnapshot)) {
              _tmpEntregadoPorNombreSnapshot = null;
            } else {
              _tmpEntregadoPorNombreSnapshot = _cursor.getString(_cursorIndexOfEntregadoPorNombreSnapshot);
            }
            _item = new RegistroBola(_tmpId,_tmpEmpleadoId,_tmpEmpleadoNombreSnapshot,_tmpCantidad,_tmpTurno,_tmpFechaLong,_tmpFechaDia,_tmpSemanaId,_tmpMesId,_tmpEntregadoPorId,_tmpEntregadoPorNombreSnapshot);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<List<RegistroBola>> getPorSemana(final String semanaId) {
    final String _sql = "SELECT * FROM produccion WHERE semanaId = ? ORDER BY fechaLong DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (semanaId == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, semanaId);
    }
    return CoroutinesRoom.createFlow(__db, false, new String[]{"produccion"}, new Callable<List<RegistroBola>>() {
      @Override
      public List<RegistroBola> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfEmpleadoId = CursorUtil.getColumnIndexOrThrow(_cursor, "empleadoId");
          final int _cursorIndexOfEmpleadoNombreSnapshot = CursorUtil.getColumnIndexOrThrow(_cursor, "empleadoNombreSnapshot");
          final int _cursorIndexOfCantidad = CursorUtil.getColumnIndexOrThrow(_cursor, "cantidad");
          final int _cursorIndexOfTurno = CursorUtil.getColumnIndexOrThrow(_cursor, "turno");
          final int _cursorIndexOfFechaLong = CursorUtil.getColumnIndexOrThrow(_cursor, "fechaLong");
          final int _cursorIndexOfFechaDia = CursorUtil.getColumnIndexOrThrow(_cursor, "fechaDia");
          final int _cursorIndexOfSemanaId = CursorUtil.getColumnIndexOrThrow(_cursor, "semanaId");
          final int _cursorIndexOfMesId = CursorUtil.getColumnIndexOrThrow(_cursor, "mesId");
          final int _cursorIndexOfEntregadoPorId = CursorUtil.getColumnIndexOrThrow(_cursor, "entregadoPorId");
          final int _cursorIndexOfEntregadoPorNombreSnapshot = CursorUtil.getColumnIndexOrThrow(_cursor, "entregadoPorNombreSnapshot");
          final List<RegistroBola> _result = new ArrayList<RegistroBola>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final RegistroBola _item;
            final String _tmpId;
            if (_cursor.isNull(_cursorIndexOfId)) {
              _tmpId = null;
            } else {
              _tmpId = _cursor.getString(_cursorIndexOfId);
            }
            final String _tmpEmpleadoId;
            if (_cursor.isNull(_cursorIndexOfEmpleadoId)) {
              _tmpEmpleadoId = null;
            } else {
              _tmpEmpleadoId = _cursor.getString(_cursorIndexOfEmpleadoId);
            }
            final String _tmpEmpleadoNombreSnapshot;
            if (_cursor.isNull(_cursorIndexOfEmpleadoNombreSnapshot)) {
              _tmpEmpleadoNombreSnapshot = null;
            } else {
              _tmpEmpleadoNombreSnapshot = _cursor.getString(_cursorIndexOfEmpleadoNombreSnapshot);
            }
            final double _tmpCantidad;
            _tmpCantidad = _cursor.getDouble(_cursorIndexOfCantidad);
            final String _tmpTurno;
            if (_cursor.isNull(_cursorIndexOfTurno)) {
              _tmpTurno = null;
            } else {
              _tmpTurno = _cursor.getString(_cursorIndexOfTurno);
            }
            final long _tmpFechaLong;
            _tmpFechaLong = _cursor.getLong(_cursorIndexOfFechaLong);
            final String _tmpFechaDia;
            if (_cursor.isNull(_cursorIndexOfFechaDia)) {
              _tmpFechaDia = null;
            } else {
              _tmpFechaDia = _cursor.getString(_cursorIndexOfFechaDia);
            }
            final String _tmpSemanaId;
            if (_cursor.isNull(_cursorIndexOfSemanaId)) {
              _tmpSemanaId = null;
            } else {
              _tmpSemanaId = _cursor.getString(_cursorIndexOfSemanaId);
            }
            final String _tmpMesId;
            if (_cursor.isNull(_cursorIndexOfMesId)) {
              _tmpMesId = null;
            } else {
              _tmpMesId = _cursor.getString(_cursorIndexOfMesId);
            }
            final String _tmpEntregadoPorId;
            if (_cursor.isNull(_cursorIndexOfEntregadoPorId)) {
              _tmpEntregadoPorId = null;
            } else {
              _tmpEntregadoPorId = _cursor.getString(_cursorIndexOfEntregadoPorId);
            }
            final String _tmpEntregadoPorNombreSnapshot;
            if (_cursor.isNull(_cursorIndexOfEntregadoPorNombreSnapshot)) {
              _tmpEntregadoPorNombreSnapshot = null;
            } else {
              _tmpEntregadoPorNombreSnapshot = _cursor.getString(_cursorIndexOfEntregadoPorNombreSnapshot);
            }
            _item = new RegistroBola(_tmpId,_tmpEmpleadoId,_tmpEmpleadoNombreSnapshot,_tmpCantidad,_tmpTurno,_tmpFechaLong,_tmpFechaDia,_tmpSemanaId,_tmpMesId,_tmpEntregadoPorId,_tmpEntregadoPorNombreSnapshot);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Object totalPorEmpleadoSemana(final String empleadoId, final String semanaId,
      final Continuation<? super Double> continuation) {
    final String _sql = "SELECT SUM(cantidad) FROM produccion WHERE empleadoId = ? AND semanaId = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    if (empleadoId == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, empleadoId);
    }
    _argIndex = 2;
    if (semanaId == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, semanaId);
    }
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Double>() {
      @Override
      public Double call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Double _result;
          if(_cursor.moveToFirst()) {
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
    }, continuation);
  }

  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
