package com.demo.ingredisearch.repository.sources.remote;

import com.demo.ingredisearch.models.Recipe;
import com.demo.ingredisearch.repository.RemoteDataSource;
import com.demo.ingredisearch.repository.sources.ResponseCallback;
import com.demo.ingredisearch.util.AppExecutors;
import com.demo.ingredisearch.util.EspressoIdlingResource;
import com.demo.ingredisearch.util.Resource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class FakeRemoteDataSource implements RemoteDataSource {

    public static final long FAKE_NETWORK_DELAY = 2000L;

    public enum DataStatus {Success, AuthError, HttpError, Error}

    private Map<String, Recipe> mRecipesTable = new LinkedHashMap<>();

    private AppExecutors appExecutors;

    private DataStatus dataStatus;

    public FakeRemoteDataSource(AppExecutors appExecutors) {
        this.appExecutors = appExecutors;
        this.dataStatus = DataStatus.Success;
    }

    @Override
    public void searchRecipes(String query, ResponseCallback<List<Recipe>> callback) {

        appExecutors.networkIO().execute(() -> {

            EspressoIdlingResource.increment();

            try {
                Thread.sleep(FAKE_NETWORK_DELAY);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            switch (dataStatus) {
                case AuthError:
                    callback.onError(Resource.error("401 Unauthorized. Token may be invalid", null));
                    break;
                case HttpError:
                    callback.onError(Resource.error("HTTP Error", null));
                    break;
                case Error:
                    callback.onError(Resource.error("Network Error", null));
                    break;
                default: // Success
                    if (mRecipesTable.size() == 0)
                        callback.onDataAvailable(Resource.success(Collections.emptyList()));
                    else
                        callback.onDataAvailable(Resource.success(new ArrayList<>(mRecipesTable.values())));
                    break;
            }

            EspressoIdlingResource.decrement();
        });
    }

    @Override
    public void searchRecipe(String recipeId, ResponseCallback<Recipe> callback) {

        appExecutors.networkIO().execute(() -> {

            EspressoIdlingResource.increment();

            try {
                Thread.sleep(FAKE_NETWORK_DELAY);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            switch (dataStatus) {
                case AuthError:
                    callback.onError(Resource.error("401 Unauthorized. Token may be invalid", null));
                    break;
                case HttpError:
                    callback.onError(Resource.error("HTTP Error", null));
                    break;
                case Error:
                    callback.onError(Resource.error("Network Error", null));
                    break;
                default: // Success
                    Recipe recipe = mRecipesTable.get(recipeId);
                    callback.onDataAvailable(Resource.success(
                            recipe != null ? new Recipe(recipe) : null
                    ));
                    break;
            }

            EspressoIdlingResource.decrement();
        });
    }

    public void addRecipes(List<Recipe> recipes) {
        recipes.forEach(r -> mRecipesTable.put(r.getRecipeId(), r));
    }

    public void addRecipes(Recipe... recipes) {
        Arrays.stream(recipes).forEach(r -> mRecipesTable.put(r.getRecipeId(), r));
    }

    public void setDataStatus(DataStatus dataStatus) {
        this.dataStatus = dataStatus;
    }
}
