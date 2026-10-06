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
import com.example.danishspelling.data.local.Converters;
import com.example.danishspelling.data.local.entities.PracticeList;
import com.example.danishspelling.domain.model.DifficultyLevel;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Integer;
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
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class PracticeListDao_Impl implements PracticeListDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<PracticeList> __insertionAdapterOfPracticeList;

  private final Converters __converters = new Converters();

  private final EntityDeletionOrUpdateAdapter<PracticeList> __deletionAdapterOfPracticeList;

  private final EntityDeletionOrUpdateAdapter<PracticeList> __updateAdapterOfPracticeList;

  private final SharedSQLiteStatement __preparedStmtOfSoftDelete;

  public PracticeListDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfPracticeList = new EntityInsertionAdapter<PracticeList>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `practice_lists` (`id`,`name`,`description`,`difficultyLevel`,`colorTheme`,`iconName`,`createdAt`,`updatedAt`,`isActive`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final PracticeList entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getName());
        statement.bindString(3, entity.getDescription());
        final String _tmp = __converters.fromDifficultyLevel(entity.getDifficultyLevel());
        statement.bindString(4, _tmp);
        statement.bindString(5, entity.getColorTheme());
        statement.bindString(6, entity.getIconName());
        statement.bindLong(7, entity.getCreatedAt());
        statement.bindLong(8, entity.getUpdatedAt());
        final int _tmp_1 = entity.isActive() ? 1 : 0;
        statement.bindLong(9, _tmp_1);
      }
    };
    this.__deletionAdapterOfPracticeList = new EntityDeletionOrUpdateAdapter<PracticeList>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `practice_lists` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final PracticeList entity) {
        statement.bindLong(1, entity.getId());
      }
    };
    this.__updateAdapterOfPracticeList = new EntityDeletionOrUpdateAdapter<PracticeList>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `practice_lists` SET `id` = ?,`name` = ?,`description` = ?,`difficultyLevel` = ?,`colorTheme` = ?,`iconName` = ?,`createdAt` = ?,`updatedAt` = ?,`isActive` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final PracticeList entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getName());
        statement.bindString(3, entity.getDescription());
        final String _tmp = __converters.fromDifficultyLevel(entity.getDifficultyLevel());
        statement.bindString(4, _tmp);
        statement.bindString(5, entity.getColorTheme());
        statement.bindString(6, entity.getIconName());
        statement.bindLong(7, entity.getCreatedAt());
        statement.bindLong(8, entity.getUpdatedAt());
        final int _tmp_1 = entity.isActive() ? 1 : 0;
        statement.bindLong(9, _tmp_1);
        statement.bindLong(10, entity.getId());
      }
    };
    this.__preparedStmtOfSoftDelete = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE practice_lists SET isActive = 0 WHERE id = ?";
        return _query;
      }
    };
  }

  @Override
  public Object insert(final PracticeList practiceList,
      final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfPracticeList.insertAndReturnId(practiceList);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object delete(final PracticeList practiceList,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfPracticeList.handle(practiceList);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object update(final PracticeList practiceList,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfPracticeList.handle(practiceList);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object softDelete(final long listId, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfSoftDelete.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, listId);
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
          __preparedStmtOfSoftDelete.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<PracticeList>> getAllLists() {
    final String _sql = "SELECT * FROM practice_lists WHERE isActive = 1 ORDER BY createdAt DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"practice_lists"}, new Callable<List<PracticeList>>() {
      @Override
      @NonNull
      public List<PracticeList> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfDifficultyLevel = CursorUtil.getColumnIndexOrThrow(_cursor, "difficultyLevel");
          final int _cursorIndexOfColorTheme = CursorUtil.getColumnIndexOrThrow(_cursor, "colorTheme");
          final int _cursorIndexOfIconName = CursorUtil.getColumnIndexOrThrow(_cursor, "iconName");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final int _cursorIndexOfIsActive = CursorUtil.getColumnIndexOrThrow(_cursor, "isActive");
          final List<PracticeList> _result = new ArrayList<PracticeList>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final PracticeList _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final String _tmpDescription;
            _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            final DifficultyLevel _tmpDifficultyLevel;
            final String _tmp;
            _tmp = _cursor.getString(_cursorIndexOfDifficultyLevel);
            _tmpDifficultyLevel = __converters.toDifficultyLevel(_tmp);
            final String _tmpColorTheme;
            _tmpColorTheme = _cursor.getString(_cursorIndexOfColorTheme);
            final String _tmpIconName;
            _tmpIconName = _cursor.getString(_cursorIndexOfIconName);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            final boolean _tmpIsActive;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfIsActive);
            _tmpIsActive = _tmp_1 != 0;
            _item = new PracticeList(_tmpId,_tmpName,_tmpDescription,_tmpDifficultyLevel,_tmpColorTheme,_tmpIconName,_tmpCreatedAt,_tmpUpdatedAt,_tmpIsActive);
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
  public Object getListById(final long listId,
      final Continuation<? super PracticeList> $completion) {
    final String _sql = "SELECT * FROM practice_lists WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, listId);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<PracticeList>() {
      @Override
      @Nullable
      public PracticeList call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfDifficultyLevel = CursorUtil.getColumnIndexOrThrow(_cursor, "difficultyLevel");
          final int _cursorIndexOfColorTheme = CursorUtil.getColumnIndexOrThrow(_cursor, "colorTheme");
          final int _cursorIndexOfIconName = CursorUtil.getColumnIndexOrThrow(_cursor, "iconName");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final int _cursorIndexOfIsActive = CursorUtil.getColumnIndexOrThrow(_cursor, "isActive");
          final PracticeList _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final String _tmpDescription;
            _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            final DifficultyLevel _tmpDifficultyLevel;
            final String _tmp;
            _tmp = _cursor.getString(_cursorIndexOfDifficultyLevel);
            _tmpDifficultyLevel = __converters.toDifficultyLevel(_tmp);
            final String _tmpColorTheme;
            _tmpColorTheme = _cursor.getString(_cursorIndexOfColorTheme);
            final String _tmpIconName;
            _tmpIconName = _cursor.getString(_cursorIndexOfIconName);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            final boolean _tmpIsActive;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfIsActive);
            _tmpIsActive = _tmp_1 != 0;
            _result = new PracticeList(_tmpId,_tmpName,_tmpDescription,_tmpDifficultyLevel,_tmpColorTheme,_tmpIconName,_tmpCreatedAt,_tmpUpdatedAt,_tmpIsActive);
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

  @Override
  public Flow<PracticeList> getListByIdFlow(final long listId) {
    final String _sql = "SELECT * FROM practice_lists WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, listId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"practice_lists"}, new Callable<PracticeList>() {
      @Override
      @Nullable
      public PracticeList call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfDifficultyLevel = CursorUtil.getColumnIndexOrThrow(_cursor, "difficultyLevel");
          final int _cursorIndexOfColorTheme = CursorUtil.getColumnIndexOrThrow(_cursor, "colorTheme");
          final int _cursorIndexOfIconName = CursorUtil.getColumnIndexOrThrow(_cursor, "iconName");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final int _cursorIndexOfIsActive = CursorUtil.getColumnIndexOrThrow(_cursor, "isActive");
          final PracticeList _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final String _tmpDescription;
            _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            final DifficultyLevel _tmpDifficultyLevel;
            final String _tmp;
            _tmp = _cursor.getString(_cursorIndexOfDifficultyLevel);
            _tmpDifficultyLevel = __converters.toDifficultyLevel(_tmp);
            final String _tmpColorTheme;
            _tmpColorTheme = _cursor.getString(_cursorIndexOfColorTheme);
            final String _tmpIconName;
            _tmpIconName = _cursor.getString(_cursorIndexOfIconName);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            final boolean _tmpIsActive;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfIsActive);
            _tmpIsActive = _tmp_1 != 0;
            _result = new PracticeList(_tmpId,_tmpName,_tmpDescription,_tmpDifficultyLevel,_tmpColorTheme,_tmpIconName,_tmpCreatedAt,_tmpUpdatedAt,_tmpIsActive);
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
  public Object getActiveListCount(final Continuation<? super Integer> $completion) {
    final String _sql = "SELECT COUNT(*) FROM practice_lists WHERE isActive = 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Integer>() {
      @Override
      @NonNull
      public Integer call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Integer _result;
          if (_cursor.moveToFirst()) {
            final int _tmp;
            _tmp = _cursor.getInt(0);
            _result = _tmp;
          } else {
            _result = 0;
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
