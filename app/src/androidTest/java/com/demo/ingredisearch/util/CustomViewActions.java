package com.demo.ingredisearch.util;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.IdRes;
import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;

import org.hamcrest.Matcher;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public interface CustomViewActions {

    static ViewAction clickChildWithId(@IdRes final int id) {
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return null;
            }

            @Override
            public String getDescription() {
                return "Click on a child View with specified id";
            }

            @Override
            public void perform(UiController uiController, View view) {
                View child = view.findViewById(id);
                child.performClick();
            }
        };
    }

    static ViewAction clickChildWithText(final String text) {
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return null;
            }

            @Override
            public String getDescription() {
                return "Click on a child View with specified text";
            }

            @Override
            public void perform(UiController uiController, View view) {
                for (View child : ViewTreeAnalyzer.getAllChildren(view)) {
                    if (child instanceof TextView) {
                        TextView textView = (TextView) child;
                        String label = textView.getText().toString();
                        if (text.equalsIgnoreCase(label)) {
                            textView.performClick();
                            return;
                        }
                    }
                }
            }
        };
    }

    class ViewTreeAnalyzer {
        public static List<View> getAllChildren(View parent) {
            if (!(parent instanceof ViewGroup)) {
                return Collections.singletonList(parent);
            }

            ArrayList<View> result = new ArrayList<>();
            ViewGroup viewGroup = (ViewGroup) parent;
            for (int i = 0; i < viewGroup.getChildCount(); i++) {
                View child = viewGroup.getChildAt(i);
                result.addAll(getAllChildren(child));
            }
            return result;
        }
    }

}
