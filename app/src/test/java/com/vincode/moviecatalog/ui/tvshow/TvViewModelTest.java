package com.vincode.moviecatalog.ui.tvshow;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.vincode.moviecatalog.data.source.CatalogRepository;
import com.vincode.moviecatalog.data.source.local.entity.TvShowEntity;
import com.vincode.moviecatalog.utils.FakeDataDummy;
import com.vincode.moviecatalog.vo.Resource;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class TvViewModelTest {

    private TvViewModel tvViewModel;
    private CatalogRepository catalogRepository = mock(CatalogRepository.class);
    private Resource<TvShowEntity> dummyTvDetail = Resource.success(FakeDataDummy.generateDummyRemoteTvShows().get(0));

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void setUp() {
        tvViewModel = new TvViewModel(catalogRepository);
        if (dummyTvDetail.data != null) {
            tvViewModel.setTvShowId(dummyTvDetail.data.getId());
        }
    }

    @Test
    public void getTvShowList() {
        Resource<List<TvShowEntity>> resource = Resource.success(FakeDataDummy.generateDummyRemoteTvShows());
        MutableLiveData<Resource<List<TvShowEntity>>> tvShows = new MutableLiveData<>();
        tvShows.setValue(resource);

        when(catalogRepository.getAllTvShows()).thenReturn(tvShows);
        Observer<Resource<List<TvShowEntity>>> observer = mock(Observer.class);
        String USERNAME = "vincode";
        tvViewModel.setUsername(USERNAME);
        tvViewModel.tvShows.observeForever(observer);
        verify(observer).onChanged(resource);
    }

    @Test
    public void getTvShowDetail() {


        MutableLiveData<Resource<TvShowEntity>> tvShowMutableLiveData = new MutableLiveData<>();
        tvShowMutableLiveData.setValue(dummyTvDetail);

        if (dummyTvDetail.data != null) {
            when(catalogRepository.getDetailTvShow(dummyTvDetail.data.getId())).thenReturn(tvShowMutableLiveData);
        }
        Observer<Resource<TvShowEntity>> observer = mock(Observer.class);
        tvViewModel.getTvShowDetail.observeForever(observer);
        verify(observer).onChanged(dummyTvDetail);
    }
}