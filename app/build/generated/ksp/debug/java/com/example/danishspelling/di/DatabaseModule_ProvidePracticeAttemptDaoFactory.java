package com.example.danishspelling.di;

import com.example.danishspelling.data.local.SpellingDatabase;
import com.example.danishspelling.data.local.dao.PracticeAttemptDao;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava"
})
public final class DatabaseModule_ProvidePracticeAttemptDaoFactory implements Factory<PracticeAttemptDao> {
  private final Provider<SpellingDatabase> databaseProvider;

  public DatabaseModule_ProvidePracticeAttemptDaoFactory(
      Provider<SpellingDatabase> databaseProvider) {
    this.databaseProvider = databaseProvider;
  }

  @Override
  public PracticeAttemptDao get() {
    return providePracticeAttemptDao(databaseProvider.get());
  }

  public static DatabaseModule_ProvidePracticeAttemptDaoFactory create(
      Provider<SpellingDatabase> databaseProvider) {
    return new DatabaseModule_ProvidePracticeAttemptDaoFactory(databaseProvider);
  }

  public static PracticeAttemptDao providePracticeAttemptDao(SpellingDatabase database) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.providePracticeAttemptDao(database));
  }
}
