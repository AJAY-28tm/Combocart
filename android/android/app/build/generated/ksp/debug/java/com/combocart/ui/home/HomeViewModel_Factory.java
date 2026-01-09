package com.combocart.ui.home;

import com.combocart.common.SessionManager;
import com.combocart.data.repository.RecipeRepository;
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
public final class HomeViewModel_Factory implements Factory<HomeViewModel> {
  private final Provider<RecipeRepository> repositoryProvider;

  private final Provider<SessionManager> sessionManagerProvider;

  public HomeViewModel_Factory(Provider<RecipeRepository> repositoryProvider,
      Provider<SessionManager> sessionManagerProvider) {
    this.repositoryProvider = repositoryProvider;
    this.sessionManagerProvider = sessionManagerProvider;
  }

  @Override
  public HomeViewModel get() {
    return newInstance(repositoryProvider.get(), sessionManagerProvider.get());
  }

  public static HomeViewModel_Factory create(Provider<RecipeRepository> repositoryProvider,
      Provider<SessionManager> sessionManagerProvider) {
    return new HomeViewModel_Factory(repositoryProvider, sessionManagerProvider);
  }

  public static HomeViewModel newInstance(RecipeRepository repository,
      SessionManager sessionManager) {
    return new HomeViewModel(repository, sessionManager);
  }
}
