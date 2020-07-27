package com.demo.ingredisearch.ui;

import android.view.Gravity;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.contrib.DrawerActions;
import androidx.test.espresso.contrib.NavigationViewActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import com.demo.ingredisearch.R;

import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.DrawerMatchers.isClosed;
import static androidx.test.espresso.contrib.DrawerMatchers.isOpen;
import static androidx.test.espresso.matcher.ViewMatchers.hasSibling;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

public class MainActivityTest {

    @Rule
    public ActivityScenarioRule<MainActivity> scenarioRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void activityInView() {
        // check toolbar main title displayed
        onView(withText(R.string.main_title)).check(matches(isDisplayed()));

        // check whether 'searchButton' view displayed
        onView(withId(R.id.searchButton)).check(matches(isDisplayed()));

        // check whether 'favButton' view displayed
        onView(withId(R.id.favButton)).check(matches(isDisplayed()));
    }

    @Test
    public void testOpenDrawer() {
        // Arrange (Given)

        // Act (When)
        // open drawer
        openDrawer();

        // Assert (Then)
        // check drawer Header displayed
        onView(allOf(withClassName(is(AppCompatImageView.class.getName())),
                withContentDescription(R.string.search_header_image_content_description))
        ).check(matches(isDisplayed()));
        onView(withText(R.string.drawer_search_title)).check(matches(isDisplayed()));

        // check drawer Contents displayed
        onView(allOf(withId(R.id.design_menu_item_text), withText(R.string.home))).check(matches(isDisplayed()));
        onView(allOf(withId(R.id.design_menu_item_text), withText(R.string.search))).check(matches(isDisplayed()));
        onView(allOf(withId(R.id.design_menu_item_text), withText(R.string.favorites))).check(matches(isDisplayed()));
    }

    @Test
    public void testCloseDrawer() {
        // Arrange (Given)

        // Act (When)
        // open drawer and close drawer
        openDrawer();
        closeDrawer();

        // Assert (Then)
        // check drawer Header displayed
//        onView(allOf(withClassName(is(AppCompatImageView.class.getName())),
//                withContentDescription(R.string.search_header_image_content_description))
//        ).check(matches(not(isDisplayed())));
        onView(allOf(instanceOf(AppCompatImageView.class),
                withContentDescription(R.string.search_header_image_content_description))
        ).check(matches(not(isDisplayed())));
        onView(withText(R.string.drawer_search_title)).check(matches(not(isDisplayed())));

        // check drawer Contents displayed
        onView(allOf(withId(R.id.design_menu_item_text), withText(R.string.home))).check(matches(not(isDisplayed())));
        onView(allOf(withId(R.id.design_menu_item_text), withText(R.string.search))).check(matches(not(isDisplayed())));
        onView(allOf(withId(R.id.design_menu_item_text), withText(R.string.favorites))).check(matches(not(isDisplayed())));
    }

    @Test
    public void navigateToHomeScreen() {
        // Arrange (Given)
        // open drawer
        openDrawer();

        // Act (When)
        // click on Home menu item
//        onView(withText(R.string.home)).perform(click());
//        onView(allOf(withId(R.id.design_menu_item_text), withText(R.string.home))).perform(click());
        onView(withId(R.id.nav_view))
                .perform(NavigationViewActions.navigateTo(R.id.mainFragment));

        // Assert (Then)
        // check whether searchButton and favButton is displayed
        onView(withId(R.id.searchButton)).check(matches(isDisplayed()));
        onView(withId(R.id.favButton)).check(matches(isDisplayed()));
    }

    @Test
    public void navigateToSearchScreen() {
        // Arrange (Given)
        // open drawer
        openDrawer();

        // Act (When)
        // click on Search menu item
        onView(withId(R.id.nav_view)).perform(NavigationViewActions.navigateTo(R.id.searchFragment));

        // Assert (Then)
        // check whether search_title text is displayed
        onView(withText(R.string.search_title)).check(matches(isDisplayed()));

        // check whether search_header text is displayed
        onView(withText(R.string.search_header)).check(matches(isDisplayed()));

        // check whether ingredients is displayed
        onView(withId(R.id.ingredients)).check(matches(isDisplayed()));

        // check whether searchActionButton is displayed
        onView(withId(R.id.searchActionButton)).check(matches(isDisplayed()));
    }

    @Test
    public void navigateToFavoriteScreen() {
        // Arrange (Given)
        // open drawer
        openDrawer();

        // Act (When)
        // click on Favorites menu item
        onView(withId(R.id.nav_view)).perform(NavigationViewActions.navigateTo(R.id.favoritesFragment));

        // Assert (Then)
        // check whether "Favorites" toolbar title is displayed
        onView(allOf(
                withParent(withId(R.id.toolbar)),
                withText(R.string.favorites_title),
                hasSibling(withContentDescription("Open navigation drawer"))
        )).check(matches(isDisplayed()));

//        onView(allOf(withParent(withId(R.id.toolbar)), withText(R.string.favorites_title))).check(matches(isDisplayed()));
    }

    @Test
    public void navigateToSearchScreen_and_backToHomeScreen() {
        // Arrange (Given)
        // click on Search button
        onView(withId(R.id.searchButton)).perform(click());

        // Act (When)
        // press on the back button or navigateBack()
        Espresso.pressBack();

        // Assert (Then)
        // check whether searchButton and favButton is displayed
        onView(withId(R.id.searchButton)).check(matches(isDisplayed()));
        onView(withId(R.id.favButton)).check(matches(isDisplayed()));
    }

    @Test
    public void navigateToSearchScreenToSearchResults_and_backToMainScreen() {
        // Arrange (Given)
        // click on Search button
        onView(withId(R.id.searchButton)).perform(click());

        // enter query and press searchActionButton
        onView(withId(R.id.ingredients)).perform(typeText("some query"), closeSoftKeyboard());
        onView(withId(R.id.searchActionButton)).perform(click());

        // Act (When)
        // press back button twice
        Espresso.pressBack();
        navigateBack();

        // Assert (Then)
        // check whether mainScreen in view
        onView(withId(R.id.searchButton)).check(matches(isDisplayed()));
        onView(withId(R.id.favButton)).check(matches(isDisplayed()));
    }

    @Test
    public void navigateToSearchScreenToSearchResults_and_backToSearchResultsScreen() throws InterruptedException {
        // Arrange (Given)
        // click on Search button
        onView(withId(R.id.searchButton)).perform(click());

        // enter query and press searchActionButton
        onView(withId(R.id.ingredients)).perform(typeText("some query"), closeSoftKeyboard());
        onView(withId(R.id.searchActionButton)).perform(click());

        // Act (When)
        // press back button
        Espresso.pressBack();

        // Assert (Then)
        // check whether mainScreen in view
        onView(withId(R.id.searchActionButton)).check(matches(isDisplayed()));
        onView(withId(R.id.ingredients)).check(matches(isDisplayed()));
    }

    private void openDrawer() {
        onView(withId(R.id.drawer_layout))
                .check(matches(isClosed(Gravity.START)))
                .perform(DrawerActions.open());
    }

    private void closeDrawer() {
        onView(withId(R.id.drawer_layout))
                .check(matches(isOpen(Gravity.START)))
                .perform(DrawerActions.close());
    }

    private void navigateBack() {
        // contentDescription "Navigate up" or R.string.abc_action_bar_up_description
        onView(withContentDescription(R.string.abc_action_bar_up_description)).perform(click());
    }

}