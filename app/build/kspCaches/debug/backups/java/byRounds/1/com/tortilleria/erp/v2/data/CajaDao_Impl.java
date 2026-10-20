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
public final class CajaDao_Impl implements CajaDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<MovimientoCaja> __insertionAdapterOfMovimientoCaja;

  public CajaDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfMovimientoCaja = new EntityInsertionAdapter<MovimientoCaja>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR REPLACE INTO `caja_movimientos` (`id`,`fechaLong`,`fechaDia`,`semanaId`,`tipo`,`categoria`,`monto`,`motivo`,`referenciaId`,`usuarioId`) VALUES (?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, MovimientoCaja value) {
        if (value.getId() == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindString(1, value.getId());
        }
        stmt.bindLong(2, value.getFechaLong());
        if (value.getFechaDia() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getFechaDia());
        }
        if (value.getSemanaId() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getSemanaId());
        }
        if (value.getTipo() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getTipo());
        }
        if (value.getCategoria() == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.getCategoria());
        }
        stmt.bindDouble(7, value.getMonto());
        if (value.getMotivo() == null) {
          stmt.bindNull(8);
        } else {
          stmt.bindString(8, value.getMotivo());
        }
        if (value.getReferenciaId() == null) {
          stmt.bindNull(9);
        } else {
          stmt.bindString(9, value.getReferenciaId());
        }
        if (value.getUsuarioId() == null) {
          stmt.bindNull(10);
        } else {
          stmt.bindString(10, value.getUsuarioId());
        }
      }
    };
  }

  @Override
  public Object insert(final MovimientoCaja mov, final Continuation<? super Unit> continuation) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfMovimientoCaja.insert(mov);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, continuation);
  }

  @Override
  public Flow<List<MovimientoCaja>> getPorDia(final String fechaDia) {
    final String _sql = "SELECT * FROM caja_movimientos WHERE fechaDia = ? ORDER BY fechaLong DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (fechaDia == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, fechaDia);
    }
    return CoroutinesRoom.createFlow(__db, false, new String[]{"caja_movimientos"}, new Callable<List<MovimientoCaja>>() {
      @Override
      public List<MovimientoCaja> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfFechaLong = CursorUtil.getColumnIndexOrThrow(_cursor, "fechaLong");
          final int _cursorIndexOfFechaDia = CursorUtil.getColumnIndexOrThrow(_cursor, "fechaDia");
          final int _cursorIndexOfSemanaId = CursorUtil.getColumnIndexOrThrow(_cursor, "semanaId");
          final int _cursorIndexOfTipo = CursorUtil.getColumnIndexOrThrow(_cursor, "tipo");
          final int _cursorIndexOfCategoria = CursorUtil.getColumnIndexOrThrow(_cursor, "categoria");
          final int _cursorIndexOfMonto = CursorUtil.getColumnIndexOrThrow(_cursor, "monto");
          final int _cursorIndexOfMotivo = CursorUtil.getColumnIndexOrThrow(_cursor, "motivo");
          final int _cursorIndexOfReferenciaId = CursorUtil.getColumnIndexOrThrow(_cursor, "referenciaId");
          final int _cursorIndexOfUsuarioId = CursorUtil.getColumnIndexOrThrow(_cursor, "usuarioId");
          final List<MovimientoCaja> _result = new ArrayList<MovimientoCaja>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final MovimientoCaja _item;
            final String _tmpId;
            if (_cursor.isNull(_cursorIndexOfId)) {
              _tmpId = null;
            } else {
              _tmpId = _cursor.getString(_cursorIndexOfId);
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
            final String _tmpTipo;
            if (_cursor.isNull(_cursorIndexOfTipo)) {
              _tmpTipo = null;
            } else {
              _tmpTipo = _cursor.getString(_cursorIndexOfTipo);
            }
            final String _tmpCategoria;
            if (_cursor.isNull(_cursorIndexOfCategoria)) {
              _tmpCategoria = null;
            } else {
              _tmpCategoria = _cursor.getString(_cursorIndexOfCategoria);
            }
            final double _tmpMonto;
            _tmpMonto = _cursor.getDouble(_cursorIndexOfMonto);
            final String _tmpMotivo;
            if (_cursor.isNull(_cursorIndexOfMotivo)) {
              _tmpMotivo = null;
            } else {
              _tmpMotivo = _cursor.getString(_cursorIndexOfMotivo);
            }
            final String _tmpReferenciaId;
            if (_cursor.isNull(_cursorIndexOfReferenciaId)) {
              _tmpReferenciaId = null;
            } else {
              _tmpReferenciaId = _cursor.getString(_cursorIndexOfReferenciaId);
            }
            final String _tmpUsuarioId;
            if (_cursor.isNull(_cursorIndexOfUsuarioId)) {
              _tmpUsuarioId = null;
            } else {
              _tmpUsuarioId = _cursor.getString(_cursorIndexOfUsuarioId);
            }
            _item = new MovimientoCaja(_tmpId,_tmpFechaLong,_tmpFechaDia,_tmpSemanaId,_tmpTipo,_tmpCategoria,_tmpMonto,_tmpMotivo,_tmpReferenciaId,_tmpUsuarioId);
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
  public Object totalPorTipoDia(final String fechaDia, final String tipo,
      final Continuation<? super Double> continuation) {
    final String _sql = "SELECT SUM(monto) FROM caja_movimientos WHERE fechaDia = ? AND tipo = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    if (fechaDia == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, fechaDia);
    }
    _argIndex = 2;
    if (tipo == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, tipo);
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
