package com.demo.ingredisearch.util;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;

public class ViewHelper {
    private LinearLayout mLoadingContainer;
    private LinearLayout mErrorContainer;
    private LinearLayout mNoResultsContainer;

    public ViewHelper(LinearLayout loadingContainer,
                      LinearLayout errorContainer,
                      LinearLayout noResultsContainer) {
        mLoadingContainer = loadingContainer;
        mErrorContainer = errorContainer;
        mNoResultsContainer = noResultsContainer;

        mLoadingContainer.setVisibility(View.GONE);
        mErrorContainer.setVisibility(View.GONE);
        mNoResultsContainer.setVisibility(View.GONE);
    }

    public void hideOthers() {
        mLoadingContainer.setVisibility(View.GONE);
        mErrorContainer.setVisibility(View.GONE);
        mNoResultsContainer.setVisibility(View.GONE);
    }

    public void showError() {
        mLoadingContainer.setVisibility(View.GONE);
        mErrorContainer.setVisibility(View.VISIBLE);
        mNoResultsContainer.setVisibility(View.GONE);
    }

    public void showLoading() {
        mLoadingContainer.setVisibility(View.VISIBLE);
        mErrorContainer.setVisibility(View.GONE);
        mNoResultsContainer.setVisibility(View.GONE);
    }

    public void showNoResults() {
        mLoadingContainer.setVisibility(View.GONE);
        mErrorContainer.setVisibility(View.GONE);
        mNoResultsContainer.setVisibility(View.VISIBLE);
    }

    public static void hideKeyboard(Fragment context) {
        View view = context.requireActivity().getCurrentFocus();
        if (view != null) {
            InputMethodManager imm =
                    (InputMethodManager) context.requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null)
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
