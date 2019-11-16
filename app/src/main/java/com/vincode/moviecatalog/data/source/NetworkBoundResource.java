package com.vincode.moviecatalog.data.source;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import com.vincode.moviecatalog.data.source.remote.ApiResponse;
import com.vincode.moviecatalog.utils.AppExecutors;
import com.vincode.moviecatalog.vo.Resource;

public abstract class NetworkBoundResource<ResultType, RequestType> {

    private MediatorLiveData<Resource<ResultType>> result = new MediatorLiveData<>();

    private AppExecutors executors;

    private void onFetchFailed() {
    }

    protected abstract LiveData<ResultType> loadFromDB();

    protected abstract Boolean shouldFetch(ResultType data);

    protected abstract LiveData<ApiResponse<RequestType>> createCall();

    protected abstract void saveCallResult(RequestType data);

    NetworkBoundResource(AppExecutors appExecutors) {
        executors = appExecutors;

        result.setValue(Resource.loading(null));

        LiveData<ResultType> dbSource = loadFromDB();

        result.addSource(dbSource, data -> {
            result.removeSource(dbSource);
            if (shouldFetch(data)) {
                fetchFromNetwork(dbSource);
            } else {
                result.addSource(dbSource, newData -> result.setValue(Resource.success(newData)));
            }
        });
    }

    private void fetchFromNetwork(LiveData<ResultType> dbSource) {
        LiveData<ApiResponse<RequestType>> apiResponse = createCall();

        result.addSource(dbSource, newData -> result.setValue(Resource.loading(newData)));

        result.addSource(apiResponse, response -> {
            result.removeSource(apiResponse);
            result.removeSource(dbSource);

            switch (response.statusResponse) {
                case SUCCESS:
                    executors.diskIO().execute(() -> {
                        saveCallResult(response.body);

                        executors.mainThread().execute(() ->
                                result.addSource(loadFromDB(), newData -> result.setValue(Resource.success(newData))));
                    });
                    break;
                case EMPTY:
                    executors.mainThread().execute(() ->
                            result.addSource(loadFromDB(), newData -> result.setValue(Resource.success(newData))));
                    break;
                case ERROR:
                    onFetchFailed();
                    result.addSource(dbSource, newData ->
                            result.setValue(Resource.error(response.message, newData)));
            }
        });
    }

    LiveData<Resource<ResultType>> asLiveData() {
        return result;
    }
}
