package com.demo.ingredisearch.repository.sources.favorites;

import com.demo.ingredisearch.models.Recipe;
import com.demo.ingredisearch.repository.FavoritesSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FakeFavoritesSource implements FavoritesSource {

    private List<Recipe> mFavorites = new ArrayList<>();

    @Override
    public List<Recipe> getFavorites() {
        return mFavorites;
    }

    @Override
    public void addFavorite(Recipe recipe) {
        if (containsDuplicate(mFavorites, recipe)) return;

        Recipe favorite = new Recipe(recipe);
        favorite.setFavorite(true);
        mFavorites.add(favorite);
    }

    private boolean containsDuplicate(List<Recipe> favorites, Recipe recipe) {
        return favorites.stream().anyMatch(f -> f.isSameAs(recipe));
    }

    @Override
    public void removeFavorite(Recipe recipe) {
        mFavorites.removeIf(f -> f.isSameAs(recipe));
    }

    @Override
    public void clearFavorites() {
        mFavorites.clear();
    }

    // bug fixed
    public void addFavorites(List<Recipe> favorites) {
        favorites.forEach(this::addFavorite);
    }

    // bug fixed
    public void addFavorites(Recipe... favorites) {
        Arrays.stream(favorites).forEach(this::addFavorite);
    }
}
