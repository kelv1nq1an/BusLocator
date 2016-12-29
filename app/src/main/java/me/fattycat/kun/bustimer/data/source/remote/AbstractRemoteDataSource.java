package me.fattycat.kun.bustimer.data.source.remote;

public abstract class AbstractRemoteDataSource {

    protected ApiService mApi;

    public AbstractRemoteDataSource(ApiService api) {
        mApi = api;
    }
}
