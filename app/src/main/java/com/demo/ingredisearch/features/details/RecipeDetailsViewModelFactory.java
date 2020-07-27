package com.demo.ingredisearch.features.details;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.demo.ingredisearch.repository.RecipeRepository;

public class RecipeDetailsViewModelFactory implements ViewModelProvider.Factory {

    private RecipeRepository recipeRepository;

    public RecipeDetailsViewModelFactory(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (!modelClass.isAssignableFrom(RecipeDetailsViewModel.class))
            throw new IllegalArgumentException("No such viewmodel exists");
        return (T) new RecipeDetailsViewModel(recipeRepository);
    }
}
