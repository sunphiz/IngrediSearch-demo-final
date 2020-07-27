package com.demo.ingredisearch.features.search;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import com.demo.ingredisearch.util.Event;
import com.demo.ingredisearch.util.LiveDataTestUtil;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;

public class SearchViewModelTest {

    @Rule
    public InstantTaskExecutorRule rule = new InstantTaskExecutorRule();

    // SUT
    SearchViewModel mViewModel;

    @Before
    public void setUp() throws Exception {
        mViewModel = new SearchViewModel();
    }

    @Test
    public void search_validQuery_triggerNavToSearchResults() throws InterruptedException {
        // Arrange (Given)

        // Act (When)
        mViewModel.search("eggs");

        // Assert (Then)
        Event<String> query = LiveDataTestUtil.getOrAwaitValue(mViewModel.navToSearchResults());
        assertThat(query.getContentIfNotHandled(), is("eggs"));
    }

    @Test
    public void search_emptyQuery_triggerEmptyQueryAlertMessage() throws InterruptedException {
        // Arrange (Given)

        // Act (When)
        mViewModel.search("");

        // Assert (Then)
        Event<Object> result = LiveDataTestUtil.getOrAwaitValue(mViewModel.isEmptyQuery());
        assertThat(result.getContentIfNotHandled(), is(not(nullValue())));
    }

}