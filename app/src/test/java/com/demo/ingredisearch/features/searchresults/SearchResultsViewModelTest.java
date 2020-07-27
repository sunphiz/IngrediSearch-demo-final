package com.demo.ingredisearch.features.searchresults;

import com.demo.ingredisearch.BaseUnitTest;
import com.demo.ingredisearch.TestData;
import com.demo.ingredisearch.models.Recipe;
import com.demo.ingredisearch.repository.sources.remote.FakeRemoteDataSource.DataStatus;
import com.demo.ingredisearch.util.Event;
import com.demo.ingredisearch.util.LiveDataTestUtil;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;

public class SearchResultsViewModelTest extends BaseUnitTest {

    // SUT
    SearchResultsViewModel mViewModel;

    @Before
    public void setUp() throws Exception {
        mViewModel = new SearchResultsViewModel(mRecipeRepository);
    }

    @Test
    public void searchRecipes_allNonFavorites_displayRecipesAsTheyAre() throws Exception {
        // Arrange (Given)
        mRemoteDataSource.addRecipes(TestData.mRecipes);

        // Act (When)
        mViewModel.searchRecipes("eggs");

        // Assert (Then)
        List<Recipe> recipes = LiveDataTestUtil.getOrAwaitValue(mViewModel.getRecipes());
        assertThat(recipes, is(TestData.mRecipes));
    }

    @Test
    public void searchRecipes_emptyRecipes_displayNoRecipes() throws Exception {
        // Arrange (Given)

        // Act (When)
        mViewModel.searchRecipes("eggs");

        // Assert (Then)
        List<Recipe> recipes = LiveDataTestUtil.getOrAwaitValue(mViewModel.getRecipes());
        assertThat(recipes, is(emptyList()));
    }

    @Test
    public void searchRecipes_networkError_displayRetry() throws Exception {
        // Arrange (Given)
        mRemoteDataSource.setDataStatus(DataStatus.Error);

        // Act (When)
        mViewModel.searchRecipes("eggs");

        // Assert (Then)
        List<Recipe> recipes = LiveDataTestUtil.getOrAwaitValue(mViewModel.getRecipes());
        assertThat(recipes, is(nullValue()));
    }

    @Test
    public void searchRecipes_withSomeFavorites_displayRecipesAsDecorated() throws Exception {
        // Arrange (Given)
        mRemoteDataSource.addRecipes(TestData.mRecipesWithFavorites);
        mFavoritesSource.addFavorites(singletonList(TestData.recipe1));

        // Act (When)
        mViewModel.searchRecipes("eggs");

        // Assert (Then)
        List<Recipe> recipes = LiveDataTestUtil.getOrAwaitValue(mViewModel.getRecipes());
        assertThat(getFavoritesStatus(recipes), is(Arrays.asList(true, false, false)));
    }

    private List<Boolean> getFavoritesStatus(List<Recipe> recipes) {
        return recipes.stream().map(Recipe::isFavorite).collect(Collectors.toList());
    }

    @Test
    public void markFavorite_reloadUpdatedRecipes() throws Exception {
        // Arrange (Given)
        mRemoteDataSource.addRecipes(TestData.mRecipes);

        // Act (When)
        mViewModel.markFavorite(TestData.recipe1);

        // Assert (Then)

        List<Recipe> recipes = LiveDataTestUtil.getOrAwaitValue(mViewModel.getRecipes());
        assertThat(getFavoritesStatus(recipes), is(Arrays.asList(true, false, false, false)));
    }

    @Test
    public void unMarkFavorite_reloadUpdatedRecipes() throws Exception {
        // Arrange (Given)
        mRemoteDataSource.addRecipes(TestData.recipe1, TestData.recipe2);
        mFavoritesSource.addFavorites(TestData.recipe1, TestData.recipe2);

        // Act (When)
        mViewModel.unmarkFavorite(TestData.recipe1_favored);

        // Assert (Then)
        List<Recipe> recipes = LiveDataTestUtil.getOrAwaitValue(mViewModel.getRecipes());
        assertThat(getFavoritesStatus(recipes), is(Arrays.asList(false, true)));
    }

    @Test
    public void requestToRecipeDetails_shouldTriggerNavToRecipeDetails() throws Exception {
        // Arrange (Given)
        mRemoteDataSource.addRecipes(TestData.recipe1);

        // Act (When)
        mViewModel.requestNavToRecipeDetails(TestData.recipe1.getRecipeId());

        // Assert (Then)
        Event<String> recipeId = LiveDataTestUtil.getOrAwaitValue(mViewModel.navToRecipeDetails());
        assertThat(recipeId.getContentIfNotHandled(), is(TestData.recipe1.getRecipeId()));
    }

    @Test
    public void requestToFavorites_shouldTriggerNavToFavorites() throws Exception {
        // Arrange (Given)

        // Act (When)
        mViewModel.requestNavToFavorites();

        // Assert (Then)
        Event<Object> result = LiveDataTestUtil.getOrAwaitValue(mViewModel.navToFavorites());
        assertThat(result.getContentIfNotHandled(), is(not(nullValue())));

    }


}