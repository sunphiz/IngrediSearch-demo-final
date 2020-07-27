package com.demo.ingredisearch.features.details;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.demo.ingredisearch.R;
import com.demo.ingredisearch.RecipeApplication;
import com.demo.ingredisearch.models.Recipe;
import com.demo.ingredisearch.repository.RecipeRepository;
import com.demo.ingredisearch.util.Resource;
import com.demo.ingredisearch.util.ViewHelper;

import java.util.Objects;

public class RecipeDetailsFragment extends Fragment {

    private static final String TAG = "RecipeApp";

    private ScrollView mScrollView;
    private ViewHelper mViewHelper;
    private RecipeDetailsViewModel mViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View root = inflater.inflate(R.layout.fragment_details, container, false);
        initUI(root);
        return root;
    }

    private void initUI(View root) {
        mScrollView = root.findViewById(R.id.parent);
        mRecipeImage = root.findViewById(R.id.recipe_image);
        mRecipeTitle = root.findViewById(R.id.recipe_title);
        mRecipeRank = root.findViewById(R.id.recipe_social_score);
        mRecipeIngredientsContainer = root.findViewById(R.id.ingredients_container);

        LinearLayout mLoadingContainer = root.findViewById(R.id.loadingContainer);
        LinearLayout mNoResultsContainer = root.findViewById(R.id.noresultsContainer);
        LinearLayout mErrorContainer = root.findViewById(R.id.errorContainer);

        mViewHelper = new ViewHelper(mLoadingContainer, mErrorContainer, mNoResultsContainer);

        if (requireActivity() instanceof AppCompatActivity) {
            Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setSubtitle(null);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        createViewModel();
        setupObservers();

        RecipeDetailsFragmentArgs arguments = RecipeDetailsFragmentArgs.fromBundle(requireArguments());
        mViewModel.searchRecipe(arguments.getRecipeId());
    }

    private void setupObservers() {
        mViewModel.getRecipe().observe(getViewLifecycleOwner(), response -> {
            if (response != null)
                handleResponse(response);
        });
    }

    private void createViewModel() {
        RecipeApplication app = (RecipeApplication) requireActivity().getApplication();
        RecipeRepository repository = app.getInjection().getRecipeRepository();
        mViewModel = new ViewModelProvider(this,
                new RecipeDetailsViewModelFactory(repository))
                .get(RecipeDetailsViewModel.class);
    }

    private <T> void handleResponse(Resource<Recipe> response) {
        switch (response.status) {
            case LOADING:
                mViewHelper.showLoading();
                break;
            case ERROR:
                mViewHelper.showError();
                break;
            default:
                mViewHelper.hideOthers();
                if (response.data != null) {
                    showRecipe(response.data);
                    mScrollView.setVisibility(View.VISIBLE);
                } else {
                    mScrollView.setVisibility(View.GONE);
                    mViewHelper.showNoResults();
                }
        }
    }

    private AppCompatImageView mRecipeImage;
    private TextView mRecipeTitle, mRecipeRank;
    private LinearLayout mRecipeIngredientsContainer;

    private void showRecipe(Recipe recipe) {
        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.drawable.ic_launcher_background);

        Glide.with(this)
                .setDefaultRequestOptions(requestOptions)
                .load(recipe.getImageUrl())
                .into(mRecipeImage);

        mRecipeTitle.setText(recipe.getTitle());
        mRecipeRank.setText(String.valueOf(Math.round(recipe.getSocial_rank())));

        mRecipeIngredientsContainer.removeAllViews();
        for (String ingredient : recipe.getIngredients()) {
            TextView textView = new TextView(getContext());
            textView.setText(ingredient);
            textView.setTextSize(15);
            textView.setLayoutParams(new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
            ));
            mRecipeIngredientsContainer.addView(textView);
        }
    }
}
