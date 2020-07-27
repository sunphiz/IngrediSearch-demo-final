package com.demo.ingredisearch.repository.sources.remote;

import com.demo.ingredisearch.TestData;
import com.demo.ingredisearch.models.Recipe;
import com.demo.ingredisearch.repository.sources.ResponseCallback;
import com.demo.ingredisearch.repository.sources.remote.FakeRemoteDataSource.DataStatus;
import com.demo.ingredisearch.util.Resource;
import com.demo.ingredisearch.util.SingleExecutors;

import org.junit.Before;
import org.junit.Test;

import java.util.Collections;
import java.util.List;

import static com.demo.ingredisearch.util.Status.ERROR;
import static com.demo.ingredisearch.util.Status.SUCCESS;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.*;

public class FakeRemoteDataSourceTest {

    // SUT
    FakeRemoteDataSource mRemoteDataSource;

    @Before
    public void setUp() {
        mRemoteDataSource = new FakeRemoteDataSource(new SingleExecutors());
    }

    @Test
    public void searchRecipes_whenFailedByNetworkError_returnsErrorResponse() {
        // Arrange (Given)
        mRemoteDataSource.setDataStatus(DataStatus.Error);
        
        // Act (When)
        mRemoteDataSource.searchRecipes("some query", new ResponseCallback<List<Recipe>>() {
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
        mRemoteDataSource.setDataStatus(DataStatus.HttpError);

        mRemoteDataSource.searchRecipes("some query", new ResponseCallback<List<Recipe>>() {
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
        mRemoteDataSource.setDataStatus(DataStatus.AuthError);

        // Act (When)
        mRemoteDataSource.searchRecipes("some query", new ResponseCallback<List<Recipe>>() {
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

        // Act (When)
        mRemoteDataSource.searchRecipes("some query", new ResponseCallback<List<Recipe>>() {
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
        mRemoteDataSource.addRecipes(TestData.mRecipes);

        // Act (When)
        mRemoteDataSource.searchRecipes("eggs", new ResponseCallback<List<Recipe>>() {
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
        mRemoteDataSource.setDataStatus(DataStatus.Error);

        // Act (When)
        mRemoteDataSource.searchRecipe("valid recipe id", new ResponseCallback<Recipe>() {
            @Override
            public void onDataAvailable(Resource<Recipe> response) {
                fail("Should not be called");
            }

            @Override
            public void onError(Resource<Recipe> response) {
                // Assert (Then)
                assertThat(response.status, is(ERROR));
                assertThat(response.message, is("Network Error"));
            }
        });
    }

    @Test
    public void searchRecipe_whenFailedWithHTTPError_returnsErrorResponse() {
        // Arrange (Given)
        mRemoteDataSource.setDataStatus(DataStatus.HttpError);

        // Act (When)
        mRemoteDataSource.searchRecipe("valid recipe id", new ResponseCallback<Recipe>() {
            @Override
            public void onDataAvailable(Resource<Recipe> response) {
                fail("Should not be called");
            }

            @Override
            public void onError(Resource<Recipe> response) {
                // Assert (Then)
                assertThat(response.status, is(ERROR));
                assertThat(response.message, is("HTTP Error"));
            }
        });
    }

    @Test
    public void searchRecipe_whenFailedWithAuthError_returnsErrorResponse() {
        // Arrange (Given)
        mRemoteDataSource.setDataStatus(DataStatus.AuthError);

        // Act (When)
        mRemoteDataSource.searchRecipe("valid recipe id", new ResponseCallback<Recipe>() {
            @Override
            public void onDataAvailable(Resource<Recipe> response) {
                fail("Should not be called");
            }

            @Override
            public void onError(Resource<Recipe> response) {
                // Assert (Then)
                assertThat(response.status, is(ERROR));
                assertThat(response.message, is("401 Unauthorized. Token may be invalid"));
            }
        });
    }

    @Test
    public void searchRecipe_whenSucceedWithNullResult_returnsNull() {
        // Arrange (Given)

        // Act (When)
        mRemoteDataSource.searchRecipe(TestData.recipe1.getRecipeId(), new ResponseCallback<Recipe>() {
            @Override
            public void onDataAvailable(Resource<Recipe> response) {
                // Assert (Then)
                assertThat(response.status, is(SUCCESS));
                assertThat(response.data, is(nullValue()));
            }

            @Override
            public void onError(Resource<Recipe> response) {
                fail("Should not be called");
            }
        });
    }

    @Test
    public void searchRecipe_whenSucceed_returnsRecipe() {
        // Arrange (Given)
        mRemoteDataSource.addRecipes(TestData.mRecipes);

        // Act (When)
        mRemoteDataSource.searchRecipe(TestData.recipe1.getRecipeId(), new ResponseCallback<Recipe>() {
            @Override
            public void onDataAvailable(Resource<Recipe> response) {
                // Assert (Then)
                assertThat(response.status, is(SUCCESS));
                assertThat(response.data, is(TestData.recipe1));
            }

            @Override
            public void onError(Resource<Recipe> response) {
                fail("Should not be called");
            }
        });
    }

}