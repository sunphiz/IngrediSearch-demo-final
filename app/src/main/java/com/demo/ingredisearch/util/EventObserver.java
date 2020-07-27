package com.demo.ingredisearch.util;

import androidx.lifecycle.Observer;

import java.util.function.Consumer;

/**
 * An [Observer] for [Event]s, simplifying the pattern of checking if the [Event]'s content has
 * already been handled.
 *
 * [onEventUnhandledContent] is *only* called if the [Event]'s contents has not been handled.
 */
public class EventObserver<T> implements Observer<Event<T>> {

    private Consumer<T> mOnEventUnhandledContent;

    public EventObserver(Consumer<T> mOnEventUnhandledContent) {
        this.mOnEventUnhandledContent = mOnEventUnhandledContent;
    }

    @Override
    public void onChanged(Event<T> event) {
        if (event != null) {
            T ev = event.getContentIfNotHandled();
            if (ev != null)
                mOnEventUnhandledContent.accept(ev);
        }
    }
}
