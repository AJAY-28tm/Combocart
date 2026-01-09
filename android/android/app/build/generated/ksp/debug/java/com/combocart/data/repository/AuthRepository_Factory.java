package com.combocart.data.repository;

import com.combocart.data.remote.AuthApi;
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
public final class AuthRepository_Factory implements Factory<AuthRepository> {
  private final Provider<AuthApi> apiProvider;

  public AuthRepository_Factory(Provider<AuthApi> apiProvider) {
    this.apiProvider = apiProvider;
  }

  @Override
  public AuthRepository get() {
    return newInstance(apiProvider.get());
  }

  public static AuthRepository_Factory create(Provider<AuthApi> apiProvider) {
    return new AuthRepository_Factory(apiProvider);
  }

  public static AuthRepository newInstance(AuthApi api) {
    return new AuthRepository(api);
  }
}
