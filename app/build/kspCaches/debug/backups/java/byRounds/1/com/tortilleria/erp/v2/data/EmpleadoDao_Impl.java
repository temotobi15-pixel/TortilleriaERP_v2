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
public final class EmpleadoDao_Impl implements EmpleadoDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Empleado> __insertionAdapterOfEmpleado;

  public EmpleadoDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfEmpleado = new EntityInsertionAdapter<Empleado>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR REPLACE INTO `empleados` (`id`,`rolId`,`nombre`,`telefono`,`pagoPorBola`,`activo`,`qrCode`,`fechaLong`,`fechaDia`,`semanaId`,`mesId`,`creadoPorId`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Empleado value) {
        if (value.getId() == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindString(1, value.getId());
        }
        if (value.getRolId() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getRolId());
        }
        if (value.getNombre() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getNombre());
        }
        if (value.getTelefono() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getTelefono());
        }
        stmt.bindDouble(5, value.getPagoPorBola());
        final int _tmp = value.getActivo() ? 1 : 0;
        stmt.bindLong(6, _tmp);
        if (value.getQrCode() == null) {
          stmt.bindNull(7);
        } else {
          stmt.bindString(7, value.getQrCode());
        }
        stmt.bindLong(8, value.getFechaLong());
        if (value.getFechaDia() == null) {
          stmt.bindNull(9);
        } else {
          stmt.bindString(9, value.getFechaDia());
        }
        if (value.getSemanaId() == null) {
          stmt.bindNull(10);
        } else {
          stmt.bindString(10, value.getSemanaId());
        }
        if (value.getMesId() == null) {
          stmt.bindNull(11);
        } else {
          stmt.bindString(11, value.getMesId());
        }
        if (value.getCreadoPorId() == null) {
          stmt.bindNull(12);
        } else {
          stmt.bindString(12, value.getCreadoPorId());
        }
      }
    };
  }

  @Override
  public Object insert(final Empleado empleado, final Continuation<? super Unit> continuation) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfEmpleado.insert(empleado);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, continuation);
  }

  @Override
  public Flow<List<Empleado>> getActivos() {
    final String _sql = "SELECT * FROM empleados WHERE activo = 1 ORDER BY nombre ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[]{"empleados"}, new Callable<List<Empleado>>() {
      @Override
      public List<Empleado> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfRolId = CursorUtil.getColumnIndexOrThrow(_cursor, "rolId");
          final int _cursorIndexOfNombre = CursorUtil.getColumnIndexOrThrow(_cursor, "nombre");
          final int _cursorIndexOfTelefono = CursorUtil.getColumnIndexOrThrow(_cursor, "telefono");
          final int _cursorIndexOfPagoPorBola = CursorUtil.getColumnIndexOrThrow(_cursor, "pagoPorBola");
          final int _cursorIndexOfActivo = CursorUtil.getColumnIndexOrThrow(_cursor, "activo");
          final int _cursorIndexOfQrCode = CursorUtil.getColumnIndexOrThrow(_cursor, "qrCode");
          final int _cursorIndexOfFechaLong = CursorUtil.getColumnIndexOrThrow(_cursor, "fechaLong");
          final int _cursorIndexOfFechaDia = CursorUtil.getColumnIndexOrThrow(_cursor, "fechaDia");
          final int _cursorIndexOfSemanaId = CursorUtil.getColumnIndexOrThrow(_cursor, "semanaId");
          final int _cursorIndexOfMesId = CursorUtil.getColumnIndexOrThrow(_cursor, "mesId");
          final int _cursorIndexOfCreadoPorId = CursorUtil.getColumnIndexOrThrow(_cursor, "creadoPorId");
          final List<Empleado> _result = new ArrayList<Empleado>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final Empleado _item;
            final String _tmpId;
            if (_cursor.isNull(_cursorIndexOfId)) {
              _tmpId = null;
            } else {
              _tmpId = _cursor.getString(_cursorIndexOfId);
            }
            final String _tmpRolId;
            if (_cursor.isNull(_cursorIndexOfRolId)) {
              _tmpRolId = null;
            } else {
              _tmpRolId = _cursor.getString(_cursorIndexOfRolId);
            }
            final String _tmpNombre;
            if (_cursor.isNull(_cursorIndexOfNombre)) {
              _tmpNombre = null;
            } else {
              _tmpNombre = _cursor.getString(_cursorIndexOfNombre);
            }
            final String _tmpTelefono;
            if (_cursor.isNull(_cursorIndexOfTelefono)) {
              _tmpTelefono = null;
            } else {
              _tmpTelefono = _cursor.getString(_cursorIndexOfTelefono);
            }
            final double _tmpPagoPorBola;
            _tmpPagoPorBola = _cursor.getDouble(_cursorIndexOfPagoPorBola);
            final boolean _tmpActivo;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfActivo);
            _tmpActivo = _tmp != 0;
            final String _tmpQrCode;
            if (_cursor.isNull(_cursorIndexOfQrCode)) {
              _tmpQrCode = null;
            } else {
              _tmpQrCode = _cursor.getString(_cursorIndexOfQrCode);
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
            final String _tmpCreadoPorId;
            if (_cursor.isNull(_cursorIndexOfCreadoPorId)) {
              _tmpCreadoPorId = null;
            } else {
              _tmpCreadoPorId = _cursor.getString(_cursorIndexOfCreadoPorId);
            }
            _item = new Empleado(_tmpId,_tmpRolId,_tmpNombre,_tmpTelefono,_tmpPagoPorBola,_tmpActivo,_tmpQrCode,_tmpFechaLong,_tmpFechaDia,_tmpSemanaId,_tmpMesId,_tmpCreadoPorId);
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
  public Object getById(final String id, final Continuation<? super Empleado> continuation) {
    final String _sql = "SELECT * FROM empleados WHERE id = ? LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (id == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, id);
    }
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Empleado>() {
      @Override
      public Empleado call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfRolId = CursorUtil.getColumnIndexOrThrow(_cursor, "rolId");
          final int _cursorIndexOfNombre = CursorUtil.getColumnIndexOrThrow(_cursor, "nombre");
          final int _cursorIndexOfTelefono = CursorUtil.getColumnIndexOrThrow(_cursor, "telefono");
          final int _cursorIndexOfPagoPorBola = CursorUtil.getColumnIndexOrThrow(_cursor, "pagoPorBola");
          final int _cursorIndexOfActivo = CursorUtil.getColumnIndexOrThrow(_cursor, "activo");
          final int _cursorIndexOfQrCode = CursorUtil.getColumnIndexOrThrow(_cursor, "qrCode");
          final int _cursorIndexOfFechaLong = CursorUtil.getColumnIndexOrThrow(_cursor, "fechaLong");
          final int _cursorIndexOfFechaDia = CursorUtil.getColumnIndexOrThrow(_cursor, "fechaDia");
          final int _cursorIndexOfSemanaId = CursorUtil.getColumnIndexOrThrow(_cursor, "semanaId");
          final int _cursorIndexOfMesId = CursorUtil.getColumnIndexOrThrow(_cursor, "mesId");
          final int _cursorIndexOfCreadoPorId = CursorUtil.getColumnIndexOrThrow(_cursor, "creadoPorId");
          final Empleado _result;
          if(_cursor.moveToFirst()) {
            final String _tmpId;
            if (_cursor.isNull(_cursorIndexOfId)) {
              _tmpId = null;
            } else {
              _tmpId = _cursor.getString(_cursorIndexOfId);
            }
            final String _tmpRolId;
            if (_cursor.isNull(_cursorIndexOfRolId)) {
              _tmpRolId = null;
            } else {
              _tmpRolId = _cursor.getString(_cursorIndexOfRolId);
            }
            final String _tmpNombre;
            if (_cursor.isNull(_cursorIndexOfNombre)) {
              _tmpNombre = null;
            } else {
              _tmpNombre = _cursor.getString(_cursorIndexOfNombre);
            }
            final String _tmpTelefono;
            if (_cursor.isNull(_cursorIndexOfTelefono)) {
              _tmpTelefono = null;
            } else {
              _tmpTelefono = _cursor.getString(_cursorIndexOfTelefono);
            }
            final double _tmpPagoPorBola;
            _tmpPagoPorBola = _cursor.getDouble(_cursorIndexOfPagoPorBola);
            final boolean _tmpActivo;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfActivo);
            _tmpActivo = _tmp != 0;
            final String _tmpQrCode;
            if (_cursor.isNull(_cursorIndexOfQrCode)) {
              _tmpQrCode = null;
            } else {
              _tmpQrCode = _cursor.getString(_cursorIndexOfQrCode);
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
            final String _tmpCreadoPorId;
            if (_cursor.isNull(_cursorIndexOfCreadoPorId)) {
              _tmpCreadoPorId = null;
            } else {
              _tmpCreadoPorId = _cursor.getString(_cursorIndexOfCreadoPorId);
            }
            _result = new Empleado(_tmpId,_tmpRolId,_tmpNombre,_tmpTelefono,_tmpPagoPorBola,_tmpActivo,_tmpQrCode,_tmpFechaLong,_tmpFechaDia,_tmpSemanaId,_tmpMesId,_tmpCreadoPorId);
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
