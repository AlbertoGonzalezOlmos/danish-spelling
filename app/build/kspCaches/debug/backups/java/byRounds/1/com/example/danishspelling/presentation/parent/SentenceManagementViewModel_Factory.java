package com.example.danishspelling.presentation.parent;

import android.content.Context;
import com.example.danishspelling.data.repository.SentenceRepository;
import com.example.danishspelling.service.DanishTTSService;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
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
public final class SentenceManagementViewModel_Factory implements Factory<SentenceManagementViewModel> {
  private final Provider<SentenceRepository> sentenceRepositoryProvider;

  private final Provider<DanishTTSService> ttsServiceProvider;

  private final Provider<Context> contextProvider;

  public SentenceManagementViewModel_Factory(
      Provider<SentenceRepository> sentenceRepositoryProvider,
      Provider<DanishTTSService> ttsServiceProvider, Provider<Context> contextProvider) {
    this.sentenceRepositoryProvider = sentenceRepositoryProvider;
    this.ttsServiceProvider = ttsServiceProvider;
    this.contextProvider = contextProvider;
  }

  @Override
  public SentenceManagementViewModel get() {
    return newInstance(sentenceRepositoryProvider.get(), ttsServiceProvider.get(), contextProvider.get());
  }

  public static SentenceManagementViewModel_Factory create(
      Provider<SentenceRepository> sentenceRepositoryProvider,
      Provider<DanishTTSService> ttsServiceProvider, Provider<Context> contextProvider) {
    return new SentenceManagementViewModel_Factory(sentenceRepositoryProvider, ttsServiceProvider, contextProvider);
  }

  public static SentenceManagementViewModel newInstance(SentenceRepository sentenceRepository,
      DanishTTSService ttsService, Context context) {
    return new SentenceManagementViewModel(sentenceRepository, ttsService, context);
  }
}
