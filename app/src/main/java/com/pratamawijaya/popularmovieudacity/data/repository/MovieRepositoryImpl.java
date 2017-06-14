package com.pratamawijaya.popularmovieudacity.data.repository;

import com.pratamawijaya.popularmovieudacity.data.TheMovieDbServices;
import com.pratamawijaya.popularmovieudacity.data.model.MovieResponse;

import retrofit2.Call;

/**
 * Created by pratama
 * Date : Jun - 6/14/17
 * Project Name : PopularMovieUdacity
 */

public class MovieRepositoryImpl implements MovieRepository {

    private TheMovieDbServices services;

    public MovieRepositoryImpl(TheMovieDbServices services) {
        this.services = services;
    }


    @Override
    public Call<MovieResponse> getPopularMovie() {
        return services.getPopularMovie();
    }

    @Override
    public Call<MovieResponse> getTopRatedMovie() {
        return services.getTopRatedMovie();
    }
}
