package com.vincode.moviecatalog.ui.movie;


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
public class MovieFragment extends Fragment {

    private RecyclerView rvMovies;
    private ProgressBar progressBar;

    public MovieFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie, container, false);
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
            MovieViewModel movieViewModel = obtainViewModel(getActivity());
            MoviesAdapter adapter = new MoviesAdapter(getActivity());

            movieViewModel.setUsername("vincode");
            movieViewModel.movies.observe(this, movie -> {
                if (movie != null) {
                    switch (movie.status) {
                        case SUCCESS:
                            progressBar.setVisibility(View.GONE);
                            adapter.setMovieList(movie.data);
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

            rvMovies.setLayoutManager(new LinearLayoutManager(getContext()));
            rvMovies.setHasFixedSize(true);
            rvMovies.setAdapter(adapter);
        }

    }

    private static MovieViewModel obtainViewModel(FragmentActivity activity) {
        ViewModelFactory factory = ViewModelFactory.getInstance(activity.getApplication());
        return ViewModelProviders.of(activity, factory).get(MovieViewModel.class);
    }

}
