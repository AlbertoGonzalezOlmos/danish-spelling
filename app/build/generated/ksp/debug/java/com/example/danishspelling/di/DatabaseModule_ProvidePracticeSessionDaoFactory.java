package com.example.danishspelling.di;

import com.example.danishspelling.data.local.SpellingDatabase;
import com.example.danishspelling.data.local.dao.PracticeSessionDao;
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
public final class DatabaseModule_ProvidePracticeSessionDaoFactory implements Factory<PracticeSessionDao> {
  private final Provider<SpellingDatabase> databaseProvider;

  public DatabaseModule_ProvidePracticeSessionDaoFactory(
      Provider<SpellingDatabase> databaseProvider) {
    this.databaseProvider = databaseProvider;
  }

  @Override
  public PracticeSessionDao get() {
    return providePracticeSessionDao(databaseProvider.get());
  }

  public static DatabaseModule_ProvidePracticeSessionDaoFactory create(
      Provider<SpellingDatabase> databaseProvider) {
    return new DatabaseModule_ProvidePracticeSessionDaoFactory(databaseProvider);
  }

  public static PracticeSessionDao providePracticeSessionDao(SpellingDatabase database) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.providePracticeSessionDao(database));
  }
}
