package com.vincode.moviecatalog.ui.favorite.movie;

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
import com.vincode.moviecatalog.data.source.local.entity.MovieEntity;
import com.vincode.moviecatalog.utils.ViewModelFactory;

public class FavoriteMovieFragment extends Fragment {

    private RecyclerView rvMovies;
    private ProgressBar progressBar;
    private FavoriteMovieViewModel favoriteMovieViewModel;
    private FavoriteMoviePagedAdapter favoriteMoviePagedAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite_movie, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvMovies = view.findViewById(R.id.rv_movies);
        progressBar = view.findViewById(R.id.progress_bar);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getActivity() != null) {
            progressBar.setVisibility(View.VISIBLE);
            favoriteMovieViewModel = obtainViewModel(getActivity());
            favoriteMoviePagedAdapter = new FavoriteMoviePagedAdapter(getActivity());

            favoriteMovieViewModel.getFavoriteMovieListPaged().observe(this, movie -> {
                if (movie != null) {
                    switch (movie.status) {
                        case SUCCESS:
                            progressBar.setVisibility(View.GONE);
                            favoriteMoviePagedAdapter.submitList(movie.data);
                            favoriteMoviePagedAdapter.notifyDataSetChanged();
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

            itemTouchHelper.attachToRecyclerView(rvMovies);

            rvMovies.setLayoutManager(new LinearLayoutManager(getContext()));
            rvMovies.setHasFixedSize(true);
            rvMovies.setAdapter(favoriteMoviePagedAdapter);
        }

    }

    private static FavoriteMovieViewModel obtainViewModel(FragmentActivity activity) {
        ViewModelFactory factory = ViewModelFactory.getInstance(activity.getApplication());
        return ViewModelProviders.of(activity, factory).get(FavoriteMovieViewModel.class
        );
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
                MovieEntity movieEntity = favoriteMoviePagedAdapter.getItemById(swipedPosition);
                favoriteMovieViewModel.setFavorite(movieEntity);
                Snackbar snackbar = Snackbar.make(getView(), "Batalkan menghapus movie?", Snackbar.LENGTH_LONG);
                snackbar.setAction("OK", v -> favoriteMovieViewModel.setFavorite(movieEntity));
                snackbar.show();

            }
        }
    });

}
