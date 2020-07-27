package com.demo.ingredisearch.features.searchresults;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.navigation.ActionOnlyNavDirections;
import androidx.navigation.NavDirections;
import com.demo.ingredisearch.R;
import java.lang.IllegalArgumentException;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.HashMap;

public class SearchResultsFragmentDirections {
  private SearchResultsFragmentDirections() {
  }

  @NonNull
  public static ActionSearchResultsFragmentToRecipeDetailsFragment actionSearchResultsFragmentToRecipeDetailsFragment(
      @NonNull String recipeId) {
    return new ActionSearchResultsFragmentToRecipeDetailsFragment(recipeId);
  }

  @NonNull
  public static NavDirections actionSearchResultsFragmentToFavoritesFragment() {
    return new ActionOnlyNavDirections(R.id.action_searchResultsFragment_to_favoritesFragment);
  }

  public static class ActionSearchResultsFragmentToRecipeDetailsFragment implements NavDirections {
    private final HashMap arguments = new HashMap();

    private ActionSearchResultsFragmentToRecipeDetailsFragment(@NonNull String recipeId) {
      if (recipeId == null) {
        throw new IllegalArgumentException("Argument \"recipeId\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("recipeId", recipeId);
    }

    @NonNull
    public ActionSearchResultsFragmentToRecipeDetailsFragment setRecipeId(
        @NonNull String recipeId) {
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
      return R.id.action_searchResultsFragment_to_recipeDetailsFragment;
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
      ActionSearchResultsFragmentToRecipeDetailsFragment that = (ActionSearchResultsFragmentToRecipeDetailsFragment) object;
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
      return "ActionSearchResultsFragmentToRecipeDetailsFragment(actionId=" + getActionId() + "){"
          + "recipeId=" + getRecipeId()
          + "}";
    }
  }
}
