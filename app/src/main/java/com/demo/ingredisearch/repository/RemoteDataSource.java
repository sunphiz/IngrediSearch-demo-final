package com.demo.ingredisearch.repository;

import com.demo.ingredisearch.models.Recipe;
import com.demo.ingredisearch.repository.sources.ResponseCallback;

import java.util.List;

public interface RemoteDataSource {
    void searchRecipes(String query, ResponseCallback<List<Recipe>> callback);

    void searchRecipe(String recipeId, ResponseCallback<Recipe> callback);
}
