package com.demo.ingredisearch.features.details;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.demo.ingredisearch.models.Recipe;
import com.demo.ingredisearch.repository.RecipeRepository;
import com.demo.ingredisearch.util.Resource;

class RecipeDetailsViewModel extends ViewModel {

    private static final String TAG = "RecipeApp";

    private RecipeRepository mRecipeRepository;

    public RecipeDetailsViewModel(@NonNull RecipeRepository recipeRepository) {
        this.mRecipeRepository = recipeRepository;
    }

    public LiveData<Resource<Recipe>> getRecipe() {
        return mRecipeRepository.getRecipe();
    }

    public void searchRecipe(String recipeId) {
        mRecipeRepository.searchRecipe(recipeId);
    }
}
