package com.pratamawijaya.popularmovieudacity.ui.home;

import com.pratamawijaya.popularmovieudacity.data.model.Movie;

import java.util.List;

/**
 * Created by pratama
 * Date : Jun - 6/14/17
 * Project Name : PopularMovieUdacity
 */

public interface MainView {
    void showLoading(boolean show);

    void displayMovies(List<Movie> results);

    void showError(String message);
}
