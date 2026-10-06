package com.example.danishspelling.di;

import com.example.danishspelling.service.ocr.OCREngine;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

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
public final class AppModule_ProvideOCREngineFactory implements Factory<OCREngine> {
  @Override
  public OCREngine get() {
    return provideOCREngine();
  }

  public static AppModule_ProvideOCREngineFactory create() {
    return InstanceHolder.INSTANCE;
  }

  public static OCREngine provideOCREngine() {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideOCREngine());
  }

  private static final class InstanceHolder {
    private static final AppModule_ProvideOCREngineFactory INSTANCE = new AppModule_ProvideOCREngineFactory();
  }
}
