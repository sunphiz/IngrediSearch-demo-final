package com.demo.ingredisearch.features.favorites;

import androidx.fragment.app.testing.FragmentScenario;
import androidx.navigation.Navigation;
import androidx.navigation.testing.TestNavHostController;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.demo.ingredisearch.BaseTest;
import com.demo.ingredisearch.R;
import com.demo.ingredisearch.TestData;

import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasSibling;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.demo.ingredisearch.util.CustomViewMatchers.withRecyclerView;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@RunWith(AndroidJUnit4.class)
public class FavoritesFragmentTest extends BaseTest {

    @Test
    public void favoritesFragmentInView_withFavorites() throws Exception {
        // Arrange (Given)
        mFavoritesSource.addFavorites(TestData.mFavorites);

        // Act (When)
        FragmentScenario.launchInContainer(FavoritesFragment.class, null, R.style.AppTheme, null);

        // Assert (Then)
        onView(withId(R.id.list)).check(matches(isDisplayed()));
    }

    @Test
    public void favoritesFragmentInView_withNoFavorites() {
        // Given

        // When
        FragmentScenario.launchInContainer(FavoritesFragment.class, null, R.style.AppTheme, null);

        // Then - No favorites yet
        onView(withId(R.id.noresultsContainer)).check(matches(isDisplayed()));
        onView(withText(R.string.nofavorites)).check(matches(isDisplayed()));
    }

    @Test
    public void selectRecipeAsNonFavorite_removesTheRecipeFromView() throws Exception {
        // Arrange (Given)
        mFavoritesSource.addFavorites(TestData.mFavorites);
        FragmentScenario.launchInContainer(FavoritesFragment.class, null, R.style.AppTheme, null);

        // Act (When)
        onView(withRecyclerView(R.id.list).atPositionOnView(0, R.id.favButton)).perform(click());

        // Assert (Then)
        onView(withRecyclerView(R.id.list).atPositionOnView(0, R.id.title))
                .check(matches(withText(TestData.recipe2_favored.getTitle())));
    }

    @Test
    public void selectLastRecipeAsNonFavorite_displaysNoFavorites() throws Exception {
        // Arrange (Given)
        mFavoritesSource.addFavorites(TestData.recipe1);
        FragmentScenario.launchInContainer(FavoritesFragment.class, null, R.style.AppTheme, null);

        onView(allOf(withText(TestData.recipe1.getTitle()), hasSibling(withId(R.id.favButton))))
                .check(matches(isDisplayed()));

        // Act (When)
        onView(withRecyclerView(R.id.list).atPositionOnView(0, R.id.favButton)).perform(click());

        // Assert (Then)
        onView(withId(R.id.noresultsContainer)).check(matches(isDisplayed()));
        onView(allOf(withId(R.id.noresultsTitle), withText(R.string.nofavorites))).check(matches(isDisplayed()));
    }


    @Test
    public void navigateToRecipeDetailsView() {
        // Arrange (Given)
        mFavoritesSource.addFavorites(TestData.mFavorites);
        TestNavHostController navHostController = new TestNavHostController(
                ApplicationProvider.getApplicationContext()
        );
        navHostController.setGraph(R.navigation.navigation);
        navHostController.setCurrentDestination(R.id.favoritesFragment);

        FragmentScenario<FavoritesFragment> scenario = FragmentScenario.launchInContainer(FavoritesFragment.class,
            null, R.style.AppTheme, null);

        scenario.onFragment(fragment ->
                Navigation.setViewNavController(fragment.requireView(), navHostController));

        // Act (When)
        onView(withId(R.id.list)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        // Assert (Then)
        assertThat(navHostController.getCurrentDestination().getId(), is(R.id.recipeDetailsFragment));
    }

}