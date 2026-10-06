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
public final class RewardSystem_Factory implements Factory<RewardSystem> {
  @Override
  public RewardSystem get() {
    return newInstance();
  }

  public static RewardSystem_Factory create() {
    return InstanceHolder.INSTANCE;
  }

  public static RewardSystem newInstance() {
    return new RewardSystem();
  }

  private static final class InstanceHolder {
    private static final RewardSystem_Factory INSTANCE = new RewardSystem_Factory();
  }
}
