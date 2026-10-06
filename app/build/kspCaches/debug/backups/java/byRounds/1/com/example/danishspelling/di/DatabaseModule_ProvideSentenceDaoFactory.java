package com.example.danishspelling.di;

import com.example.danishspelling.data.local.SpellingDatabase;
import com.example.danishspelling.data.local.dao.SentenceDao;
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
public final class DatabaseModule_ProvideSentenceDaoFactory implements Factory<SentenceDao> {
  private final Provider<SpellingDatabase> databaseProvider;

  public DatabaseModule_ProvideSentenceDaoFactory(Provider<SpellingDatabase> databaseProvider) {
    this.databaseProvider = databaseProvider;
  }

  @Override
  public SentenceDao get() {
    return provideSentenceDao(databaseProvider.get());
  }

  public static DatabaseModule_ProvideSentenceDaoFactory create(
      Provider<SpellingDatabase> databaseProvider) {
    return new DatabaseModule_ProvideSentenceDaoFactory(databaseProvider);
  }

  public static SentenceDao provideSentenceDao(SpellingDatabase database) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideSentenceDao(database));
  }
}
