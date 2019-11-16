package com.vincode.moviecatalog.ui.favorite.tvshow;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.paging.PagedList;

import com.vincode.moviecatalog.data.source.CatalogRepository;
import com.vincode.moviecatalog.data.source.local.entity.TvShowEntity;
import com.vincode.moviecatalog.vo.Resource;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class FavoriteTvViewModelTest {

    private CatalogRepository catalogRepository = mock(CatalogRepository.class);
    private FavoriteTvViewModel favoriteTvViewModel;

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void setUp() {
        favoriteTvViewModel = new FavoriteTvViewModel(catalogRepository);
    }

    @Test
    public void getMovieFavorite() {
        MutableLiveData<Resource<PagedList<TvShowEntity>>> tvShows = new MutableLiveData<>();
        PagedList<TvShowEntity> pagedList = mock(PagedList.class);
        tvShows.setValue(Resource.success(pagedList));

        when(catalogRepository.getAllFavoriteTvShowsPaged()).thenReturn(tvShows);
        Observer<Resource<PagedList<TvShowEntity>>> observer = mock(Observer.class);
        favoriteTvViewModel.getFavoriteTvShowListPaged().observeForever(observer);
        verify(observer).onChanged(Resource.success(pagedList));
    }
}