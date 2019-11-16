package com.vincode.moviecatalog.ui.tvshow;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.vincode.moviecatalog.R;
import com.vincode.moviecatalog.utils.ViewModelFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class TvShowFragment extends Fragment {
    private ProgressBar progressBar;
    private RecyclerView rvTvShows;

    public TvShowFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tv_show, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvTvShows = view.findViewById(R.id.rv_tv);
        progressBar = view.findViewById(R.id.progress_bar);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getActivity() != null) {
            TvViewModel tvViewModel = obtainViewModel(getActivity());
            TvShowAdapter adapter = new TvShowAdapter(getActivity());
            progressBar.setVisibility(View.VISIBLE);

            tvViewModel.setUsername("vincode");
            tvViewModel.tvShows.observe(this, tvShow -> {
                if (tvShow != null) {
                    switch (tvShow.status) {
                        case SUCCESS:
                            progressBar.setVisibility(View.GONE);
                            adapter.setTvShowList(tvShow.data);
                            adapter.notifyDataSetChanged();
                            break;
                        case LOADING:
                            progressBar.setVisibility(View.VISIBLE);
                            break;
                        case ERROR:
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(getContext(), "Ada Kesalahan Pada Aplikasi", Toast.LENGTH_SHORT).show();
                            break;
                    }
                }
            });

            rvTvShows.setLayoutManager(new LinearLayoutManager(getContext()));
            rvTvShows.setHasFixedSize(true);
            rvTvShows.setAdapter(adapter);
        }

    }

    private static TvViewModel obtainViewModel(FragmentActivity activity) {
        ViewModelFactory factory = ViewModelFactory.getInstance(activity.getApplication());
        return ViewModelProviders.of(activity, factory).get(TvViewModel.class);
    }
}
