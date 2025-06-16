package com.vivalnk.sdk.demo.repository.database.room;

import android.database.Cursor;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Class;
import java.lang.Integer;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class IRoomDataDAO_Impl implements IRoomDataDAO {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<VitalData_Room> __insertionAdapterOfVitalData_Room;

  private final DeviceModelConverter __deviceModelConverter = new DeviceModelConverter();

  private final MapConverter __mapConverter = new MapConverter();

  private final EntityDeletionOrUpdateAdapter<VitalData_Room> __deletionAdapterOfVitalData_Room;

  private final EntityDeletionOrUpdateAdapter<VitalData_Room> __updateAdapterOfVitalData_Room;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAll;

  public IRoomDataDAO_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfVitalData_Room = new EntityInsertionAdapter<VitalData_Room>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR REPLACE INTO `demo_data` (`id`,`d_id`,`d_sn`,`d_name`,`model`,`time`,`extras`) VALUES (?,?,?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, VitalData_Room value) {
        stmt.bindLong(1, value.id);
        if (value.deviceID == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.deviceID);
        }
        if (value.deviceSN == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.deviceSN);
        }
        if (value.deviceName == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.deviceName);
        }
        final Integer _tmp = __deviceModelConverter.convertToDatabaseValue(value.deviceModel);
        if (_tmp == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindLong(5, _tmp);
        }
        stmt.bindLong(6, value.time);
        final String _tmp_1 = __mapConverter.convertToDatabaseValue(value.extras);
        if (_tmp_1 == null) {
          stmt.bindNull(7);
        } else {
          stmt.bindString(7, _tmp_1);
        }
      }
    };
    this.__deletionAdapterOfVitalData_Room = new EntityDeletionOrUpdateAdapter<VitalData_Room>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `demo_data` WHERE `d_id` = ? AND `model` = ? AND `time` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, VitalData_Room value) {
        if (value.deviceID == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindString(1, value.deviceID);
        }
        final Integer _tmp = __deviceModelConverter.convertToDatabaseValue(value.deviceModel);
        if (_tmp == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindLong(2, _tmp);
        }
        stmt.bindLong(3, value.time);
      }
    };
    this.__updateAdapterOfVitalData_Room = new EntityDeletionOrUpdateAdapter<VitalData_Room>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `demo_data` SET `id` = ?,`d_id` = ?,`d_sn` = ?,`d_name` = ?,`model` = ?,`time` = ?,`extras` = ? WHERE `d_id` = ? AND `model` = ? AND `time` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, VitalData_Room value) {
        stmt.bindLong(1, value.id);
        if (value.deviceID == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.deviceID);
        }
        if (value.deviceSN == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.deviceSN);
        }
        if (value.deviceName == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.deviceName);
        }
        final Integer _tmp = __deviceModelConverter.convertToDatabaseValue(value.deviceModel);
        if (_tmp == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindLong(5, _tmp);
        }
        stmt.bindLong(6, value.time);
        final String _tmp_1 = __mapConverter.convertToDatabaseValue(value.extras);
        if (_tmp_1 == null) {
          stmt.bindNull(7);
        } else {
          stmt.bindString(7, _tmp_1);
        }
        if (value.deviceID == null) {
          stmt.bindNull(8);
        } else {
          stmt.bindString(8, value.deviceID);
        }
        final Integer _tmp_2 = __deviceModelConverter.convertToDatabaseValue(value.deviceModel);
        if (_tmp_2 == null) {
          stmt.bindNull(9);
        } else {
          stmt.bindLong(9, _tmp_2);
        }
        stmt.bindLong(10, value.time);
      }
    };
    this.__preparedStmtOfDeleteAll = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM demo_data";
        return _query;
      }
    };
  }

  @Override
  public void insert(final VitalData_Room... data) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfVitalData_Room.insert(data);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void delete(final VitalData_Room... data) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __deletionAdapterOfVitalData_Room.handleMultiple(data);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void update(final VitalData_Room... data) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __updateAdapterOfVitalData_Room.handleMultiple(data);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deleteAll() {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteAll.acquire();
    __db.beginTransaction();
    try {
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfDeleteAll.release(_stmt);
    }
  }

  @Override
  public List<VitalData_Room> queryAll() {
    final String _sql = "SELECT * FROM demo_data";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfDeviceID = CursorUtil.getColumnIndexOrThrow(_cursor, "d_id");
      final int _cursorIndexOfDeviceSN = CursorUtil.getColumnIndexOrThrow(_cursor, "d_sn");
      final int _cursorIndexOfDeviceName = CursorUtil.getColumnIndexOrThrow(_cursor, "d_name");
      final int _cursorIndexOfDeviceModel = CursorUtil.getColumnIndexOrThrow(_cursor, "model");
      final int _cursorIndexOfTime = CursorUtil.getColumnIndexOrThrow(_cursor, "time");
      final int _cursorIndexOfExtras = CursorUtil.getColumnIndexOrThrow(_cursor, "extras");
      final List<VitalData_Room> _result = new ArrayList<VitalData_Room>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final VitalData_Room _item;
        _item = new VitalData_Room();
        _item.id = _cursor.getLong(_cursorIndexOfId);
        if (_cursor.isNull(_cursorIndexOfDeviceID)) {
          _item.deviceID = null;
        } else {
          _item.deviceID = _cursor.getString(_cursorIndexOfDeviceID);
        }
        if (_cursor.isNull(_cursorIndexOfDeviceSN)) {
          _item.deviceSN = null;
        } else {
          _item.deviceSN = _cursor.getString(_cursorIndexOfDeviceSN);
        }
        if (_cursor.isNull(_cursorIndexOfDeviceName)) {
          _item.deviceName = null;
        } else {
          _item.deviceName = _cursor.getString(_cursorIndexOfDeviceName);
        }
        final Integer _tmp;
        if (_cursor.isNull(_cursorIndexOfDeviceModel)) {
          _tmp = null;
        } else {
          _tmp = _cursor.getInt(_cursorIndexOfDeviceModel);
        }
        _item.deviceModel = __deviceModelConverter.convertToEntityProperty(_tmp);
        _item.time = _cursor.getLong(_cursorIndexOfTime);
        final String _tmp_1;
        if (_cursor.isNull(_cursorIndexOfExtras)) {
          _tmp_1 = null;
        } else {
          _tmp_1 = _cursor.getString(_cursorIndexOfExtras);
        }
        _item.extras = __mapConverter.convertToEntityProperty(_tmp_1);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public List<VitalData_Room> queryAll(final String deviceID) {
    final String _sql = "SELECT * FROM demo_data WHERE d_id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (deviceID == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, deviceID);
    }
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfDeviceID = CursorUtil.getColumnIndexOrThrow(_cursor, "d_id");
      final int _cursorIndexOfDeviceSN = CursorUtil.getColumnIndexOrThrow(_cursor, "d_sn");
      final int _cursorIndexOfDeviceName = CursorUtil.getColumnIndexOrThrow(_cursor, "d_name");
      final int _cursorIndexOfDeviceModel = CursorUtil.getColumnIndexOrThrow(_cursor, "model");
      final int _cursorIndexOfTime = CursorUtil.getColumnIndexOrThrow(_cursor, "time");
      final int _cursorIndexOfExtras = CursorUtil.getColumnIndexOrThrow(_cursor, "extras");
      final List<VitalData_Room> _result = new ArrayList<VitalData_Room>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final VitalData_Room _item;
        _item = new VitalData_Room();
        _item.id = _cursor.getLong(_cursorIndexOfId);
        if (_cursor.isNull(_cursorIndexOfDeviceID)) {
          _item.deviceID = null;
        } else {
          _item.deviceID = _cursor.getString(_cursorIndexOfDeviceID);
        }
        if (_cursor.isNull(_cursorIndexOfDeviceSN)) {
          _item.deviceSN = null;
        } else {
          _item.deviceSN = _cursor.getString(_cursorIndexOfDeviceSN);
        }
        if (_cursor.isNull(_cursorIndexOfDeviceName)) {
          _item.deviceName = null;
        } else {
          _item.deviceName = _cursor.getString(_cursorIndexOfDeviceName);
        }
        final Integer _tmp;
        if (_cursor.isNull(_cursorIndexOfDeviceModel)) {
          _tmp = null;
        } else {
          _tmp = _cursor.getInt(_cursorIndexOfDeviceModel);
        }
        _item.deviceModel = __deviceModelConverter.convertToEntityProperty(_tmp);
        _item.time = _cursor.getLong(_cursorIndexOfTime);
        final String _tmp_1;
        if (_cursor.isNull(_cursorIndexOfExtras)) {
          _tmp_1 = null;
        } else {
          _tmp_1 = _cursor.getString(_cursorIndexOfExtras);
        }
        _item.extras = __mapConverter.convertToEntityProperty(_tmp_1);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public List<VitalData_Room> queryAllOrderByTimeASC(final String deviceId) {
    final String _sql = "SELECT * FROM demo_data WHERE d_id = ? ORDER BY time ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (deviceId == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, deviceId);
    }
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfDeviceID = CursorUtil.getColumnIndexOrThrow(_cursor, "d_id");
      final int _cursorIndexOfDeviceSN = CursorUtil.getColumnIndexOrThrow(_cursor, "d_sn");
      final int _cursorIndexOfDeviceName = CursorUtil.getColumnIndexOrThrow(_cursor, "d_name");
      final int _cursorIndexOfDeviceModel = CursorUtil.getColumnIndexOrThrow(_cursor, "model");
      final int _cursorIndexOfTime = CursorUtil.getColumnIndexOrThrow(_cursor, "time");
      final int _cursorIndexOfExtras = CursorUtil.getColumnIndexOrThrow(_cursor, "extras");
      final List<VitalData_Room> _result = new ArrayList<VitalData_Room>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final VitalData_Room _item;
        _item = new VitalData_Room();
        _item.id = _cursor.getLong(_cursorIndexOfId);
        if (_cursor.isNull(_cursorIndexOfDeviceID)) {
          _item.deviceID = null;
        } else {
          _item.deviceID = _cursor.getString(_cursorIndexOfDeviceID);
        }
        if (_cursor.isNull(_cursorIndexOfDeviceSN)) {
          _item.deviceSN = null;
        } else {
          _item.deviceSN = _cursor.getString(_cursorIndexOfDeviceSN);
        }
        if (_cursor.isNull(_cursorIndexOfDeviceName)) {
          _item.deviceName = null;
        } else {
          _item.deviceName = _cursor.getString(_cursorIndexOfDeviceName);
        }
        final Integer _tmp;
        if (_cursor.isNull(_cursorIndexOfDeviceModel)) {
          _tmp = null;
        } else {
          _tmp = _cursor.getInt(_cursorIndexOfDeviceModel);
        }
        _item.deviceModel = __deviceModelConverter.convertToEntityProperty(_tmp);
        _item.time = _cursor.getLong(_cursorIndexOfTime);
        final String _tmp_1;
        if (_cursor.isNull(_cursorIndexOfExtras)) {
          _tmp_1 = null;
        } else {
          _tmp_1 = _cursor.getString(_cursorIndexOfExtras);
        }
        _item.extras = __mapConverter.convertToEntityProperty(_tmp_1);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public List<VitalData_Room> queryOldestAll(final long count) {
    final String _sql = "SELECT * FROM demo_data ORDER BY time ASC LIMIT ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, count);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfDeviceID = CursorUtil.getColumnIndexOrThrow(_cursor, "d_id");
      final int _cursorIndexOfDeviceSN = CursorUtil.getColumnIndexOrThrow(_cursor, "d_sn");
      final int _cursorIndexOfDeviceName = CursorUtil.getColumnIndexOrThrow(_cursor, "d_name");
      final int _cursorIndexOfDeviceModel = CursorUtil.getColumnIndexOrThrow(_cursor, "model");
      final int _cursorIndexOfTime = CursorUtil.getColumnIndexOrThrow(_cursor, "time");
      final int _cursorIndexOfExtras = CursorUtil.getColumnIndexOrThrow(_cursor, "extras");
      final List<VitalData_Room> _result = new ArrayList<VitalData_Room>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final VitalData_Room _item;
        _item = new VitalData_Room();
        _item.id = _cursor.getLong(_cursorIndexOfId);
        if (_cursor.isNull(_cursorIndexOfDeviceID)) {
          _item.deviceID = null;
        } else {
          _item.deviceID = _cursor.getString(_cursorIndexOfDeviceID);
        }
        if (_cursor.isNull(_cursorIndexOfDeviceSN)) {
          _item.deviceSN = null;
        } else {
          _item.deviceSN = _cursor.getString(_cursorIndexOfDeviceSN);
        }
        if (_cursor.isNull(_cursorIndexOfDeviceName)) {
          _item.deviceName = null;
        } else {
          _item.deviceName = _cursor.getString(_cursorIndexOfDeviceName);
        }
        final Integer _tmp;
        if (_cursor.isNull(_cursorIndexOfDeviceModel)) {
          _tmp = null;
        } else {
          _tmp = _cursor.getInt(_cursorIndexOfDeviceModel);
        }
        _item.deviceModel = __deviceModelConverter.convertToEntityProperty(_tmp);
        _item.time = _cursor.getLong(_cursorIndexOfTime);
        final String _tmp_1;
        if (_cursor.isNull(_cursorIndexOfExtras)) {
          _tmp_1 = null;
        } else {
          _tmp_1 = _cursor.getString(_cursorIndexOfExtras);
        }
        _item.extras = __mapConverter.convertToEntityProperty(_tmp_1);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public List<VitalData_Room> queryLatestAll(final long count) {
    final String _sql = "SELECT * FROM demo_data ORDER BY time DESC LIMIT ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, count);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfDeviceID = CursorUtil.getColumnIndexOrThrow(_cursor, "d_id");
      final int _cursorIndexOfDeviceSN = CursorUtil.getColumnIndexOrThrow(_cursor, "d_sn");
      final int _cursorIndexOfDeviceName = CursorUtil.getColumnIndexOrThrow(_cursor, "d_name");
      final int _cursorIndexOfDeviceModel = CursorUtil.getColumnIndexOrThrow(_cursor, "model");
      final int _cursorIndexOfTime = CursorUtil.getColumnIndexOrThrow(_cursor, "time");
      final int _cursorIndexOfExtras = CursorUtil.getColumnIndexOrThrow(_cursor, "extras");
      final List<VitalData_Room> _result = new ArrayList<VitalData_Room>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final VitalData_Room _item;
        _item = new VitalData_Room();
        _item.id = _cursor.getLong(_cursorIndexOfId);
        if (_cursor.isNull(_cursorIndexOfDeviceID)) {
          _item.deviceID = null;
        } else {
          _item.deviceID = _cursor.getString(_cursorIndexOfDeviceID);
        }
        if (_cursor.isNull(_cursorIndexOfDeviceSN)) {
          _item.deviceSN = null;
        } else {
          _item.deviceSN = _cursor.getString(_cursorIndexOfDeviceSN);
        }
        if (_cursor.isNull(_cursorIndexOfDeviceName)) {
          _item.deviceName = null;
        } else {
          _item.deviceName = _cursor.getString(_cursorIndexOfDeviceName);
        }
        final Integer _tmp;
        if (_cursor.isNull(_cursorIndexOfDeviceModel)) {
          _tmp = null;
        } else {
          _tmp = _cursor.getInt(_cursorIndexOfDeviceModel);
        }
        _item.deviceModel = __deviceModelConverter.convertToEntityProperty(_tmp);
        _item.time = _cursor.getLong(_cursorIndexOfTime);
        final String _tmp_1;
        if (_cursor.isNull(_cursorIndexOfExtras)) {
          _tmp_1 = null;
        } else {
          _tmp_1 = _cursor.getString(_cursorIndexOfExtras);
        }
        _item.extras = __mapConverter.convertToEntityProperty(_tmp_1);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public VitalData_Room query(final String deviceID, final long time) {
    final String _sql = "SELECT * FROM demo_data WHERE d_id = ? AND time = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    if (deviceID == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, deviceID);
    }
    _argIndex = 2;
    _statement.bindLong(_argIndex, time);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfDeviceID = CursorUtil.getColumnIndexOrThrow(_cursor, "d_id");
      final int _cursorIndexOfDeviceSN = CursorUtil.getColumnIndexOrThrow(_cursor, "d_sn");
      final int _cursorIndexOfDeviceName = CursorUtil.getColumnIndexOrThrow(_cursor, "d_name");
      final int _cursorIndexOfDeviceModel = CursorUtil.getColumnIndexOrThrow(_cursor, "model");
      final int _cursorIndexOfTime = CursorUtil.getColumnIndexOrThrow(_cursor, "time");
      final int _cursorIndexOfExtras = CursorUtil.getColumnIndexOrThrow(_cursor, "extras");
      final VitalData_Room _result;
      if(_cursor.moveToFirst()) {
        _result = new VitalData_Room();
        _result.id = _cursor.getLong(_cursorIndexOfId);
        if (_cursor.isNull(_cursorIndexOfDeviceID)) {
          _result.deviceID = null;
        } else {
          _result.deviceID = _cursor.getString(_cursorIndexOfDeviceID);
        }
        if (_cursor.isNull(_cursorIndexOfDeviceSN)) {
          _result.deviceSN = null;
        } else {
          _result.deviceSN = _cursor.getString(_cursorIndexOfDeviceSN);
        }
        if (_cursor.isNull(_cursorIndexOfDeviceName)) {
          _result.deviceName = null;
        } else {
          _result.deviceName = _cursor.getString(_cursorIndexOfDeviceName);
        }
        final Integer _tmp;
        if (_cursor.isNull(_cursorIndexOfDeviceModel)) {
          _tmp = null;
        } else {
          _tmp = _cursor.getInt(_cursorIndexOfDeviceModel);
        }
        _result.deviceModel = __deviceModelConverter.convertToEntityProperty(_tmp);
        _result.time = _cursor.getLong(_cursorIndexOfTime);
        final String _tmp_1;
        if (_cursor.isNull(_cursorIndexOfExtras)) {
          _tmp_1 = null;
        } else {
          _tmp_1 = _cursor.getString(_cursorIndexOfExtras);
        }
        _result.extras = __mapConverter.convertToEntityProperty(_tmp_1);
      } else {
        _result = null;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public List<VitalData_Room> query(final String deviceID, final long startTime,
      final long endTime) {
    final String _sql = "SELECT * FROM demo_data WHERE time BETWEEN ? AND ? AND d_id = ? ORDER BY time ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 3);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, startTime);
    _argIndex = 2;
    _statement.bindLong(_argIndex, endTime);
    _argIndex = 3;
    if (deviceID == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, deviceID);
    }
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfDeviceID = CursorUtil.getColumnIndexOrThrow(_cursor, "d_id");
      final int _cursorIndexOfDeviceSN = CursorUtil.getColumnIndexOrThrow(_cursor, "d_sn");
      final int _cursorIndexOfDeviceName = CursorUtil.getColumnIndexOrThrow(_cursor, "d_name");
      final int _cursorIndexOfDeviceModel = CursorUtil.getColumnIndexOrThrow(_cursor, "model");
      final int _cursorIndexOfTime = CursorUtil.getColumnIndexOrThrow(_cursor, "time");
      final int _cursorIndexOfExtras = CursorUtil.getColumnIndexOrThrow(_cursor, "extras");
      final List<VitalData_Room> _result = new ArrayList<VitalData_Room>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final VitalData_Room _item;
        _item = new VitalData_Room();
        _item.id = _cursor.getLong(_cursorIndexOfId);
        if (_cursor.isNull(_cursorIndexOfDeviceID)) {
          _item.deviceID = null;
        } else {
          _item.deviceID = _cursor.getString(_cursorIndexOfDeviceID);
        }
        if (_cursor.isNull(_cursorIndexOfDeviceSN)) {
          _item.deviceSN = null;
        } else {
          _item.deviceSN = _cursor.getString(_cursorIndexOfDeviceSN);
        }
        if (_cursor.isNull(_cursorIndexOfDeviceName)) {
          _item.deviceName = null;
        } else {
          _item.deviceName = _cursor.getString(_cursorIndexOfDeviceName);
        }
        final Integer _tmp;
        if (_cursor.isNull(_cursorIndexOfDeviceModel)) {
          _tmp = null;
        } else {
          _tmp = _cursor.getInt(_cursorIndexOfDeviceModel);
        }
        _item.deviceModel = __deviceModelConverter.convertToEntityProperty(_tmp);
        _item.time = _cursor.getLong(_cursorIndexOfTime);
        final String _tmp_1;
        if (_cursor.isNull(_cursorIndexOfExtras)) {
          _tmp_1 = null;
        } else {
          _tmp_1 = _cursor.getString(_cursorIndexOfExtras);
        }
        _item.extras = __mapConverter.convertToEntityProperty(_tmp_1);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public long getCount() {
    final String _sql = "SELECT COUNT(*) FROM demo_data";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final long _result;
      if(_cursor.moveToFirst()) {
        _result = _cursor.getLong(0);
      } else {
        _result = 0L;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public long getCount(final String deviceId) {
    final String _sql = "SELECT COUNT(*) FROM demo_data WHERE d_id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (deviceId == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, deviceId);
    }
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final long _result;
      if(_cursor.moveToFirst()) {
        _result = _cursor.getLong(0);
      } else {
        _result = 0L;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
