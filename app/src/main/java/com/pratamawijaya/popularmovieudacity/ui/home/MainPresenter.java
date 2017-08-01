package com.pratamawijaya.popularmovieudacity.ui.home;

import com.pratamawijaya.popularmovieudacity.data.model.Movie;
import com.pratamawijaya.popularmovieudacity.data.repository.MovieRepository;
import com.pratamawijaya.popularmovieudacity.ui.base.BasePresenter;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by pratama
 * Date : Jun - 6/14/17
 * Project Name : PopularMovieUdacity
 */

public class MainPresenter extends BasePresenter<MainView> {
    private MovieRepository movieRepository;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Inject
    public MainPresenter(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public void getMovie(int sort, int page) {
        getView().showLoading(true);

        Observable<List<Movie>> observableMovies;


        if (sort == MainActivity.POPULAR) {
            observableMovies = movieRepository.getPopularMovie(page);
        } else {
            observableMovies = movieRepository.getTopRatedMovie(page);
        }

        compositeDisposable.add(observableMovies.
                subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(movies -> {
                    getView().showLoading(false);
                    if (movies != null && movies.size() > 0) {
                        getView().displayMovies(movies);
                    }
                }, throwable -> {
                    getView().showLoading(false);
                    getView().showError(throwable.getLocalizedMessage());
                }));
    }

    public void getFavoriteMovie() {
        compositeDisposable.add(movieRepository.getFavoriteMovies()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(movies -> getView().displayMovies(movies),
                        throwable -> getView().showError(throwable.getLocalizedMessage())));
    }
}
