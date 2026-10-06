package com.example.danishspelling.di;

import android.content.Context;
import com.example.danishspelling.data.local.SpellingDatabase;
import com.example.danishspelling.data.local.dao.PracticeListDao;
import com.example.danishspelling.data.local.dao.SentenceDao;
import com.example.danishspelling.data.local.dao.UserSettingsDao;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata("dagger.hilt.android.qualifiers.ApplicationContext")
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
public final class DatabaseModule_ProvideSpellingDatabaseFactory implements Factory<SpellingDatabase> {
  private final Provider<Context> contextProvider;

  private final Provider<PracticeListDao> providerProvider;

  private final Provider<SentenceDao> providerSentenceProvider;

  private final Provider<UserSettingsDao> providerSettingsProvider;

  public DatabaseModule_ProvideSpellingDatabaseFactory(Provider<Context> contextProvider,
      Provider<PracticeListDao> providerProvider, Provider<SentenceDao> providerSentenceProvider,
      Provider<UserSettingsDao> providerSettingsProvider) {
    this.contextProvider = contextProvider;
    this.providerProvider = providerProvider;
    this.providerSentenceProvider = providerSentenceProvider;
    this.providerSettingsProvider = providerSettingsProvider;
  }

  @Override
  public SpellingDatabase get() {
    return provideSpellingDatabase(contextProvider.get(), providerProvider, providerSentenceProvider, providerSettingsProvider);
  }

  public static DatabaseModule_ProvideSpellingDatabaseFactory create(
      Provider<Context> contextProvider, Provider<PracticeListDao> providerProvider,
      Provider<SentenceDao> providerSentenceProvider,
      Provider<UserSettingsDao> providerSettingsProvider) {
    return new DatabaseModule_ProvideSpellingDatabaseFactory(contextProvider, providerProvider, providerSentenceProvider, providerSettingsProvider);
  }

  public static SpellingDatabase provideSpellingDatabase(Context context,
      Provider<PracticeListDao> provider, Provider<SentenceDao> providerSentence,
      Provider<UserSettingsDao> providerSettings) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideSpellingDatabase(context, provider, providerSentence, providerSettings));
  }
}
