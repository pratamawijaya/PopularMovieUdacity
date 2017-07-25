package com.pratamawijaya.popularmovieudacity.data.repository;

import com.pratamawijaya.popularmovieudacity.data.model.Movie;
import com.pratamawijaya.popularmovieudacity.data.model.MovieVideo;
import com.pratamawijaya.popularmovieudacity.data.model.Review;

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

    Observable<List<MovieVideo>> getVideoTrailers(int movieId);

    Observable<List<Review>> getMovieReviews(int movieId, int page);
}
