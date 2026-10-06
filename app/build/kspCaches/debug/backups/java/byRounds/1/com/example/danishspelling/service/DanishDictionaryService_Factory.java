package com.example.danishspelling.service;

import android.content.Context;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;
import kotlinx.coroutines.CoroutineDispatcher;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata({
    "dagger.hilt.android.qualifiers.ApplicationContext",
    "com.example.danishspelling.di.IoDispatcher"
})
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
public final class DanishDictionaryService_Factory implements Factory<DanishDictionaryService> {
  private final Provider<Context> contextProvider;

  private final Provider<CoroutineDispatcher> ioDispatcherProvider;

  public DanishDictionaryService_Factory(Provider<Context> contextProvider,
      Provider<CoroutineDispatcher> ioDispatcherProvider) {
    this.contextProvider = contextProvider;
    this.ioDispatcherProvider = ioDispatcherProvider;
  }

  @Override
  public DanishDictionaryService get() {
    return newInstance(contextProvider.get(), ioDispatcherProvider.get());
  }

  public static DanishDictionaryService_Factory create(Provider<Context> contextProvider,
      Provider<CoroutineDispatcher> ioDispatcherProvider) {
    return new DanishDictionaryService_Factory(contextProvider, ioDispatcherProvider);
  }

  public static DanishDictionaryService newInstance(Context context,
      CoroutineDispatcher ioDispatcher) {
    return new DanishDictionaryService(context, ioDispatcher);
  }
}
