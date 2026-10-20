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
import java.lang.Integer;
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
public final class VentaDao_Impl implements VentaDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Venta> __insertionAdapterOfVenta;

  private final EntityInsertionAdapter<VentaItem> __insertionAdapterOfVentaItem;

  public VentaDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfVenta = new EntityInsertionAdapter<Venta>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR REPLACE INTO `ventas` (`id`,`folioGlobal`,`folioDiario`,`folioSemanal`,`fechaLong`,`fechaDia`,`semanaId`,`clienteId`,`clienteNombreSnapshot`,`usuarioId`,`total`,`metodoPago`,`estado`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Venta value) {
        if (value.getId() == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindString(1, value.getId());
        }
        stmt.bindLong(2, value.getFolioGlobal());
        if (value.getFolioDiario() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getFolioDiario());
        }
        if (value.getFolioSemanal() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getFolioSemanal());
        }
        stmt.bindLong(5, value.getFechaLong());
        if (value.getFechaDia() == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.getFechaDia());
        }
        if (value.getSemanaId() == null) {
          stmt.bindNull(7);
        } else {
          stmt.bindString(7, value.getSemanaId());
        }
        if (value.getClienteId() == null) {
          stmt.bindNull(8);
        } else {
          stmt.bindString(8, value.getClienteId());
        }
        if (value.getClienteNombreSnapshot() == null) {
          stmt.bindNull(9);
        } else {
          stmt.bindString(9, value.getClienteNombreSnapshot());
        }
        if (value.getUsuarioId() == null) {
          stmt.bindNull(10);
        } else {
          stmt.bindString(10, value.getUsuarioId());
        }
        stmt.bindDouble(11, value.getTotal());
        if (value.getMetodoPago() == null) {
          stmt.bindNull(12);
        } else {
          stmt.bindString(12, value.getMetodoPago());
        }
        if (value.getEstado() == null) {
          stmt.bindNull(13);
        } else {
          stmt.bindString(13, value.getEstado());
        }
      }
    };
    this.__insertionAdapterOfVentaItem = new EntityInsertionAdapter<VentaItem>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR REPLACE INTO `venta_items` (`id`,`ventaId`,`productoId`,`varianteId`,`nombreSnapshot`,`cantidad`,`precioUnitarioSnapshot`,`subtotal`) VALUES (?,?,?,?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, VentaItem value) {
        if (value.getId() == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindString(1, value.getId());
        }
        if (value.getVentaId() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getVentaId());
        }
        if (value.getProductoId() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getProductoId());
        }
        if (value.getVarianteId() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getVarianteId());
        }
        if (value.getNombreSnapshot() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getNombreSnapshot());
        }
        stmt.bindDouble(6, value.getCantidad());
        stmt.bindDouble(7, value.getPrecioUnitarioSnapshot());
        stmt.bindDouble(8, value.getSubtotal());
      }
    };
  }

  @Override
  public Object insert(final Venta venta, final Continuation<? super Unit> continuation) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfVenta.insert(venta);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, continuation);
  }

  @Override
  public Object insertItems(final List<VentaItem> items,
      final Continuation<? super Unit> continuation) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfVentaItem.insert(items);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, continuation);
  }

  @Override
  public Flow<List<Venta>> getPorDia(final String fechaDia) {
    final String _sql = "SELECT * FROM ventas WHERE fechaDia = ? ORDER BY fechaLong DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (fechaDia == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, fechaDia);
    }
    return CoroutinesRoom.createFlow(__db, false, new String[]{"ventas"}, new Callable<List<Venta>>() {
      @Override
      public List<Venta> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfFolioGlobal = CursorUtil.getColumnIndexOrThrow(_cursor, "folioGlobal");
          final int _cursorIndexOfFolioDiario = CursorUtil.getColumnIndexOrThrow(_cursor, "folioDiario");
          final int _cursorIndexOfFolioSemanal = CursorUtil.getColumnIndexOrThrow(_cursor, "folioSemanal");
          final int _cursorIndexOfFechaLong = CursorUtil.getColumnIndexOrThrow(_cursor, "fechaLong");
          final int _cursorIndexOfFechaDia = CursorUtil.getColumnIndexOrThrow(_cursor, "fechaDia");
          final int _cursorIndexOfSemanaId = CursorUtil.getColumnIndexOrThrow(_cursor, "semanaId");
          final int _cursorIndexOfClienteId = CursorUtil.getColumnIndexOrThrow(_cursor, "clienteId");
          final int _cursorIndexOfClienteNombreSnapshot = CursorUtil.getColumnIndexOrThrow(_cursor, "clienteNombreSnapshot");
          final int _cursorIndexOfUsuarioId = CursorUtil.getColumnIndexOrThrow(_cursor, "usuarioId");
          final int _cursorIndexOfTotal = CursorUtil.getColumnIndexOrThrow(_cursor, "total");
          final int _cursorIndexOfMetodoPago = CursorUtil.getColumnIndexOrThrow(_cursor, "metodoPago");
          final int _cursorIndexOfEstado = CursorUtil.getColumnIndexOrThrow(_cursor, "estado");
          final List<Venta> _result = new ArrayList<Venta>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final Venta _item;
            final String _tmpId;
            if (_cursor.isNull(_cursorIndexOfId)) {
              _tmpId = null;
            } else {
              _tmpId = _cursor.getString(_cursorIndexOfId);
            }
            final int _tmpFolioGlobal;
            _tmpFolioGlobal = _cursor.getInt(_cursorIndexOfFolioGlobal);
            final String _tmpFolioDiario;
            if (_cursor.isNull(_cursorIndexOfFolioDiario)) {
              _tmpFolioDiario = null;
            } else {
              _tmpFolioDiario = _cursor.getString(_cursorIndexOfFolioDiario);
            }
            final String _tmpFolioSemanal;
            if (_cursor.isNull(_cursorIndexOfFolioSemanal)) {
              _tmpFolioSemanal = null;
            } else {
              _tmpFolioSemanal = _cursor.getString(_cursorIndexOfFolioSemanal);
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
            final String _tmpClienteId;
            if (_cursor.isNull(_cursorIndexOfClienteId)) {
              _tmpClienteId = null;
            } else {
              _tmpClienteId = _cursor.getString(_cursorIndexOfClienteId);
            }
            final String _tmpClienteNombreSnapshot;
            if (_cursor.isNull(_cursorIndexOfClienteNombreSnapshot)) {
              _tmpClienteNombreSnapshot = null;
            } else {
              _tmpClienteNombreSnapshot = _cursor.getString(_cursorIndexOfClienteNombreSnapshot);
            }
            final String _tmpUsuarioId;
            if (_cursor.isNull(_cursorIndexOfUsuarioId)) {
              _tmpUsuarioId = null;
            } else {
              _tmpUsuarioId = _cursor.getString(_cursorIndexOfUsuarioId);
            }
            final double _tmpTotal;
            _tmpTotal = _cursor.getDouble(_cursorIndexOfTotal);
            final String _tmpMetodoPago;
            if (_cursor.isNull(_cursorIndexOfMetodoPago)) {
              _tmpMetodoPago = null;
            } else {
              _tmpMetodoPago = _cursor.getString(_cursorIndexOfMetodoPago);
            }
            final String _tmpEstado;
            if (_cursor.isNull(_cursorIndexOfEstado)) {
              _tmpEstado = null;
            } else {
              _tmpEstado = _cursor.getString(_cursorIndexOfEstado);
            }
            _item = new Venta(_tmpId,_tmpFolioGlobal,_tmpFolioDiario,_tmpFolioSemanal,_tmpFechaLong,_tmpFechaDia,_tmpSemanaId,_tmpClienteId,_tmpClienteNombreSnapshot,_tmpUsuarioId,_tmpTotal,_tmpMetodoPago,_tmpEstado);
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
  public Flow<List<Venta>> getPorSemana(final String semanaId) {
    final String _sql = "SELECT * FROM ventas WHERE semanaId = ? ORDER BY folioGlobal DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (semanaId == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, semanaId);
    }
    return CoroutinesRoom.createFlow(__db, false, new String[]{"ventas"}, new Callable<List<Venta>>() {
      @Override
      public List<Venta> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfFolioGlobal = CursorUtil.getColumnIndexOrThrow(_cursor, "folioGlobal");
          final int _cursorIndexOfFolioDiario = CursorUtil.getColumnIndexOrThrow(_cursor, "folioDiario");
          final int _cursorIndexOfFolioSemanal = CursorUtil.getColumnIndexOrThrow(_cursor, "folioSemanal");
          final int _cursorIndexOfFechaLong = CursorUtil.getColumnIndexOrThrow(_cursor, "fechaLong");
          final int _cursorIndexOfFechaDia = CursorUtil.getColumnIndexOrThrow(_cursor, "fechaDia");
          final int _cursorIndexOfSemanaId = CursorUtil.getColumnIndexOrThrow(_cursor, "semanaId");
          final int _cursorIndexOfClienteId = CursorUtil.getColumnIndexOrThrow(_cursor, "clienteId");
          final int _cursorIndexOfClienteNombreSnapshot = CursorUtil.getColumnIndexOrThrow(_cursor, "clienteNombreSnapshot");
          final int _cursorIndexOfUsuarioId = CursorUtil.getColumnIndexOrThrow(_cursor, "usuarioId");
          final int _cursorIndexOfTotal = CursorUtil.getColumnIndexOrThrow(_cursor, "total");
          final int _cursorIndexOfMetodoPago = CursorUtil.getColumnIndexOrThrow(_cursor, "metodoPago");
          final int _cursorIndexOfEstado = CursorUtil.getColumnIndexOrThrow(_cursor, "estado");
          final List<Venta> _result = new ArrayList<Venta>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final Venta _item;
            final String _tmpId;
            if (_cursor.isNull(_cursorIndexOfId)) {
              _tmpId = null;
            } else {
              _tmpId = _cursor.getString(_cursorIndexOfId);
            }
            final int _tmpFolioGlobal;
            _tmpFolioGlobal = _cursor.getInt(_cursorIndexOfFolioGlobal);
            final String _tmpFolioDiario;
            if (_cursor.isNull(_cursorIndexOfFolioDiario)) {
              _tmpFolioDiario = null;
            } else {
              _tmpFolioDiario = _cursor.getString(_cursorIndexOfFolioDiario);
            }
            final String _tmpFolioSemanal;
            if (_cursor.isNull(_cursorIndexOfFolioSemanal)) {
              _tmpFolioSemanal = null;
            } else {
              _tmpFolioSemanal = _cursor.getString(_cursorIndexOfFolioSemanal);
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
            final String _tmpClienteId;
            if (_cursor.isNull(_cursorIndexOfClienteId)) {
              _tmpClienteId = null;
            } else {
              _tmpClienteId = _cursor.getString(_cursorIndexOfClienteId);
            }
            final String _tmpClienteNombreSnapshot;
            if (_cursor.isNull(_cursorIndexOfClienteNombreSnapshot)) {
              _tmpClienteNombreSnapshot = null;
            } else {
              _tmpClienteNombreSnapshot = _cursor.getString(_cursorIndexOfClienteNombreSnapshot);
            }
            final String _tmpUsuarioId;
            if (_cursor.isNull(_cursorIndexOfUsuarioId)) {
              _tmpUsuarioId = null;
            } else {
              _tmpUsuarioId = _cursor.getString(_cursorIndexOfUsuarioId);
            }
            final double _tmpTotal;
            _tmpTotal = _cursor.getDouble(_cursorIndexOfTotal);
            final String _tmpMetodoPago;
            if (_cursor.isNull(_cursorIndexOfMetodoPago)) {
              _tmpMetodoPago = null;
            } else {
              _tmpMetodoPago = _cursor.getString(_cursorIndexOfMetodoPago);
            }
            final String _tmpEstado;
            if (_cursor.isNull(_cursorIndexOfEstado)) {
              _tmpEstado = null;
            } else {
              _tmpEstado = _cursor.getString(_cursorIndexOfEstado);
            }
            _item = new Venta(_tmpId,_tmpFolioGlobal,_tmpFolioDiario,_tmpFolioSemanal,_tmpFechaLong,_tmpFechaDia,_tmpSemanaId,_tmpClienteId,_tmpClienteNombreSnapshot,_tmpUsuarioId,_tmpTotal,_tmpMetodoPago,_tmpEstado);
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
  public Object getMaxFolioGlobal(final Continuation<? super Integer> continuation) {
    final String _sql = "SELECT MAX(folioGlobal) FROM ventas";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Integer>() {
      @Override
      public Integer call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Integer _result;
          if(_cursor.moveToFirst()) {
            final Integer _tmp;
            if (_cursor.isNull(0)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getInt(0);
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
