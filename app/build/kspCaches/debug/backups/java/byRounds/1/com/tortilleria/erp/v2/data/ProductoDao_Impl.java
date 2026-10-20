package com.tortilleria.erp.v2.data;

import android.database.Cursor;
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
public final class ProductoDao_Impl implements ProductoDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Producto> __insertionAdapterOfProducto;

  private final EntityInsertionAdapter<VarianteProducto> __insertionAdapterOfVarianteProducto;

  public ProductoDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfProducto = new EntityInsertionAdapter<Producto>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR REPLACE INTO `productos` (`id`,`nombre`,`categoriaId`,`proveedorId`,`unidadBase`,`precioBase`,`activo`) VALUES (?,?,?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Producto value) {
        if (value.getId() == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindString(1, value.getId());
        }
        if (value.getNombre() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getNombre());
        }
        if (value.getCategoriaId() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getCategoriaId());
        }
        if (value.getProveedorId() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getProveedorId());
        }
        if (value.getUnidadBase() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getUnidadBase());
        }
        stmt.bindDouble(6, value.getPrecioBase());
        final int _tmp = value.getActivo() ? 1 : 0;
        stmt.bindLong(7, _tmp);
      }
    };
    this.__insertionAdapterOfVarianteProducto = new EntityInsertionAdapter<VarianteProducto>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR REPLACE INTO `variantes` (`id`,`productoId`,`nombreVariante`,`sku`,`precioVenta`,`precioMayoreo`,`costo`,`stock`) VALUES (?,?,?,?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, VarianteProducto value) {
        if (value.getId() == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindString(1, value.getId());
        }
        if (value.getProductoId() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getProductoId());
        }
        if (value.getNombreVariante() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getNombreVariante());
        }
        if (value.getSku() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getSku());
        }
        stmt.bindDouble(5, value.getPrecioVenta());
        stmt.bindDouble(6, value.getPrecioMayoreo());
        stmt.bindDouble(7, value.getCosto());
        stmt.bindDouble(8, value.getStock());
      }
    };
  }

  @Override
  public Object insertProducto(final Producto p, final Continuation<? super Unit> continuation) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfProducto.insert(p);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, continuation);
  }

  @Override
  public Object insertVariante(final VarianteProducto v,
      final Continuation<? super Unit> continuation) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfVarianteProducto.insert(v);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, continuation);
  }

  @Override
  public Flow<List<Producto>> getAll() {
    final String _sql = "SELECT * FROM productos WHERE activo = 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[]{"productos"}, new Callable<List<Producto>>() {
      @Override
      public List<Producto> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfNombre = CursorUtil.getColumnIndexOrThrow(_cursor, "nombre");
          final int _cursorIndexOfCategoriaId = CursorUtil.getColumnIndexOrThrow(_cursor, "categoriaId");
          final int _cursorIndexOfProveedorId = CursorUtil.getColumnIndexOrThrow(_cursor, "proveedorId");
          final int _cursorIndexOfUnidadBase = CursorUtil.getColumnIndexOrThrow(_cursor, "unidadBase");
          final int _cursorIndexOfPrecioBase = CursorUtil.getColumnIndexOrThrow(_cursor, "precioBase");
          final int _cursorIndexOfActivo = CursorUtil.getColumnIndexOrThrow(_cursor, "activo");
          final List<Producto> _result = new ArrayList<Producto>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final Producto _item;
            final String _tmpId;
            if (_cursor.isNull(_cursorIndexOfId)) {
              _tmpId = null;
            } else {
              _tmpId = _cursor.getString(_cursorIndexOfId);
            }
            final String _tmpNombre;
            if (_cursor.isNull(_cursorIndexOfNombre)) {
              _tmpNombre = null;
            } else {
              _tmpNombre = _cursor.getString(_cursorIndexOfNombre);
            }
            final String _tmpCategoriaId;
            if (_cursor.isNull(_cursorIndexOfCategoriaId)) {
              _tmpCategoriaId = null;
            } else {
              _tmpCategoriaId = _cursor.getString(_cursorIndexOfCategoriaId);
            }
            final String _tmpProveedorId;
            if (_cursor.isNull(_cursorIndexOfProveedorId)) {
              _tmpProveedorId = null;
            } else {
              _tmpProveedorId = _cursor.getString(_cursorIndexOfProveedorId);
            }
            final String _tmpUnidadBase;
            if (_cursor.isNull(_cursorIndexOfUnidadBase)) {
              _tmpUnidadBase = null;
            } else {
              _tmpUnidadBase = _cursor.getString(_cursorIndexOfUnidadBase);
            }
            final double _tmpPrecioBase;
            _tmpPrecioBase = _cursor.getDouble(_cursorIndexOfPrecioBase);
            final boolean _tmpActivo;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfActivo);
            _tmpActivo = _tmp != 0;
            _item = new Producto(_tmpId,_tmpNombre,_tmpCategoriaId,_tmpProveedorId,_tmpUnidadBase,_tmpPrecioBase,_tmpActivo);
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
  public Flow<List<VarianteProducto>> getVariantes(final String productoId) {
    final String _sql = "SELECT * FROM variantes WHERE productoId = ? AND stock > 0";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (productoId == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, productoId);
    }
    return CoroutinesRoom.createFlow(__db, false, new String[]{"variantes"}, new Callable<List<VarianteProducto>>() {
      @Override
      public List<VarianteProducto> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfProductoId = CursorUtil.getColumnIndexOrThrow(_cursor, "productoId");
          final int _cursorIndexOfNombreVariante = CursorUtil.getColumnIndexOrThrow(_cursor, "nombreVariante");
          final int _cursorIndexOfSku = CursorUtil.getColumnIndexOrThrow(_cursor, "sku");
          final int _cursorIndexOfPrecioVenta = CursorUtil.getColumnIndexOrThrow(_cursor, "precioVenta");
          final int _cursorIndexOfPrecioMayoreo = CursorUtil.getColumnIndexOrThrow(_cursor, "precioMayoreo");
          final int _cursorIndexOfCosto = CursorUtil.getColumnIndexOrThrow(_cursor, "costo");
          final int _cursorIndexOfStock = CursorUtil.getColumnIndexOrThrow(_cursor, "stock");
          final List<VarianteProducto> _result = new ArrayList<VarianteProducto>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final VarianteProducto _item;
            final String _tmpId;
            if (_cursor.isNull(_cursorIndexOfId)) {
              _tmpId = null;
            } else {
              _tmpId = _cursor.getString(_cursorIndexOfId);
            }
            final String _tmpProductoId;
            if (_cursor.isNull(_cursorIndexOfProductoId)) {
              _tmpProductoId = null;
            } else {
              _tmpProductoId = _cursor.getString(_cursorIndexOfProductoId);
            }
            final String _tmpNombreVariante;
            if (_cursor.isNull(_cursorIndexOfNombreVariante)) {
              _tmpNombreVariante = null;
            } else {
              _tmpNombreVariante = _cursor.getString(_cursorIndexOfNombreVariante);
            }
            final String _tmpSku;
            if (_cursor.isNull(_cursorIndexOfSku)) {
              _tmpSku = null;
            } else {
              _tmpSku = _cursor.getString(_cursorIndexOfSku);
            }
            final double _tmpPrecioVenta;
            _tmpPrecioVenta = _cursor.getDouble(_cursorIndexOfPrecioVenta);
            final double _tmpPrecioMayoreo;
            _tmpPrecioMayoreo = _cursor.getDouble(_cursorIndexOfPrecioMayoreo);
            final double _tmpCosto;
            _tmpCosto = _cursor.getDouble(_cursorIndexOfCosto);
            final double _tmpStock;
            _tmpStock = _cursor.getDouble(_cursorIndexOfStock);
            _item = new VarianteProducto(_tmpId,_tmpProductoId,_tmpNombreVariante,_tmpSku,_tmpPrecioVenta,_tmpPrecioMayoreo,_tmpCosto,_tmpStock);
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

  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
