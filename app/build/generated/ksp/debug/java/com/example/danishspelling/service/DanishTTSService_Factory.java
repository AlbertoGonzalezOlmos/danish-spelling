package com.example.danishspelling.service;

import android.content.Context;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
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
public final class DanishTTSService_Factory implements Factory<DanishTTSService> {
  private final Provider<Context> contextProvider;

  public DanishTTSService_Factory(Provider<Context> contextProvider) {
    this.contextProvider = contextProvider;
  }

  @Override
  public DanishTTSService get() {
    return newInstance(contextProvider.get());
  }

  public static DanishTTSService_Factory create(Provider<Context> contextProvider) {
    return new DanishTTSService_Factory(contextProvider);
  }

  public static DanishTTSService newInstance(Context context) {
    return new DanishTTSService(context);
  }
}
