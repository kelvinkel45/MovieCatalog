package com.vincode.moviecatalog.ui.favorite.movie;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.PagedList;

import com.vincode.moviecatalog.data.source.CatalogRepository;
import com.vincode.moviecatalog.data.source.local.entity.MovieEntity;
import com.vincode.moviecatalog.vo.Resource;


public class FavoriteMovieViewModel extends ViewModel {

    private CatalogRepository catalogRepository;


    public FavoriteMovieViewModel(CatalogRepository catalogRepository) {
        this.catalogRepository = catalogRepository;
    }

    LiveData<Resource<PagedList<MovieEntity>>> getFavoriteMovieListPaged() {
        return catalogRepository.getAllFavoriteMoviesPaged();
    }

    void setFavorite(MovieEntity movieEntity) {
        final boolean state = !movieEntity.isFavorite();
        catalogRepository.setMoviesFavorite(movieEntity, state);
    }
}
