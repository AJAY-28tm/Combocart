package com.combocart.di;

import com.combocart.data.remote.RecipeApi;
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
public final class NetworkModule_ProvideRecipeApiFactory implements Factory<RecipeApi> {
  private final Provider<Retrofit> retrofitProvider;

  public NetworkModule_ProvideRecipeApiFactory(Provider<Retrofit> retrofitProvider) {
    this.retrofitProvider = retrofitProvider;
  }

  @Override
  public RecipeApi get() {
    return provideRecipeApi(retrofitProvider.get());
  }

  public static NetworkModule_ProvideRecipeApiFactory create(Provider<Retrofit> retrofitProvider) {
    return new NetworkModule_ProvideRecipeApiFactory(retrofitProvider);
  }

  public static RecipeApi provideRecipeApi(Retrofit retrofit) {
    return Preconditions.checkNotNullFromProvides(NetworkModule.INSTANCE.provideRecipeApi(retrofit));
  }
}
