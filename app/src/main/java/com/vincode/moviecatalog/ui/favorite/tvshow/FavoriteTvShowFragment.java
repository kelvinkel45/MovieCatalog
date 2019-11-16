package com.vincode.moviecatalog.ui.favorite.tvshow;


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
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.vincode.moviecatalog.R;
import com.vincode.moviecatalog.data.source.local.entity.TvShowEntity;
import com.vincode.moviecatalog.utils.ViewModelFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteTvShowFragment extends Fragment {
    private FavoriteTvPagedAdapter favoriteTvPagedAdapter;
    private ProgressBar progressBar;
    private RecyclerView rvTvShows;
    private FavoriteTvViewModel favoriteTvViewModel;

    public FavoriteTvShowFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite_tv_show, container, false);
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
            favoriteTvViewModel = obtainViewModel(getActivity());
            favoriteTvPagedAdapter = new FavoriteTvPagedAdapter(getActivity());
            progressBar.setVisibility(View.VISIBLE);

            favoriteTvViewModel.getFavoriteTvShowListPaged().observe(this, tvShow -> {
                if (tvShow != null) {
                    switch (tvShow.status) {
                        case SUCCESS:
                            progressBar.setVisibility(View.GONE);
                            favoriteTvPagedAdapter.submitList(tvShow.data);
                            favoriteTvPagedAdapter.notifyDataSetChanged();
                            break;
                        case LOADING:
                            progressBar.setVisibility(View.VISIBLE);
                            break;
                        case ERROR:
                            progressBar.setVisibility(View.VISIBLE);
                            Toast.makeText(getContext(), "Ada Terjadi Kesalahan", Toast.LENGTH_SHORT).show();
                            break;
                    }
                }
            });

            itemTouchHelper.attachToRecyclerView(rvTvShows);

            rvTvShows.setLayoutManager(new LinearLayoutManager(getContext()));
            rvTvShows.setHasFixedSize(true);
            rvTvShows.setAdapter(favoriteTvPagedAdapter);
        }

    }

    private static FavoriteTvViewModel obtainViewModel(FragmentActivity activity) {
        ViewModelFactory factory = ViewModelFactory.getInstance(activity.getApplication());
        return ViewModelProviders.of(activity, factory).get(FavoriteTvViewModel.class);
    }

    private ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.Callback() {

        @Override
        public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
            return makeMovementFlags(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
        }

        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return true;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            if (getView() != null) {
                int swipedPosition = viewHolder.getAdapterPosition();
                TvShowEntity movieEntity = favoriteTvPagedAdapter.getItemById(swipedPosition);
                favoriteTvViewModel.setFavorite(movieEntity);
                Snackbar snackbar = Snackbar.make(getView(), "Batalkan menghapus TV Show?", Snackbar.LENGTH_LONG);
                snackbar.setAction("OK", v -> favoriteTvViewModel.setFavorite(movieEntity));
                snackbar.show();

            }
        }
    });
}
