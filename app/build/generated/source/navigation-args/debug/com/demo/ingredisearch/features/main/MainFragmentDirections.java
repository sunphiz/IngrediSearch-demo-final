package com.demo.ingredisearch.features.main;

import androidx.annotation.NonNull;
import androidx.navigation.ActionOnlyNavDirections;
import androidx.navigation.NavDirections;
import com.demo.ingredisearch.R;

public class MainFragmentDirections {
  private MainFragmentDirections() {
  }

  @NonNull
  public static NavDirections actionMainFragmentToSearchFragment() {
    return new ActionOnlyNavDirections(R.id.action_mainFragment_to_searchFragment);
  }

  @NonNull
  public static NavDirections actionMainFragmentToFavoritesFragment() {
    return new ActionOnlyNavDirections(R.id.action_mainFragment_to_favoritesFragment);
  }
}
