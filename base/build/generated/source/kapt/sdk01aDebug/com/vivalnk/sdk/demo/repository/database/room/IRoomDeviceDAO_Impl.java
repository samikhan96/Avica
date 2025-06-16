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
public final class IRoomDeviceDAO_Impl implements IRoomDeviceDAO {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<VitalDevice_Room> __insertionAdapterOfVitalDevice_Room;

  private final DeviceModelConverter __deviceModelConverter = new DeviceModelConverter();

  private final EntityDeletionOrUpdateAdapter<VitalDevice_Room> __deletionAdapterOfVitalDevice_Room;

  private final EntityDeletionOrUpdateAdapter<VitalDevice_Room> __updateAdapterOfVitalDevice_Room;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAll;

  public IRoomDeviceDAO_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfVitalDevice_Room = new EntityInsertionAdapter<VitalDevice_Room>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR REPLACE INTO `demo_device` (`id`,`d_id`,`d_sn`,`d_name`,`d_model`,`hw_v`,`fw_v`,`info`) VALUES (?,?,?,?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, VitalDevice_Room value) {
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
        if (value.hwVersion == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.hwVersion);
        }
        if (value.fwVersion == null) {
          stmt.bindNull(7);
        } else {
          stmt.bindString(7, value.fwVersion);
        }
        if (value.deviceInfo == null) {
          stmt.bindNull(8);
        } else {
          stmt.bindString(8, value.deviceInfo);
        }
      }
    };
    this.__deletionAdapterOfVitalDevice_Room = new EntityDeletionOrUpdateAdapter<VitalDevice_Room>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `demo_device` WHERE `d_id` = ? AND `d_model` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, VitalDevice_Room value) {
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
      }
    };
    this.__updateAdapterOfVitalDevice_Room = new EntityDeletionOrUpdateAdapter<VitalDevice_Room>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `demo_device` SET `id` = ?,`d_id` = ?,`d_sn` = ?,`d_name` = ?,`d_model` = ?,`hw_v` = ?,`fw_v` = ?,`info` = ? WHERE `d_id` = ? AND `d_model` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, VitalDevice_Room value) {
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
        if (value.hwVersion == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.hwVersion);
        }
        if (value.fwVersion == null) {
          stmt.bindNull(7);
        } else {
          stmt.bindString(7, value.fwVersion);
        }
        if (value.deviceInfo == null) {
          stmt.bindNull(8);
        } else {
          stmt.bindString(8, value.deviceInfo);
        }
        if (value.deviceID == null) {
          stmt.bindNull(9);
        } else {
          stmt.bindString(9, value.deviceID);
        }
        final Integer _tmp_1 = __deviceModelConverter.convertToDatabaseValue(value.deviceModel);
        if (_tmp_1 == null) {
          stmt.bindNull(10);
        } else {
          stmt.bindLong(10, _tmp_1);
        }
      }
    };
    this.__preparedStmtOfDeleteAll = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM demo_device";
        return _query;
      }
    };
  }

  @Override
  public void insert(final VitalDevice_Room... data) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfVitalDevice_Room.insert(data);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void delete(final VitalDevice_Room... data) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __deletionAdapterOfVitalDevice_Room.handleMultiple(data);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void update(final VitalDevice_Room... data) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __updateAdapterOfVitalDevice_Room.handleMultiple(data);
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
  public List<VitalDevice_Room> queryAll() {
    final String _sql = "SELECT * FROM demo_device";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfDeviceID = CursorUtil.getColumnIndexOrThrow(_cursor, "d_id");
      final int _cursorIndexOfDeviceSN = CursorUtil.getColumnIndexOrThrow(_cursor, "d_sn");
      final int _cursorIndexOfDeviceName = CursorUtil.getColumnIndexOrThrow(_cursor, "d_name");
      final int _cursorIndexOfDeviceModel = CursorUtil.getColumnIndexOrThrow(_cursor, "d_model");
      final int _cursorIndexOfHwVersion = CursorUtil.getColumnIndexOrThrow(_cursor, "hw_v");
      final int _cursorIndexOfFwVersion = CursorUtil.getColumnIndexOrThrow(_cursor, "fw_v");
      final int _cursorIndexOfDeviceInfo = CursorUtil.getColumnIndexOrThrow(_cursor, "info");
      final List<VitalDevice_Room> _result = new ArrayList<VitalDevice_Room>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final VitalDevice_Room _item;
        _item = new VitalDevice_Room();
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
        if (_cursor.isNull(_cursorIndexOfHwVersion)) {
          _item.hwVersion = null;
        } else {
          _item.hwVersion = _cursor.getString(_cursorIndexOfHwVersion);
        }
        if (_cursor.isNull(_cursorIndexOfFwVersion)) {
          _item.fwVersion = null;
        } else {
          _item.fwVersion = _cursor.getString(_cursorIndexOfFwVersion);
        }
        if (_cursor.isNull(_cursorIndexOfDeviceInfo)) {
          _item.deviceInfo = null;
        } else {
          _item.deviceInfo = _cursor.getString(_cursorIndexOfDeviceInfo);
        }
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public VitalDevice_Room query(final String deviceID) {
    final String _sql = "SELECT * FROM demo_device WHERE d_id = ?";
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
      final int _cursorIndexOfDeviceModel = CursorUtil.getColumnIndexOrThrow(_cursor, "d_model");
      final int _cursorIndexOfHwVersion = CursorUtil.getColumnIndexOrThrow(_cursor, "hw_v");
      final int _cursorIndexOfFwVersion = CursorUtil.getColumnIndexOrThrow(_cursor, "fw_v");
      final int _cursorIndexOfDeviceInfo = CursorUtil.getColumnIndexOrThrow(_cursor, "info");
      final VitalDevice_Room _result;
      if(_cursor.moveToFirst()) {
        _result = new VitalDevice_Room();
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
        if (_cursor.isNull(_cursorIndexOfHwVersion)) {
          _result.hwVersion = null;
        } else {
          _result.hwVersion = _cursor.getString(_cursorIndexOfHwVersion);
        }
        if (_cursor.isNull(_cursorIndexOfFwVersion)) {
          _result.fwVersion = null;
        } else {
          _result.fwVersion = _cursor.getString(_cursorIndexOfFwVersion);
        }
        if (_cursor.isNull(_cursorIndexOfDeviceInfo)) {
          _result.deviceInfo = null;
        } else {
          _result.deviceInfo = _cursor.getString(_cursorIndexOfDeviceInfo);
        }
      } else {
        _result = null;
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
