package com.demo.ingredisearch.repository.util;

import com.demo.ingredisearch.models.Recipe;
import com.demo.ingredisearch.repository.sources.remote.RecipeResponse;
import com.demo.ingredisearch.repository.sources.remote.RecipesContainer;
import com.google.gson.Gson;

import java.util.List;

public class JsonConverter {

    private static Gson gson = new Gson();

    public static String toJson(List<Recipe> recipes) {
        return gson.toJson(new RecipesContainer(recipes));
    }

    public static List<Recipe> toRecipes(String string) {
        return gson.fromJson(string, RecipesContainer.class).getRecipes();
    }

    public static String toJson(Recipe recipe) {
        return gson.toJson(new RecipeResponse(recipe));
    }

    public static Recipe toRecipe(String string) {
        return gson.fromJson(string, RecipeResponse.class).getRecipe();
    }
}
