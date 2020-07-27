package com.demo.ingredisearch.features.search;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.demo.ingredisearch.R;
import com.demo.ingredisearch.util.EventObserver;
import com.demo.ingredisearch.util.ViewHelper;
import com.google.android.material.snackbar.Snackbar;

import java.util.Objects;

public class SearchFragment extends Fragment {

    private static final String TAG = "RecipeApp";

    private EditText ingredients;
    private SearchViewModel mViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View root = inflater.inflate(R.layout.fragment_search, container, false);
        initUI(root);
        return root;
    }

    private void initUI(View root) {
        ingredients = root.findViewById(R.id.ingredients);
        Button searchActionButton = root.findViewById(R.id.searchActionButton);

        searchActionButton.setOnClickListener(view -> {
            String query = ingredients.getText().toString();
            ViewHelper.hideKeyboard(this);
            mViewModel.search(query);
        });

        if (requireActivity() instanceof AppCompatActivity) {
            Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setSubtitle(null);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        createViewModel();
        subscribeObservers();
    }

    private void createViewModel() {
        mViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory())
                .get(SearchViewModel.class);
    }

    private void subscribeObservers() {
        mViewModel.navToSearchResults().observe(getViewLifecycleOwner(), new EventObserver<>(query -> {
            if (query != null) {
                navToSearchResults(query);
            }
        }));

        mViewModel.isEmptyQuery().observe(getViewLifecycleOwner(), new EventObserver<>(isEmpty -> emptyQueryAlert()));
    }

    private void emptyQueryAlert() {
        Snackbar.make(requireView(), R.string.search_query_required, Snackbar.LENGTH_LONG).show();
    }

    private void navToSearchResults(String query) {
        Navigation.findNavController(requireView()).navigate(
                SearchFragmentDirections.actionSearchFragmentToSearchResultsFragment(query));
    }
}
