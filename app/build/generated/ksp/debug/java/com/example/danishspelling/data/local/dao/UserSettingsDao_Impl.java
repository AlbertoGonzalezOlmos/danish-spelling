package com.example.danishspelling.data.local.dao;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.example.danishspelling.data.local.entities.UserSettings;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Long;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class UserSettingsDao_Impl implements UserSettingsDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<UserSettings> __insertionAdapterOfUserSettings;

  private final EntityDeletionOrUpdateAdapter<UserSettings> __updateAdapterOfUserSettings;

  private final SharedSQLiteStatement __preparedStmtOfAddStars;

  private final SharedSQLiteStatement __preparedStmtOfUpdateStreak;

  private final SharedSQLiteStatement __preparedStmtOfUpdateTtsSpeed;

  public UserSettingsDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfUserSettings = new EntityInsertionAdapter<UserSettings>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `user_settings` (`id`,`childName`,`ttsSpeedMultiplier`,`ttsLanguage`,`soundEffectsEnabled`,`animationsEnabled`,`totalStarsEarned`,`currentStreak`,`lastPracticeDate`) VALUES (?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final UserSettings entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getChildName());
        statement.bindDouble(3, entity.getTtsSpeedMultiplier());
        statement.bindString(4, entity.getTtsLanguage());
        final int _tmp = entity.getSoundEffectsEnabled() ? 1 : 0;
        statement.bindLong(5, _tmp);
        final int _tmp_1 = entity.getAnimationsEnabled() ? 1 : 0;
        statement.bindLong(6, _tmp_1);
        statement.bindLong(7, entity.getTotalStarsEarned());
        statement.bindLong(8, entity.getCurrentStreak());
        if (entity.getLastPracticeDate() == null) {
          statement.bindNull(9);
        } else {
          statement.bindLong(9, entity.getLastPracticeDate());
        }
      }
    };
    this.__updateAdapterOfUserSettings = new EntityDeletionOrUpdateAdapter<UserSettings>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `user_settings` SET `id` = ?,`childName` = ?,`ttsSpeedMultiplier` = ?,`ttsLanguage` = ?,`soundEffectsEnabled` = ?,`animationsEnabled` = ?,`totalStarsEarned` = ?,`currentStreak` = ?,`lastPracticeDate` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final UserSettings entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getChildName());
        statement.bindDouble(3, entity.getTtsSpeedMultiplier());
        statement.bindString(4, entity.getTtsLanguage());
        final int _tmp = entity.getSoundEffectsEnabled() ? 1 : 0;
        statement.bindLong(5, _tmp);
        final int _tmp_1 = entity.getAnimationsEnabled() ? 1 : 0;
        statement.bindLong(6, _tmp_1);
        statement.bindLong(7, entity.getTotalStarsEarned());
        statement.bindLong(8, entity.getCurrentStreak());
        if (entity.getLastPracticeDate() == null) {
          statement.bindNull(9);
        } else {
          statement.bindLong(9, entity.getLastPracticeDate());
        }
        statement.bindLong(10, entity.getId());
      }
    };
    this.__preparedStmtOfAddStars = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE user_settings SET totalStarsEarned = totalStarsEarned + ? WHERE id = 1";
        return _query;
      }
    };
    this.__preparedStmtOfUpdateStreak = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE user_settings SET currentStreak = ?, lastPracticeDate = ? WHERE id = 1";
        return _query;
      }
    };
    this.__preparedStmtOfUpdateTtsSpeed = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE user_settings SET ttsSpeedMultiplier = ? WHERE id = 1";
        return _query;
      }
    };
  }

  @Override
  public Object insert(final UserSettings settings, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfUserSettings.insert(settings);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object update(final UserSettings settings, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfUserSettings.handle(settings);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object addStars(final int stars, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfAddStars.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, stars);
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
          __preparedStmtOfAddStars.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object updateStreak(final int streak, final long date,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfUpdateStreak.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, streak);
        _argIndex = 2;
        _stmt.bindLong(_argIndex, date);
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
          __preparedStmtOfUpdateStreak.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object updateTtsSpeed(final float speed, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfUpdateTtsSpeed.acquire();
        int _argIndex = 1;
        _stmt.bindDouble(_argIndex, speed);
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
          __preparedStmtOfUpdateTtsSpeed.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Flow<UserSettings> getSettings() {
    final String _sql = "SELECT * FROM user_settings WHERE id = 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"user_settings"}, new Callable<UserSettings>() {
      @Override
      @Nullable
      public UserSettings call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfChildName = CursorUtil.getColumnIndexOrThrow(_cursor, "childName");
          final int _cursorIndexOfTtsSpeedMultiplier = CursorUtil.getColumnIndexOrThrow(_cursor, "ttsSpeedMultiplier");
          final int _cursorIndexOfTtsLanguage = CursorUtil.getColumnIndexOrThrow(_cursor, "ttsLanguage");
          final int _cursorIndexOfSoundEffectsEnabled = CursorUtil.getColumnIndexOrThrow(_cursor, "soundEffectsEnabled");
          final int _cursorIndexOfAnimationsEnabled = CursorUtil.getColumnIndexOrThrow(_cursor, "animationsEnabled");
          final int _cursorIndexOfTotalStarsEarned = CursorUtil.getColumnIndexOrThrow(_cursor, "totalStarsEarned");
          final int _cursorIndexOfCurrentStreak = CursorUtil.getColumnIndexOrThrow(_cursor, "currentStreak");
          final int _cursorIndexOfLastPracticeDate = CursorUtil.getColumnIndexOrThrow(_cursor, "lastPracticeDate");
          final UserSettings _result;
          if (_cursor.moveToFirst()) {
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final String _tmpChildName;
            _tmpChildName = _cursor.getString(_cursorIndexOfChildName);
            final float _tmpTtsSpeedMultiplier;
            _tmpTtsSpeedMultiplier = _cursor.getFloat(_cursorIndexOfTtsSpeedMultiplier);
            final String _tmpTtsLanguage;
            _tmpTtsLanguage = _cursor.getString(_cursorIndexOfTtsLanguage);
            final boolean _tmpSoundEffectsEnabled;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfSoundEffectsEnabled);
            _tmpSoundEffectsEnabled = _tmp != 0;
            final boolean _tmpAnimationsEnabled;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfAnimationsEnabled);
            _tmpAnimationsEnabled = _tmp_1 != 0;
            final int _tmpTotalStarsEarned;
            _tmpTotalStarsEarned = _cursor.getInt(_cursorIndexOfTotalStarsEarned);
            final int _tmpCurrentStreak;
            _tmpCurrentStreak = _cursor.getInt(_cursorIndexOfCurrentStreak);
            final Long _tmpLastPracticeDate;
            if (_cursor.isNull(_cursorIndexOfLastPracticeDate)) {
              _tmpLastPracticeDate = null;
            } else {
              _tmpLastPracticeDate = _cursor.getLong(_cursorIndexOfLastPracticeDate);
            }
            _result = new UserSettings(_tmpId,_tmpChildName,_tmpTtsSpeedMultiplier,_tmpTtsLanguage,_tmpSoundEffectsEnabled,_tmpAnimationsEnabled,_tmpTotalStarsEarned,_tmpCurrentStreak,_tmpLastPracticeDate);
          } else {
            _result = null;
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
  public Object getSettingsOnce(final Continuation<? super UserSettings> $completion) {
    final String _sql = "SELECT * FROM user_settings WHERE id = 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<UserSettings>() {
      @Override
      @Nullable
      public UserSettings call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfChildName = CursorUtil.getColumnIndexOrThrow(_cursor, "childName");
          final int _cursorIndexOfTtsSpeedMultiplier = CursorUtil.getColumnIndexOrThrow(_cursor, "ttsSpeedMultiplier");
          final int _cursorIndexOfTtsLanguage = CursorUtil.getColumnIndexOrThrow(_cursor, "ttsLanguage");
          final int _cursorIndexOfSoundEffectsEnabled = CursorUtil.getColumnIndexOrThrow(_cursor, "soundEffectsEnabled");
          final int _cursorIndexOfAnimationsEnabled = CursorUtil.getColumnIndexOrThrow(_cursor, "animationsEnabled");
          final int _cursorIndexOfTotalStarsEarned = CursorUtil.getColumnIndexOrThrow(_cursor, "totalStarsEarned");
          final int _cursorIndexOfCurrentStreak = CursorUtil.getColumnIndexOrThrow(_cursor, "currentStreak");
          final int _cursorIndexOfLastPracticeDate = CursorUtil.getColumnIndexOrThrow(_cursor, "lastPracticeDate");
          final UserSettings _result;
          if (_cursor.moveToFirst()) {
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final String _tmpChildName;
            _tmpChildName = _cursor.getString(_cursorIndexOfChildName);
            final float _tmpTtsSpeedMultiplier;
            _tmpTtsSpeedMultiplier = _cursor.getFloat(_cursorIndexOfTtsSpeedMultiplier);
            final String _tmpTtsLanguage;
            _tmpTtsLanguage = _cursor.getString(_cursorIndexOfTtsLanguage);
            final boolean _tmpSoundEffectsEnabled;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfSoundEffectsEnabled);
            _tmpSoundEffectsEnabled = _tmp != 0;
            final boolean _tmpAnimationsEnabled;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfAnimationsEnabled);
            _tmpAnimationsEnabled = _tmp_1 != 0;
            final int _tmpTotalStarsEarned;
            _tmpTotalStarsEarned = _cursor.getInt(_cursorIndexOfTotalStarsEarned);
            final int _tmpCurrentStreak;
            _tmpCurrentStreak = _cursor.getInt(_cursorIndexOfCurrentStreak);
            final Long _tmpLastPracticeDate;
            if (_cursor.isNull(_cursorIndexOfLastPracticeDate)) {
              _tmpLastPracticeDate = null;
            } else {
              _tmpLastPracticeDate = _cursor.getLong(_cursorIndexOfLastPracticeDate);
            }
            _result = new UserSettings(_tmpId,_tmpChildName,_tmpTtsSpeedMultiplier,_tmpTtsLanguage,_tmpSoundEffectsEnabled,_tmpAnimationsEnabled,_tmpTotalStarsEarned,_tmpCurrentStreak,_tmpLastPracticeDate);
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
