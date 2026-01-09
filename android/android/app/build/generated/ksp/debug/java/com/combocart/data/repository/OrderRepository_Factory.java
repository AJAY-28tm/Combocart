package com.combocart.data.repository;

import com.combocart.data.remote.OrderApi;
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
public final class OrderRepository_Factory implements Factory<OrderRepository> {
  private final Provider<OrderApi> orderApiProvider;

  public OrderRepository_Factory(Provider<OrderApi> orderApiProvider) {
    this.orderApiProvider = orderApiProvider;
  }

  @Override
  public OrderRepository get() {
    return newInstance(orderApiProvider.get());
  }

  public static OrderRepository_Factory create(Provider<OrderApi> orderApiProvider) {
    return new OrderRepository_Factory(orderApiProvider);
  }

  public static OrderRepository newInstance(OrderApi orderApi) {
    return new OrderRepository(orderApi);
  }
}
