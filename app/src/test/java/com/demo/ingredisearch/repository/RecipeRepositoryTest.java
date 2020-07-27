package com.demo.ingredisearch.repository;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import com.demo.ingredisearch.TestData;
import com.demo.ingredisearch.models.Recipe;
import com.demo.ingredisearch.repository.RecipeRepository;
import com.demo.ingredisearch.repository.sources.favorites.FakeFavoritesSource;
import com.demo.ingredisearch.repository.sources.remote.FakeRemoteDataSource;
import com.demo.ingredisearch.util.LiveDataTestUtil;
import com.demo.ingredisearch.util.Resource;
import com.demo.ingredisearch.util.SingleExecutors;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.demo.ingredisearch.util.Status.ERROR;
import static com.demo.ingredisearch.util.Status.SUCCESS;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;

public class RecipeRepositoryTest {

    @Rule
    public InstantTaskExecutorRule rule = new InstantTaskExecutorRule();

    // SUT
    RecipeRepository mRecipeRepository;

    FakeRemoteDataSource mRemoteDataSource;

    FakeFavoritesSource mFavoritesSource;

    @Before
    public void setUp() {
        mRemoteDataSource = new FakeRemoteDataSource(new SingleExecutors());
        mFavoritesSource = new FakeFavoritesSource();
        mRecipeRepository = RecipeRepository.getInstance(mRemoteDataSource, mFavoritesSource);
    }

    @After
    public void tearDown() {
        mRecipeRepository.destroy();
    }

    @Test
    public void searchRecipes_whenFailedByNetworkError_returnsErrorResponse() throws InterruptedException {
        // Arrange (Given)
        mRemoteDataSource.setDataStatus(FakeRemoteDataSource.DataStatus.Error);

        // Act (When)
        mRecipeRepository.searchRecipes("some query");

        // Assert (Then)
        Resource<List<Recipe>> response = LiveDataTestUtil.getOrAwaitValue(mRecipeRepository.getRecipes());
        assertThat(response.status, is(ERROR));
        assertThat(response.message, is("Network Error"));
    }

    @Test
    public void searchRecipes_whenFailedWithHTTPError_returnsErrorResponse() throws InterruptedException {
        // Arrange (Given)
        mRemoteDataSource.setDataStatus(FakeRemoteDataSource.DataStatus.HttpError);

        // Act (When)
        mRecipeRepository.searchRecipes("some query");

        // Assert (Then)
        Resource<List<Recipe>> response = LiveDataTestUtil.getOrAwaitValue(mRecipeRepository.getRecipes());
        assertThat(response.status, is(ERROR));
        assertThat(response.message, is("HTTP Error"));
    }

    @Test
    public void searchRecipes_whenFailedWithAuthError_returnsErrorResponse() throws InterruptedException {
        // Arrange (Given)
        mRemoteDataSource.setDataStatus(FakeRemoteDataSource.DataStatus.AuthError);

        // Act (When)
        mRecipeRepository.searchRecipes("some query");

        // Assert (Then)
        Resource<List<Recipe>> response = LiveDataTestUtil.getOrAwaitValue(mRecipeRepository.getRecipes());
        assertThat(response.status, is(ERROR));
        assertThat(response.message, is("401 Unauthorized. Token may be invalid"));
    }

    @Test
    public void searchRecipes_whenSucceedWithNullResult_returnsEmptyList() throws InterruptedException {
        // Arrange (Given)

        // Act (When)
        mRecipeRepository.searchRecipes("some query");

        // Assert (Then)
        Resource<List<Recipe>> response = LiveDataTestUtil.getOrAwaitValue(mRecipeRepository.getRecipes());
        assertThat(response.status, is(SUCCESS));
        assertThat(response.data, is(Collections.emptyList()));
    }

    @Test
    public void searchRecipes_whenSucceed_returnsRecipesList() throws InterruptedException {
        // Arrange (Given)
        mRemoteDataSource.addRecipes(TestData.mRecipes);

        // Act (When)
        mRecipeRepository.searchRecipes("eggs");

        // Assert (Then)
        Resource<List<Recipe>> response = LiveDataTestUtil.getOrAwaitValue(mRecipeRepository.getRecipes());
        assertThat(response.status, is(SUCCESS));
        assertThat(response.data, is(TestData.mRecipes));
    }

    @Test
    public void searchRecipe_whenFailedByNetworkError_returnsErrorResponse() throws InterruptedException {
        // Arrange (Given)
        mRemoteDataSource.setDataStatus(FakeRemoteDataSource.DataStatus.Error);

        // Act (When)
        mRecipeRepository.searchRecipe("valid recipe id");

        // Assert (Then)
        Resource<Recipe> response = LiveDataTestUtil.getOrAwaitValue(mRecipeRepository.getRecipe());
        assertThat(response.status, is(ERROR));
        assertThat(response.message, is("Network Error"));
    }

    @Test
    public void searchRecipe_whenFailedWithHTTPError_returnsErrorResponse() throws InterruptedException {
        // Arrange (Given)
        mRemoteDataSource.setDataStatus(FakeRemoteDataSource.DataStatus.HttpError);

        // Act (When)
        mRecipeRepository.searchRecipe("valid recipe id");

        // Assert (Then)
        Resource<Recipe> response = LiveDataTestUtil.getOrAwaitValue(mRecipeRepository.getRecipe());

        // Assert (Then)
        assertThat(response.status, is(ERROR));
        assertThat(response.message, is("HTTP Error"));
    }

    @Test
    public void searchRecipe_whenFailedWithAuthError_returnsErrorResponse() throws InterruptedException {
        // Arrange (Given)
        mRemoteDataSource.setDataStatus(FakeRemoteDataSource.DataStatus.AuthError);

        // Act (When)
        mRecipeRepository.searchRecipe("valid recipe id");

        // Assert (Then)
        Resource<Recipe> response = LiveDataTestUtil.getOrAwaitValue(mRecipeRepository.getRecipe());
        assertThat(response.status, is(ERROR));
        assertThat(response.message, is("401 Unauthorized. Token may be invalid"));
    }

    @Test
    public void searchRecipe_whenSucceedWithNullResult_returnsNull() throws InterruptedException {
        // Arrange (Given)

        // Act (When)
        mRecipeRepository.searchRecipe(TestData.recipe1.getRecipeId());

        // Assert (Then)
        Resource<Recipe> response = LiveDataTestUtil.getOrAwaitValue(mRecipeRepository.getRecipe());
        assertThat(response.status, is(SUCCESS));
        assertThat(response.data, is(nullValue()));
    }

    @Test
    public void searchRecipe_whenSucceed_returnsRecipe() throws InterruptedException {
        // Arrange (Given)
        mRemoteDataSource.addRecipes(TestData.mRecipes);

        // Act (When)
        mRecipeRepository.searchRecipe(TestData.recipe1.getRecipeId());

        // Assert (Then)
        Resource<Recipe> response = LiveDataTestUtil.getOrAwaitValue(mRecipeRepository.getRecipe());
        assertThat(response.status, is(SUCCESS));
        assertThat(response.data, is(TestData.recipe1));
    }

    /**/

    @Test
    public void getFavorites_noFavorites_returnEmptyList() {
        // Arrange (Given)

        // Act (When)
        List<Recipe> favorites = mRecipeRepository.getFavorites();

        // Assert (Then)
        assertThat(favorites, is(emptyList()));
    }

    @Test
    public void getFavorites_someFavorites_returnAll() {
        // Arrange (Given)
        mFavoritesSource.addFavorite(TestData.recipe1);

        // Act (When)
        List<Recipe> favorites = mRecipeRepository.getFavorites();

        // Assert (Then)
        assertThat(favorites, is(singletonList(TestData.recipe1_favored)));
    }

    @Test
    public void addFavorites_noDuplicateId_addToFavoritesWithFavoriteStatusAsTrue() {
        // Arrange (Given)
        mFavoritesSource.addFavorite(TestData.recipe1);

        // Act (When)
        mRecipeRepository.addFavorite(TestData.recipe2);

        // Assert (Then)
        List<Recipe> favorites = mRecipeRepository.getFavorites();
        assertThat(favorites, is(Arrays.asList(TestData.recipe1_favored, TestData.recipe2_favored)));
    }

    @Test
    public void addFavorites_recipeWithSameIdAlreadyExists_rejectAddition() {
        // Arrange (Given)
        mFavoritesSource.addFavorite(TestData.recipe1);

        // Act (When)
        mRecipeRepository.addFavorite(TestData.recipe1);

        // Assert (Then)
        List<Recipe> favorites = mRecipeRepository.getFavorites();
        assertThat(favorites.size(), is(1));
        assertThat(favorites, is(singletonList(TestData.recipe1_favored)));
    }

    @Test
    public void removeFavorite_removesRecipeFromFavorites() {
        // Arrange (Given)
        mFavoritesSource.addFavorite(TestData.recipe1);
        mFavoritesSource.addFavorite(TestData.recipe2);

        // Act (When)
        mRecipeRepository.removeFavorite(TestData.recipe1_favored);

        // Assert (Then)
        List<Recipe> favorites = mRecipeRepository.getFavorites();
        assertThat(favorites.size(), is(1));
        assertThat(favorites, is(singletonList(TestData.recipe2_favored)));
    }

    @Test
    public void clearFavorites_removeAllFavorites() {
        // Arrange (Given)
        mFavoritesSource.addFavorite(TestData.recipe1);
        mFavoritesSource.addFavorite(TestData.recipe2);

        // Act (When)
        mRecipeRepository.clearFavorites();

        // Assert (Then)
        List<Recipe> favorites = mRecipeRepository.getFavorites();
        assertThat(favorites, is(emptyList()));
    }

}