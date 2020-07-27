package com.demo.ingredisearch.repository.sources.remote;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

class ServiceGenerator {

    private static final String API_KEY = "";
    private static final String BASE_URL = "https://recipesapi.herokuapp.com";

    Call<RecipeSearchResponse> getRecipesService(String query) {
        return getRecipeApi().search(API_KEY, query);
    }

    Call<RecipeResponse> getRecipeService(String recipeId) {
        return getRecipeApi().getRecipe(API_KEY, recipeId);
    }

    private RecipeApi getRecipeApi() {
        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        return retrofit.create(RecipeApi.class);
    }
}
