package com.demo.ingredisearch.features.searchresults;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.demo.ingredisearch.repository.RecipeRepository;

public class SearchResultsViewModelFactory implements ViewModelProvider.Factory {

    private RecipeRepository recipeRepository;

    public SearchResultsViewModelFactory(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (!modelClass.isAssignableFrom(modelClass))
            throw new IllegalArgumentException("No such viewmodel exists");
        return (T) new SearchResultsViewModel(recipeRepository);
    }
}
