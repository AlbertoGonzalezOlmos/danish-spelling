package com.example.danishspelling.di;

import com.example.danishspelling.data.local.SpellingDatabase;
import com.example.danishspelling.data.local.dao.PracticeListDao;
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
public final class DatabaseModule_ProvidePracticeListDaoFactory implements Factory<PracticeListDao> {
  private final Provider<SpellingDatabase> databaseProvider;

  public DatabaseModule_ProvidePracticeListDaoFactory(Provider<SpellingDatabase> databaseProvider) {
    this.databaseProvider = databaseProvider;
  }

  @Override
  public PracticeListDao get() {
    return providePracticeListDao(databaseProvider.get());
  }

  public static DatabaseModule_ProvidePracticeListDaoFactory create(
      Provider<SpellingDatabase> databaseProvider) {
    return new DatabaseModule_ProvidePracticeListDaoFactory(databaseProvider);
  }

  public static PracticeListDao providePracticeListDao(SpellingDatabase database) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.providePracticeListDao(database));
  }
}
