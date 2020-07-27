package com.demo.ingredisearch.features.favorites;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.demo.ingredisearch.models.Recipe;
import com.demo.ingredisearch.repository.RecipeRepository;
import com.demo.ingredisearch.util.Event;

import java.util.List;

public class FavoritesViewModel extends ViewModel {
    private RecipeRepository mRecipeRepository;

    public FavoritesViewModel(RecipeRepository mRecipeRepository) {
        this.mRecipeRepository = mRecipeRepository;
    }

    private MutableLiveData<List<Recipe>> mFavorites = new MutableLiveData<>();

    public LiveData<List<Recipe>> getFavorites() {
        load();
        return mFavorites;
    }

    private void load() {
        mFavorites.setValue(mRecipeRepository.getFavorites());
    }

    public void removeFavorite(Recipe recipe) {
        mRecipeRepository.removeFavorite(recipe);
        load();
    }

    public void clearFavorites() {
        mRecipeRepository.clearFavorites();
        load();
    }

    private MutableLiveData<Event<String>> mNavToRecipeDetails = new MutableLiveData<>();

    public LiveData<Event<String>> navToRecipeDetails() {
        return mNavToRecipeDetails;
    }

    public void requestToNavToRecipeDetails(String recipeId) {
        mNavToRecipeDetails.setValue(new Event<>(recipeId));
    }
}
