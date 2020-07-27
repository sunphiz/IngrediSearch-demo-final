package com.demo.ingredisearch.features.searchresults;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.NavArgs;
import java.lang.IllegalArgumentException;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.HashMap;

public class SearchResultsFragmentArgs implements NavArgs {
  private final HashMap arguments = new HashMap();

  private SearchResultsFragmentArgs() {
  }

  private SearchResultsFragmentArgs(HashMap argumentsMap) {
    this.arguments.putAll(argumentsMap);
  }

  @NonNull
  @SuppressWarnings("unchecked")
  public static SearchResultsFragmentArgs fromBundle(@NonNull Bundle bundle) {
    SearchResultsFragmentArgs __result = new SearchResultsFragmentArgs();
    bundle.setClassLoader(SearchResultsFragmentArgs.class.getClassLoader());
    if (bundle.containsKey("query")) {
      String query;
      query = bundle.getString("query");
      __result.arguments.put("query", query);
    } else {
      throw new IllegalArgumentException("Required argument \"query\" is missing and does not have an android:defaultValue");
    }
    return __result;
  }

  @SuppressWarnings("unchecked")
  @Nullable
  public String getQuery() {
    return (String) arguments.get("query");
  }

  @SuppressWarnings("unchecked")
  @NonNull
  public Bundle toBundle() {
    Bundle __result = new Bundle();
    if (arguments.containsKey("query")) {
      String query = (String) arguments.get("query");
      __result.putString("query", query);
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
    SearchResultsFragmentArgs that = (SearchResultsFragmentArgs) object;
    if (arguments.containsKey("query") != that.arguments.containsKey("query")) {
      return false;
    }
    if (getQuery() != null ? !getQuery().equals(that.getQuery()) : that.getQuery() != null) {
      return false;
    }
    return true;
  }

  @Override
  public int hashCode() {
    int result = 1;
    result = 31 * result + (getQuery() != null ? getQuery().hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "SearchResultsFragmentArgs{"
        + "query=" + getQuery()
        + "}";
  }

  public static class Builder {
    private final HashMap arguments = new HashMap();

    public Builder(SearchResultsFragmentArgs original) {
      this.arguments.putAll(original.arguments);
    }

    public Builder(@Nullable String query) {
      this.arguments.put("query", query);
    }

    @NonNull
    public SearchResultsFragmentArgs build() {
      SearchResultsFragmentArgs result = new SearchResultsFragmentArgs(arguments);
      return result;
    }

    @NonNull
    public Builder setQuery(@Nullable String query) {
      this.arguments.put("query", query);
      return this;
    }

    @SuppressWarnings("unchecked")
    @Nullable
    public String getQuery() {
      return (String) arguments.get("query");
    }
  }
}
