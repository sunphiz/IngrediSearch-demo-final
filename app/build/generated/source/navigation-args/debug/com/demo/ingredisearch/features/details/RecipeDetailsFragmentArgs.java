package com.demo.ingredisearch.features.details;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.navigation.NavArgs;
import java.lang.IllegalArgumentException;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.HashMap;

public class RecipeDetailsFragmentArgs implements NavArgs {
  private final HashMap arguments = new HashMap();

  private RecipeDetailsFragmentArgs() {
  }

  private RecipeDetailsFragmentArgs(HashMap argumentsMap) {
    this.arguments.putAll(argumentsMap);
  }

  @NonNull
  @SuppressWarnings("unchecked")
  public static RecipeDetailsFragmentArgs fromBundle(@NonNull Bundle bundle) {
    RecipeDetailsFragmentArgs __result = new RecipeDetailsFragmentArgs();
    bundle.setClassLoader(RecipeDetailsFragmentArgs.class.getClassLoader());
    if (bundle.containsKey("recipeId")) {
      String recipeId;
      recipeId = bundle.getString("recipeId");
      if (recipeId == null) {
        throw new IllegalArgumentException("Argument \"recipeId\" is marked as non-null but was passed a null value.");
      }
      __result.arguments.put("recipeId", recipeId);
    } else {
      throw new IllegalArgumentException("Required argument \"recipeId\" is missing and does not have an android:defaultValue");
    }
    return __result;
  }

  @SuppressWarnings("unchecked")
  @NonNull
  public String getRecipeId() {
    return (String) arguments.get("recipeId");
  }

  @SuppressWarnings("unchecked")
  @NonNull
  public Bundle toBundle() {
    Bundle __result = new Bundle();
    if (arguments.containsKey("recipeId")) {
      String recipeId = (String) arguments.get("recipeId");
      __result.putString("recipeId", recipeId);
    }
    return __result;
  }

  @Override
  public boolean equals(Object object) {
    if (this == object) {
        return true;
    }
    if (object == null || getClass() != object.getClass()) {
        return false;
    }
    RecipeDetailsFragmentArgs that = (RecipeDetailsFragmentArgs) object;
    if (arguments.containsKey("recipeId") != that.arguments.containsKey("recipeId")) {
      return false;
    }
    if (getRecipeId() != null ? !getRecipeId().equals(that.getRecipeId()) : that.getRecipeId() != null) {
      return false;
    }
    return true;
  }

  @Override
  public int hashCode() {
    int result = 1;
    result = 31 * result + (getRecipeId() != null ? getRecipeId().hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "RecipeDetailsFragmentArgs{"
        + "recipeId=" + getRecipeId()
        + "}";
  }

  public static class Builder {
    private final HashMap arguments = new HashMap();

    public Builder(RecipeDetailsFragmentArgs original) {
      this.arguments.putAll(original.arguments);
    }

    public Builder(@NonNull String recipeId) {
      if (recipeId == null) {
        throw new IllegalArgumentException("Argument \"recipeId\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("recipeId", recipeId);
    }

    @NonNull
    public RecipeDetailsFragmentArgs build() {
      RecipeDetailsFragmentArgs result = new RecipeDetailsFragmentArgs(arguments);
      return result;
    }

    @NonNull
    public Builder setRecipeId(@NonNull String recipeId) {
      if (recipeId == null) {
        throw new IllegalArgumentException("Argument \"recipeId\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("recipeId", recipeId);
      return this;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    public String getRecipeId() {
      return (String) arguments.get("recipeId");
    }
  }
}
