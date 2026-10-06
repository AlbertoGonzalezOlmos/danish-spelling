package com.example.danishspelling.data.repository;

import com.example.danishspelling.data.local.dao.PracticeAttemptDao;
import com.example.danishspelling.data.local.dao.PracticeSessionDao;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
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
public final class PracticeRepository_Factory implements Factory<PracticeRepository> {
  private final Provider<PracticeSessionDao> sessionDaoProvider;

  private final Provider<PracticeAttemptDao> attemptDaoProvider;

  public PracticeRepository_Factory(Provider<PracticeSessionDao> sessionDaoProvider,
      Provider<PracticeAttemptDao> attemptDaoProvider) {
    this.sessionDaoProvider = sessionDaoProvider;
    this.attemptDaoProvider = attemptDaoProvider;
  }

  @Override
  public PracticeRepository get() {
    return newInstance(sessionDaoProvider.get(), attemptDaoProvider.get());
  }

  public static PracticeRepository_Factory create(Provider<PracticeSessionDao> sessionDaoProvider,
      Provider<PracticeAttemptDao> attemptDaoProvider) {
    return new PracticeRepository_Factory(sessionDaoProvider, attemptDaoProvider);
  }

  public static PracticeRepository newInstance(PracticeSessionDao sessionDao,
      PracticeAttemptDao attemptDao) {
    return new PracticeRepository(sessionDao, attemptDao);
  }
}
