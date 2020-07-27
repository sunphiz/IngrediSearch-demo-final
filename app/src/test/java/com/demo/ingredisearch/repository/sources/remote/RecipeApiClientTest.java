package com.demo.ingredisearch.repository.sources.remote;

import com.demo.ingredisearch.TestData;
import com.demo.ingredisearch.models.Recipe;
import com.demo.ingredisearch.repository.RemoteDataSource;
import com.demo.ingredisearch.repository.sources.ResponseCallback;
import com.demo.ingredisearch.util.Resource;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.demo.ingredisearch.util.Status.ERROR;
import static com.demo.ingredisearch.util.Status.SUCCESS;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class RecipeApiClientTest {

    // SUT
    RemoteDataSource mClient;

    @Mock
    Call<RecipeSearchResponse> mRecipeSearchResponseCall;

    @Mock
    Response<RecipeSearchResponse> mRecipeSearchResponse;

    @Mock
    Call<RecipeResponse> mRecipeResponseCall;

    @Mock
    Response<RecipeResponse> mRecipeResponseResponse;

    @Mock
    ResponseCallback<Recipe> mRecipeResponseCallback;

    @Captor
    ArgumentCaptor<Callback<RecipeResponse>> mCaptor;

    @Before
    public void setUp() {
        mClient = new TestRecipeApiClient();
        MockitoAnnotations.initMocks(this);
    }

       @Test
    public void searchRecipes_whenFailedByNetworkError_returnsErrorResponse() {
        // Arrange (Given)
        doAnswer(answer -> {
            Callback<RecipeSearchResponse> callback = answer.getArgument(0);
            callback.onFailure(mRecipeSearchResponseCall, new IOException("Network Error"));
            return null;
        }).when(mRecipeSearchResponseCall).enqueue(isA(Callback.class));

        // Act (When)
        mClient.searchRecipes("some query", new ResponseCallback<List<Recipe>>() {
            @Override
            public void onDataAvailable(Resource<List<Recipe>> response) {
                fail("Should not be called");
            }

            @Override
            public void onError(Resource<List<Recipe>> response) {
                // Assert (Then)
                assertThat(response.status, is(ERROR));
                assertThat(response.message, is("Network Error"));
            }
        });
    }

    @Test
    public void searchRecipes_whenFailedWithHTTPError_returnsErrorResponse() {
        // Arrange (Given)
        when(mRecipeSearchResponse.isSuccessful()).thenReturn(false);
        when(mRecipeSearchResponse.body()).thenReturn(new RecipeSearchResponse(null));
        when(mRecipeSearchResponse.message()).thenReturn("HTTP Error");

        doAnswer(answer -> {
            Callback<RecipeSearchResponse> callback = answer.getArgument(0);
            callback.onResponse(mRecipeSearchResponseCall, mRecipeSearchResponse);
            return null;
        }).when(mRecipeSearchResponseCall).enqueue(isA(Callback.class));

        // Act (When)
        mClient.searchRecipes("some query", new ResponseCallback<List<Recipe>>() {
            @Override
            public void onDataAvailable(Resource<List<Recipe>> response) {
                fail("Should not be called");
            }

            @Override
            public void onError(Resource<List<Recipe>> response) {
                // Assert (Then)
                assertThat(response.status, is(ERROR));
                assertThat(response.message, is("HTTP Error"));
            }
        });

    }

    @Test
    public void searchRecipes_whenFailedWithAuthError_returnsErrorResponse() {
        // Arrange (Given)
        when(mRecipeSearchResponse.isSuccessful()).thenReturn(true);
        when(mRecipeSearchResponse.code()).thenReturn(401);

        doAnswer(answer -> {
            Callback<RecipeSearchResponse> callback = answer.getArgument(0);
            callback.onResponse(mRecipeSearchResponseCall, mRecipeSearchResponse);
            return null;
        }).when(mRecipeSearchResponseCall).enqueue(isA(Callback.class));

        // Act (When)
        mClient.searchRecipes("some query", new ResponseCallback<List<Recipe>>() {
            @Override
            public void onDataAvailable(Resource<List<Recipe>> response) {
                fail("Should not be called");
            }

            @Override
            public void onError(Resource<List<Recipe>> response) {
                // Assert (Then)
                assertThat(response.status, is(ERROR));
                assertThat(response.message, is("401 Unauthorized. Token may be invalid"));
            }
        });
    }

    @Test
    public void searchRecipes_whenSucceedWithNullResult_returnsEmptyList() {
        // Arrange (Given)
        when(mRecipeSearchResponse.isSuccessful()).thenReturn(true);
        when(mRecipeSearchResponse.body()).thenReturn(null);

        doAnswer(answer -> {
            Callback<RecipeSearchResponse> callback = answer.getArgument(0);
            callback.onResponse(mRecipeSearchResponseCall, mRecipeSearchResponse);
            return null;
        }).when(mRecipeSearchResponseCall).enqueue(isA(Callback.class));

        // Act (When)
        mClient.searchRecipes("some query", new ResponseCallback<List<Recipe>>() {
            @Override
            public void onDataAvailable(Resource<List<Recipe>> response) {
                // Assert (Then)
                assertThat(response.status, is(SUCCESS));
                assertThat(response.data, is(Collections.emptyList()));
            }

            @Override
            public void onError(Resource<List<Recipe>> response) {
                fail("Should not be called");
            }
        });
    }

    @Test
    public void searchRecipes_whenSucceed_returnsRecipesList() {
        // Arrange (Given)
        when(mRecipeSearchResponse.isSuccessful()).thenReturn(true);
        when(mRecipeSearchResponse.body()).thenReturn(new RecipeSearchResponse(TestData.mRecipes));

        doAnswer(answer -> {
            Callback<RecipeSearchResponse> callback = answer.getArgument(0);
            callback.onResponse(mRecipeSearchResponseCall, mRecipeSearchResponse);
            return null;
        }).when(mRecipeSearchResponseCall).enqueue(isA(Callback.class));

        // Act (When)
        mClient.searchRecipes("some query", new ResponseCallback<List<Recipe>>() {
            @Override
            public void onDataAvailable(Resource<List<Recipe>> response) {
                // Assert (Then)
                assertThat(response.status, is(SUCCESS));
                assertThat(response.data, is(TestData.mRecipes));
            }

            @Override
            public void onError(Resource<List<Recipe>> response) {
                fail("Should not be called");
            }
        });
    }

    @Test
    public void searchRecipe_whenFailedByNetworkError_returnsErrorResponse() {
        // Arrange (Given)

        // Act (When)
        mClient.searchRecipe("valid recipe id", mRecipeResponseCallback);

        // Assert (Then)
        verify(mRecipeResponseCall).enqueue(mCaptor.capture());
        mCaptor.getValue().onFailure(mRecipeResponseCall, new IOException("Network Error"));
        verify(mRecipeResponseCallback).onError(Resource.error("Network Error", null));
    }

    @Test
    public void searchRecipe_whenFailedWithHTTPError_returnsErrorResponse() {
        // Arrange (Given)
        when(mRecipeResponseResponse.isSuccessful()).thenReturn(false);
        when(mRecipeResponseResponse.message()).thenReturn("HTTP Error");

        // Act (When)
        mClient.searchRecipe("valid recipe id", mRecipeResponseCallback);

        // Assert (Then)
        verify(mRecipeResponseCall).enqueue(mCaptor.capture());
        mCaptor.getValue().onResponse(mRecipeResponseCall, mRecipeResponseResponse);
        verify(mRecipeResponseCallback)
                .onError(Resource.error("HTTP Error", null));
    }

    @Test
    public void searchRecipe_whenFailedWithAuthError_returnsErrorResponse() {
        // Arrange (Given)
        when(mRecipeResponseResponse.isSuccessful()).thenReturn(true);
        when(mRecipeResponseResponse.code()).thenReturn(401);

        // Act (When)
        mClient.searchRecipe("valid recipe id", mRecipeResponseCallback);

        // Assert (Then)
        verify(mRecipeResponseCall).enqueue(mCaptor.capture());
        mCaptor.getValue().onResponse(mRecipeResponseCall, mRecipeResponseResponse);
        verify(mRecipeResponseCallback)
                .onError(Resource.error("401 Unauthorized. Token may be invalid", null));
    }

    @Test
    public void searchRecipe_whenSucceedWithNullResult_returnsNull() {
        // Arrange (Given)
        when(mRecipeResponseResponse.isSuccessful()).thenReturn(true);
        when(mRecipeResponseResponse.body()).thenReturn(null);

        // Act (When)
        mClient.searchRecipe("valid recipe id", mRecipeResponseCallback);

        // Assert (Then)
        verify(mRecipeResponseCall).enqueue(mCaptor.capture());
        mCaptor.getValue().onResponse(mRecipeResponseCall, mRecipeResponseResponse);
        verify(mRecipeResponseCallback).onDataAvailable(Resource.success(null));
    }

    @Test
    public void searchRecipe_whenSucceed_returnsRecipe() {
        // Arrange (Given)
        when(mRecipeResponseResponse.isSuccessful()).thenReturn(true);
        when(mRecipeResponseResponse.body()).thenReturn(new RecipeResponse(TestData.recipe1));

        // Act (When)
        mClient.searchRecipe("valid recipe id", mRecipeResponseCallback);

        // Assert (Then)
        verify(mRecipeResponseCall).enqueue(mCaptor.capture());
        mCaptor.getValue().onResponse(mRecipeResponseCall, mRecipeResponseResponse);
        verify(mRecipeResponseCallback).onDataAvailable(Resource.success(TestData.recipe1));
    }

    private class TestRecipeApiClient extends RecipeApiClient {
        @Override
        protected Call<RecipeSearchResponse> getRecipesService(String query) {
            return mRecipeSearchResponseCall;
        }

        @Override
        protected Call<RecipeResponse> getRecipeService(String recipeId) {
            return mRecipeResponseCall;
        }
    }
}