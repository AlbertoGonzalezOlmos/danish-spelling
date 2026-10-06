package com.example.danishspelling.data.repository;

import com.example.danishspelling.data.local.dao.SentenceDao;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

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
public final class SentenceRepository_Factory implements Factory<SentenceRepository> {
  private final Provider<SentenceDao> sentenceDaoProvider;

  public SentenceRepository_Factory(Provider<SentenceDao> sentenceDaoProvider) {
    this.sentenceDaoProvider = sentenceDaoProvider;
  }

  @Override
  public SentenceRepository get() {
    return newInstance(sentenceDaoProvider.get());
  }

  public static SentenceRepository_Factory create(Provider<SentenceDao> sentenceDaoProvider) {
    return new SentenceRepository_Factory(sentenceDaoProvider);
  }

  public static SentenceRepository newInstance(SentenceDao sentenceDao) {
    return new SentenceRepository(sentenceDao);
  }
}
