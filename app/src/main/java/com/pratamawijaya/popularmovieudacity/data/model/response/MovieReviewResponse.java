package com.pratamawijaya.popularmovieudacity.data.model.response;

import com.pratamawijaya.popularmovieudacity.data.model.Review;

import java.util.List;

/**
 * Created by pratama on 7/22/17.
 */

public class MovieReviewResponse {
    private List<Review> results;

    public List<Review> getResults() {
        return results;
    }

    public void setResults(List<Review> results) {
        this.results = results;
    }
}
