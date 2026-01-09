package com.combocart.ui.checkout;

import com.combocart.data.local.LocalCartManager;
import com.combocart.data.repository.CartRepository;
import com.combocart.data.repository.OrderRepository;
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
public final class CheckoutViewModel_Factory implements Factory<CheckoutViewModel> {
  private final Provider<OrderRepository> repositoryProvider;

  private final Provider<CartRepository> cartRepositoryProvider;

  private final Provider<LocalCartManager> localCartManagerProvider;

  public CheckoutViewModel_Factory(Provider<OrderRepository> repositoryProvider,
      Provider<CartRepository> cartRepositoryProvider,
      Provider<LocalCartManager> localCartManagerProvider) {
    this.repositoryProvider = repositoryProvider;
    this.cartRepositoryProvider = cartRepositoryProvider;
    this.localCartManagerProvider = localCartManagerProvider;
  }

  @Override
  public CheckoutViewModel get() {
    return newInstance(repositoryProvider.get(), cartRepositoryProvider.get(), localCartManagerProvider.get());
  }

  public static CheckoutViewModel_Factory create(Provider<OrderRepository> repositoryProvider,
      Provider<CartRepository> cartRepositoryProvider,
      Provider<LocalCartManager> localCartManagerProvider) {
    return new CheckoutViewModel_Factory(repositoryProvider, cartRepositoryProvider, localCartManagerProvider);
  }

  public static CheckoutViewModel newInstance(OrderRepository repository,
      CartRepository cartRepository, LocalCartManager localCartManager) {
    return new CheckoutViewModel(repository, cartRepository, localCartManager);
  }
}
