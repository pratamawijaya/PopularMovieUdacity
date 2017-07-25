package com.pratamawijaya.popularmovieudacity.data.model.response;

import com.pratamawijaya.popularmovieudacity.data.model.MovieVideo;

import java.util.List;

/**
 * Created by pratama on 7/22/17.
 */

public class MovieTrailersResponse {
    private int id;
    private List<MovieVideo> results;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<MovieVideo> getResults() {
        return results;
    }

    public void setResults(List<MovieVideo> results) {
        this.results = results;
    }
}
