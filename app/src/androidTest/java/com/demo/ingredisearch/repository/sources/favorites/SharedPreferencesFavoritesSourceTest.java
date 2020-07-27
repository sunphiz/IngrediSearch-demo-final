package com.demo.ingredisearch.repository.sources.favorites;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.demo.ingredisearch.TestData;
import com.demo.ingredisearch.models.Recipe;
import com.demo.ingredisearch.repository.FavoritesSource;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.List;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@RunWith(AndroidJUnit4.class)
public class SharedPreferencesFavoritesSourceTest {

    // SUT
    FavoritesSource mFavoritesSource;

    @Before
    public void setUp() throws Exception {
        mFavoritesSource = new SharedPreferencesFavoritesSource(
                ApplicationProvider.getApplicationContext()
        );
        mFavoritesSource.clearFavorites();
    }

    @Test
    public void getFavorites_noFavorites_returnEmptyList() {
        // Arrange (Given)

        // Act (When)
        List<Recipe> favorites = mFavoritesSource.getFavorites();

        // Assert (Then)
        assertThat(favorites, is(emptyList()));
    }

    @Test
    public void getFavorites_someFavorites_returnAll() {
        // Arrange (Given)
        mFavoritesSource.addFavorite(TestData.recipe1);

        // Act (When)
        List<Recipe> favorites = mFavoritesSource.getFavorites();

        // Assert (Then)
        assertThat(favorites, is(singletonList(TestData.recipe1_favored)));
    }

    @Test
    public void addFavorites_noDuplicateId_addToFavoritesWithFavoriteStatusAsTrue() {
        // Arrange (Given)
        mFavoritesSource.addFavorite(TestData.recipe1);

        // Act (When)
        mFavoritesSource.addFavorite(TestData.recipe2);

        // Assert (Then)
        List<Recipe> favorites = mFavoritesSource.getFavorites();
        assertThat(favorites, is(Arrays.asList(TestData.recipe1_favored, TestData.recipe2_favored)));
    }

    @Test
    public void addFavorites_recipeWithSameIdAlreadyExists_rejectAddition() {
        // Arrange (Given)
        mFavoritesSource.addFavorite(TestData.recipe1);

        // Act (When)
        mFavoritesSource.addFavorite(TestData.recipe1);

        // Assert (Then)
        List<Recipe> favorites = mFavoritesSource.getFavorites();
        assertThat(favorites.size(), is(1));
        assertThat(favorites, is(singletonList(TestData.recipe1_favored)));
    }

    @Test
    public void removeFavorite_removesRecipeFromFavorites() {
        // Arrange (Given)
        mFavoritesSource.addFavorite(TestData.recipe1);
        mFavoritesSource.addFavorite(TestData.recipe2);

        // Act (When)
        mFavoritesSource.removeFavorite(TestData.recipe1_favored);

        // Assert (Then)
        List<Recipe> favorites = mFavoritesSource.getFavorites();
        assertThat(favorites.size(), is(1));
        assertThat(favorites, is(singletonList(TestData.recipe2_favored)));
    }

    @Test
    public void clearFavorites_removeAllFavorites() {
        // Arrange (Given)
        mFavoritesSource.addFavorite(TestData.recipe1);
        mFavoritesSource.addFavorite(TestData.recipe2);

        // Act (When)
        mFavoritesSource.clearFavorites();

        // Assert (Then)
        List<Recipe> favorites = mFavoritesSource.getFavorites();
        assertThat(favorites, is(emptyList()));
    }

    @After
    public void tearDown() throws Exception {
        mFavoritesSource.clearFavorites();
    }
}