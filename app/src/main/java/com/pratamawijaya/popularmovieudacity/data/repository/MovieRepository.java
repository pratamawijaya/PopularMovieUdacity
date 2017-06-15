package com.pratamawijaya.popularmovieudacity.data.repository;

import com.pratamawijaya.popularmovieudacity.data.model.Movie;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by pratama
 * Date : Jun - 6/14/17
 * Project Name : PopularMovieUdacity
 */

public interface MovieRepository {
    Observable<List<Movie>> getPopularMovie();

    Observable<List<Movie>> getTopRatedMovie();
}
