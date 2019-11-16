package com.vincode.moviecatalog.ui.favorite.tvshow;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.PagedList;

import com.vincode.moviecatalog.data.source.CatalogRepository;
import com.vincode.moviecatalog.data.source.local.entity.TvShowEntity;
import com.vincode.moviecatalog.vo.Resource;

public class FavoriteTvViewModel extends ViewModel {

    private CatalogRepository catalogRepository;


    public FavoriteTvViewModel(CatalogRepository catalogRepository) {
        this.catalogRepository = catalogRepository;
    }

    LiveData<Resource<PagedList<TvShowEntity>>> getFavoriteTvShowListPaged() {
        return catalogRepository.getAllFavoriteTvShowsPaged();
    }

    void setFavorite(TvShowEntity tvShowEntity) {
        final boolean state = !tvShowEntity.isFavorite();
        catalogRepository.setFavoriteTvShow(tvShowEntity, state);
    }
}
