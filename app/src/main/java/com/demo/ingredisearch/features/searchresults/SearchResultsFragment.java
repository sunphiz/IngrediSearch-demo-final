package com.demo.ingredisearch.features.searchresults;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.demo.ingredisearch.R;
import com.demo.ingredisearch.RecipeApplication;
import com.demo.ingredisearch.adapters.RecipeAdapter;
import com.demo.ingredisearch.models.Recipe;
import com.demo.ingredisearch.repository.RecipeRepository;
import com.demo.ingredisearch.util.EventObserver;
import com.demo.ingredisearch.util.ViewHelper;

import java.util.List;
import java.util.Objects;

import static android.view.View.GONE;

public class SearchResultsFragment extends Fragment {

    private static final String TAG = "RecipeApp";

    private RecyclerView mRecyclerView;
    private RecipeAdapter mAdapter;

    private ViewHelper mViewHelper;
    private String mQuery;

    private SearchResultsViewModel mViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View root = inflater.inflate(R.layout.fragment_list, container, false);
        initUI(root);
        return root;
    }

    private void initUI(View root) {
        mRecyclerView = root.findViewById(R.id.list);
        LinearLayout mLoadingContainer = root.findViewById(R.id.loadingContainer);
        LinearLayout mErrorContainer = root.findViewById(R.id.errorContainer);
        LinearLayout mNoResultsContainer = root.findViewById(R.id.noresultsContainer);

        mViewHelper = new ViewHelper(mLoadingContainer, mErrorContainer, mNoResultsContainer);

        SearchResultsFragmentArgs arguments = SearchResultsFragmentArgs.fromBundle(requireArguments());
        mQuery = arguments.getQuery();

        TextView mRetry = root.findViewById(R.id.retry);
        mRetry.setOnClickListener(view -> searchRecipes(mQuery));

        if (requireActivity() instanceof AppCompatActivity) {
            Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setSubtitle(null);
        }

        setHasOptionsMenu(true);
        setupRecyclerView();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        createViewModel();
        subscribeObservers();

        searchRecipes(mQuery);
    }

    private void createViewModel() {
        RecipeApplication app = (RecipeApplication) requireActivity().getApplication();
        RecipeRepository recipeRepository = app.getInjection().getRecipeRepository();
        mViewModel = new ViewModelProvider(this, new SearchResultsViewModelFactory(recipeRepository))
                .get(SearchResultsViewModel.class);
    }

    private void subscribeObservers() {
        mViewModel.getRecipes().observe(getViewLifecycleOwner(), recipes -> {
            if (recipes != null) {
                handleRecipes(recipes);
            }
        });

        mViewModel.retry().observe(getViewLifecycleOwner(), new EventObserver<>(event ->  {
                mRecyclerView.setVisibility(GONE);
                mViewHelper.showError();
        }));

        mViewModel.navToRecipeDetails().observe(getViewLifecycleOwner(), new EventObserver<>(recipeId -> {
            if (recipeId != null) {
                navToRecipeDetails(recipeId);
            }
        }));

        mViewModel.navToFavorites().observe(getViewLifecycleOwner(),
                new EventObserver<>(okToGo -> navigateToFavorites()));
        
        mViewModel.isLoading().observe(getViewLifecycleOwner(), new EventObserver<>(isLoading -> {
                mRecyclerView.setVisibility(GONE);
                mViewHelper.showLoading();
        }));
    }

    private void handleRecipes(List<Recipe> recipes) {
        if (recipes.isEmpty()) {
            mRecyclerView.setVisibility(GONE);
            mViewHelper.showNoResults();
        } else {
            mViewHelper.hideOthers();
            mRecyclerView.setVisibility(View.VISIBLE);
            mAdapter.setRecipes(recipes);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.favoritesFragment) {
            mViewModel.requestNavToFavorites();
            return true;
        }
        return false;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.filter_recipes, menu);
    }

    private void setupRecyclerView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        mAdapter = new RecipeAdapter(new RecipeAdapter.Interaction() {
            @Override
            public void onRemoveFavorite(@NonNull Recipe recipe) {
                mViewModel.unmarkFavorite(recipe);
            }

            @Override
            public void onAddFavorite(@NonNull Recipe recipe) {
                mViewModel.markFavorite(recipe);
            }

            @Override
            public void onClickItem(@NonNull Recipe recipe) {
                mViewModel.requestNavToRecipeDetails(recipe.getRecipeId());
            }
        });
        mRecyclerView.setAdapter(mAdapter);
    }

    private void navToRecipeDetails(@NonNull String recipeId) {
        Navigation.findNavController(requireView()).navigate(
                SearchResultsFragmentDirections.actionSearchResultsFragmentToRecipeDetailsFragment(recipeId));
    }

    private void navigateToFavorites() {
        Navigation.findNavController(requireView()).navigate(R.id.action_searchResultsFragment_to_favoritesFragment);
    }

    public void searchRecipes(String query) {
        mViewModel.searchRecipes(query);
    }
}
