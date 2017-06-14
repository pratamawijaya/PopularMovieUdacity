package com.pratamawijaya.popularmovieudacity.data.repository;

import com.pratamawijaya.popularmovieudacity.data.model.MovieResponse;

import retrofit2.Call;

/**
 * Created by pratama
 * Date : Jun - 6/14/17
 * Project Name : PopularMovieUdacity
 */

public interface MovieRepository {
    Call<MovieResponse> getPopularMovie();

    Call<MovieResponse> getTopRatedMovie();
}
