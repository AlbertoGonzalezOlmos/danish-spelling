package com.example.danishspelling.presentation.parent;

import com.example.danishspelling.data.repository.PracticeListRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
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
public final class ListManagementViewModel_Factory implements Factory<ListManagementViewModel> {
  private final Provider<PracticeListRepository> practiceListRepositoryProvider;

  public ListManagementViewModel_Factory(
      Provider<PracticeListRepository> practiceListRepositoryProvider) {
    this.practiceListRepositoryProvider = practiceListRepositoryProvider;
  }

  @Override
  public ListManagementViewModel get() {
    return newInstance(practiceListRepositoryProvider.get());
  }

  public static ListManagementViewModel_Factory create(
      Provider<PracticeListRepository> practiceListRepositoryProvider) {
    return new ListManagementViewModel_Factory(practiceListRepositoryProvider);
  }

  public static ListManagementViewModel newInstance(PracticeListRepository practiceListRepository) {
    return new ListManagementViewModel(practiceListRepository);
  }
}
