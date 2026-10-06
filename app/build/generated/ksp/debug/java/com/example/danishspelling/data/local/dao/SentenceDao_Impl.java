package com.example.danishspelling.data.local.dao;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomDatabaseKt;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.example.danishspelling.data.local.entities.Sentence;
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
import kotlin.Pair;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class SentenceDao_Impl implements SentenceDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Sentence> __insertionAdapterOfSentence;

  private final EntityDeletionOrUpdateAdapter<Sentence> __deletionAdapterOfSentence;

  private final EntityDeletionOrUpdateAdapter<Sentence> __updateAdapterOfSentence;

  private final SharedSQLiteStatement __preparedStmtOfSoftDelete;

  private final SharedSQLiteStatement __preparedStmtOfUpdateOrderIndex;

  private final SharedSQLiteStatement __preparedStmtOfIncrementIncorrectCount;

  private final SharedSQLiteStatement __preparedStmtOfResetIncorrectCount;

  public SentenceDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfSentence = new EntityInsertionAdapter<Sentence>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `sentences` (`id`,`listId`,`text`,`difficulty`,`hints`,`orderIndex`,`isActive`,`incorrectCount`,`createdAt`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Sentence entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getListId());
        statement.bindString(3, entity.getText());
        statement.bindLong(4, entity.getDifficulty());
        if (entity.getHints() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getHints());
        }
        statement.bindLong(6, entity.getOrderIndex());
        final int _tmp = entity.isActive() ? 1 : 0;
        statement.bindLong(7, _tmp);
        statement.bindLong(8, entity.getIncorrectCount());
        statement.bindLong(9, entity.getCreatedAt());
      }
    };
    this.__deletionAdapterOfSentence = new EntityDeletionOrUpdateAdapter<Sentence>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `sentences` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Sentence entity) {
        statement.bindLong(1, entity.getId());
      }
    };
    this.__updateAdapterOfSentence = new EntityDeletionOrUpdateAdapter<Sentence>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `sentences` SET `id` = ?,`listId` = ?,`text` = ?,`difficulty` = ?,`hints` = ?,`orderIndex` = ?,`isActive` = ?,`incorrectCount` = ?,`createdAt` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Sentence entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getListId());
        statement.bindString(3, entity.getText());
        statement.bindLong(4, entity.getDifficulty());
        if (entity.getHints() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getHints());
        }
        statement.bindLong(6, entity.getOrderIndex());
        final int _tmp = entity.isActive() ? 1 : 0;
        statement.bindLong(7, _tmp);
        statement.bindLong(8, entity.getIncorrectCount());
        statement.bindLong(9, entity.getCreatedAt());
        statement.bindLong(10, entity.getId());
      }
    };
    this.__preparedStmtOfSoftDelete = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE sentences SET isActive = 0 WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfUpdateOrderIndex = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE sentences SET orderIndex = ? WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfIncrementIncorrectCount = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE sentences SET incorrectCount = incorrectCount + 1 WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfResetIncorrectCount = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE sentences SET incorrectCount = 0 WHERE id = ?";
        return _query;
      }
    };
  }

  @Override
  public Object insert(final Sentence sentence, final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfSentence.insertAndReturnId(sentence);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object insertAll(final List<Sentence> sentences,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfSentence.insert(sentences);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object delete(final Sentence sentence, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfSentence.handle(sentence);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object update(final Sentence sentence, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfSentence.handle(sentence);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object updateOrderIndices(final List<Pair<Long, Integer>> updates,
      final Continuation<? super Unit> $completion) {
    return RoomDatabaseKt.withTransaction(__db, (__cont) -> SentenceDao.DefaultImpls.updateOrderIndices(SentenceDao_Impl.this, updates, __cont), $completion);
  }

  @Override
  public Object softDelete(final long sentenceId, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfSoftDelete.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, sentenceId);
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
  public Object updateOrderIndex(final long sentenceId, final int newIndex,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfUpdateOrderIndex.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, newIndex);
        _argIndex = 2;
        _stmt.bindLong(_argIndex, sentenceId);
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
          __preparedStmtOfUpdateOrderIndex.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object incrementIncorrectCount(final long sentenceId,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfIncrementIncorrectCount.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, sentenceId);
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
          __preparedStmtOfIncrementIncorrectCount.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object resetIncorrectCount(final long sentenceId,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfResetIncorrectCount.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, sentenceId);
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
          __preparedStmtOfResetIncorrectCount.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<Sentence>> getSentencesByListId(final long listId) {
    final String _sql = "SELECT * FROM sentences WHERE listId = ? AND isActive = 1 ORDER BY orderIndex ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, listId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"sentences"}, new Callable<List<Sentence>>() {
      @Override
      @NonNull
      public List<Sentence> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfListId = CursorUtil.getColumnIndexOrThrow(_cursor, "listId");
          final int _cursorIndexOfText = CursorUtil.getColumnIndexOrThrow(_cursor, "text");
          final int _cursorIndexOfDifficulty = CursorUtil.getColumnIndexOrThrow(_cursor, "difficulty");
          final int _cursorIndexOfHints = CursorUtil.getColumnIndexOrThrow(_cursor, "hints");
          final int _cursorIndexOfOrderIndex = CursorUtil.getColumnIndexOrThrow(_cursor, "orderIndex");
          final int _cursorIndexOfIsActive = CursorUtil.getColumnIndexOrThrow(_cursor, "isActive");
          final int _cursorIndexOfIncorrectCount = CursorUtil.getColumnIndexOrThrow(_cursor, "incorrectCount");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final List<Sentence> _result = new ArrayList<Sentence>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Sentence _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpListId;
            _tmpListId = _cursor.getLong(_cursorIndexOfListId);
            final String _tmpText;
            _tmpText = _cursor.getString(_cursorIndexOfText);
            final int _tmpDifficulty;
            _tmpDifficulty = _cursor.getInt(_cursorIndexOfDifficulty);
            final String _tmpHints;
            if (_cursor.isNull(_cursorIndexOfHints)) {
              _tmpHints = null;
            } else {
              _tmpHints = _cursor.getString(_cursorIndexOfHints);
            }
            final int _tmpOrderIndex;
            _tmpOrderIndex = _cursor.getInt(_cursorIndexOfOrderIndex);
            final boolean _tmpIsActive;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsActive);
            _tmpIsActive = _tmp != 0;
            final int _tmpIncorrectCount;
            _tmpIncorrectCount = _cursor.getInt(_cursorIndexOfIncorrectCount);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _item = new Sentence(_tmpId,_tmpListId,_tmpText,_tmpDifficulty,_tmpHints,_tmpOrderIndex,_tmpIsActive,_tmpIncorrectCount,_tmpCreatedAt);
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
  public Object getSentenceById(final long sentenceId,
      final Continuation<? super Sentence> $completion) {
    final String _sql = "SELECT * FROM sentences WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, sentenceId);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Sentence>() {
      @Override
      @Nullable
      public Sentence call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfListId = CursorUtil.getColumnIndexOrThrow(_cursor, "listId");
          final int _cursorIndexOfText = CursorUtil.getColumnIndexOrThrow(_cursor, "text");
          final int _cursorIndexOfDifficulty = CursorUtil.getColumnIndexOrThrow(_cursor, "difficulty");
          final int _cursorIndexOfHints = CursorUtil.getColumnIndexOrThrow(_cursor, "hints");
          final int _cursorIndexOfOrderIndex = CursorUtil.getColumnIndexOrThrow(_cursor, "orderIndex");
          final int _cursorIndexOfIsActive = CursorUtil.getColumnIndexOrThrow(_cursor, "isActive");
          final int _cursorIndexOfIncorrectCount = CursorUtil.getColumnIndexOrThrow(_cursor, "incorrectCount");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final Sentence _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpListId;
            _tmpListId = _cursor.getLong(_cursorIndexOfListId);
            final String _tmpText;
            _tmpText = _cursor.getString(_cursorIndexOfText);
            final int _tmpDifficulty;
            _tmpDifficulty = _cursor.getInt(_cursorIndexOfDifficulty);
            final String _tmpHints;
            if (_cursor.isNull(_cursorIndexOfHints)) {
              _tmpHints = null;
            } else {
              _tmpHints = _cursor.getString(_cursorIndexOfHints);
            }
            final int _tmpOrderIndex;
            _tmpOrderIndex = _cursor.getInt(_cursorIndexOfOrderIndex);
            final boolean _tmpIsActive;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsActive);
            _tmpIsActive = _tmp != 0;
            final int _tmpIncorrectCount;
            _tmpIncorrectCount = _cursor.getInt(_cursorIndexOfIncorrectCount);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _result = new Sentence(_tmpId,_tmpListId,_tmpText,_tmpDifficulty,_tmpHints,_tmpOrderIndex,_tmpIsActive,_tmpIncorrectCount,_tmpCreatedAt);
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
  public Object getSentenceCountForList(final long listId,
      final Continuation<? super Integer> $completion) {
    final String _sql = "SELECT COUNT(*) FROM sentences WHERE listId = ? AND isActive = 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, listId);
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

  @Override
  public Flow<List<Sentence>> getIncorrectSentencesByListId(final long listId) {
    final String _sql = "SELECT * FROM sentences WHERE listId = ? AND isActive = 1 AND incorrectCount > 0 ORDER BY incorrectCount DESC, orderIndex ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, listId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"sentences"}, new Callable<List<Sentence>>() {
      @Override
      @NonNull
      public List<Sentence> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfListId = CursorUtil.getColumnIndexOrThrow(_cursor, "listId");
          final int _cursorIndexOfText = CursorUtil.getColumnIndexOrThrow(_cursor, "text");
          final int _cursorIndexOfDifficulty = CursorUtil.getColumnIndexOrThrow(_cursor, "difficulty");
          final int _cursorIndexOfHints = CursorUtil.getColumnIndexOrThrow(_cursor, "hints");
          final int _cursorIndexOfOrderIndex = CursorUtil.getColumnIndexOrThrow(_cursor, "orderIndex");
          final int _cursorIndexOfIsActive = CursorUtil.getColumnIndexOrThrow(_cursor, "isActive");
          final int _cursorIndexOfIncorrectCount = CursorUtil.getColumnIndexOrThrow(_cursor, "incorrectCount");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final List<Sentence> _result = new ArrayList<Sentence>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Sentence _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpListId;
            _tmpListId = _cursor.getLong(_cursorIndexOfListId);
            final String _tmpText;
            _tmpText = _cursor.getString(_cursorIndexOfText);
            final int _tmpDifficulty;
            _tmpDifficulty = _cursor.getInt(_cursorIndexOfDifficulty);
            final String _tmpHints;
            if (_cursor.isNull(_cursorIndexOfHints)) {
              _tmpHints = null;
            } else {
              _tmpHints = _cursor.getString(_cursorIndexOfHints);
            }
            final int _tmpOrderIndex;
            _tmpOrderIndex = _cursor.getInt(_cursorIndexOfOrderIndex);
            final boolean _tmpIsActive;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsActive);
            _tmpIsActive = _tmp != 0;
            final int _tmpIncorrectCount;
            _tmpIncorrectCount = _cursor.getInt(_cursorIndexOfIncorrectCount);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _item = new Sentence(_tmpId,_tmpListId,_tmpText,_tmpDifficulty,_tmpHints,_tmpOrderIndex,_tmpIsActive,_tmpIncorrectCount,_tmpCreatedAt);
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
  public Object getIncorrectSentenceCountForList(final long listId,
      final Continuation<? super Integer> $completion) {
    final String _sql = "SELECT COUNT(*) FROM sentences WHERE listId = ? AND isActive = 1 AND incorrectCount > 0";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, listId);
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
