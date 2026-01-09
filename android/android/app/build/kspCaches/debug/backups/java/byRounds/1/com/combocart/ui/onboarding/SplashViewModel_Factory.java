package com.combocart.ui.onboarding;

import com.combocart.common.SessionManager;
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
public final class SplashViewModel_Factory implements Factory<SplashViewModel> {
  private final Provider<SessionManager> sessionManagerProvider;

  public SplashViewModel_Factory(Provider<SessionManager> sessionManagerProvider) {
    this.sessionManagerProvider = sessionManagerProvider;
  }

  @Override
  public SplashViewModel get() {
    return newInstance(sessionManagerProvider.get());
  }

  public static SplashViewModel_Factory create(Provider<SessionManager> sessionManagerProvider) {
    return new SplashViewModel_Factory(sessionManagerProvider);
  }

  public static SplashViewModel newInstance(SessionManager sessionManager) {
    return new SplashViewModel(sessionManager);
  }
}
