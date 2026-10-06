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
import com.example.danishspelling.data.local.entities.PracticeAttempt;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Float;
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
public final class PracticeAttemptDao_Impl implements PracticeAttemptDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<PracticeAttempt> __insertionAdapterOfPracticeAttempt;

  private final EntityDeletionOrUpdateAdapter<PracticeAttempt> __deletionAdapterOfPracticeAttempt;

  private final EntityDeletionOrUpdateAdapter<PracticeAttempt> __updateAdapterOfPracticeAttempt;

  public PracticeAttemptDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfPracticeAttempt = new EntityInsertionAdapter<PracticeAttempt>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `practice_attempts` (`id`,`sentenceId`,`sessionId`,`userInput`,`correctText`,`accuracy`,`starsEarned`,`attemptNumber`,`timestamp`,`speechSpeedUsed`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final PracticeAttempt entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getSentenceId());
        statement.bindLong(3, entity.getSessionId());
        statement.bindString(4, entity.getUserInput());
        statement.bindString(5, entity.getCorrectText());
        statement.bindDouble(6, entity.getAccuracy());
        statement.bindLong(7, entity.getStarsEarned());
        statement.bindLong(8, entity.getAttemptNumber());
        statement.bindLong(9, entity.getTimestamp());
        statement.bindDouble(10, entity.getSpeechSpeedUsed());
      }
    };
    this.__deletionAdapterOfPracticeAttempt = new EntityDeletionOrUpdateAdapter<PracticeAttempt>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `practice_attempts` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final PracticeAttempt entity) {
        statement.bindLong(1, entity.getId());
      }
    };
    this.__updateAdapterOfPracticeAttempt = new EntityDeletionOrUpdateAdapter<PracticeAttempt>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `practice_attempts` SET `id` = ?,`sentenceId` = ?,`sessionId` = ?,`userInput` = ?,`correctText` = ?,`accuracy` = ?,`starsEarned` = ?,`attemptNumber` = ?,`timestamp` = ?,`speechSpeedUsed` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final PracticeAttempt entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getSentenceId());
        statement.bindLong(3, entity.getSessionId());
        statement.bindString(4, entity.getUserInput());
        statement.bindString(5, entity.getCorrectText());
        statement.bindDouble(6, entity.getAccuracy());
        statement.bindLong(7, entity.getStarsEarned());
        statement.bindLong(8, entity.getAttemptNumber());
        statement.bindLong(9, entity.getTimestamp());
        statement.bindDouble(10, entity.getSpeechSpeedUsed());
        statement.bindLong(11, entity.getId());
      }
    };
  }

  @Override
  public Object insert(final PracticeAttempt attempt,
      final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfPracticeAttempt.insertAndReturnId(attempt);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object delete(final PracticeAttempt attempt,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfPracticeAttempt.handle(attempt);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object update(final PracticeAttempt attempt,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfPracticeAttempt.handle(attempt);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<PracticeAttempt>> getAttemptsBySessionId(final long sessionId) {
    final String _sql = "SELECT * FROM practice_attempts WHERE sessionId = ? ORDER BY timestamp ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, sessionId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"practice_attempts"}, new Callable<List<PracticeAttempt>>() {
      @Override
      @NonNull
      public List<PracticeAttempt> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfSentenceId = CursorUtil.getColumnIndexOrThrow(_cursor, "sentenceId");
          final int _cursorIndexOfSessionId = CursorUtil.getColumnIndexOrThrow(_cursor, "sessionId");
          final int _cursorIndexOfUserInput = CursorUtil.getColumnIndexOrThrow(_cursor, "userInput");
          final int _cursorIndexOfCorrectText = CursorUtil.getColumnIndexOrThrow(_cursor, "correctText");
          final int _cursorIndexOfAccuracy = CursorUtil.getColumnIndexOrThrow(_cursor, "accuracy");
          final int _cursorIndexOfStarsEarned = CursorUtil.getColumnIndexOrThrow(_cursor, "starsEarned");
          final int _cursorIndexOfAttemptNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "attemptNumber");
          final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
          final int _cursorIndexOfSpeechSpeedUsed = CursorUtil.getColumnIndexOrThrow(_cursor, "speechSpeedUsed");
          final List<PracticeAttempt> _result = new ArrayList<PracticeAttempt>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final PracticeAttempt _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpSentenceId;
            _tmpSentenceId = _cursor.getLong(_cursorIndexOfSentenceId);
            final long _tmpSessionId;
            _tmpSessionId = _cursor.getLong(_cursorIndexOfSessionId);
            final String _tmpUserInput;
            _tmpUserInput = _cursor.getString(_cursorIndexOfUserInput);
            final String _tmpCorrectText;
            _tmpCorrectText = _cursor.getString(_cursorIndexOfCorrectText);
            final float _tmpAccuracy;
            _tmpAccuracy = _cursor.getFloat(_cursorIndexOfAccuracy);
            final int _tmpStarsEarned;
            _tmpStarsEarned = _cursor.getInt(_cursorIndexOfStarsEarned);
            final int _tmpAttemptNumber;
            _tmpAttemptNumber = _cursor.getInt(_cursorIndexOfAttemptNumber);
            final long _tmpTimestamp;
            _tmpTimestamp = _cursor.getLong(_cursorIndexOfTimestamp);
            final float _tmpSpeechSpeedUsed;
            _tmpSpeechSpeedUsed = _cursor.getFloat(_cursorIndexOfSpeechSpeedUsed);
            _item = new PracticeAttempt(_tmpId,_tmpSentenceId,_tmpSessionId,_tmpUserInput,_tmpCorrectText,_tmpAccuracy,_tmpStarsEarned,_tmpAttemptNumber,_tmpTimestamp,_tmpSpeechSpeedUsed);
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
  public Flow<List<PracticeAttempt>> getAttemptsBySentenceId(final long sentenceId) {
    final String _sql = "SELECT * FROM practice_attempts WHERE sentenceId = ? ORDER BY timestamp DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, sentenceId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"practice_attempts"}, new Callable<List<PracticeAttempt>>() {
      @Override
      @NonNull
      public List<PracticeAttempt> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfSentenceId = CursorUtil.getColumnIndexOrThrow(_cursor, "sentenceId");
          final int _cursorIndexOfSessionId = CursorUtil.getColumnIndexOrThrow(_cursor, "sessionId");
          final int _cursorIndexOfUserInput = CursorUtil.getColumnIndexOrThrow(_cursor, "userInput");
          final int _cursorIndexOfCorrectText = CursorUtil.getColumnIndexOrThrow(_cursor, "correctText");
          final int _cursorIndexOfAccuracy = CursorUtil.getColumnIndexOrThrow(_cursor, "accuracy");
          final int _cursorIndexOfStarsEarned = CursorUtil.getColumnIndexOrThrow(_cursor, "starsEarned");
          final int _cursorIndexOfAttemptNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "attemptNumber");
          final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
          final int _cursorIndexOfSpeechSpeedUsed = CursorUtil.getColumnIndexOrThrow(_cursor, "speechSpeedUsed");
          final List<PracticeAttempt> _result = new ArrayList<PracticeAttempt>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final PracticeAttempt _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpSentenceId;
            _tmpSentenceId = _cursor.getLong(_cursorIndexOfSentenceId);
            final long _tmpSessionId;
            _tmpSessionId = _cursor.getLong(_cursorIndexOfSessionId);
            final String _tmpUserInput;
            _tmpUserInput = _cursor.getString(_cursorIndexOfUserInput);
            final String _tmpCorrectText;
            _tmpCorrectText = _cursor.getString(_cursorIndexOfCorrectText);
            final float _tmpAccuracy;
            _tmpAccuracy = _cursor.getFloat(_cursorIndexOfAccuracy);
            final int _tmpStarsEarned;
            _tmpStarsEarned = _cursor.getInt(_cursorIndexOfStarsEarned);
            final int _tmpAttemptNumber;
            _tmpAttemptNumber = _cursor.getInt(_cursorIndexOfAttemptNumber);
            final long _tmpTimestamp;
            _tmpTimestamp = _cursor.getLong(_cursorIndexOfTimestamp);
            final float _tmpSpeechSpeedUsed;
            _tmpSpeechSpeedUsed = _cursor.getFloat(_cursorIndexOfSpeechSpeedUsed);
            _item = new PracticeAttempt(_tmpId,_tmpSentenceId,_tmpSessionId,_tmpUserInput,_tmpCorrectText,_tmpAccuracy,_tmpStarsEarned,_tmpAttemptNumber,_tmpTimestamp,_tmpSpeechSpeedUsed);
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
  public Object getAttemptById(final long attemptId,
      final Continuation<? super PracticeAttempt> $completion) {
    final String _sql = "SELECT * FROM practice_attempts WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, attemptId);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<PracticeAttempt>() {
      @Override
      @Nullable
      public PracticeAttempt call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfSentenceId = CursorUtil.getColumnIndexOrThrow(_cursor, "sentenceId");
          final int _cursorIndexOfSessionId = CursorUtil.getColumnIndexOrThrow(_cursor, "sessionId");
          final int _cursorIndexOfUserInput = CursorUtil.getColumnIndexOrThrow(_cursor, "userInput");
          final int _cursorIndexOfCorrectText = CursorUtil.getColumnIndexOrThrow(_cursor, "correctText");
          final int _cursorIndexOfAccuracy = CursorUtil.getColumnIndexOrThrow(_cursor, "accuracy");
          final int _cursorIndexOfStarsEarned = CursorUtil.getColumnIndexOrThrow(_cursor, "starsEarned");
          final int _cursorIndexOfAttemptNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "attemptNumber");
          final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
          final int _cursorIndexOfSpeechSpeedUsed = CursorUtil.getColumnIndexOrThrow(_cursor, "speechSpeedUsed");
          final PracticeAttempt _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpSentenceId;
            _tmpSentenceId = _cursor.getLong(_cursorIndexOfSentenceId);
            final long _tmpSessionId;
            _tmpSessionId = _cursor.getLong(_cursorIndexOfSessionId);
            final String _tmpUserInput;
            _tmpUserInput = _cursor.getString(_cursorIndexOfUserInput);
            final String _tmpCorrectText;
            _tmpCorrectText = _cursor.getString(_cursorIndexOfCorrectText);
            final float _tmpAccuracy;
            _tmpAccuracy = _cursor.getFloat(_cursorIndexOfAccuracy);
            final int _tmpStarsEarned;
            _tmpStarsEarned = _cursor.getInt(_cursorIndexOfStarsEarned);
            final int _tmpAttemptNumber;
            _tmpAttemptNumber = _cursor.getInt(_cursorIndexOfAttemptNumber);
            final long _tmpTimestamp;
            _tmpTimestamp = _cursor.getLong(_cursorIndexOfTimestamp);
            final float _tmpSpeechSpeedUsed;
            _tmpSpeechSpeedUsed = _cursor.getFloat(_cursorIndexOfSpeechSpeedUsed);
            _result = new PracticeAttempt(_tmpId,_tmpSentenceId,_tmpSessionId,_tmpUserInput,_tmpCorrectText,_tmpAccuracy,_tmpStarsEarned,_tmpAttemptNumber,_tmpTimestamp,_tmpSpeechSpeedUsed);
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
  public Object getAverageAccuracyForSentence(final long sentenceId,
      final Continuation<? super Float> $completion) {
    final String _sql = "SELECT AVG(accuracy) FROM practice_attempts WHERE sentenceId = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, sentenceId);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Float>() {
      @Override
      @Nullable
      public Float call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Float _result;
          if (_cursor.moveToFirst()) {
            final Float _tmp;
            if (_cursor.isNull(0)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getFloat(0);
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

  @Override
  public Object getAttemptCountForSentence(final long sentenceId,
      final Continuation<? super Integer> $completion) {
    final String _sql = "SELECT COUNT(*) FROM practice_attempts WHERE sentenceId = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, sentenceId);
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
  public Object getAverageAccuracyForSession(final long sessionId,
      final Continuation<? super Float> $completion) {
    final String _sql = "SELECT AVG(accuracy) FROM practice_attempts WHERE sessionId = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, sessionId);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Float>() {
      @Override
      @Nullable
      public Float call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Float _result;
          if (_cursor.moveToFirst()) {
            final Float _tmp;
            if (_cursor.isNull(0)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getFloat(0);
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
