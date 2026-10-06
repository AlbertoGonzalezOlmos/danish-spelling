package com.example.danishspelling.presentation.child;

import com.example.danishspelling.data.repository.PracticeRepository;
import com.example.danishspelling.data.repository.SentenceRepository;
import com.example.danishspelling.data.repository.UserSettingsRepository;
import com.example.danishspelling.service.DanishTTSService;
import com.example.danishspelling.service.RewardSystem;
import com.example.danishspelling.service.SpellingAccuracyCalculator;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
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
public final class PracticeViewModel_Factory implements Factory<PracticeViewModel> {
  private final Provider<SentenceRepository> sentenceRepositoryProvider;

  private final Provider<PracticeRepository> practiceRepositoryProvider;

  private final Provider<UserSettingsRepository> userSettingsRepositoryProvider;

  private final Provider<DanishTTSService> ttsServiceProvider;

  private final Provider<SpellingAccuracyCalculator> spellingCalculatorProvider;

  private final Provider<RewardSystem> rewardSystemProvider;

  public PracticeViewModel_Factory(Provider<SentenceRepository> sentenceRepositoryProvider,
      Provider<PracticeRepository> practiceRepositoryProvider,
      Provider<UserSettingsRepository> userSettingsRepositoryProvider,
      Provider<DanishTTSService> ttsServiceProvider,
      Provider<SpellingAccuracyCalculator> spellingCalculatorProvider,
      Provider<RewardSystem> rewardSystemProvider) {
    this.sentenceRepositoryProvider = sentenceRepositoryProvider;
    this.practiceRepositoryProvider = practiceRepositoryProvider;
    this.userSettingsRepositoryProvider = userSettingsRepositoryProvider;
    this.ttsServiceProvider = ttsServiceProvider;
    this.spellingCalculatorProvider = spellingCalculatorProvider;
    this.rewardSystemProvider = rewardSystemProvider;
  }

  @Override
  public PracticeViewModel get() {
    return newInstance(sentenceRepositoryProvider.get(), practiceRepositoryProvider.get(), userSettingsRepositoryProvider.get(), ttsServiceProvider.get(), spellingCalculatorProvider.get(), rewardSystemProvider.get());
  }

  public static PracticeViewModel_Factory create(
      Provider<SentenceRepository> sentenceRepositoryProvider,
      Provider<PracticeRepository> practiceRepositoryProvider,
      Provider<UserSettingsRepository> userSettingsRepositoryProvider,
      Provider<DanishTTSService> ttsServiceProvider,
      Provider<SpellingAccuracyCalculator> spellingCalculatorProvider,
      Provider<RewardSystem> rewardSystemProvider) {
    return new PracticeViewModel_Factory(sentenceRepositoryProvider, practiceRepositoryProvider, userSettingsRepositoryProvider, ttsServiceProvider, spellingCalculatorProvider, rewardSystemProvider);
  }

  public static PracticeViewModel newInstance(SentenceRepository sentenceRepository,
      PracticeRepository practiceRepository, UserSettingsRepository userSettingsRepository,
      DanishTTSService ttsService, SpellingAccuracyCalculator spellingCalculator,
      RewardSystem rewardSystem) {
    return new PracticeViewModel(sentenceRepository, practiceRepository, userSettingsRepository, ttsService, spellingCalculator, rewardSystem);
  }
}
