package com.combocart.ui.auth;

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
public final class AuthViewModel_Factory implements Factory<AuthViewModel> {
  private final Provider<SessionManager> sessionManagerProvider;

  public AuthViewModel_Factory(Provider<SessionManager> sessionManagerProvider) {
    this.sessionManagerProvider = sessionManagerProvider;
  }

  @Override
  public AuthViewModel get() {
    return newInstance(sessionManagerProvider.get());
  }

  public static AuthViewModel_Factory create(Provider<SessionManager> sessionManagerProvider) {
    return new AuthViewModel_Factory(sessionManagerProvider);
  }

  public static AuthViewModel newInstance(SessionManager sessionManager) {
    return new AuthViewModel(sessionManager);
  }
}
