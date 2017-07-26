package com.pratamawijaya.popularmovieudacity.ui.detailmovie;

import com.pratamawijaya.popularmovieudacity.data.model.MovieVideo;
import com.pratamawijaya.popularmovieudacity.data.model.Review;

import java.util.List;

/**
 * Created by pratama on 7/25/17.
 */

public interface DetailView {
    void displayVideo(List<MovieVideo> first);

    void displayReview(List<Review> second);

    void displayError(String localizedMessage);

    void favoriteSuccess();

    void showError(String localizedMessage);
}
