package com.demo.ingredisearch.features.favorites;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.navigation.NavDirections;
import com.demo.ingredisearch.R;
import java.lang.IllegalArgumentException;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.HashMap;

public class FavoritesFragmentDirections {
  private FavoritesFragmentDirections() {
  }

  @NonNull
  public static ActionFavoritesFragmentToRecipeDetailsFragment actionFavoritesFragmentToRecipeDetailsFragment(
      @NonNull String recipeId) {
    return new ActionFavoritesFragmentToRecipeDetailsFragment(recipeId);
  }

  public static class ActionFavoritesFragmentToRecipeDetailsFragment implements NavDirections {
    private final HashMap arguments = new HashMap();

    private ActionFavoritesFragmentToRecipeDetailsFragment(@NonNull String recipeId) {
      if (recipeId == null) {
        throw new IllegalArgumentException("Argument \"recipeId\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("recipeId", recipeId);
    }

    @NonNull
    public ActionFavoritesFragmentToRecipeDetailsFragment setRecipeId(@NonNull String recipeId) {
      if (recipeId == null) {
        throw new IllegalArgumentException("Argument \"recipeId\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("recipeId", recipeId);
      return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    @NonNull
    public Bundle getArguments() {
      Bundle __result = new Bundle();
      if (arguments.containsKey("recipeId")) {
        String recipeId = (String) arguments.get("recipeId");
        __result.putString("recipeId", recipeId);
      }
      return __result;
    }

    @Override
    public int getActionId() {
      return R.id.action_favoritesFragment_to_recipeDetailsFragment;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    public String getRecipeId() {
      return (String) arguments.get("recipeId");
    }

    @Override
    public boolean equals(Object object) {
      if (this == object) {
          return true;
      }
      if (object == null || getClass() != object.getClass()) {
          return false;
      }
      ActionFavoritesFragmentToRecipeDetailsFragment that = (ActionFavoritesFragmentToRecipeDetailsFragment) object;
      if (arguments.containsKey("recipeId") != that.arguments.containsKey("recipeId")) {
        return false;
      }
      if (getRecipeId() != null ? !getRecipeId().equals(that.getRecipeId()) : that.getRecipeId() != null) {
        return false;
      }
      if (getActionId() != that.getActionId()) {
        return false;
      }
      return true;
    }

    @Override
    public int hashCode() {
      int result = 1;
      result = 31 * result + (getRecipeId() != null ? getRecipeId().hashCode() : 0);
      result = 31 * result + getActionId();
      return result;
    }

    @Override
    public String toString() {
      return "ActionFavoritesFragmentToRecipeDetailsFragment(actionId=" + getActionId() + "){"
          + "recipeId=" + getRecipeId()
          + "}";
    }
  }
}
