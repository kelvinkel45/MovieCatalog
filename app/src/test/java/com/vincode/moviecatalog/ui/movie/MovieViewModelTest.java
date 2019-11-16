package com.vincode.moviecatalog.ui.movie;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.vincode.moviecatalog.data.source.CatalogRepository;
import com.vincode.moviecatalog.data.source.local.entity.MovieEntity;
import com.vincode.moviecatalog.utils.FakeDataDummy;
import com.vincode.moviecatalog.vo.Resource;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class MovieViewModelTest {
    private MovieViewModel movieViewModel;
    private CatalogRepository catalogRepository = mock(CatalogRepository.class);
    private Resource<MovieEntity> dummyMovieDetail = Resource.success(FakeDataDummy.generateDummyRemoteMovies().get(0));

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void setUp() {
        movieViewModel = new MovieViewModel(catalogRepository);
        if (dummyMovieDetail.data != null) {
            movieViewModel.setMovieId(dummyMovieDetail.data.getId());
        }
    }

    @Test
    public void getMovieList() {
        Resource<List<MovieEntity>> resource = Resource.success(FakeDataDummy.generateDummyRemoteMovies());
        MutableLiveData<Resource<List<MovieEntity>>> movies = new MutableLiveData<>();
        movies.setValue(resource);

        when(catalogRepository.getAllMovies()).thenReturn(movies);
        Observer<Resource<List<MovieEntity>>> observer = mock(Observer.class);
        String USERNAME = "vincode";
        movieViewModel.setUsername(USERNAME);
        movieViewModel.movies.observeForever(observer);
        verify(observer).onChanged(resource);

    }

    @Test
    public void getMovieDetail() {

        MutableLiveData<Resource<MovieEntity>> movieMutableLiveData = new MutableLiveData<>();
        movieMutableLiveData.setValue(dummyMovieDetail);

        if (dummyMovieDetail.data != null) {
            when(catalogRepository.getDetailMovie(dummyMovieDetail.data.getId())).thenReturn(movieMutableLiveData);
        }

        Observer<Resource<MovieEntity>> observer = Mockito.mock(Observer.class);
        movieViewModel.getMovieDetail.observeForever(observer);
        verify(observer).onChanged(dummyMovieDetail);
    }

}