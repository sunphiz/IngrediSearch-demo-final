package com.demo.ingredisearch.features.favorites;

import com.demo.ingredisearch.BaseUnitTest;
import com.demo.ingredisearch.TestData;
import com.demo.ingredisearch.models.Recipe;
import com.demo.ingredisearch.util.Event;
import com.demo.ingredisearch.util.LiveDataTestUtil;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static java.util.Collections.emptyList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class FavoritesViewModelTest extends BaseUnitTest {

    // SUT
    FavoritesViewModel mViewModel;

    @Before
    public void setUp() {
        mViewModel = new FavoritesViewModel(mRecipeRepository);
    }

    @Test
    public void getFavorites_returnFavoriteRecipes() throws Exception {
        // Arrange (Given)
        mFavoritesSource.addFavorites(TestData.mFavorites);

        // Act (When)
        List<Recipe> favorites = LiveDataTestUtil.getOrAwaitValue(mViewModel.getFavorites());

        // Assert (Then)
        assertThat(favorites, is(TestData.mFavorites));
    }

    @Test
    public void removeFavorite_shouldRemoveFavorite() throws InterruptedException {
        // Arrange (Given)
        mFavoritesSource.addFavorites(TestData.mFavorites);

        // Act (When)
        mViewModel.removeFavorite(TestData.recipe1_favored);

        // Assert (Then)
        List<Recipe> favorites = LiveDataTestUtil.getOrAwaitValue(mViewModel.getFavorites());
        assertThat(favorites.size(), is(1));
        assertThat(favorites.contains(TestData.recipe1_favored), is(false));
        assertThat(favorites.contains(TestData.recipe2_favored), is(true));
    }

    @Test
    public void clearFavorites_shouldResetFavoritesToEmpty() throws Exception {
        // Arrange (Given)
        mFavoritesSource.addFavorites(TestData.mFavorites);

        // Act (When)
        mViewModel.clearFavorites();

        // Assert (Then)
        List<Recipe> favorites = LiveDataTestUtil.getOrAwaitValue(mViewModel.getFavorites());
        assertThat(favorites, is(emptyList()));
    }

    @Test
    public void requestToRecipeDetails_shouldTriggerNavToRecipeDetails() throws Exception {
        // Arrange (Given)
        mFavoritesSource.addFavorites(TestData.mFavorites);

        // Act (When)
        mViewModel.requestToNavToRecipeDetails(TestData.recipe1_favored.getRecipeId());

        // Assert (Then)
        Event<String> result = LiveDataTestUtil.getOrAwaitValue(mViewModel.navToRecipeDetails());
        assertThat(result.getContentIfNotHandled(), is(TestData.recipe1_favored.getRecipeId()));
    }
}