package com.example.danishspelling;

import android.app.Activity;
import android.app.Service;
import android.view.View;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;
import com.example.danishspelling.data.local.SpellingDatabase;
import com.example.danishspelling.data.local.dao.PracticeAttemptDao;
import com.example.danishspelling.data.local.dao.PracticeListDao;
import com.example.danishspelling.data.local.dao.PracticeSessionDao;
import com.example.danishspelling.data.local.dao.SentenceDao;
import com.example.danishspelling.data.local.dao.UserSettingsDao;
import com.example.danishspelling.data.repository.PracticeListRepository;
import com.example.danishspelling.data.repository.PracticeRepository;
import com.example.danishspelling.data.repository.SentenceRepository;
import com.example.danishspelling.data.repository.UserSettingsRepository;
import com.example.danishspelling.di.AppModule_ProvideOCREngineFactory;
import com.example.danishspelling.di.DatabaseModule_ProvidePracticeAttemptDaoFactory;
import com.example.danishspelling.di.DatabaseModule_ProvidePracticeListDaoFactory;
import com.example.danishspelling.di.DatabaseModule_ProvidePracticeSessionDaoFactory;
import com.example.danishspelling.di.DatabaseModule_ProvideSentenceDaoFactory;
import com.example.danishspelling.di.DatabaseModule_ProvideSpellingDatabaseFactory;
import com.example.danishspelling.di.DatabaseModule_ProvideUserSettingsDaoFactory;
import com.example.danishspelling.presentation.MainActivity;
import com.example.danishspelling.presentation.ModeSelectionFragment;
import com.example.danishspelling.presentation.child.ListSelectionFragment;
import com.example.danishspelling.presentation.child.ListSelectionViewModel;
import com.example.danishspelling.presentation.child.ListSelectionViewModel_HiltModules_KeyModule_ProvideFactory;
import com.example.danishspelling.presentation.child.PracticeFragment;
import com.example.danishspelling.presentation.child.PracticeFragment_MembersInjector;
import com.example.danishspelling.presentation.child.PracticeViewModel;
import com.example.danishspelling.presentation.child.PracticeViewModel_HiltModules_KeyModule_ProvideFactory;
import com.example.danishspelling.presentation.child.RewardFragment;
import com.example.danishspelling.presentation.parent.ListManagementFragment;
import com.example.danishspelling.presentation.parent.ListManagementViewModel;
import com.example.danishspelling.presentation.parent.ListManagementViewModel_HiltModules_KeyModule_ProvideFactory;
import com.example.danishspelling.presentation.parent.SentenceManagementFragment;
import com.example.danishspelling.presentation.parent.SentenceManagementFragment_MembersInjector;
import com.example.danishspelling.presentation.parent.SentenceManagementViewModel;
import com.example.danishspelling.presentation.parent.SentenceManagementViewModel_HiltModules_KeyModule_ProvideFactory;
import com.example.danishspelling.service.DanishSpellCheckService;
import com.example.danishspelling.service.DanishTTSService;
import com.example.danishspelling.service.RewardSystem;
import com.example.danishspelling.service.SpellingAccuracyCalculator;
import com.example.danishspelling.service.ocr.OCREngine;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import dagger.hilt.android.ActivityRetainedLifecycle;
import dagger.hilt.android.ViewModelLifecycle;
import dagger.hilt.android.internal.builders.ActivityComponentBuilder;
import dagger.hilt.android.internal.builders.ActivityRetainedComponentBuilder;
import dagger.hilt.android.internal.builders.FragmentComponentBuilder;
import dagger.hilt.android.internal.builders.ServiceComponentBuilder;
import dagger.hilt.android.internal.builders.ViewComponentBuilder;
import dagger.hilt.android.internal.builders.ViewModelComponentBuilder;
import dagger.hilt.android.internal.builders.ViewWithFragmentComponentBuilder;
import dagger.hilt.android.internal.lifecycle.DefaultViewModelFactories;
import dagger.hilt.android.internal.lifecycle.DefaultViewModelFactories_InternalFactoryFactory_Factory;
import dagger.hilt.android.internal.managers.ActivityRetainedComponentManager_LifecycleModule_ProvideActivityRetainedLifecycleFactory;
import dagger.hilt.android.internal.managers.SavedStateHandleHolder;
import dagger.hilt.android.internal.modules.ApplicationContextModule;
import dagger.hilt.android.internal.modules.ApplicationContextModule_ProvideContextFactory;
import dagger.internal.DaggerGenerated;
import dagger.internal.DelegateFactory;
import dagger.internal.DoubleCheck;
import dagger.internal.MapBuilder;
import dagger.internal.Preconditions;
import dagger.internal.Provider;
import dagger.internal.SetBuilder;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;

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
public final class DaggerSpellingApplication_HiltComponents_SingletonC {
  private DaggerSpellingApplication_HiltComponents_SingletonC() {
  }

  public static Builder builder() {
    return new Builder();
  }

  public static final class Builder {
    private ApplicationContextModule applicationContextModule;

    private Builder() {
    }

    public Builder applicationContextModule(ApplicationContextModule applicationContextModule) {
      this.applicationContextModule = Preconditions.checkNotNull(applicationContextModule);
      return this;
    }

    public SpellingApplication_HiltComponents.SingletonC build() {
      Preconditions.checkBuilderRequirement(applicationContextModule, ApplicationContextModule.class);
      return new SingletonCImpl(applicationContextModule);
    }
  }

  private static final class ActivityRetainedCBuilder implements SpellingApplication_HiltComponents.ActivityRetainedC.Builder {
    private final SingletonCImpl singletonCImpl;

    private SavedStateHandleHolder savedStateHandleHolder;

    private ActivityRetainedCBuilder(SingletonCImpl singletonCImpl) {
      this.singletonCImpl = singletonCImpl;
    }

    @Override
    public ActivityRetainedCBuilder savedStateHandleHolder(
        SavedStateHandleHolder savedStateHandleHolder) {
      this.savedStateHandleHolder = Preconditions.checkNotNull(savedStateHandleHolder);
      return this;
    }

    @Override
    public SpellingApplication_HiltComponents.ActivityRetainedC build() {
      Preconditions.checkBuilderRequirement(savedStateHandleHolder, SavedStateHandleHolder.class);
      return new ActivityRetainedCImpl(singletonCImpl, savedStateHandleHolder);
    }
  }

  private static final class ActivityCBuilder implements SpellingApplication_HiltComponents.ActivityC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private Activity activity;

    private ActivityCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
    }

    @Override
    public ActivityCBuilder activity(Activity activity) {
      this.activity = Preconditions.checkNotNull(activity);
      return this;
    }

    @Override
    public SpellingApplication_HiltComponents.ActivityC build() {
      Preconditions.checkBuilderRequirement(activity, Activity.class);
      return new ActivityCImpl(singletonCImpl, activityRetainedCImpl, activity);
    }
  }

  private static final class FragmentCBuilder implements SpellingApplication_HiltComponents.FragmentC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private Fragment fragment;

    private FragmentCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
    }

    @Override
    public FragmentCBuilder fragment(Fragment fragment) {
      this.fragment = Preconditions.checkNotNull(fragment);
      return this;
    }

    @Override
    public SpellingApplication_HiltComponents.FragmentC build() {
      Preconditions.checkBuilderRequirement(fragment, Fragment.class);
      return new FragmentCImpl(singletonCImpl, activityRetainedCImpl, activityCImpl, fragment);
    }
  }

  private static final class ViewWithFragmentCBuilder implements SpellingApplication_HiltComponents.ViewWithFragmentC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final FragmentCImpl fragmentCImpl;

    private View view;

    private ViewWithFragmentCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl,
        FragmentCImpl fragmentCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
      this.fragmentCImpl = fragmentCImpl;
    }

    @Override
    public ViewWithFragmentCBuilder view(View view) {
      this.view = Preconditions.checkNotNull(view);
      return this;
    }

    @Override
    public SpellingApplication_HiltComponents.ViewWithFragmentC build() {
      Preconditions.checkBuilderRequirement(view, View.class);
      return new ViewWithFragmentCImpl(singletonCImpl, activityRetainedCImpl, activityCImpl, fragmentCImpl, view);
    }
  }

  private static final class ViewCBuilder implements SpellingApplication_HiltComponents.ViewC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private View view;

    private ViewCBuilder(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
        ActivityCImpl activityCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
    }

    @Override
    public ViewCBuilder view(View view) {
      this.view = Preconditions.checkNotNull(view);
      return this;
    }

    @Override
    public SpellingApplication_HiltComponents.ViewC build() {
      Preconditions.checkBuilderRequirement(view, View.class);
      return new ViewCImpl(singletonCImpl, activityRetainedCImpl, activityCImpl, view);
    }
  }

  private static final class ViewModelCBuilder implements SpellingApplication_HiltComponents.ViewModelC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private SavedStateHandle savedStateHandle;

    private ViewModelLifecycle viewModelLifecycle;

    private ViewModelCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
    }

    @Override
    public ViewModelCBuilder savedStateHandle(SavedStateHandle handle) {
      this.savedStateHandle = Preconditions.checkNotNull(handle);
      return this;
    }

    @Override
    public ViewModelCBuilder viewModelLifecycle(ViewModelLifecycle viewModelLifecycle) {
      this.viewModelLifecycle = Preconditions.checkNotNull(viewModelLifecycle);
      return this;
    }

    @Override
    public SpellingApplication_HiltComponents.ViewModelC build() {
      Preconditions.checkBuilderRequirement(savedStateHandle, SavedStateHandle.class);
      Preconditions.checkBuilderRequirement(viewModelLifecycle, ViewModelLifecycle.class);
      return new ViewModelCImpl(singletonCImpl, activityRetainedCImpl, savedStateHandle, viewModelLifecycle);
    }
  }

  private static final class ServiceCBuilder implements SpellingApplication_HiltComponents.ServiceC.Builder {
    private final SingletonCImpl singletonCImpl;

    private Service service;

    private ServiceCBuilder(SingletonCImpl singletonCImpl) {
      this.singletonCImpl = singletonCImpl;
    }

    @Override
    public ServiceCBuilder service(Service service) {
      this.service = Preconditions.checkNotNull(service);
      return this;
    }

    @Override
    public SpellingApplication_HiltComponents.ServiceC build() {
      Preconditions.checkBuilderRequirement(service, Service.class);
      return new ServiceCImpl(singletonCImpl, service);
    }
  }

  private static final class ViewWithFragmentCImpl extends SpellingApplication_HiltComponents.ViewWithFragmentC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final FragmentCImpl fragmentCImpl;

    private final ViewWithFragmentCImpl viewWithFragmentCImpl = this;

    private ViewWithFragmentCImpl(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl,
        FragmentCImpl fragmentCImpl, View viewParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
      this.fragmentCImpl = fragmentCImpl;


    }
  }

  private static final class FragmentCImpl extends SpellingApplication_HiltComponents.FragmentC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final FragmentCImpl fragmentCImpl = this;

    private FragmentCImpl(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl,
        Fragment fragmentParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;


    }

    @Override
    public void injectModeSelectionFragment(ModeSelectionFragment modeSelectionFragment) {
    }

    @Override
    public void injectListSelectionFragment(ListSelectionFragment listSelectionFragment) {
    }

    @Override
    public void injectPracticeFragment(PracticeFragment practiceFragment) {
      injectPracticeFragment2(practiceFragment);
    }

    @Override
    public void injectRewardFragment(RewardFragment rewardFragment) {
    }

    @Override
    public void injectListManagementFragment(ListManagementFragment listManagementFragment) {
    }

    @Override
    public void injectSentenceManagementFragment(
        SentenceManagementFragment sentenceManagementFragment) {
      injectSentenceManagementFragment2(sentenceManagementFragment);
    }

    @Override
    public DefaultViewModelFactories.InternalFactoryFactory getHiltInternalFactoryFactory() {
      return activityCImpl.getHiltInternalFactoryFactory();
    }

    @Override
    public ViewWithFragmentComponentBuilder viewWithFragmentComponentBuilder() {
      return new ViewWithFragmentCBuilder(singletonCImpl, activityRetainedCImpl, activityCImpl, fragmentCImpl);
    }

    @CanIgnoreReturnValue
    private PracticeFragment injectPracticeFragment2(PracticeFragment instance) {
      PracticeFragment_MembersInjector.injectTtsService(instance, singletonCImpl.danishTTSServiceProvider.get());
      return instance;
    }

    @CanIgnoreReturnValue
    private SentenceManagementFragment injectSentenceManagementFragment2(
        SentenceManagementFragment instance) {
      SentenceManagementFragment_MembersInjector.injectOcrEngine(instance, singletonCImpl.provideOCREngineProvider.get());
      SentenceManagementFragment_MembersInjector.injectSpellCheckService(instance, singletonCImpl.danishSpellCheckServiceProvider.get());
      return instance;
    }
  }

  private static final class ViewCImpl extends SpellingApplication_HiltComponents.ViewC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final ViewCImpl viewCImpl = this;

    private ViewCImpl(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
        ActivityCImpl activityCImpl, View viewParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;


    }
  }

  private static final class ActivityCImpl extends SpellingApplication_HiltComponents.ActivityC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl = this;

    private ActivityCImpl(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, Activity activityParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;


    }

    @Override
    public void injectMainActivity(MainActivity mainActivity) {
    }

    @Override
    public DefaultViewModelFactories.InternalFactoryFactory getHiltInternalFactoryFactory() {
      return DefaultViewModelFactories_InternalFactoryFactory_Factory.newInstance(getViewModelKeys(), new ViewModelCBuilder(singletonCImpl, activityRetainedCImpl));
    }

    @Override
    public Set<String> getViewModelKeys() {
      return SetBuilder.<String>newSetBuilder(4).add(ListManagementViewModel_HiltModules_KeyModule_ProvideFactory.provide()).add(ListSelectionViewModel_HiltModules_KeyModule_ProvideFactory.provide()).add(PracticeViewModel_HiltModules_KeyModule_ProvideFactory.provide()).add(SentenceManagementViewModel_HiltModules_KeyModule_ProvideFactory.provide()).build();
    }

    @Override
    public ViewModelComponentBuilder getViewModelComponentBuilder() {
      return new ViewModelCBuilder(singletonCImpl, activityRetainedCImpl);
    }

    @Override
    public FragmentComponentBuilder fragmentComponentBuilder() {
      return new FragmentCBuilder(singletonCImpl, activityRetainedCImpl, activityCImpl);
    }

    @Override
    public ViewComponentBuilder viewComponentBuilder() {
      return new ViewCBuilder(singletonCImpl, activityRetainedCImpl, activityCImpl);
    }
  }

  private static final class ViewModelCImpl extends SpellingApplication_HiltComponents.ViewModelC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ViewModelCImpl viewModelCImpl = this;

    private Provider<ListManagementViewModel> listManagementViewModelProvider;

    private Provider<ListSelectionViewModel> listSelectionViewModelProvider;

    private Provider<PracticeViewModel> practiceViewModelProvider;

    private Provider<SentenceManagementViewModel> sentenceManagementViewModelProvider;

    private ViewModelCImpl(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, SavedStateHandle savedStateHandleParam,
        ViewModelLifecycle viewModelLifecycleParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;

      initialize(savedStateHandleParam, viewModelLifecycleParam);

    }

    @SuppressWarnings("unchecked")
    private void initialize(final SavedStateHandle savedStateHandleParam,
        final ViewModelLifecycle viewModelLifecycleParam) {
      this.listManagementViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 0);
      this.listSelectionViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 1);
      this.practiceViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 2);
      this.sentenceManagementViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 3);
    }

    @Override
    public Map<String, javax.inject.Provider<ViewModel>> getHiltViewModelMap() {
      return MapBuilder.<String, javax.inject.Provider<ViewModel>>newMapBuilder(4).put("com.example.danishspelling.presentation.parent.ListManagementViewModel", ((Provider) listManagementViewModelProvider)).put("com.example.danishspelling.presentation.child.ListSelectionViewModel", ((Provider) listSelectionViewModelProvider)).put("com.example.danishspelling.presentation.child.PracticeViewModel", ((Provider) practiceViewModelProvider)).put("com.example.danishspelling.presentation.parent.SentenceManagementViewModel", ((Provider) sentenceManagementViewModelProvider)).build();
    }

    @Override
    public Map<String, Object> getHiltViewModelAssistedMap() {
      return Collections.<String, Object>emptyMap();
    }

    private static final class SwitchingProvider<T> implements Provider<T> {
      private final SingletonCImpl singletonCImpl;

      private final ActivityRetainedCImpl activityRetainedCImpl;

      private final ViewModelCImpl viewModelCImpl;

      private final int id;

      SwitchingProvider(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
          ViewModelCImpl viewModelCImpl, int id) {
        this.singletonCImpl = singletonCImpl;
        this.activityRetainedCImpl = activityRetainedCImpl;
        this.viewModelCImpl = viewModelCImpl;
        this.id = id;
      }

      @SuppressWarnings("unchecked")
      @Override
      public T get() {
        switch (id) {
          case 0: // com.example.danishspelling.presentation.parent.ListManagementViewModel 
          return (T) new ListManagementViewModel(singletonCImpl.practiceListRepositoryProvider.get());

          case 1: // com.example.danishspelling.presentation.child.ListSelectionViewModel 
          return (T) new ListSelectionViewModel(singletonCImpl.practiceListRepositoryProvider.get(), singletonCImpl.sentenceRepositoryProvider.get());

          case 2: // com.example.danishspelling.presentation.child.PracticeViewModel 
          return (T) new PracticeViewModel(singletonCImpl.sentenceRepositoryProvider.get(), singletonCImpl.practiceRepositoryProvider.get(), singletonCImpl.userSettingsRepositoryProvider.get(), singletonCImpl.danishTTSServiceProvider.get(), singletonCImpl.spellingAccuracyCalculatorProvider.get(), singletonCImpl.rewardSystemProvider.get());

          case 3: // com.example.danishspelling.presentation.parent.SentenceManagementViewModel 
          return (T) new SentenceManagementViewModel(singletonCImpl.sentenceRepositoryProvider.get(), singletonCImpl.danishTTSServiceProvider.get(), ApplicationContextModule_ProvideContextFactory.provideContext(singletonCImpl.applicationContextModule));

          default: throw new AssertionError(id);
        }
      }
    }
  }

  private static final class ActivityRetainedCImpl extends SpellingApplication_HiltComponents.ActivityRetainedC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl = this;

    private Provider<ActivityRetainedLifecycle> provideActivityRetainedLifecycleProvider;

    private ActivityRetainedCImpl(SingletonCImpl singletonCImpl,
        SavedStateHandleHolder savedStateHandleHolderParam) {
      this.singletonCImpl = singletonCImpl;

      initialize(savedStateHandleHolderParam);

    }

    @SuppressWarnings("unchecked")
    private void initialize(final SavedStateHandleHolder savedStateHandleHolderParam) {
      this.provideActivityRetainedLifecycleProvider = DoubleCheck.provider(new SwitchingProvider<ActivityRetainedLifecycle>(singletonCImpl, activityRetainedCImpl, 0));
    }

    @Override
    public ActivityComponentBuilder activityComponentBuilder() {
      return new ActivityCBuilder(singletonCImpl, activityRetainedCImpl);
    }

    @Override
    public ActivityRetainedLifecycle getActivityRetainedLifecycle() {
      return provideActivityRetainedLifecycleProvider.get();
    }

    private static final class SwitchingProvider<T> implements Provider<T> {
      private final SingletonCImpl singletonCImpl;

      private final ActivityRetainedCImpl activityRetainedCImpl;

      private final int id;

      SwitchingProvider(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
          int id) {
        this.singletonCImpl = singletonCImpl;
        this.activityRetainedCImpl = activityRetainedCImpl;
        this.id = id;
      }

      @SuppressWarnings("unchecked")
      @Override
      public T get() {
        switch (id) {
          case 0: // dagger.hilt.android.ActivityRetainedLifecycle 
          return (T) ActivityRetainedComponentManager_LifecycleModule_ProvideActivityRetainedLifecycleFactory.provideActivityRetainedLifecycle();

          default: throw new AssertionError(id);
        }
      }
    }
  }

  private static final class ServiceCImpl extends SpellingApplication_HiltComponents.ServiceC {
    private final SingletonCImpl singletonCImpl;

    private final ServiceCImpl serviceCImpl = this;

    private ServiceCImpl(SingletonCImpl singletonCImpl, Service serviceParam) {
      this.singletonCImpl = singletonCImpl;


    }
  }

  private static final class SingletonCImpl extends SpellingApplication_HiltComponents.SingletonC {
    private final ApplicationContextModule applicationContextModule;

    private final SingletonCImpl singletonCImpl = this;

    private Provider<DanishTTSService> danishTTSServiceProvider;

    private Provider<OCREngine> provideOCREngineProvider;

    private Provider<DanishSpellCheckService> danishSpellCheckServiceProvider;

    private Provider<PracticeListDao> providePracticeListDaoProvider;

    private Provider<SpellingDatabase> provideSpellingDatabaseProvider;

    private Provider<SentenceDao> provideSentenceDaoProvider;

    private Provider<UserSettingsDao> provideUserSettingsDaoProvider;

    private Provider<PracticeListRepository> practiceListRepositoryProvider;

    private Provider<SentenceRepository> sentenceRepositoryProvider;

    private Provider<PracticeRepository> practiceRepositoryProvider;

    private Provider<UserSettingsRepository> userSettingsRepositoryProvider;

    private Provider<SpellingAccuracyCalculator> spellingAccuracyCalculatorProvider;

    private Provider<RewardSystem> rewardSystemProvider;

    private SingletonCImpl(ApplicationContextModule applicationContextModuleParam) {
      this.applicationContextModule = applicationContextModuleParam;
      initialize(applicationContextModuleParam);

    }

    private PracticeSessionDao practiceSessionDao() {
      return DatabaseModule_ProvidePracticeSessionDaoFactory.providePracticeSessionDao(provideSpellingDatabaseProvider.get());
    }

    private PracticeAttemptDao practiceAttemptDao() {
      return DatabaseModule_ProvidePracticeAttemptDaoFactory.providePracticeAttemptDao(provideSpellingDatabaseProvider.get());
    }

    @SuppressWarnings("unchecked")
    private void initialize(final ApplicationContextModule applicationContextModuleParam) {
      this.danishTTSServiceProvider = DoubleCheck.provider(new SwitchingProvider<DanishTTSService>(singletonCImpl, 0));
      this.provideOCREngineProvider = DoubleCheck.provider(new SwitchingProvider<OCREngine>(singletonCImpl, 1));
      this.danishSpellCheckServiceProvider = DoubleCheck.provider(new SwitchingProvider<DanishSpellCheckService>(singletonCImpl, 2));
      this.providePracticeListDaoProvider = new DelegateFactory<>();
      this.provideSpellingDatabaseProvider = new DelegateFactory<>();
      this.provideSentenceDaoProvider = new SwitchingProvider<>(singletonCImpl, 6);
      this.provideUserSettingsDaoProvider = new SwitchingProvider<>(singletonCImpl, 7);
      DelegateFactory.setDelegate(provideSpellingDatabaseProvider, DoubleCheck.provider(new SwitchingProvider<SpellingDatabase>(singletonCImpl, 5)));
      DelegateFactory.setDelegate(providePracticeListDaoProvider, new SwitchingProvider<>(singletonCImpl, 4));
      this.practiceListRepositoryProvider = DoubleCheck.provider(new SwitchingProvider<PracticeListRepository>(singletonCImpl, 3));
      this.sentenceRepositoryProvider = DoubleCheck.provider(new SwitchingProvider<SentenceRepository>(singletonCImpl, 8));
      this.practiceRepositoryProvider = DoubleCheck.provider(new SwitchingProvider<PracticeRepository>(singletonCImpl, 9));
      this.userSettingsRepositoryProvider = DoubleCheck.provider(new SwitchingProvider<UserSettingsRepository>(singletonCImpl, 10));
      this.spellingAccuracyCalculatorProvider = DoubleCheck.provider(new SwitchingProvider<SpellingAccuracyCalculator>(singletonCImpl, 11));
      this.rewardSystemProvider = DoubleCheck.provider(new SwitchingProvider<RewardSystem>(singletonCImpl, 12));
    }

    @Override
    public void injectSpellingApplication(SpellingApplication spellingApplication) {
    }

    @Override
    public Set<Boolean> getDisableFragmentGetContextFix() {
      return Collections.<Boolean>emptySet();
    }

    @Override
    public ActivityRetainedComponentBuilder retainedComponentBuilder() {
      return new ActivityRetainedCBuilder(singletonCImpl);
    }

    @Override
    public ServiceComponentBuilder serviceComponentBuilder() {
      return new ServiceCBuilder(singletonCImpl);
    }

    private static final class SwitchingProvider<T> implements Provider<T> {
      private final SingletonCImpl singletonCImpl;

      private final int id;

      SwitchingProvider(SingletonCImpl singletonCImpl, int id) {
        this.singletonCImpl = singletonCImpl;
        this.id = id;
      }

      @SuppressWarnings("unchecked")
      @Override
      public T get() {
        switch (id) {
          case 0: // com.example.danishspelling.service.DanishTTSService 
          return (T) new DanishTTSService(ApplicationContextModule_ProvideContextFactory.provideContext(singletonCImpl.applicationContextModule));

          case 1: // com.example.danishspelling.service.ocr.OCREngine 
          return (T) AppModule_ProvideOCREngineFactory.provideOCREngine();

          case 2: // com.example.danishspelling.service.DanishSpellCheckService 
          return (T) new DanishSpellCheckService(ApplicationContextModule_ProvideContextFactory.provideContext(singletonCImpl.applicationContextModule));

          case 3: // com.example.danishspelling.data.repository.PracticeListRepository 
          return (T) new PracticeListRepository(singletonCImpl.providePracticeListDaoProvider.get());

          case 4: // com.example.danishspelling.data.local.dao.PracticeListDao 
          return (T) DatabaseModule_ProvidePracticeListDaoFactory.providePracticeListDao(singletonCImpl.provideSpellingDatabaseProvider.get());

          case 5: // com.example.danishspelling.data.local.SpellingDatabase 
          return (T) DatabaseModule_ProvideSpellingDatabaseFactory.provideSpellingDatabase(ApplicationContextModule_ProvideContextFactory.provideContext(singletonCImpl.applicationContextModule), singletonCImpl.providePracticeListDaoProvider, singletonCImpl.provideSentenceDaoProvider, singletonCImpl.provideUserSettingsDaoProvider);

          case 6: // com.example.danishspelling.data.local.dao.SentenceDao 
          return (T) DatabaseModule_ProvideSentenceDaoFactory.provideSentenceDao(singletonCImpl.provideSpellingDatabaseProvider.get());

          case 7: // com.example.danishspelling.data.local.dao.UserSettingsDao 
          return (T) DatabaseModule_ProvideUserSettingsDaoFactory.provideUserSettingsDao(singletonCImpl.provideSpellingDatabaseProvider.get());

          case 8: // com.example.danishspelling.data.repository.SentenceRepository 
          return (T) new SentenceRepository(singletonCImpl.provideSentenceDaoProvider.get());

          case 9: // com.example.danishspelling.data.repository.PracticeRepository 
          return (T) new PracticeRepository(singletonCImpl.practiceSessionDao(), singletonCImpl.practiceAttemptDao());

          case 10: // com.example.danishspelling.data.repository.UserSettingsRepository 
          return (T) new UserSettingsRepository(singletonCImpl.provideUserSettingsDaoProvider.get());

          case 11: // com.example.danishspelling.service.SpellingAccuracyCalculator 
          return (T) new SpellingAccuracyCalculator();

          case 12: // com.example.danishspelling.service.RewardSystem 
          return (T) new RewardSystem();

          default: throw new AssertionError(id);
        }
      }
    }
  }
}
