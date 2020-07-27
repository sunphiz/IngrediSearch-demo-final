package com.demo.ingredisearch.features.search;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.NavDirections;
import com.demo.ingredisearch.R;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.HashMap;

public class SearchFragmentDirections {
  private SearchFragmentDirections() {
  }

  @NonNull
  public static ActionSearchFragmentToSearchResultsFragment actionSearchFragmentToSearchResultsFragment(
      @Nullable String query) {
    return new ActionSearchFragmentToSearchResultsFragment(query);
  }

  public static class ActionSearchFragmentToSearchResultsFragment implements NavDirections {
    private final HashMap arguments = new HashMap();

    private ActionSearchFragmentToSearchResultsFragment(@Nullable String query) {
      this.arguments.put("query", query);
    }

    @NonNull
    public ActionSearchFragmentToSearchResultsFragment setQuery(@Nullable String query) {
      this.arguments.put("query", query);
      return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    @NonNull
    public Bundle getArguments() {
      Bundle __result = new Bundle();
      if (arguments.containsKey("query")) {
        String query = (String) arguments.get("query");
        __result.putString("query", query);
      }
      return __result;
    }

    @Override
    public int getActionId() {
      return R.id.action_searchFragment_to_searchResultsFragment;
    }

    @SuppressWarnings("unchecked")
    @Nullable
    public String getQuery() {
      return (String) arguments.get("query");
    }

    @Override
    public boolean equals(Object object) {
      if (this == object) {
          return true;
      }
      if (object == null || getClass() != object.getClass()) {
          return false;
      }
      ActionSearchFragmentToSearchResultsFragment that = (ActionSearchFragmentToSearchResultsFragment) object;
      if (arguments.containsKey("query") != that.arguments.containsKey("query")) {
        return false;
      }
      if (getQuery() != null ? !getQuery().equals(that.getQuery()) : that.getQuery() != null) {
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
      result = 31 * result + (getQuery() != null ? getQuery().hashCode() : 0);
      result = 31 * result + getActionId();
      return result;
    }

    @Override
    public String toString() {
      return "ActionSearchFragmentToSearchResultsFragment(actionId=" + getActionId() + "){"
          + "query=" + getQuery()
          + "}";
    }
  }
}
