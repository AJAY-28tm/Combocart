package com.combocart.data.repository;

import com.combocart.data.local.LocalCartManager;
import com.combocart.data.remote.CartApi;
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
public final class CartRepository_Factory implements Factory<CartRepository> {
  private final Provider<CartApi> cartApiProvider;

  private final Provider<LocalCartManager> localCartManagerProvider;

  public CartRepository_Factory(Provider<CartApi> cartApiProvider,
      Provider<LocalCartManager> localCartManagerProvider) {
    this.cartApiProvider = cartApiProvider;
    this.localCartManagerProvider = localCartManagerProvider;
  }

  @Override
  public CartRepository get() {
    return newInstance(cartApiProvider.get(), localCartManagerProvider.get());
  }

  public static CartRepository_Factory create(Provider<CartApi> cartApiProvider,
      Provider<LocalCartManager> localCartManagerProvider) {
    return new CartRepository_Factory(cartApiProvider, localCartManagerProvider);
  }

  public static CartRepository newInstance(CartApi cartApi, LocalCartManager localCartManager) {
    return new CartRepository(cartApi, localCartManager);
  }
}
