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
import java.lang.Long;
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
public final class TicketDao_Impl implements TicketDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Ticket> __insertionAdapterOfTicket;

  private final EntityInsertionAdapter<TicketDetalle> __insertionAdapterOfTicketDetalle;

  private final SharedSQLiteStatement __preparedStmtOfMarcarCobrado;

  public TicketDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfTicket = new EntityInsertionAdapter<Ticket>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `tickets` (`id`,`total`,`tipo`,`cliente`,`fecha`) VALUES (nullif(?, 0),?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Ticket entity) {
        statement.bindLong(1, entity.getId());
        statement.bindDouble(2, entity.getTotal());
        statement.bindString(3, entity.getTipo());
        statement.bindString(4, entity.getCliente());
        statement.bindLong(5, entity.getFecha());
      }
    };
    this.__insertionAdapterOfTicketDetalle = new EntityInsertionAdapter<TicketDetalle>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `ticket_detalle` (`id`,`ticketId`,`producto`,`variante`,`kilos`,`precioKg`,`total`) VALUES (nullif(?, 0),?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final TicketDetalle entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getTicketId());
        statement.bindString(3, entity.getProducto());
        statement.bindString(4, entity.getVariante());
        statement.bindDouble(5, entity.getKilos());
        statement.bindDouble(6, entity.getPrecioKg());
        statement.bindDouble(7, entity.getTotal());
      }
    };
    this.__preparedStmtOfMarcarCobrado = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE tickets SET tipo='COBRADO' WHERE id=?";
        return _query;
      }
    };
  }

  @Override
  public Object insertarTicket(final Ticket t, final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfTicket.insertAndReturnId(t);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object insertarDetalles(final List<TicketDetalle> detalles,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfTicketDetalle.insert(detalles);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object marcarCobrado(final int id, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfMarcarCobrado.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, id);
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
          __preparedStmtOfMarcarCobrado.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object obtenerTickets(final Continuation<? super List<Ticket>> $completion) {
    final String _sql = "SELECT * FROM tickets ORDER BY fecha DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<Ticket>>() {
      @Override
      @NonNull
      public List<Ticket> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTotal = CursorUtil.getColumnIndexOrThrow(_cursor, "total");
          final int _cursorIndexOfTipo = CursorUtil.getColumnIndexOrThrow(_cursor, "tipo");
          final int _cursorIndexOfCliente = CursorUtil.getColumnIndexOrThrow(_cursor, "cliente");
          final int _cursorIndexOfFecha = CursorUtil.getColumnIndexOrThrow(_cursor, "fecha");
          final List<Ticket> _result = new ArrayList<Ticket>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Ticket _item;
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final double _tmpTotal;
            _tmpTotal = _cursor.getDouble(_cursorIndexOfTotal);
            final String _tmpTipo;
            _tmpTipo = _cursor.getString(_cursorIndexOfTipo);
            final String _tmpCliente;
            _tmpCliente = _cursor.getString(_cursorIndexOfCliente);
            final long _tmpFecha;
            _tmpFecha = _cursor.getLong(_cursorIndexOfFecha);
            _item = new Ticket(_tmpId,_tmpTotal,_tmpTipo,_tmpCliente,_tmpFecha);
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
  public Object obtenerPorCobrar(final Continuation<? super List<Ticket>> $completion) {
    final String _sql = "SELECT * FROM tickets WHERE tipo='POR_COBRAR' ORDER BY fecha DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<Ticket>>() {
      @Override
      @NonNull
      public List<Ticket> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTotal = CursorUtil.getColumnIndexOrThrow(_cursor, "total");
          final int _cursorIndexOfTipo = CursorUtil.getColumnIndexOrThrow(_cursor, "tipo");
          final int _cursorIndexOfCliente = CursorUtil.getColumnIndexOrThrow(_cursor, "cliente");
          final int _cursorIndexOfFecha = CursorUtil.getColumnIndexOrThrow(_cursor, "fecha");
          final List<Ticket> _result = new ArrayList<Ticket>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Ticket _item;
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final double _tmpTotal;
            _tmpTotal = _cursor.getDouble(_cursorIndexOfTotal);
            final String _tmpTipo;
            _tmpTipo = _cursor.getString(_cursorIndexOfTipo);
            final String _tmpCliente;
            _tmpCliente = _cursor.getString(_cursorIndexOfCliente);
            final long _tmpFecha;
            _tmpFecha = _cursor.getLong(_cursorIndexOfFecha);
            _item = new Ticket(_tmpId,_tmpTotal,_tmpTipo,_tmpCliente,_tmpFecha);
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
  public Object obtenerCobrados(final Continuation<? super List<Ticket>> $completion) {
    final String _sql = "SELECT * FROM tickets WHERE tipo='COBRADO' ORDER BY fecha DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<Ticket>>() {
      @Override
      @NonNull
      public List<Ticket> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTotal = CursorUtil.getColumnIndexOrThrow(_cursor, "total");
          final int _cursorIndexOfTipo = CursorUtil.getColumnIndexOrThrow(_cursor, "tipo");
          final int _cursorIndexOfCliente = CursorUtil.getColumnIndexOrThrow(_cursor, "cliente");
          final int _cursorIndexOfFecha = CursorUtil.getColumnIndexOrThrow(_cursor, "fecha");
          final List<Ticket> _result = new ArrayList<Ticket>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Ticket _item;
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final double _tmpTotal;
            _tmpTotal = _cursor.getDouble(_cursorIndexOfTotal);
            final String _tmpTipo;
            _tmpTipo = _cursor.getString(_cursorIndexOfTipo);
            final String _tmpCliente;
            _tmpCliente = _cursor.getString(_cursorIndexOfCliente);
            final long _tmpFecha;
            _tmpFecha = _cursor.getLong(_cursorIndexOfFecha);
            _item = new Ticket(_tmpId,_tmpTotal,_tmpTipo,_tmpCliente,_tmpFecha);
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
  public Object detalleDe(final int id,
      final Continuation<? super List<TicketDetalle>> $completion) {
    final String _sql = "SELECT * FROM ticket_detalle WHERE ticketId=?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<TicketDetalle>>() {
      @Override
      @NonNull
      public List<TicketDetalle> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTicketId = CursorUtil.getColumnIndexOrThrow(_cursor, "ticketId");
          final int _cursorIndexOfProducto = CursorUtil.getColumnIndexOrThrow(_cursor, "producto");
          final int _cursorIndexOfVariante = CursorUtil.getColumnIndexOrThrow(_cursor, "variante");
          final int _cursorIndexOfKilos = CursorUtil.getColumnIndexOrThrow(_cursor, "kilos");
          final int _cursorIndexOfPrecioKg = CursorUtil.getColumnIndexOrThrow(_cursor, "precioKg");
          final int _cursorIndexOfTotal = CursorUtil.getColumnIndexOrThrow(_cursor, "total");
          final List<TicketDetalle> _result = new ArrayList<TicketDetalle>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final TicketDetalle _item;
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final int _tmpTicketId;
            _tmpTicketId = _cursor.getInt(_cursorIndexOfTicketId);
            final String _tmpProducto;
            _tmpProducto = _cursor.getString(_cursorIndexOfProducto);
            final String _tmpVariante;
            _tmpVariante = _cursor.getString(_cursorIndexOfVariante);
            final double _tmpKilos;
            _tmpKilos = _cursor.getDouble(_cursorIndexOfKilos);
            final double _tmpPrecioKg;
            _tmpPrecioKg = _cursor.getDouble(_cursorIndexOfPrecioKg);
            final double _tmpTotal;
            _tmpTotal = _cursor.getDouble(_cursorIndexOfTotal);
            _item = new TicketDetalle(_tmpId,_tmpTicketId,_tmpProducto,_tmpVariante,_tmpKilos,_tmpPrecioKg,_tmpTotal);
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
  public Object totalHoy(final Continuation<? super Double> $completion) {
    final String _sql = "SELECT SUM(total) FROM tickets WHERE tipo='COBRADO' AND date(fecha/1000,'unixepoch','localtime') = date('now','localtime')";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
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
