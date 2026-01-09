package com.combocart.ui.orders;

import androidx.lifecycle.SavedStateHandle;
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
public final class OrdersViewModel_Factory implements Factory<OrdersViewModel> {
  private final Provider<OrderRepository> repositoryProvider;

  private final Provider<SavedStateHandle> savedStateHandleProvider;

  public OrdersViewModel_Factory(Provider<OrderRepository> repositoryProvider,
      Provider<SavedStateHandle> savedStateHandleProvider) {
    this.repositoryProvider = repositoryProvider;
    this.savedStateHandleProvider = savedStateHandleProvider;
  }

  @Override
  public OrdersViewModel get() {
    return newInstance(repositoryProvider.get(), savedStateHandleProvider.get());
  }

  public static OrdersViewModel_Factory create(Provider<OrderRepository> repositoryProvider,
      Provider<SavedStateHandle> savedStateHandleProvider) {
    return new OrdersViewModel_Factory(repositoryProvider, savedStateHandleProvider);
  }

  public static OrdersViewModel newInstance(OrderRepository repository,
      SavedStateHandle savedStateHandle) {
    return new OrdersViewModel(repository, savedStateHandle);
  }
}
