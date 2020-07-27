package com.demo.ingredisearch.util;

import java.util.concurrent.Executor;

public class SingleExecutors extends AppExecutors {
    private static final Executor instant = Runnable::run;

    public SingleExecutors() {
        super(instant, instant);
    }
}
