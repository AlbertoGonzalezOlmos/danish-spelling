package com.example.danishspelling.presentation.parent;

import com.example.danishspelling.service.DanishSpellCheckService;
import com.example.danishspelling.service.ocr.OCREngine;
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
public final class SentenceManagementFragment_MembersInjector implements MembersInjector<SentenceManagementFragment> {
  private final Provider<OCREngine> ocrEngineProvider;

  private final Provider<DanishSpellCheckService> spellCheckServiceProvider;

  public SentenceManagementFragment_MembersInjector(Provider<OCREngine> ocrEngineProvider,
      Provider<DanishSpellCheckService> spellCheckServiceProvider) {
    this.ocrEngineProvider = ocrEngineProvider;
    this.spellCheckServiceProvider = spellCheckServiceProvider;
  }

  public static MembersInjector<SentenceManagementFragment> create(
      Provider<OCREngine> ocrEngineProvider,
      Provider<DanishSpellCheckService> spellCheckServiceProvider) {
    return new SentenceManagementFragment_MembersInjector(ocrEngineProvider, spellCheckServiceProvider);
  }

  @Override
  public void injectMembers(SentenceManagementFragment instance) {
    injectOcrEngine(instance, ocrEngineProvider.get());
    injectSpellCheckService(instance, spellCheckServiceProvider.get());
  }

  @InjectedFieldSignature("com.example.danishspelling.presentation.parent.SentenceManagementFragment.ocrEngine")
  public static void injectOcrEngine(SentenceManagementFragment instance, OCREngine ocrEngine) {
    instance.ocrEngine = ocrEngine;
  }

  @InjectedFieldSignature("com.example.danishspelling.presentation.parent.SentenceManagementFragment.spellCheckService")
  public static void injectSpellCheckService(SentenceManagementFragment instance,
      DanishSpellCheckService spellCheckService) {
    instance.spellCheckService = spellCheckService;
  }
}
