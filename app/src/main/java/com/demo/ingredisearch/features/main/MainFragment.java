package com.demo.ingredisearch.features.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.demo.ingredisearch.R;
import com.demo.ingredisearch.util.ViewHelper;

import java.util.Objects;

public class MainFragment extends Fragment {

    private LinearLayout searchButton;
    private LinearLayout favButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View root = inflater.inflate(R.layout.fragment_main, container, false);

        initUI(root);
        setupNavigation();

        ViewHelper.hideKeyboard(this);

        return root;
    }

    private void initUI(View root) {
        searchButton = root.findViewById(R.id.searchButton);
        favButton = root.findViewById(R.id.favButton);

        // Type check is required for isolated fragment testing ...
        if (requireActivity() instanceof AppCompatActivity) {
            Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setSubtitle(null);
        }
    }

    private void setupNavigation() {
        searchButton.setOnClickListener(view ->
                Navigation.findNavController(view).navigate(R.id.action_mainFragment_to_searchFragment));

        favButton.setOnClickListener(view ->
                Navigation.findNavController(view).navigate(R.id.action_mainFragment_to_favoritesFragment));
    }

}
