package com.combocart.di;

import com.combocart.data.remote.OrderApi;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;
import retrofit2.Retrofit;

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
public final class NetworkModule_ProvideOrderApiFactory implements Factory<OrderApi> {
  private final Provider<Retrofit> retrofitProvider;

  public NetworkModule_ProvideOrderApiFactory(Provider<Retrofit> retrofitProvider) {
    this.retrofitProvider = retrofitProvider;
  }

  @Override
  public OrderApi get() {
    return provideOrderApi(retrofitProvider.get());
  }

  public static NetworkModule_ProvideOrderApiFactory create(Provider<Retrofit> retrofitProvider) {
    return new NetworkModule_ProvideOrderApiFactory(retrofitProvider);
  }

  public static OrderApi provideOrderApi(Retrofit retrofit) {
    return Preconditions.checkNotNullFromProvides(NetworkModule.INSTANCE.provideOrderApi(retrofit));
  }
}
