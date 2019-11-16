package com.vincode.moviecatalog.ui.favorite;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.vincode.moviecatalog.R;
import com.vincode.moviecatalog.ui.favorite.movie.FavoriteMovieFragment;
import com.vincode.moviecatalog.ui.favorite.tvshow.FavoriteTvShowFragment;

public class FavoriteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        TabLayout tabLayout = findViewById(R.id.tablayout);
        ViewPager viewPager = findViewById(R.id.viewpager);
        Toolbar toolbar = findViewById(R.id.toolbar);

        FavoritePagerAdapter fragmentPagerAdapter = new FavoritePagerAdapter(getSupportFragmentManager());
        fragmentPagerAdapter.addFragment(new FavoriteMovieFragment(), "Movie");
        fragmentPagerAdapter.addFragment(new FavoriteTvShowFragment(), "Tv Show");

        viewPager.setAdapter(fragmentPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(R.string.favorite);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

//        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white);
//        toolbar.setNavigationOnClickListener(v -> onBackPressed());

    }
}
