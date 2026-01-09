package com.combocart.ui.setup;

import android.content.Context;
import com.combocart.common.SessionManager;
import com.google.android.gms.location.FusedLocationProviderClient;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
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
public final class LocationViewModel_Factory implements Factory<LocationViewModel> {
  private final Provider<FusedLocationProviderClient> fusedLocationClientProvider;

  private final Provider<SessionManager> sessionManagerProvider;

  private final Provider<Context> contextProvider;

  public LocationViewModel_Factory(
      Provider<FusedLocationProviderClient> fusedLocationClientProvider,
      Provider<SessionManager> sessionManagerProvider, Provider<Context> contextProvider) {
    this.fusedLocationClientProvider = fusedLocationClientProvider;
    this.sessionManagerProvider = sessionManagerProvider;
    this.contextProvider = contextProvider;
  }

  @Override
  public LocationViewModel get() {
    return newInstance(fusedLocationClientProvider.get(), sessionManagerProvider.get(), contextProvider.get());
  }

  public static LocationViewModel_Factory create(
      Provider<FusedLocationProviderClient> fusedLocationClientProvider,
      Provider<SessionManager> sessionManagerProvider, Provider<Context> contextProvider) {
    return new LocationViewModel_Factory(fusedLocationClientProvider, sessionManagerProvider, contextProvider);
  }

  public static LocationViewModel newInstance(FusedLocationProviderClient fusedLocationClient,
      SessionManager sessionManager, Context context) {
    return new LocationViewModel(fusedLocationClient, sessionManager, context);
  }
}
