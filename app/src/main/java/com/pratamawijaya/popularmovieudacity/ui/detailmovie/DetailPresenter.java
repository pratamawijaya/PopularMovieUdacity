package com.pratamawijaya.popularmovieudacity.ui.detailmovie;

import android.util.Pair;

import com.pratamawijaya.popularmovieudacity.data.model.Movie;
import com.pratamawijaya.popularmovieudacity.data.repository.MovieRepository;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by pratama on 7/25/17.
 */

public class DetailPresenter {
    private MovieRepository movieRepository;
    private DetailView view;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public DetailPresenter(MovieRepository movieRepository, DetailView view) {
        this.movieRepository = movieRepository;
        this.view = view;
    }

    public void getVideoAndReview(int movieId) {
        compositeDisposable.add(Observable.zip(movieRepository.getVideoTrailers(movieId), movieRepository.getMovieReviews(movieId, 1),
                (movieVideos, reviews) -> new Pair<>(movieVideos, reviews))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(listListPair -> {
                    view.displayVideo(listListPair.first);
                    view.displayReview(listListPair.second);
                }, throwable -> {
                    view.displayError(throwable.getLocalizedMessage());
                }));
    }

    public void favoriteMovie(Movie movie) {
        compositeDisposable.add(movieRepository.saveMovie(movie)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> view.favoriteSuccess(),
                        throwable -> view.showError(throwable.getLocalizedMessage())));
    }
}
