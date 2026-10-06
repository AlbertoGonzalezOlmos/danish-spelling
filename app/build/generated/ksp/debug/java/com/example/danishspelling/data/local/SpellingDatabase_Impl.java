package com.example.danishspelling.data.local;

import androidx.annotation.NonNull;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.room.RoomOpenHelper;
import androidx.room.migration.AutoMigrationSpec;
import androidx.room.migration.Migration;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import com.example.danishspelling.data.local.dao.PracticeAttemptDao;
import com.example.danishspelling.data.local.dao.PracticeAttemptDao_Impl;
import com.example.danishspelling.data.local.dao.PracticeListDao;
import com.example.danishspelling.data.local.dao.PracticeListDao_Impl;
import com.example.danishspelling.data.local.dao.PracticeSessionDao;
import com.example.danishspelling.data.local.dao.PracticeSessionDao_Impl;
import com.example.danishspelling.data.local.dao.SentenceDao;
import com.example.danishspelling.data.local.dao.SentenceDao_Impl;
import com.example.danishspelling.data.local.dao.UserSettingsDao;
import com.example.danishspelling.data.local.dao.UserSettingsDao_Impl;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class SpellingDatabase_Impl extends SpellingDatabase {
  private volatile PracticeListDao _practiceListDao;

  private volatile SentenceDao _sentenceDao;

  private volatile PracticeSessionDao _practiceSessionDao;

  private volatile PracticeAttemptDao _practiceAttemptDao;

  private volatile UserSettingsDao _userSettingsDao;

  @Override
  @NonNull
  protected SupportSQLiteOpenHelper createOpenHelper(@NonNull final DatabaseConfiguration config) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(config, new RoomOpenHelper.Delegate(2) {
      @Override
      public void createAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS `practice_lists` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `name` TEXT NOT NULL, `description` TEXT NOT NULL, `difficultyLevel` TEXT NOT NULL, `colorTheme` TEXT NOT NULL, `iconName` TEXT NOT NULL, `createdAt` INTEGER NOT NULL, `updatedAt` INTEGER NOT NULL, `isActive` INTEGER NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `sentences` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `listId` INTEGER NOT NULL, `text` TEXT NOT NULL, `difficulty` INTEGER NOT NULL, `hints` TEXT, `orderIndex` INTEGER NOT NULL, `isActive` INTEGER NOT NULL, `incorrectCount` INTEGER NOT NULL, `createdAt` INTEGER NOT NULL, FOREIGN KEY(`listId`) REFERENCES `practice_lists`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE )");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_sentences_listId` ON `sentences` (`listId`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `practice_sessions` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `listId` INTEGER NOT NULL, `startTime` INTEGER NOT NULL, `endTime` INTEGER, `totalSentences` INTEGER NOT NULL, `completedSentences` INTEGER NOT NULL, `totalStarsEarned` INTEGER NOT NULL, FOREIGN KEY(`listId`) REFERENCES `practice_lists`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE )");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_practice_sessions_listId` ON `practice_sessions` (`listId`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `practice_attempts` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `sentenceId` INTEGER NOT NULL, `sessionId` INTEGER NOT NULL, `userInput` TEXT NOT NULL, `correctText` TEXT NOT NULL, `accuracy` REAL NOT NULL, `starsEarned` INTEGER NOT NULL, `attemptNumber` INTEGER NOT NULL, `timestamp` INTEGER NOT NULL, `speechSpeedUsed` REAL NOT NULL, FOREIGN KEY(`sentenceId`) REFERENCES `sentences`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE , FOREIGN KEY(`sessionId`) REFERENCES `practice_sessions`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE )");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_practice_attempts_sentenceId` ON `practice_attempts` (`sentenceId`)");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_practice_attempts_sessionId` ON `practice_attempts` (`sessionId`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `user_settings` (`id` INTEGER NOT NULL, `childName` TEXT NOT NULL, `ttsSpeedMultiplier` REAL NOT NULL, `ttsLanguage` TEXT NOT NULL, `soundEffectsEnabled` INTEGER NOT NULL, `animationsEnabled` INTEGER NOT NULL, `totalStarsEarned` INTEGER NOT NULL, `currentStreak` INTEGER NOT NULL, `lastPracticeDate` INTEGER, PRIMARY KEY(`id`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, '22e39bce453a17169aadf71366705ce3')");
      }

      @Override
      public void dropAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS `practice_lists`");
        db.execSQL("DROP TABLE IF EXISTS `sentences`");
        db.execSQL("DROP TABLE IF EXISTS `practice_sessions`");
        db.execSQL("DROP TABLE IF EXISTS `practice_attempts`");
        db.execSQL("DROP TABLE IF EXISTS `user_settings`");
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onDestructiveMigration(db);
          }
        }
      }

      @Override
      public void onCreate(@NonNull final SupportSQLiteDatabase db) {
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onCreate(db);
          }
        }
      }

      @Override
      public void onOpen(@NonNull final SupportSQLiteDatabase db) {
        mDatabase = db;
        db.execSQL("PRAGMA foreign_keys = ON");
        internalInitInvalidationTracker(db);
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onOpen(db);
          }
        }
      }

      @Override
      public void onPreMigrate(@NonNull final SupportSQLiteDatabase db) {
        DBUtil.dropFtsSyncTriggers(db);
      }

      @Override
      public void onPostMigrate(@NonNull final SupportSQLiteDatabase db) {
      }

      @Override
      @NonNull
      public RoomOpenHelper.ValidationResult onValidateSchema(
          @NonNull final SupportSQLiteDatabase db) {
        final HashMap<String, TableInfo.Column> _columnsPracticeLists = new HashMap<String, TableInfo.Column>(9);
        _columnsPracticeLists.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPracticeLists.put("name", new TableInfo.Column("name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPracticeLists.put("description", new TableInfo.Column("description", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPracticeLists.put("difficultyLevel", new TableInfo.Column("difficultyLevel", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPracticeLists.put("colorTheme", new TableInfo.Column("colorTheme", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPracticeLists.put("iconName", new TableInfo.Column("iconName", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPracticeLists.put("createdAt", new TableInfo.Column("createdAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPracticeLists.put("updatedAt", new TableInfo.Column("updatedAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPracticeLists.put("isActive", new TableInfo.Column("isActive", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysPracticeLists = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesPracticeLists = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoPracticeLists = new TableInfo("practice_lists", _columnsPracticeLists, _foreignKeysPracticeLists, _indicesPracticeLists);
        final TableInfo _existingPracticeLists = TableInfo.read(db, "practice_lists");
        if (!_infoPracticeLists.equals(_existingPracticeLists)) {
          return new RoomOpenHelper.ValidationResult(false, "practice_lists(com.example.danishspelling.data.local.entities.PracticeList).\n"
                  + " Expected:\n" + _infoPracticeLists + "\n"
                  + " Found:\n" + _existingPracticeLists);
        }
        final HashMap<String, TableInfo.Column> _columnsSentences = new HashMap<String, TableInfo.Column>(9);
        _columnsSentences.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSentences.put("listId", new TableInfo.Column("listId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSentences.put("text", new TableInfo.Column("text", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSentences.put("difficulty", new TableInfo.Column("difficulty", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSentences.put("hints", new TableInfo.Column("hints", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSentences.put("orderIndex", new TableInfo.Column("orderIndex", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSentences.put("isActive", new TableInfo.Column("isActive", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSentences.put("incorrectCount", new TableInfo.Column("incorrectCount", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSentences.put("createdAt", new TableInfo.Column("createdAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysSentences = new HashSet<TableInfo.ForeignKey>(1);
        _foreignKeysSentences.add(new TableInfo.ForeignKey("practice_lists", "CASCADE", "NO ACTION", Arrays.asList("listId"), Arrays.asList("id")));
        final HashSet<TableInfo.Index> _indicesSentences = new HashSet<TableInfo.Index>(1);
        _indicesSentences.add(new TableInfo.Index("index_sentences_listId", false, Arrays.asList("listId"), Arrays.asList("ASC")));
        final TableInfo _infoSentences = new TableInfo("sentences", _columnsSentences, _foreignKeysSentences, _indicesSentences);
        final TableInfo _existingSentences = TableInfo.read(db, "sentences");
        if (!_infoSentences.equals(_existingSentences)) {
          return new RoomOpenHelper.ValidationResult(false, "sentences(com.example.danishspelling.data.local.entities.Sentence).\n"
                  + " Expected:\n" + _infoSentences + "\n"
                  + " Found:\n" + _existingSentences);
        }
        final HashMap<String, TableInfo.Column> _columnsPracticeSessions = new HashMap<String, TableInfo.Column>(7);
        _columnsPracticeSessions.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPracticeSessions.put("listId", new TableInfo.Column("listId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPracticeSessions.put("startTime", new TableInfo.Column("startTime", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPracticeSessions.put("endTime", new TableInfo.Column("endTime", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPracticeSessions.put("totalSentences", new TableInfo.Column("totalSentences", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPracticeSessions.put("completedSentences", new TableInfo.Column("completedSentences", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPracticeSessions.put("totalStarsEarned", new TableInfo.Column("totalStarsEarned", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysPracticeSessions = new HashSet<TableInfo.ForeignKey>(1);
        _foreignKeysPracticeSessions.add(new TableInfo.ForeignKey("practice_lists", "CASCADE", "NO ACTION", Arrays.asList("listId"), Arrays.asList("id")));
        final HashSet<TableInfo.Index> _indicesPracticeSessions = new HashSet<TableInfo.Index>(1);
        _indicesPracticeSessions.add(new TableInfo.Index("index_practice_sessions_listId", false, Arrays.asList("listId"), Arrays.asList("ASC")));
        final TableInfo _infoPracticeSessions = new TableInfo("practice_sessions", _columnsPracticeSessions, _foreignKeysPracticeSessions, _indicesPracticeSessions);
        final TableInfo _existingPracticeSessions = TableInfo.read(db, "practice_sessions");
        if (!_infoPracticeSessions.equals(_existingPracticeSessions)) {
          return new RoomOpenHelper.ValidationResult(false, "practice_sessions(com.example.danishspelling.data.local.entities.PracticeSession).\n"
                  + " Expected:\n" + _infoPracticeSessions + "\n"
                  + " Found:\n" + _existingPracticeSessions);
        }
        final HashMap<String, TableInfo.Column> _columnsPracticeAttempts = new HashMap<String, TableInfo.Column>(10);
        _columnsPracticeAttempts.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPracticeAttempts.put("sentenceId", new TableInfo.Column("sentenceId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPracticeAttempts.put("sessionId", new TableInfo.Column("sessionId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPracticeAttempts.put("userInput", new TableInfo.Column("userInput", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPracticeAttempts.put("correctText", new TableInfo.Column("correctText", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPracticeAttempts.put("accuracy", new TableInfo.Column("accuracy", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPracticeAttempts.put("starsEarned", new TableInfo.Column("starsEarned", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPracticeAttempts.put("attemptNumber", new TableInfo.Column("attemptNumber", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPracticeAttempts.put("timestamp", new TableInfo.Column("timestamp", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPracticeAttempts.put("speechSpeedUsed", new TableInfo.Column("speechSpeedUsed", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysPracticeAttempts = new HashSet<TableInfo.ForeignKey>(2);
        _foreignKeysPracticeAttempts.add(new TableInfo.ForeignKey("sentences", "CASCADE", "NO ACTION", Arrays.asList("sentenceId"), Arrays.asList("id")));
        _foreignKeysPracticeAttempts.add(new TableInfo.ForeignKey("practice_sessions", "CASCADE", "NO ACTION", Arrays.asList("sessionId"), Arrays.asList("id")));
        final HashSet<TableInfo.Index> _indicesPracticeAttempts = new HashSet<TableInfo.Index>(2);
        _indicesPracticeAttempts.add(new TableInfo.Index("index_practice_attempts_sentenceId", false, Arrays.asList("sentenceId"), Arrays.asList("ASC")));
        _indicesPracticeAttempts.add(new TableInfo.Index("index_practice_attempts_sessionId", false, Arrays.asList("sessionId"), Arrays.asList("ASC")));
        final TableInfo _infoPracticeAttempts = new TableInfo("practice_attempts", _columnsPracticeAttempts, _foreignKeysPracticeAttempts, _indicesPracticeAttempts);
        final TableInfo _existingPracticeAttempts = TableInfo.read(db, "practice_attempts");
        if (!_infoPracticeAttempts.equals(_existingPracticeAttempts)) {
          return new RoomOpenHelper.ValidationResult(false, "practice_attempts(com.example.danishspelling.data.local.entities.PracticeAttempt).\n"
                  + " Expected:\n" + _infoPracticeAttempts + "\n"
                  + " Found:\n" + _existingPracticeAttempts);
        }
        final HashMap<String, TableInfo.Column> _columnsUserSettings = new HashMap<String, TableInfo.Column>(9);
        _columnsUserSettings.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUserSettings.put("childName", new TableInfo.Column("childName", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUserSettings.put("ttsSpeedMultiplier", new TableInfo.Column("ttsSpeedMultiplier", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUserSettings.put("ttsLanguage", new TableInfo.Column("ttsLanguage", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUserSettings.put("soundEffectsEnabled", new TableInfo.Column("soundEffectsEnabled", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUserSettings.put("animationsEnabled", new TableInfo.Column("animationsEnabled", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUserSettings.put("totalStarsEarned", new TableInfo.Column("totalStarsEarned", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUserSettings.put("currentStreak", new TableInfo.Column("currentStreak", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUserSettings.put("lastPracticeDate", new TableInfo.Column("lastPracticeDate", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysUserSettings = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesUserSettings = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoUserSettings = new TableInfo("user_settings", _columnsUserSettings, _foreignKeysUserSettings, _indicesUserSettings);
        final TableInfo _existingUserSettings = TableInfo.read(db, "user_settings");
        if (!_infoUserSettings.equals(_existingUserSettings)) {
          return new RoomOpenHelper.ValidationResult(false, "user_settings(com.example.danishspelling.data.local.entities.UserSettings).\n"
                  + " Expected:\n" + _infoUserSettings + "\n"
                  + " Found:\n" + _existingUserSettings);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "22e39bce453a17169aadf71366705ce3", "720cdb197124de112b99d36a9b6756db");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(config.context).name(config.name).callback(_openCallback).build();
    final SupportSQLiteOpenHelper _helper = config.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  @NonNull
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    final HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "practice_lists","sentences","practice_sessions","practice_attempts","user_settings");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    final boolean _supportsDeferForeignKeys = android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP;
    try {
      if (!_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA foreign_keys = FALSE");
      }
      super.beginTransaction();
      if (_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA defer_foreign_keys = TRUE");
      }
      _db.execSQL("DELETE FROM `practice_lists`");
      _db.execSQL("DELETE FROM `sentences`");
      _db.execSQL("DELETE FROM `practice_sessions`");
      _db.execSQL("DELETE FROM `practice_attempts`");
      _db.execSQL("DELETE FROM `user_settings`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      if (!_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA foreign_keys = TRUE");
      }
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  @NonNull
  protected Map<Class<?>, List<Class<?>>> getRequiredTypeConverters() {
    final HashMap<Class<?>, List<Class<?>>> _typeConvertersMap = new HashMap<Class<?>, List<Class<?>>>();
    _typeConvertersMap.put(PracticeListDao.class, PracticeListDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(SentenceDao.class, SentenceDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(PracticeSessionDao.class, PracticeSessionDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(PracticeAttemptDao.class, PracticeAttemptDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(UserSettingsDao.class, UserSettingsDao_Impl.getRequiredConverters());
    return _typeConvertersMap;
  }

  @Override
  @NonNull
  public Set<Class<? extends AutoMigrationSpec>> getRequiredAutoMigrationSpecs() {
    final HashSet<Class<? extends AutoMigrationSpec>> _autoMigrationSpecsSet = new HashSet<Class<? extends AutoMigrationSpec>>();
    return _autoMigrationSpecsSet;
  }

  @Override
  @NonNull
  public List<Migration> getAutoMigrations(
      @NonNull final Map<Class<? extends AutoMigrationSpec>, AutoMigrationSpec> autoMigrationSpecs) {
    final List<Migration> _autoMigrations = new ArrayList<Migration>();
    return _autoMigrations;
  }

  @Override
  public PracticeListDao practiceListDao() {
    if (_practiceListDao != null) {
      return _practiceListDao;
    } else {
      synchronized(this) {
        if(_practiceListDao == null) {
          _practiceListDao = new PracticeListDao_Impl(this);
        }
        return _practiceListDao;
      }
    }
  }

  @Override
  public SentenceDao sentenceDao() {
    if (_sentenceDao != null) {
      return _sentenceDao;
    } else {
      synchronized(this) {
        if(_sentenceDao == null) {
          _sentenceDao = new SentenceDao_Impl(this);
        }
        return _sentenceDao;
      }
    }
  }

  @Override
  public PracticeSessionDao practiceSessionDao() {
    if (_practiceSessionDao != null) {
      return _practiceSessionDao;
    } else {
      synchronized(this) {
        if(_practiceSessionDao == null) {
          _practiceSessionDao = new PracticeSessionDao_Impl(this);
        }
        return _practiceSessionDao;
      }
    }
  }

  @Override
  public PracticeAttemptDao practiceAttemptDao() {
    if (_practiceAttemptDao != null) {
      return _practiceAttemptDao;
    } else {
      synchronized(this) {
        if(_practiceAttemptDao == null) {
          _practiceAttemptDao = new PracticeAttemptDao_Impl(this);
        }
        return _practiceAttemptDao;
      }
    }
  }

  @Override
  public UserSettingsDao userSettingsDao() {
    if (_userSettingsDao != null) {
      return _userSettingsDao;
    } else {
      synchronized(this) {
        if(_userSettingsDao == null) {
          _userSettingsDao = new UserSettingsDao_Impl(this);
        }
        return _userSettingsDao;
      }
    }
  }
}
