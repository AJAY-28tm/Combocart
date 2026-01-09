package com.combocart.ui.home;

import com.combocart.data.repository.RetailRepository;
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
public final class RetailViewModel_Factory implements Factory<RetailViewModel> {
  private final Provider<RetailRepository> repositoryProvider;

  public RetailViewModel_Factory(Provider<RetailRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public RetailViewModel get() {
    return newInstance(repositoryProvider.get());
  }

  public static RetailViewModel_Factory create(Provider<RetailRepository> repositoryProvider) {
    return new RetailViewModel_Factory(repositoryProvider);
  }

  public static RetailViewModel newInstance(RetailRepository repository) {
    return new RetailViewModel(repository);
  }
}
