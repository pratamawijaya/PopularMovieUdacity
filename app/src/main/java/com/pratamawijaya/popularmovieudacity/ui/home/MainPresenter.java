package com.pratamawijaya.popularmovieudacity.ui.home;

import com.pratamawijaya.popularmovieudacity.data.model.Movie;
import com.pratamawijaya.popularmovieudacity.data.repository.MovieRepository;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by pratama
 * Date : Jun - 6/14/17
 * Project Name : PopularMovieUdacity
 */

public class MainPresenter {
    private MovieRepository movieRepository;
    private MainView view;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public MainPresenter(MovieRepository movieRepository, MainView view) {
        this.movieRepository = movieRepository;
        this.view = view;
    }


    public void getMovie(int sort) {
        view.showLoading(true);

        Observable<List<Movie>> observableMovies;


        if (sort == MainActivity.POPULAR) {
            observableMovies = movieRepository.getPopularMovie();
        } else {
            observableMovies = movieRepository.getTopRatedMovie();
        }

        compositeDisposable.add(observableMovies.
                subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(movies -> {
                    view.showLoading(false);
                    if (movies != null && movies.size() > 0) {
                        view.displayMovies(movies);
                    }
                }, throwable -> {
                    view.showLoading(false);
                    view.showError(throwable.getLocalizedMessage());
                }));
    }

    public void detachView() {
        compositeDisposable.dispose();
    }
}
