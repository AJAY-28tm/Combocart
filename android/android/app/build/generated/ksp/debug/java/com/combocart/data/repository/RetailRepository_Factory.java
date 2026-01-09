package com.combocart.data.repository;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

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
public final class RetailRepository_Factory implements Factory<RetailRepository> {
  @Override
  public RetailRepository get() {
    return newInstance();
  }

  public static RetailRepository_Factory create() {
    return InstanceHolder.INSTANCE;
  }

  public static RetailRepository newInstance() {
    return new RetailRepository();
  }

  private static final class InstanceHolder {
    private static final RetailRepository_Factory INSTANCE = new RetailRepository_Factory();
  }
}
