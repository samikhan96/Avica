package com.vivalnk.sdk.demo.repository.database.room;

import androidx.annotation.NonNull;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomOpenHelper;
import androidx.room.RoomOpenHelper.Delegate;
import androidx.room.RoomOpenHelper.ValidationResult;
import androidx.room.migration.AutoMigrationSpec;
import androidx.room.migration.Migration;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.room.util.TableInfo.Column;
import androidx.room.util.TableInfo.ForeignKey;
import androidx.room.util.TableInfo.Index;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import androidx.sqlite.db.SupportSQLiteOpenHelper.Callback;
import androidx.sqlite.db.SupportSQLiteOpenHelper.Configuration;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class VitalDatabase_Impl extends VitalDatabase {
  private volatile IRoomDataDAO _iRoomDataDAO;

  private volatile IRoomDeviceDAO _iRoomDeviceDAO;

  @Override
  protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration configuration) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(configuration, new RoomOpenHelper.Delegate(2) {
      @Override
      public void createAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("CREATE TABLE IF NOT EXISTS `demo_data` (`id` INTEGER NOT NULL, `d_id` TEXT NOT NULL, `d_sn` TEXT, `d_name` TEXT, `model` INTEGER NOT NULL, `time` INTEGER NOT NULL, `extras` TEXT, PRIMARY KEY(`d_id`, `model`, `time`))");
        _db.execSQL("CREATE UNIQUE INDEX IF NOT EXISTS `index_demo_data_d_id_model_time` ON `demo_data` (`d_id`, `model`, `time`)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `demo_device` (`id` INTEGER NOT NULL, `d_id` TEXT NOT NULL, `d_sn` TEXT, `d_name` TEXT, `d_model` INTEGER NOT NULL, `hw_v` TEXT, `fw_v` TEXT, `info` TEXT, PRIMARY KEY(`d_id`, `d_model`))");
        _db.execSQL("CREATE UNIQUE INDEX IF NOT EXISTS `index_demo_device_d_id_d_model` ON `demo_device` (`d_id`, `d_model`)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        _db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, '2e84db12e330739dd9bb52f6f42eb797')");
      }

      @Override
      public void dropAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("DROP TABLE IF EXISTS `demo_data`");
        _db.execSQL("DROP TABLE IF EXISTS `demo_device`");
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onDestructiveMigration(_db);
          }
        }
      }

      @Override
      public void onCreate(SupportSQLiteDatabase _db) {
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onCreate(_db);
          }
        }
      }

      @Override
      public void onOpen(SupportSQLiteDatabase _db) {
        mDatabase = _db;
        internalInitInvalidationTracker(_db);
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onOpen(_db);
          }
        }
      }

      @Override
      public void onPreMigrate(SupportSQLiteDatabase _db) {
        DBUtil.dropFtsSyncTriggers(_db);
      }

      @Override
      public void onPostMigrate(SupportSQLiteDatabase _db) {
      }

      @Override
      public RoomOpenHelper.ValidationResult onValidateSchema(SupportSQLiteDatabase _db) {
        final HashMap<String, TableInfo.Column> _columnsDemoData = new HashMap<String, TableInfo.Column>(7);
        _columnsDemoData.put("id", new TableInfo.Column("id", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDemoData.put("d_id", new TableInfo.Column("d_id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDemoData.put("d_sn", new TableInfo.Column("d_sn", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDemoData.put("d_name", new TableInfo.Column("d_name", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDemoData.put("model", new TableInfo.Column("model", "INTEGER", true, 2, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDemoData.put("time", new TableInfo.Column("time", "INTEGER", true, 3, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDemoData.put("extras", new TableInfo.Column("extras", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysDemoData = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesDemoData = new HashSet<TableInfo.Index>(1);
        _indicesDemoData.add(new TableInfo.Index("index_demo_data_d_id_model_time", true, Arrays.asList("d_id","model","time"), Arrays.asList("ASC","ASC","ASC")));
        final TableInfo _infoDemoData = new TableInfo("demo_data", _columnsDemoData, _foreignKeysDemoData, _indicesDemoData);
        final TableInfo _existingDemoData = TableInfo.read(_db, "demo_data");
        if (! _infoDemoData.equals(_existingDemoData)) {
          return new RoomOpenHelper.ValidationResult(false, "demo_data(com.vivalnk.sdk.demo.repository.database.room.VitalData_Room).\n"
                  + " Expected:\n" + _infoDemoData + "\n"
                  + " Found:\n" + _existingDemoData);
        }
        final HashMap<String, TableInfo.Column> _columnsDemoDevice = new HashMap<String, TableInfo.Column>(8);
        _columnsDemoDevice.put("id", new TableInfo.Column("id", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDemoDevice.put("d_id", new TableInfo.Column("d_id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDemoDevice.put("d_sn", new TableInfo.Column("d_sn", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDemoDevice.put("d_name", new TableInfo.Column("d_name", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDemoDevice.put("d_model", new TableInfo.Column("d_model", "INTEGER", true, 2, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDemoDevice.put("hw_v", new TableInfo.Column("hw_v", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDemoDevice.put("fw_v", new TableInfo.Column("fw_v", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDemoDevice.put("info", new TableInfo.Column("info", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysDemoDevice = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesDemoDevice = new HashSet<TableInfo.Index>(1);
        _indicesDemoDevice.add(new TableInfo.Index("index_demo_device_d_id_d_model", true, Arrays.asList("d_id","d_model"), Arrays.asList("ASC","ASC")));
        final TableInfo _infoDemoDevice = new TableInfo("demo_device", _columnsDemoDevice, _foreignKeysDemoDevice, _indicesDemoDevice);
        final TableInfo _existingDemoDevice = TableInfo.read(_db, "demo_device");
        if (! _infoDemoDevice.equals(_existingDemoDevice)) {
          return new RoomOpenHelper.ValidationResult(false, "demo_device(com.vivalnk.sdk.demo.repository.database.room.VitalDevice_Room).\n"
                  + " Expected:\n" + _infoDemoDevice + "\n"
                  + " Found:\n" + _existingDemoDevice);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "2e84db12e330739dd9bb52f6f42eb797", "0184f1c7fd1d5199cc8b16072594b770");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(configuration.context)
        .name(configuration.name)
        .callback(_openCallback)
        .build();
    final SupportSQLiteOpenHelper _helper = configuration.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "demo_data","demo_device");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    try {
      super.beginTransaction();
      _db.execSQL("DELETE FROM `demo_data`");
      _db.execSQL("DELETE FROM `demo_device`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  protected Map<Class<?>, List<Class<?>>> getRequiredTypeConverters() {
    final HashMap<Class<?>, List<Class<?>>> _typeConvertersMap = new HashMap<Class<?>, List<Class<?>>>();
    _typeConvertersMap.put(IRoomDataDAO.class, IRoomDataDAO_Impl.getRequiredConverters());
    _typeConvertersMap.put(IRoomDeviceDAO.class, IRoomDeviceDAO_Impl.getRequiredConverters());
    return _typeConvertersMap;
  }

  @Override
  public Set<Class<? extends AutoMigrationSpec>> getRequiredAutoMigrationSpecs() {
    final HashSet<Class<? extends AutoMigrationSpec>> _autoMigrationSpecsSet = new HashSet<Class<? extends AutoMigrationSpec>>();
    return _autoMigrationSpecsSet;
  }

  @Override
  public List<Migration> getAutoMigrations(
      @NonNull Map<Class<? extends AutoMigrationSpec>, AutoMigrationSpec> autoMigrationSpecsMap) {
    return Arrays.asList();
  }

  @Override
  public IRoomDataDAO getVitalDataDAO() {
    if (_iRoomDataDAO != null) {
      return _iRoomDataDAO;
    } else {
      synchronized(this) {
        if(_iRoomDataDAO == null) {
          _iRoomDataDAO = new IRoomDataDAO_Impl(this);
        }
        return _iRoomDataDAO;
      }
    }
  }

  @Override
  public IRoomDeviceDAO getVitalDeviceDAO() {
    if (_iRoomDeviceDAO != null) {
      return _iRoomDeviceDAO;
    } else {
      synchronized(this) {
        if(_iRoomDeviceDAO == null) {
          _iRoomDeviceDAO = new IRoomDeviceDAO_Impl(this);
        }
        return _iRoomDeviceDAO;
      }
    }
  }
}
