package com.pratamawijaya.popularmovieudacity.data.repository;

import com.pratamawijaya.popularmovieudacity.data.TheMovieDbServices;
import com.pratamawijaya.popularmovieudacity.data.model.Movie;

import java.util.List;

import io.reactivex.Observable;

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
    public Observable<List<Movie>> getPopularMovie() {
        return services.getPopularMovie().flatMap(movieResponse -> Observable.just(movieResponse.results));
    }

    @Override
    public Observable<List<Movie>> getTopRatedMovie() {
        return services.getTopRatedMovie().flatMap(movieResponse -> Observable.just(movieResponse.results));
    }
}
