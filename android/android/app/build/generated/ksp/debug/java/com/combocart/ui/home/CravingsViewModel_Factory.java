package com.combocart.ui.home;

import androidx.lifecycle.SavedStateHandle;
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
public final class CravingsViewModel_Factory implements Factory<CravingsViewModel> {
  private final Provider<RecipeRepository> repositoryProvider;

  private final Provider<SavedStateHandle> savedStateHandleProvider;

  public CravingsViewModel_Factory(Provider<RecipeRepository> repositoryProvider,
      Provider<SavedStateHandle> savedStateHandleProvider) {
    this.repositoryProvider = repositoryProvider;
    this.savedStateHandleProvider = savedStateHandleProvider;
  }

  @Override
  public CravingsViewModel get() {
    return newInstance(repositoryProvider.get(), savedStateHandleProvider.get());
  }

  public static CravingsViewModel_Factory create(Provider<RecipeRepository> repositoryProvider,
      Provider<SavedStateHandle> savedStateHandleProvider) {
    return new CravingsViewModel_Factory(repositoryProvider, savedStateHandleProvider);
  }

  public static CravingsViewModel newInstance(RecipeRepository repository,
      SavedStateHandle savedStateHandle) {
    return new CravingsViewModel(repository, savedStateHandle);
  }
}
