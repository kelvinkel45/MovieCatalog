package com.vincode.moviecatalog.data.source;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;
import androidx.paging.PagedList;

import com.vincode.moviecatalog.data.source.local.LocalRepository;
import com.vincode.moviecatalog.data.source.local.entity.MovieEntity;
import com.vincode.moviecatalog.data.source.local.entity.TvShowEntity;
import com.vincode.moviecatalog.data.source.remote.RemoteRepository;
import com.vincode.moviecatalog.utils.FakeDataDummy;
import com.vincode.moviecatalog.utils.InstantAppExecutor;
import com.vincode.moviecatalog.utils.LiveDataTestUtil;
import com.vincode.moviecatalog.utils.PagedListUtil;
import com.vincode.moviecatalog.vo.Resource;

import org.junit.Rule;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CatalogRepositoryTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private LocalRepository localRepository = mock(LocalRepository.class);
    private RemoteRepository remoteRepository = mock(RemoteRepository.class);
    private InstantAppExecutor instantAppExecutor = mock(InstantAppExecutor.class);
    private FakeCatalogRepository fakeCatalogRepository = new FakeCatalogRepository(localRepository, remoteRepository, instantAppExecutor);


    private List<MovieEntity> movies = FakeDataDummy.generateDummyRemoteMovies();
    private List<TvShowEntity> tvShows = FakeDataDummy.generateDummyRemoteTvShows();

    private MovieEntity movie = FakeDataDummy.generateDummyRemoteMovies().get(0);
    private TvShowEntity tvShow = FakeDataDummy.generateDummyRemoteTvShows().get(0);

    private long movieId = movie.getId();
    private long tvShowId = tvShow.getId();


    @Test
    public void getAllMovies() {
        MutableLiveData<List<MovieEntity>> dummyMovies = new MutableLiveData<>();
        dummyMovies.setValue(FakeDataDummy.generateDummyRemoteMovies());

        when(localRepository.getAllMovies()).thenReturn(dummyMovies);
        Resource<List<MovieEntity>> result = LiveDataTestUtil.getValue(fakeCatalogRepository.getAllMovies());

        verify(localRepository).getAllMovies();
        assertNotNull(result.data);
        assertEquals(movies.size(), result.data.size());
    }

    @Test
    public void getAllTvShows() {
        MutableLiveData<List<TvShowEntity>> dummyTvShow = new MutableLiveData<>();
        dummyTvShow.setValue(FakeDataDummy.generateDummyRemoteTvShows());

        when(localRepository.getAllTvShows()).thenReturn(dummyTvShow);
        Resource<List<TvShowEntity>> result = LiveDataTestUtil.getValue(fakeCatalogRepository.getAllTvShows());

        verify(localRepository).getAllTvShows();
        assertNotNull(result.data);
        assertEquals(tvShows.size(), result.data.size());
    }

    @Test
    public void getDetailMovie() {
        MutableLiveData<MovieEntity> dummyMovie = new MutableLiveData<>();
        dummyMovie.setValue(movie);
        when(localRepository.getMovieDetail(movieId)).thenReturn(dummyMovie);

        Resource<MovieEntity> resultMovie = LiveDataTestUtil.getValue(fakeCatalogRepository.getDetailMovie(movieId));

        verify(localRepository).getMovieDetail(movieId);

        assertNotNull(resultMovie);
        if (resultMovie.data != null) {
            assertEquals(movieId, resultMovie.data.getId());
        }
    }

    @Test
    public void getDetailTvShow() {
        MutableLiveData<TvShowEntity> dummyTvShow = new MutableLiveData<>();
        dummyTvShow.setValue(tvShow);
        when(localRepository.getTvShowDetail(tvShowId)).thenReturn(dummyTvShow);

        Resource<TvShowEntity> resultTvShow = LiveDataTestUtil.getValue(fakeCatalogRepository.getDetailTvShow(tvShowId));

        verify(localRepository).getTvShowDetail(tvShowId);

        assertNotNull(resultTvShow);
        if (resultTvShow.data != null) {
            assertEquals(tvShowId, resultTvShow.data.getId());
        }
    }

    @Test
    public void getAllFavoriteMovies() {
        DataSource.Factory<Integer, MovieEntity> dataSourceFactory = mock(DataSource.Factory.class);

        when(localRepository.getFavMoviesPaged()).thenReturn(dataSourceFactory);
        fakeCatalogRepository.getAllFavoriteMoviesPaged();
        Resource<PagedList<MovieEntity>> resultFavorite = Resource.success(PagedListUtil.mockPagedList(movies));

        verify(localRepository).getFavMoviesPaged();
        assertNotNull(resultFavorite.data);
        assertEquals(movies.size(), resultFavorite.data.size());

    }

    @Test
    public void getAllFavoriteTv() {
        DataSource.Factory<Integer, TvShowEntity> dataSourceFactory = mock(DataSource.Factory.class);

        when(localRepository.getFavTvShowsPaged()).thenReturn(dataSourceFactory);
        fakeCatalogRepository.getAllFavoriteTvShowsPaged();
        Resource<PagedList<TvShowEntity>> resultFavorite = Resource.success(PagedListUtil.mockPagedList(tvShows));

        verify(localRepository).getFavTvShowsPaged();
        assertNotNull(resultFavorite.data);
        assertEquals(tvShows.size(), resultFavorite.data.size());
    }
}





