package com.demo.ingredisearch.features.favorites;

import android.os.Bundle;
import android.view.LayoutInflater;
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

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

public class FavoritesFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private RecipeAdapter mAdapter;

    private LinearLayout mLoadingContainer;
    private LinearLayout mErrorContainer;
    private LinearLayout mNoResultsContainer;

    private ViewHelper mViewHelper;
    private FavoritesViewModel mViewModel;

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
        mLoadingContainer = root.findViewById(R.id.loadingContainer);
        mErrorContainer = root.findViewById(R.id.errorContainer);
        mNoResultsContainer = root.findViewById(R.id.noresultsContainer);
        TextView noResultsTitle = root.findViewById(R.id.noresultsTitle);
        noResultsTitle.setText(getString(R.string.nofavorites));

        mViewHelper = new ViewHelper(mLoadingContainer, mErrorContainer, mNoResultsContainer);

        if (requireActivity() instanceof AppCompatActivity) {
            Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setSubtitle(null);
        }

        setupRecyclerView();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        createViewModel();
        subscribeObservers();
    }

    private void createViewModel() {
        RecipeApplication app = (RecipeApplication) requireActivity().getApplication();
        RecipeRepository repository = app.getInjection().getRecipeRepository();
        mViewModel = new ViewModelProvider(this, new FavoritesViewModelFactory(repository))
                .get(FavoritesViewModel.class);
        }

    private void subscribeObservers() {
        mViewModel.getFavorites().observe(getViewLifecycleOwner(), favorites -> {
            if (favorites != null) {
                handleFavorites(favorites);
            }
        });

        mViewModel.navToRecipeDetails().observe(getViewLifecycleOwner(), new EventObserver<>(recipeId -> {
            if (recipeId != null) {
                navigateToRecipeDetails(recipeId);
            }
        }));
    }

    private void handleFavorites(List<Recipe> favorites) {
        if (favorites.isEmpty()) {
            mRecyclerView.setVisibility(View.GONE);
            mViewHelper.showNoResults();
        } else {
            mRecyclerView.setVisibility(View.VISIBLE);
            mViewHelper.hideOthers();
            mAdapter.setRecipes(favorites);
        }
    }

    private void setupRecyclerView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mAdapter = new RecipeAdapter(new RecipeAdapter.Interaction() {
            @Override
            public void onRemoveFavorite(@NotNull Recipe recipe) {
                mViewModel.removeFavorite(recipe);
            }

            @Override
            public void onAddFavorite(@NotNull Recipe item) {
                // Not used here
            }

            @Override
            public void onClickItem(@NotNull Recipe recipe) {
                mViewModel.requestToNavToRecipeDetails(recipe.getRecipeId());
            }
        });
        mRecyclerView.setAdapter(mAdapter);
    }

    private void navigateToRecipeDetails(String recipeId) {
        Navigation.findNavController(requireView()).navigate(
                FavoritesFragmentDirections.actionFavoritesFragmentToRecipeDetailsFragment(recipeId)
        );
    }
}
