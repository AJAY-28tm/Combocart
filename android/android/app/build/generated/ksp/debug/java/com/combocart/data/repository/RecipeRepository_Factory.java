package com.combocart.data.repository;

import com.combocart.data.remote.RecipeApi;
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
public final class RecipeRepository_Factory implements Factory<RecipeRepository> {
  private final Provider<RecipeApi> recipeApiProvider;

  public RecipeRepository_Factory(Provider<RecipeApi> recipeApiProvider) {
    this.recipeApiProvider = recipeApiProvider;
  }

  @Override
  public RecipeRepository get() {
    return newInstance(recipeApiProvider.get());
  }

  public static RecipeRepository_Factory create(Provider<RecipeApi> recipeApiProvider) {
    return new RecipeRepository_Factory(recipeApiProvider);
  }

  public static RecipeRepository newInstance(RecipeApi recipeApi) {
    return new RecipeRepository(recipeApi);
  }
}
