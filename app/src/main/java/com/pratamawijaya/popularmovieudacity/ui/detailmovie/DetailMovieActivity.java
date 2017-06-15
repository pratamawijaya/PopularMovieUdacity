package com.pratamawijaya.popularmovieudacity.ui.detailmovie;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.pratamawijaya.popularmovieudacity.R;
import com.pratamawijaya.popularmovieudacity.data.model.Movie;
import com.pratamawijaya.popularmovieudacity.databinding.ActivityDetailMovieBinding;

import org.parceler.Parcels;

public class DetailMovieActivity extends AppCompatActivity {

    private Movie movie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        movie = Parcels.unwrap(getIntent().getParcelableExtra("movie"));

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (movie != null) {
            getSupportActionBar().setTitle(movie.getTitle());

            ActivityDetailMovieBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_detail_movie);
            binding.setMovie(movie);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}
