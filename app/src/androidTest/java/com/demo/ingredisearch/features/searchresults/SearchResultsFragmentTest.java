package com.demo.ingredisearch.features.searchresults;

import android.os.Bundle;

import androidx.fragment.app.testing.FragmentScenario;
import androidx.navigation.Navigation;
import androidx.navigation.testing.TestNavHostController;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.demo.ingredisearch.BaseTest;
import com.demo.ingredisearch.R;
import com.demo.ingredisearch.TestData;
import com.demo.ingredisearch.repository.sources.remote.FakeRemoteDataSource.DataStatus;

import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withTagValue;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.demo.ingredisearch.util.CustomViewMatchers.withRecyclerView;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.allOf;

@RunWith(AndroidJUnit4.class)
public class SearchResultsFragmentTest extends BaseTest {

    @Test
    public void SearchResultsFragmentInView() {
        // Arrange (Given)
         mRemoteDataSource.addRecipes(TestData.mRecipes);

        // Act (When)
        Bundle args = new SearchResultsFragmentArgs.Builder("eggs").build().toBundle();
        FragmentScenario.launchInContainer(SearchResultsFragment.class,
                 args, R.style.AppTheme, null);

        // Assert (Then)
         onView(withId(R.id.list)).check(matches(isDisplayed()));
         onView(withRecyclerView(R.id.list).atPositionOnView(0, R.id.title))
                 .check(matches(withText(TestData.recipe1.getTitle())));
    }

    @Test
    public void searchRecipes_queryWithNoResults_displayNoRecipesView() {
        // Arrange (Given)

        // Act (When)
        Bundle args = new SearchResultsFragmentArgs.Builder("query").build().toBundle();
        FragmentScenario.launchInContainer(SearchResultsFragment.class,
                args, R.style.AppTheme, null);

        // Assert (Then)
        onView(withId(R.id.noresultsContainer)).check(matches(isDisplayed()));
        onView(allOf(withId(R.id.noresultsTitle), withText(R.string.noresults))).check(matches(isDisplayed()));
    }

    @Test
    public void searchRecipes_errorOnConnection_displayRetryView() {
        // Arrange (Given)
        mRemoteDataSource.setDataStatus(DataStatus.Error);

        // Act (When)
        Bundle args = new SearchResultsFragmentArgs.Builder("query").build().toBundle();
        FragmentScenario.launchInContainer(SearchResultsFragment.class,
                args, R.style.AppTheme, null);

        // Assert (Then)
        onView(allOf(withId(R.id.retry), withText(R.string.retry))).check(matches(isDisplayed()));
    }

    @Test
    public void searchRecipes_errorOnConnection_displayRetryView_andThen_retry() {
        // Arrange (Given)
        mRemoteDataSource.addRecipes(TestData.mRecipes);
        mRemoteDataSource.setDataStatus(DataStatus.Error);

        // Act (When)
        Bundle args = new SearchResultsFragmentArgs.Builder("query").build().toBundle();
        FragmentScenario.launchInContainer(SearchResultsFragment.class,
                args, R.style.AppTheme, null);

        // Assert (Then)
        onView(withId(R.id.retry)).check(matches(isDisplayed()));

        // Act (When)
        mRemoteDataSource.setDataStatus(DataStatus.Success);
        onView(withId(R.id.retry)).perform(click());

        // Assert (Then)
        onView(withId(R.id.list)).check(matches(isDisplayed()));
        onView(withRecyclerView(R.id.list).atPositionOnView(0, R.id.title))
                .check(matches(withText(TestData.recipe1.getTitle())));
    }

    @Test
    public void selectRecipeAsFavorite_markItsStatusAsFavorite() throws Exception {
        // Given
        mRemoteDataSource.addRecipes(TestData.mRecipes);

        Bundle args = new SearchResultsFragmentArgs.Builder("query").build().toBundle();
        FragmentScenario.launchInContainer(SearchResultsFragment.class,
                args, R.style.AppTheme, null);

        onView(withRecyclerView(R.id.list).atPositionOnView(0, R.id.favButton))
                .check(matches(withTagValue(is(R.drawable.ic_favorite_border_24dp))));

        // Act (When)
        onView(withRecyclerView(R.id.list).atPositionOnView(0, R.id.favButton)).perform(click());

        // Assert (Then)
        onView(withRecyclerView(R.id.list).atPositionOnView(0, R.id.favButton))
                .check(matches(withTagValue(is(R.drawable.ic_favorite_24dp))));
    }

    @Test
    public void selectRecipeAsUnFavorite_markItsStatusAsUnFavorite() throws Exception {
        // Given
        mRemoteDataSource.addRecipes(TestData.mRecipes);
        mFavoritesSource.addFavorites(TestData.recipe1);

        Bundle args = new SearchResultsFragmentArgs.Builder("query").build().toBundle();
        FragmentScenario.launchInContainer(SearchResultsFragment.class,
                args, R.style.AppTheme, null);

        onView(withRecyclerView(R.id.list).atPositionOnView(0, R.id.favButton))
                .check(matches(withTagValue(is(R.drawable.ic_favorite_24dp))));

        // Act (When)
        onView(withRecyclerView(R.id.list).atPositionOnView(0, R.id.favButton)).perform(click());

        // Assert (Then)
        onView(withRecyclerView(R.id.list).atPositionOnView(0, R.id.favButton))
                .check(matches(withTagValue(is(R.drawable.ic_favorite_border_24dp))));
    }

    @Test
    public void navigateToRecipeDetailsView() {
        // Arrange (Given)
        mRemoteDataSource.addRecipes(TestData.mRecipes);

        TestNavHostController navHostController = new TestNavHostController(
                ApplicationProvider.getApplicationContext()
        );
        navHostController.setGraph(R.navigation.navigation);
        navHostController.setCurrentDestination(R.id.searchResultsFragment);

        Bundle args = new SearchResultsFragmentArgs.Builder("query").build().toBundle();
        FragmentScenario<SearchResultsFragment> scenario = FragmentScenario.launchInContainer(SearchResultsFragment.class,
                args, R.style.AppTheme, null);

        scenario.onFragment(fragment ->
                Navigation.setViewNavController(fragment.requireView(), navHostController));

        // Act (When)
        onView(withId(R.id.list)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        // Assert (Then)
        assertThat(navHostController.getCurrentDestination().getId(), is(R.id.recipeDetailsFragment));
    }
}