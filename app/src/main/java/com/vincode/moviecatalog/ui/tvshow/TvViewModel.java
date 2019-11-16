package com.vincode.moviecatalog.ui.tvshow;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.vincode.moviecatalog.data.source.CatalogRepository;
import com.vincode.moviecatalog.data.source.local.entity.TvShowEntity;
import com.vincode.moviecatalog.vo.Resource;

import java.util.List;

public class TvViewModel extends ViewModel {

    private CatalogRepository catalogRepository;
    private MutableLiveData<Long> tvShowId = new MutableLiveData<>();
    private MutableLiveData<String> mLosin = new MutableLiveData<>();


    public TvViewModel(CatalogRepository catalogRepository) {
        this.catalogRepository = catalogRepository;
    }

    public LiveData<Resource<List<TvShowEntity>>> tvShows = Transformations.switchMap(mLosin,
            data -> catalogRepository.getAllTvShows());

    LiveData<Resource<TvShowEntity>> getTvShowDetail = Transformations.switchMap(tvShowId,
            tvShowId -> catalogRepository.getDetailTvShow(tvShowId));


    void setUsername(String username) {
        mLosin.setValue(username);
    }

    void setTvShowId(long tvShowId) {
        this.tvShowId.setValue(tvShowId);
    }

    void setFavoriteTvShow() {
        if (getTvShowDetail.getValue() != null) {
            TvShowEntity tvShowEntity = getTvShowDetail.getValue().data;

            if (tvShowEntity != null) {
                final boolean state = !tvShowEntity.isFavorite();
                catalogRepository.setFavoriteTvShow(tvShowEntity, state);
            }
        }
    }


}
