package com.pratamawijaya.popularmovieudacity.data.repository;

import com.pratamawijaya.popularmovieudacity.data.TheMovieDbServices;
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

    @Override
    public Observable<List<MovieVideo>> getVideoTrailers(int movieId) {
        return services.getMovieTrailers(movieId).flatMap(movieTrailersResponse -> Observable.just(movieTrailersResponse.getResults()));
    }

    @Override
    public Observable<List<Review>> getMovieReviews(int movieId, int page) {
        return services.getMovieReview(movieId, page).flatMap(movieReviewResponse -> Observable.just(movieReviewResponse.getResults()));
    }

}
