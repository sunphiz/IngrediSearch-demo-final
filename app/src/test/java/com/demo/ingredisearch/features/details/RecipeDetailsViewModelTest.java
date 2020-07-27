package com.demo.ingredisearch.features.details;

import com.demo.ingredisearch.BaseUnitTest;
import com.demo.ingredisearch.TestData;
import com.demo.ingredisearch.models.Recipe;
import com.demo.ingredisearch.repository.sources.remote.FakeRemoteDataSource;
import com.demo.ingredisearch.repository.sources.remote.FakeRemoteDataSource.DataStatus;
import com.demo.ingredisearch.util.LiveDataTestUtil;
import com.demo.ingredisearch.util.Resource;

import org.junit.Before;
import org.junit.Test;

import static com.demo.ingredisearch.TestData.recipeDetails01;
import static com.demo.ingredisearch.util.Status.ERROR;
import static com.demo.ingredisearch.util.Status.SUCCESS;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;

public class RecipeDetailsViewModelTest extends BaseUnitTest {

    // SUT
    private RecipeDetailsViewModel mViewModel;

    @Before
    public void setUp() {
        mViewModel = new RecipeDetailsViewModel(mRecipeRepository);
    }

    @Test
    public void searchRecipe_returnsThatRecipe() throws InterruptedException {
        // Given
        mRemoteDataSource.addRecipes(TestData.mRecipes);

        // When
        mViewModel.searchRecipe(recipeDetails01.getRecipeId());

        // Then
        Resource<Recipe> response = LiveDataTestUtil.getOrAwaitValue(mViewModel.getRecipe());
        assertThat(response.status, is(SUCCESS));
        assertThat(response.data.getRecipeId(), is(recipeDetails01.getRecipeId()));
    }

    @Test
    public void searchRecipe_noMatch_returnsNull() throws InterruptedException {
        // Given

        // When
        mViewModel.searchRecipe(recipeDetails01.getRecipeId());

        // Then
        Resource<Recipe> response = LiveDataTestUtil.getOrAwaitValue(mViewModel.getRecipe());
        assertThat(response.status, is(SUCCESS));
        assertThat(response.data, is(nullValue()));
    }

    @Test
    public void searchRecipe_error_returnsError() throws InterruptedException {
        // Given
        mRemoteDataSource.setDataStatus(DataStatus.Error);

        // When
        mViewModel.searchRecipe(recipeDetails01.getRecipeId());

        // Then
        Resource<Recipe> response = LiveDataTestUtil.getOrAwaitValue(mViewModel.getRecipe());
        assertThat(response.status, is(ERROR));
        assertThat(response.message, is("Network Error"));
        assertThat(response.data, is(nullValue()));
    }
}