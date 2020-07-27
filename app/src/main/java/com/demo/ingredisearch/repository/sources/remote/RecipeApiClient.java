package com.demo.ingredisearch.repository.sources.remote;

import androidx.annotation.NonNull;

import com.demo.ingredisearch.models.Recipe;
import com.demo.ingredisearch.repository.RemoteDataSource;
import com.demo.ingredisearch.repository.sources.ResponseCallback;
import com.demo.ingredisearch.util.Resource;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static java.util.Collections.emptyList;

public class RecipeApiClient implements RemoteDataSource {

    private static final String TAG = "RecipeApp";

    private ServiceGenerator mServiceGenerator = new ServiceGenerator();

    @Override
    public void searchRecipes(String query, ResponseCallback<List<Recipe>> callback) {
        Call<RecipeSearchResponse> call = getRecipesService(query);

        call.enqueue(new Callback<RecipeSearchResponse>() {
            @Override
            public void onResponse(@NonNull Call<RecipeSearchResponse> call, @NonNull Response<RecipeSearchResponse> response) {
                RecipeSearchResponse searchResponse = response.body();
                if (response.isSuccessful()) {
                    if (response.code() == 401) { // Unauthorised error
                        callback.onError(Resource.error("401 Unauthorized. Token may be invalid", null));
                    } else if (searchResponse == null) {
                        callback.onDataAvailable(Resource.success(emptyList()));
                    } else {
                        callback.onDataAvailable(Resource.success(searchResponse.getRecipes()));
                    }
                } else {
                    callback.onError(Resource.error(response.message() , null));
                }
            }

            @Override
            public void onFailure(@NonNull Call<RecipeSearchResponse> call, @NonNull Throwable throwable) {
                callback.onError(Resource.error(throwable.getMessage(), null));
            }
        });
    }

    protected Call<RecipeSearchResponse> getRecipesService(String query) {
        return mServiceGenerator.getRecipesService(query);
    }

    @Override
    public void searchRecipe(String recipeId, ResponseCallback<Recipe> callback) {
        Call<RecipeResponse> call = getRecipeService(recipeId);

        call.enqueue(new Callback<RecipeResponse>() {
            @Override
            public void onResponse(@NonNull Call<RecipeResponse> call, @NonNull Response<RecipeResponse> response) {
                RecipeResponse recipe = response.body();
                if (response.isSuccessful()) {
                    if (response.code() == 401) { // Unauthorised error
                        callback.onError(Resource.error("401 Unauthorized. Token may be invalid", null));
                    } else if (recipe == null) {
                        callback.onDataAvailable(Resource.success(null));
                    } else {
                        callback.onDataAvailable(Resource.success(recipe.getRecipe()));
                    }
                } else {
                    callback.onError(Resource.error(response.message() , null));
                }
            }

            @Override
            public void onFailure(@NonNull Call<RecipeResponse> call, @NonNull Throwable throwable) {
                callback.onError(Resource.error(throwable.getMessage(), null));
            }
        });
    }

    protected Call<RecipeResponse> getRecipeService(String recipeId) {
        return mServiceGenerator.getRecipeService(recipeId);
    }

}
