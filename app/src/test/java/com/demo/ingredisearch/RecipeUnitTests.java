package com.demo.ingredisearch;

import com.demo.ingredisearch.features.details.RecipeDetailsViewModelTest;
import com.demo.ingredisearch.features.favorites.FavoritesViewModelTest;
import com.demo.ingredisearch.features.search.SearchViewModelTest;
import com.demo.ingredisearch.features.searchresults.SearchResultsViewModelTest;
import com.demo.ingredisearch.repository.RecipeRepositoryTest;
import com.demo.ingredisearch.repository.sources.favorites.FakeFavoritesSourceTest;
import com.demo.ingredisearch.repository.sources.favorites.SharedPreferencesFavoritesSourceRoboTest;
import com.demo.ingredisearch.repository.sources.remote.FakeRemoteDataSourceTest;
import com.demo.ingredisearch.repository.sources.remote.RecipeApiClientTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        FakeRemoteDataSourceTest.class,
        FakeFavoritesSourceTest.class,
        RecipeApiClientTest.class,
        RecipeRepositoryTest.class,
        SharedPreferencesFavoritesSourceRoboTest.class,
        SearchViewModelTest.class,
        SearchResultsViewModelTest.class,
        FavoritesViewModelTest.class,
        RecipeDetailsViewModelTest.class,
})
public class RecipeUnitTests {
}
