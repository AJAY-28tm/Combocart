package com.combocart.data.local;

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
public final class LocalCartManager_Factory implements Factory<LocalCartManager> {
  @Override
  public LocalCartManager get() {
    return newInstance();
  }

  public static LocalCartManager_Factory create() {
    return InstanceHolder.INSTANCE;
  }

  public static LocalCartManager newInstance() {
    return new LocalCartManager();
  }

  private static final class InstanceHolder {
    private static final LocalCartManager_Factory INSTANCE = new LocalCartManager_Factory();
  }
}
