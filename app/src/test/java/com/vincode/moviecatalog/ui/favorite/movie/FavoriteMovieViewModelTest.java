package com.vincode.moviecatalog.ui.favorite.movie;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.paging.PagedList;

import com.vincode.moviecatalog.data.source.CatalogRepository;
import com.vincode.moviecatalog.data.source.local.entity.MovieEntity;
import com.vincode.moviecatalog.vo.Resource;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class FavoriteMovieViewModelTest {

    private CatalogRepository catalogRepository = mock(CatalogRepository.class);
    private FavoriteMovieViewModel favoriteMovieViewModel;

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void setUp() {
        favoriteMovieViewModel = new FavoriteMovieViewModel(catalogRepository);
    }

    @Test
    public void getMovieFavorite() {
        MutableLiveData<Resource<PagedList<MovieEntity>>> movies = new MutableLiveData<>();
        PagedList<MovieEntity> pagedList = mock(PagedList.class);
        movies.setValue(Resource.success(pagedList));

        when(catalogRepository.getAllFavoriteMoviesPaged()).thenReturn(movies);
        Observer<Resource<PagedList<MovieEntity>>> observer = mock(Observer.class);
        favoriteMovieViewModel.getFavoriteMovieListPaged().observeForever(observer);
        verify(observer).onChanged(Resource.success(pagedList));
    }
}