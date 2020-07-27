package com.demo.ingredisearch.repository.sources.remote;

import androidx.annotation.NonNull;

import com.demo.ingredisearch.models.Recipe;

import java.util.List;

public class RecipesContainer {

    private List<Recipe> recipes;

    public RecipesContainer(List<Recipe> recipes) {
        this.recipes = recipes;
    }

    public List<Recipe> getRecipes() {
        return recipes;
    }

    @NonNull
    @Override
    public String toString() {
        return "FavoriteRecipesContainer{" +
                "recipes=" + recipes +
                '}';
    }
}
