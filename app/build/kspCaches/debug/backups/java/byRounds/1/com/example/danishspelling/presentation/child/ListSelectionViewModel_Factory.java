package com.example.danishspelling.presentation.child;

import com.example.danishspelling.data.repository.PracticeListRepository;
import com.example.danishspelling.data.repository.SentenceRepository;
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
public final class ListSelectionViewModel_Factory implements Factory<ListSelectionViewModel> {
  private final Provider<PracticeListRepository> practiceListRepositoryProvider;

  private final Provider<SentenceRepository> sentenceRepositoryProvider;

  public ListSelectionViewModel_Factory(
      Provider<PracticeListRepository> practiceListRepositoryProvider,
      Provider<SentenceRepository> sentenceRepositoryProvider) {
    this.practiceListRepositoryProvider = practiceListRepositoryProvider;
    this.sentenceRepositoryProvider = sentenceRepositoryProvider;
  }

  @Override
  public ListSelectionViewModel get() {
    return newInstance(practiceListRepositoryProvider.get(), sentenceRepositoryProvider.get());
  }

  public static ListSelectionViewModel_Factory create(
      Provider<PracticeListRepository> practiceListRepositoryProvider,
      Provider<SentenceRepository> sentenceRepositoryProvider) {
    return new ListSelectionViewModel_Factory(practiceListRepositoryProvider, sentenceRepositoryProvider);
  }

  public static ListSelectionViewModel newInstance(PracticeListRepository practiceListRepository,
      SentenceRepository sentenceRepository) {
    return new ListSelectionViewModel(practiceListRepository, sentenceRepository);
  }
}
