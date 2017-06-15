package com.pratamawijaya.popularmovieudacity.ui.home;

import android.util.Log;

import com.pratamawijaya.popularmovieudacity.data.model.MovieResponse;
import com.pratamawijaya.popularmovieudacity.data.repository.MovieRepository;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by pratama
 * Date : Jun - 6/14/17
 * Project Name : PopularMovieUdacity
 */

public class MainPresenter {
    private static final String TAG = MainPresenter.class.getSimpleName();
    private MovieRepository movieRepository;
    private MainView view;

    public MainPresenter(MovieRepository movieRepository, MainView view) {
        this.movieRepository = movieRepository;
        this.view = view;
    }

    public void getMovie(int sort) {
        view.showLoading(true);

        Call<MovieResponse> callMovie;

        if (sort == MainActivity.POPULAR) {
            callMovie = movieRepository.getPopularMovie();
        } else {
            callMovie = movieRepository.getTopRatedMovie();
        }

        callMovie.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                view.showLoading(false);

                if (response.isSuccessful()) {
                    MovieResponse movieResponse = response.body();
                    Log.d(TAG, "onResponse: " + movieResponse.results.size());

                    if (movieResponse != null) {
                        if (movieResponse.results != null && movieResponse.results.size() > 0) {
                            view.displayMovies(movieResponse.results);
                        }
                    }
                } else {
                    view.showError(response.message());
                }

            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                view.showLoading(false);
                view.showError(t.getLocalizedMessage());
                Log.e(TAG, "onFailure: " + t.getLocalizedMessage());
            }
        });

    }

    public void detachView() {
        movieRepository.getPopularMovie().cancel();
    }
}
