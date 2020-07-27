package com.demo.ingredisearch;

import android.content.Context;

import com.demo.ingredisearch.repository.RecipeRepository;
import com.demo.ingredisearch.repository.sources.favorites.SharedPreferencesFavoritesSource;
import com.demo.ingredisearch.repository.sources.remote.RecipeApiClient;

public class Injection {

    private Context context;
    private RecipeRepository recipeRepository;

    public Injection(Context context) {
        this.context = context;
    }

    public RecipeRepository getRecipeRepository() {
        if (recipeRepository == null) {
            recipeRepository = RecipeRepository.getInstance(
                    new RecipeApiClient(), new SharedPreferencesFavoritesSource(context));
        }
        return recipeRepository;
    }

    public void setRecipeRepository(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }
}
