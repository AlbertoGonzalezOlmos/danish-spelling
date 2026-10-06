package com.example.danishspelling.di;

import android.content.Context;
import com.example.danishspelling.service.tts.TTSEngine;
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
public final class AppModule_ProvideTTSEngineFactory implements Factory<TTSEngine> {
  private final Provider<Context> contextProvider;

  public AppModule_ProvideTTSEngineFactory(Provider<Context> contextProvider) {
    this.contextProvider = contextProvider;
  }

  @Override
  public TTSEngine get() {
    return provideTTSEngine(contextProvider.get());
  }

  public static AppModule_ProvideTTSEngineFactory create(Provider<Context> contextProvider) {
    return new AppModule_ProvideTTSEngineFactory(contextProvider);
  }

  public static TTSEngine provideTTSEngine(Context context) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideTTSEngine(context));
  }
}
