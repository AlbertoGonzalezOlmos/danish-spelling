package com.example.danishspelling.service;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
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
public final class SpellingAccuracyCalculator_Factory implements Factory<SpellingAccuracyCalculator> {
  @Override
  public SpellingAccuracyCalculator get() {
    return newInstance();
  }

  public static SpellingAccuracyCalculator_Factory create() {
    return InstanceHolder.INSTANCE;
  }

  public static SpellingAccuracyCalculator newInstance() {
    return new SpellingAccuracyCalculator();
  }

  private static final class InstanceHolder {
    private static final SpellingAccuracyCalculator_Factory INSTANCE = new SpellingAccuracyCalculator_Factory();
  }
}
