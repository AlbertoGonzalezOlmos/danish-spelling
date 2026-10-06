package com.example.danishspelling.di;

import com.example.danishspelling.data.local.SpellingDatabase;
import com.example.danishspelling.data.local.dao.UserSettingsDao;
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
public final class DatabaseModule_ProvideUserSettingsDaoFactory implements Factory<UserSettingsDao> {
  private final Provider<SpellingDatabase> databaseProvider;

  public DatabaseModule_ProvideUserSettingsDaoFactory(Provider<SpellingDatabase> databaseProvider) {
    this.databaseProvider = databaseProvider;
  }

  @Override
  public UserSettingsDao get() {
    return provideUserSettingsDao(databaseProvider.get());
  }

  public static DatabaseModule_ProvideUserSettingsDaoFactory create(
      Provider<SpellingDatabase> databaseProvider) {
    return new DatabaseModule_ProvideUserSettingsDaoFactory(databaseProvider);
  }

  public static UserSettingsDao provideUserSettingsDao(SpellingDatabase database) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideUserSettingsDao(database));
  }
}
