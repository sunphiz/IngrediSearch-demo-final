package com.demo.ingredisearch.repository.sources.favorites;

import android.content.Context;
import android.content.SharedPreferences;

import com.demo.ingredisearch.models.Recipe;
import com.demo.ingredisearch.repository.FavoritesSource;
import com.demo.ingredisearch.repository.util.JsonConverter;

import java.util.ArrayList;
import java.util.List;

public class SharedPreferencesFavoritesSource implements FavoritesSource {

    public static final String FAVORTIES_KEY = "FAVORITES_KEY";

    private SharedPreferences mSharedPreferences;

    public SharedPreferencesFavoritesSource(Context context) {
        mSharedPreferences = context.getSharedPreferences(FAVORTIES_KEY, Context.MODE_PRIVATE);
    }

    @Override
    public List<Recipe> getFavorites() {
        String serializedRecipes = mSharedPreferences.getString(FAVORTIES_KEY, null);
        return serializedRecipes != null ? JsonConverter.toRecipes(serializedRecipes) : new ArrayList<>();
    }

    @Override
    public void addFavorite(Recipe recipe) {
        List<Recipe> favorites = getFavorites();

        if (containsDuplicate(favorites, recipe)) return;

        Recipe favorite = new Recipe(recipe);
        favorite.setFavorite(true);
        favorites.add(favorite);

        saveFavorites(favorites);
    }

    private boolean containsDuplicate(List<Recipe> favorites, Recipe recipe) {
        return favorites.stream().anyMatch(f -> f.isSameAs(recipe));
    }

    private void saveFavorites(List<Recipe> favorites) {
        mSharedPreferences.edit()
                .putString(FAVORTIES_KEY, JsonConverter.toJson(favorites))
                .apply();
    }

    @Override
    public void removeFavorite(Recipe recipe) {
        List<Recipe> favorites = getFavorites();
        favorites.removeIf(f -> f.isSameAs(recipe));
        saveFavorites(favorites);
    }

    @Override
    public void clearFavorites() {
        mSharedPreferences.edit().clear().apply();
    }
}
