package com.example.danishspelling.presentation.child;

import com.example.danishspelling.service.DanishTTSService;
import dagger.MembersInjector;
import dagger.internal.DaggerGenerated;
import dagger.internal.InjectedFieldSignature;
import dagger.internal.QualifierMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

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
public final class PracticeFragment_MembersInjector implements MembersInjector<PracticeFragment> {
  private final Provider<DanishTTSService> ttsServiceProvider;

  public PracticeFragment_MembersInjector(Provider<DanishTTSService> ttsServiceProvider) {
    this.ttsServiceProvider = ttsServiceProvider;
  }

  public static MembersInjector<PracticeFragment> create(
      Provider<DanishTTSService> ttsServiceProvider) {
    return new PracticeFragment_MembersInjector(ttsServiceProvider);
  }

  @Override
  public void injectMembers(PracticeFragment instance) {
    injectTtsService(instance, ttsServiceProvider.get());
  }

  @InjectedFieldSignature("com.example.danishspelling.presentation.child.PracticeFragment.ttsService")
  public static void injectTtsService(PracticeFragment instance, DanishTTSService ttsService) {
    instance.ttsService = ttsService;
  }
}
