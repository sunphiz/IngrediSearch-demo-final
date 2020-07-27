package com.demo.ingredisearch.features.searchresults;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.demo.ingredisearch.models.Recipe;
import com.demo.ingredisearch.repository.RecipeRepository;
import com.demo.ingredisearch.util.Event;
import com.demo.ingredisearch.util.Resource;

import java.util.List;
import java.util.stream.Collectors;

import static com.demo.ingredisearch.util.Status.ERROR;
import static com.demo.ingredisearch.util.Status.SUCCESS;

public class SearchResultsViewModel extends ViewModel {

    private RecipeRepository mRecipeRepository;

    public SearchResultsViewModel(RecipeRepository mRecipeRepository) {
        this.mRecipeRepository = mRecipeRepository;
    }

    private String mQuery;

    public void searchRecipes(String query) {
        mQuery = query;
        mRecipeRepository.searchRecipes(query);
    }

    private MutableLiveData<Event<Object>> mIsLoading = new MutableLiveData<>();

    public LiveData<Event<Object>> isLoading() {
        return mIsLoading;
    }

    public LiveData<List<Recipe>> getRecipes() {
        LiveData<Resource<List<Recipe>>> response = mRecipeRepository.getRecipes();
        return Transformations.map(response, resource -> {
            if (resource.status == SUCCESS) {
                return markFavorites(resource.data);
            } else if (resource.status == ERROR) {
                mRetry.setValue(new Event<>(new Object()));
                return null;
            } else {
                mIsLoading.setValue(new Event<>(new Object()));
                return null;
            }
        });
    }

    protected List<Recipe> markFavorites(List<Recipe> recipes) {
        List<Recipe> favorites = mRecipeRepository.getFavorites();

        return recipes.stream().map(recipe -> {
            if (favorites.stream().anyMatch(f -> f.isSameAs(recipe))) {
                Recipe temp = new Recipe(recipe);
                temp.setFavorite(true);
                return temp;
            } else {
                return recipe;
            }
        }).collect(Collectors.toList());

    }

    public void markFavorite(Recipe recipe) {
        mRecipeRepository.addFavorite(recipe);
        mRecipeRepository.load();
    }

    public void unmarkFavorite(Recipe recipe) {
        mRecipeRepository.removeFavorite(recipe);
        mRecipeRepository.load();
    }

    private MutableLiveData<Event<String>> mNavToRecipeDetails = new MutableLiveData<>();

    public LiveData<Event<String>> navToRecipeDetails() {
        return mNavToRecipeDetails;
    }

    public void requestNavToRecipeDetails(String recipeId) {
        mNavToRecipeDetails.setValue(new Event<>(recipeId));
    }

    private MutableLiveData<Event<Object>> mNavToFavorites = new MutableLiveData<>();

    public LiveData<Event<Object>> navToFavorites() {
        return mNavToFavorites;
    }

    public void requestNavToFavorites() {
        mNavToFavorites.setValue(new Event<>(new Object()));
    }

    private MutableLiveData<Event<Object>> mRetry = new MutableLiveData<>();

    public LiveData<Event<Object>> retry() {
        return mRetry;
    }
}
