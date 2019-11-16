package com.vincode.moviecatalog.di;

import android.app.Application;

import com.vincode.moviecatalog.data.source.CatalogRepository;
import com.vincode.moviecatalog.data.source.local.LocalRepository;
import com.vincode.moviecatalog.data.source.local.room.CatalogDatabase;
import com.vincode.moviecatalog.data.source.remote.RemoteRepository;
import com.vincode.moviecatalog.network.ApiClient;
import com.vincode.moviecatalog.network.ApiInterface;
import com.vincode.moviecatalog.utils.AppExecutors;

public class Injection {
    public static CatalogRepository catalogRepository(Application application) {
        CatalogDatabase catalogDatabase = CatalogDatabase.getINSTANCE(application);

        LocalRepository localRepository = LocalRepository.getINSTANCE(catalogDatabase.catalogDao());
        RemoteRepository remoteRepository = RemoteRepository.getInstance(ApiClient.getClient().create(ApiInterface.class));
        AppExecutors appExecutors = new AppExecutors();
        return CatalogRepository.getInstance(localRepository, remoteRepository, appExecutors);
    }
}
