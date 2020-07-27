package com.demo.ingredisearch.repository.sources;

import com.demo.ingredisearch.util.Resource;

public interface ResponseCallback<T> {

    void onDataAvailable(Resource<T> response);

    void onError(Resource<T> response);
}
