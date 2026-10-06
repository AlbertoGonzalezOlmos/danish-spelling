package com.example.danishspelling.data.repository;

import com.example.danishspelling.data.local.dao.PracticeListDao;
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
public final class PracticeListRepository_Factory implements Factory<PracticeListRepository> {
  private final Provider<PracticeListDao> practiceListDaoProvider;

  public PracticeListRepository_Factory(Provider<PracticeListDao> practiceListDaoProvider) {
    this.practiceListDaoProvider = practiceListDaoProvider;
  }

  @Override
  public PracticeListRepository get() {
    return newInstance(practiceListDaoProvider.get());
  }

  public static PracticeListRepository_Factory create(
      Provider<PracticeListDao> practiceListDaoProvider) {
    return new PracticeListRepository_Factory(practiceListDaoProvider);
  }

  public static PracticeListRepository newInstance(PracticeListDao practiceListDao) {
    return new PracticeListRepository(practiceListDao);
  }
}
