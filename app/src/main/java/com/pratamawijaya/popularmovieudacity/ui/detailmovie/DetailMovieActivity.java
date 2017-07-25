package com.pratamawijaya.popularmovieudacity.ui.detailmovie;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.github.nitrico.lastadapter.Holder;
import com.github.nitrico.lastadapter.ItemType;
import com.github.nitrico.lastadapter.LastAdapter;
import com.pratamawijaya.popularmovieudacity.BR;
import com.pratamawijaya.popularmovieudacity.R;
import com.pratamawijaya.popularmovieudacity.data.TheMovieDbServices;
import com.pratamawijaya.popularmovieudacity.data.model.Movie;
import com.pratamawijaya.popularmovieudacity.data.model.MovieVideo;
import com.pratamawijaya.popularmovieudacity.data.model.Review;
import com.pratamawijaya.popularmovieudacity.data.repository.MovieRepository;
import com.pratamawijaya.popularmovieudacity.data.repository.MovieRepositoryImpl;
import com.pratamawijaya.popularmovieudacity.databinding.ActivityDetailMovieBinding;
import com.pratamawijaya.popularmovieudacity.databinding.ItemMovieReviewBinding;
import com.pratamawijaya.popularmovieudacity.databinding.ItemMovieTrailersBinding;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailMovieActivity extends AppCompatActivity implements DetailView {

    private static final String TAG = DetailMovieActivity.class.getSimpleName();
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.collapsingToolbar)
    CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.appBarLayout)
    AppBarLayout appBarLayout;
    @BindView(R.id.rvTrailers)
    RecyclerView rvTrailers;
    @BindView(R.id.rvReview)
    RecyclerView rvReview;

    private Movie movie;
    private DetailPresenter presenter;
    private MovieRepository movieRepository;
    private TheMovieDbServices movieDbServices;

    private List<MovieVideo> movieVideos = new ArrayList<>();
    private List<Review> movieReview = new ArrayList<>();
    private LastAdapter trailersAdapter;
    private LastAdapter reviewAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        movie = Parcels.unwrap(getIntent().getParcelableExtra("movie"));

        // init retrofit
        movieDbServices = TheMovieDbServices.Creator.instance();
        // init repository
        movieRepository = new MovieRepositoryImpl(movieDbServices);

        presenter = new DetailPresenter(movieRepository, this);

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

            // request movie review n video
            presenter.getVideoAndReview(movie.getId());

            initRvTrailers();
            initRvReview();
        }

    }

    private void initRvReview() {
        rvReview.setLayoutManager(new LinearLayoutManager(this));
        reviewAdapter = new LastAdapter(movieReview, BR.review)
                .map(Review.class, new ItemType<ItemMovieReviewBinding>(R.layout.item_movie_review) {
                    @Override
                    public void onCreate(Holder<ItemMovieReviewBinding> holder) {
                    }

                    @Override
                    public void onBind(Holder<ItemMovieReviewBinding> holder) {
                        super.onBind(holder);
                        Log.d(TAG, "onBind: " + holder.getBinding().getReview().getAuthor());
                    }
                })
                .into(rvReview);
    }

    private void initRvTrailers() {
        rvTrailers.setLayoutManager(new LinearLayoutManager(this));
        trailersAdapter = new LastAdapter(movieVideos, BR.trailers)
                .map(MovieVideo.class, new ItemType<ItemMovieTrailersBinding>(R.layout.item_movie_trailers) {
                    @Override
                    public void onBind(Holder<ItemMovieTrailersBinding> holder) {
                        super.onBind(holder);
                        Log.d(TAG, "onBind: " + holder.getBinding().getTrailers().getName());
                    }

                    @Override
                    public void onCreate(Holder<ItemMovieTrailersBinding> holder) {
                        super.onCreate(holder);
                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Log.d(TAG, "onClick: " + holder.getBinding().getTrailers().getKey());
                                openVideoTrailers(holder.getBinding().getTrailers().getKey());
                            }
                        });
                    }
                }).into(rvTrailers);
    }

    private void openVideoTrailers(String key) {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=" + key)));
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    public void displayVideo(List<MovieVideo> videos) {
        movieVideos.addAll(videos);
        trailersAdapter.notifyDataSetChanged();

        for (MovieVideo video : videos) {
            Log.d(TAG, "displayVideo: " + video.getKey() + " " + video.getSize());
        }
    }

    @Override
    public void displayReview(List<Review> reviews) {
        reviews.addAll(reviews);
        reviewAdapter.notifyDataSetChanged();
        for (Review review : reviews) {
            Log.d("tag", "review data " + review.getAuthor());
        }
    }

    @Override
    public void displayError(String localizedMessage) {

    }
}
