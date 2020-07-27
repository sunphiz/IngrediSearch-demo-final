package com.demo.ingredisearch;

import com.demo.ingredisearch.features.details.RecipeDetailsFragmentTest;
import com.demo.ingredisearch.features.favorites.FavoritesFragmentTest;
import com.demo.ingredisearch.features.search.SearchFragmentTest;
import com.demo.ingredisearch.features.searchresults.SearchResultsFragmentTest;
import com.demo.ingredisearch.repository.sources.favorites.SharedPreferencesFavoritesSourceTest;
import com.demo.ingredisearch.ui.MainActivityTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@Suite.SuiteClasses({
        SearchFragmentTest.class,
        SearchResultsFragmentTest.class,
        FavoritesFragmentTest.class,
        RecipeDetailsFragmentTest.class,
        SharedPreferencesFavoritesSourceTest.class,
        MainActivityTest.class
})
@RunWith(Suite.class)
public class RecipeInstrumentationTests {
}
