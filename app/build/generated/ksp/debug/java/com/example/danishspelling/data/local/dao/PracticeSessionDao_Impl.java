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
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.example.danishspelling.data.local.entities.PracticeSession;
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
public final class PracticeSessionDao_Impl implements PracticeSessionDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<PracticeSession> __insertionAdapterOfPracticeSession;

  private final EntityDeletionOrUpdateAdapter<PracticeSession> __deletionAdapterOfPracticeSession;

  private final EntityDeletionOrUpdateAdapter<PracticeSession> __updateAdapterOfPracticeSession;

  public PracticeSessionDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfPracticeSession = new EntityInsertionAdapter<PracticeSession>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `practice_sessions` (`id`,`listId`,`startTime`,`endTime`,`totalSentences`,`completedSentences`,`totalStarsEarned`) VALUES (nullif(?, 0),?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final PracticeSession entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getListId());
        statement.bindLong(3, entity.getStartTime());
        if (entity.getEndTime() == null) {
          statement.bindNull(4);
        } else {
          statement.bindLong(4, entity.getEndTime());
        }
        statement.bindLong(5, entity.getTotalSentences());
        statement.bindLong(6, entity.getCompletedSentences());
        statement.bindLong(7, entity.getTotalStarsEarned());
      }
    };
    this.__deletionAdapterOfPracticeSession = new EntityDeletionOrUpdateAdapter<PracticeSession>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `practice_sessions` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final PracticeSession entity) {
        statement.bindLong(1, entity.getId());
      }
    };
    this.__updateAdapterOfPracticeSession = new EntityDeletionOrUpdateAdapter<PracticeSession>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `practice_sessions` SET `id` = ?,`listId` = ?,`startTime` = ?,`endTime` = ?,`totalSentences` = ?,`completedSentences` = ?,`totalStarsEarned` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final PracticeSession entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getListId());
        statement.bindLong(3, entity.getStartTime());
        if (entity.getEndTime() == null) {
          statement.bindNull(4);
        } else {
          statement.bindLong(4, entity.getEndTime());
        }
        statement.bindLong(5, entity.getTotalSentences());
        statement.bindLong(6, entity.getCompletedSentences());
        statement.bindLong(7, entity.getTotalStarsEarned());
        statement.bindLong(8, entity.getId());
      }
    };
  }

  @Override
  public Object insert(final PracticeSession session,
      final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfPracticeSession.insertAndReturnId(session);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object delete(final PracticeSession session,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfPracticeSession.handle(session);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object update(final PracticeSession session,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfPracticeSession.handle(session);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<PracticeSession>> getSessionsByListId(final long listId) {
    final String _sql = "SELECT * FROM practice_sessions WHERE listId = ? ORDER BY startTime DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, listId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"practice_sessions"}, new Callable<List<PracticeSession>>() {
      @Override
      @NonNull
      public List<PracticeSession> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfListId = CursorUtil.getColumnIndexOrThrow(_cursor, "listId");
          final int _cursorIndexOfStartTime = CursorUtil.getColumnIndexOrThrow(_cursor, "startTime");
          final int _cursorIndexOfEndTime = CursorUtil.getColumnIndexOrThrow(_cursor, "endTime");
          final int _cursorIndexOfTotalSentences = CursorUtil.getColumnIndexOrThrow(_cursor, "totalSentences");
          final int _cursorIndexOfCompletedSentences = CursorUtil.getColumnIndexOrThrow(_cursor, "completedSentences");
          final int _cursorIndexOfTotalStarsEarned = CursorUtil.getColumnIndexOrThrow(_cursor, "totalStarsEarned");
          final List<PracticeSession> _result = new ArrayList<PracticeSession>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final PracticeSession _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpListId;
            _tmpListId = _cursor.getLong(_cursorIndexOfListId);
            final long _tmpStartTime;
            _tmpStartTime = _cursor.getLong(_cursorIndexOfStartTime);
            final Long _tmpEndTime;
            if (_cursor.isNull(_cursorIndexOfEndTime)) {
              _tmpEndTime = null;
            } else {
              _tmpEndTime = _cursor.getLong(_cursorIndexOfEndTime);
            }
            final int _tmpTotalSentences;
            _tmpTotalSentences = _cursor.getInt(_cursorIndexOfTotalSentences);
            final int _tmpCompletedSentences;
            _tmpCompletedSentences = _cursor.getInt(_cursorIndexOfCompletedSentences);
            final int _tmpTotalStarsEarned;
            _tmpTotalStarsEarned = _cursor.getInt(_cursorIndexOfTotalStarsEarned);
            _item = new PracticeSession(_tmpId,_tmpListId,_tmpStartTime,_tmpEndTime,_tmpTotalSentences,_tmpCompletedSentences,_tmpTotalStarsEarned);
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
  public Object getSessionById(final long sessionId,
      final Continuation<? super PracticeSession> $completion) {
    final String _sql = "SELECT * FROM practice_sessions WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, sessionId);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<PracticeSession>() {
      @Override
      @Nullable
      public PracticeSession call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfListId = CursorUtil.getColumnIndexOrThrow(_cursor, "listId");
          final int _cursorIndexOfStartTime = CursorUtil.getColumnIndexOrThrow(_cursor, "startTime");
          final int _cursorIndexOfEndTime = CursorUtil.getColumnIndexOrThrow(_cursor, "endTime");
          final int _cursorIndexOfTotalSentences = CursorUtil.getColumnIndexOrThrow(_cursor, "totalSentences");
          final int _cursorIndexOfCompletedSentences = CursorUtil.getColumnIndexOrThrow(_cursor, "completedSentences");
          final int _cursorIndexOfTotalStarsEarned = CursorUtil.getColumnIndexOrThrow(_cursor, "totalStarsEarned");
          final PracticeSession _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpListId;
            _tmpListId = _cursor.getLong(_cursorIndexOfListId);
            final long _tmpStartTime;
            _tmpStartTime = _cursor.getLong(_cursorIndexOfStartTime);
            final Long _tmpEndTime;
            if (_cursor.isNull(_cursorIndexOfEndTime)) {
              _tmpEndTime = null;
            } else {
              _tmpEndTime = _cursor.getLong(_cursorIndexOfEndTime);
            }
            final int _tmpTotalSentences;
            _tmpTotalSentences = _cursor.getInt(_cursorIndexOfTotalSentences);
            final int _tmpCompletedSentences;
            _tmpCompletedSentences = _cursor.getInt(_cursorIndexOfCompletedSentences);
            final int _tmpTotalStarsEarned;
            _tmpTotalStarsEarned = _cursor.getInt(_cursorIndexOfTotalStarsEarned);
            _result = new PracticeSession(_tmpId,_tmpListId,_tmpStartTime,_tmpEndTime,_tmpTotalSentences,_tmpCompletedSentences,_tmpTotalStarsEarned);
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
  public Flow<List<PracticeSession>> getRecentSessions() {
    final String _sql = "SELECT * FROM practice_sessions ORDER BY startTime DESC LIMIT 10";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"practice_sessions"}, new Callable<List<PracticeSession>>() {
      @Override
      @NonNull
      public List<PracticeSession> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfListId = CursorUtil.getColumnIndexOrThrow(_cursor, "listId");
          final int _cursorIndexOfStartTime = CursorUtil.getColumnIndexOrThrow(_cursor, "startTime");
          final int _cursorIndexOfEndTime = CursorUtil.getColumnIndexOrThrow(_cursor, "endTime");
          final int _cursorIndexOfTotalSentences = CursorUtil.getColumnIndexOrThrow(_cursor, "totalSentences");
          final int _cursorIndexOfCompletedSentences = CursorUtil.getColumnIndexOrThrow(_cursor, "completedSentences");
          final int _cursorIndexOfTotalStarsEarned = CursorUtil.getColumnIndexOrThrow(_cursor, "totalStarsEarned");
          final List<PracticeSession> _result = new ArrayList<PracticeSession>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final PracticeSession _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpListId;
            _tmpListId = _cursor.getLong(_cursorIndexOfListId);
            final long _tmpStartTime;
            _tmpStartTime = _cursor.getLong(_cursorIndexOfStartTime);
            final Long _tmpEndTime;
            if (_cursor.isNull(_cursorIndexOfEndTime)) {
              _tmpEndTime = null;
            } else {
              _tmpEndTime = _cursor.getLong(_cursorIndexOfEndTime);
            }
            final int _tmpTotalSentences;
            _tmpTotalSentences = _cursor.getInt(_cursorIndexOfTotalSentences);
            final int _tmpCompletedSentences;
            _tmpCompletedSentences = _cursor.getInt(_cursorIndexOfCompletedSentences);
            final int _tmpTotalStarsEarned;
            _tmpTotalStarsEarned = _cursor.getInt(_cursorIndexOfTotalStarsEarned);
            _item = new PracticeSession(_tmpId,_tmpListId,_tmpStartTime,_tmpEndTime,_tmpTotalSentences,_tmpCompletedSentences,_tmpTotalStarsEarned);
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
  public Object getSessionCountForList(final long listId,
      final Continuation<? super Integer> $completion) {
    final String _sql = "SELECT COUNT(*) FROM practice_sessions WHERE listId = ?";
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
  public Object getTotalStarsForList(final long listId,
      final Continuation<? super Integer> $completion) {
    final String _sql = "SELECT SUM(totalStarsEarned) FROM practice_sessions WHERE listId = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, listId);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Integer>() {
      @Override
      @Nullable
      public Integer call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Integer _result;
          if (_cursor.moveToFirst()) {
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
    }, $completion);
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
