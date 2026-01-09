package com.combocart.ui.home;

import com.combocart.data.repository.NotificationRepository;
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
public final class NotificationViewModel_Factory implements Factory<NotificationViewModel> {
  private final Provider<NotificationRepository> repositoryProvider;

  public NotificationViewModel_Factory(Provider<NotificationRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public NotificationViewModel get() {
    return newInstance(repositoryProvider.get());
  }

  public static NotificationViewModel_Factory create(
      Provider<NotificationRepository> repositoryProvider) {
    return new NotificationViewModel_Factory(repositoryProvider);
  }

  public static NotificationViewModel newInstance(NotificationRepository repository) {
    return new NotificationViewModel(repository);
  }
}
