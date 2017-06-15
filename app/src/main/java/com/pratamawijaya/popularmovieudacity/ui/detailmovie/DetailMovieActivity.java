package com.pratamawijaya.popularmovieudacity.ui.detailmovie;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.pratamawijaya.popularmovieudacity.R;
import com.pratamawijaya.popularmovieudacity.data.model.Movie;
import com.pratamawijaya.popularmovieudacity.databinding.ActivityDetailMovieBinding;

import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailMovieActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.collapsingToolbar)
    CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.appBarLayout)
    AppBarLayout appBarLayout;

    private Movie movie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        movie = Parcels.unwrap(getIntent().getParcelableExtra("movie"));

        if (movie != null) {

            ActivityDetailMovieBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_detail_movie);
            ButterKnife.bind(this, binding.getRoot());

            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            binding.setMovie(movie);
            collapsingToolbarLayout.setTitle(" ");

            // https://stackoverflow.com/a/32724422/1277751
            appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {

                boolean isShow = false;
                int scrollRange = -1;

                @Override
                public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                    if (scrollRange == -1) {
                        scrollRange = appBarLayout.getTotalScrollRange();
                    }
                    if (scrollRange + verticalOffset == 0) {
                        collapsingToolbarLayout.setTitle(movie.getTitle());
                        isShow = true;
                    } else if (isShow) {
                        collapsingToolbarLayout.setTitle(" ");
                        isShow = false;
                    }
                }
            });
        }

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}
