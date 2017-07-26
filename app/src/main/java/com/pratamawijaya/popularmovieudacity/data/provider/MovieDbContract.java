package com.pratamawijaya.popularmovieudacity.data.provider;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by pratama on 7/26/17.
 */

public class MovieDbContract {

    static final String CONTENT_AUTHORITY = "tama.popularmoviesudacity";
    static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    private static final String PATH_MOVIES = "movies";

    public static class Movie implements BaseColumns {
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_MOVIES).build();
        public static final String TABLE_NAME = "movies";
        public static final String COLUMN_MOVIE_ID = "movie_id";
        public static final String COLUMN_MOVIE_TITLE = "movie_title";
        public static final String COLUMN_MOVIE_OVERVIEW = "movie_overview";
        public static final String COLUMN_MOVIE_VOTE_COUNT = "movie_vote_count";
        public static final String COLUMN_MOVIE_VOTE_AVERAGE = "movie_vote_average";
        public static final String COLUMN_MOVIE_RELEASE_DATE = "movie_release_date";
        public static final String COLUMN_MOVIE_FAVORED = "movie_favored";
        public static final String COLUMN_MOVIE_POSTER_PATH = "movie_poster_path";
        public static final String COLUMN_MOVIE_BACKDROP_PATH = "movie_backdrop_path";

        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.popularmovies.movie";
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.popularmovies.movie";

        public static final String DEFAULT_SORT = BaseColumns._ID + " DESC";

        static Uri buildMovieUri(String movieId) {
            return CONTENT_URI.buildUpon().appendPath(movieId).build();
        }

        public static String getMovieId(Uri uri) {
            return uri.getPathSegments().get(1);
        }
    }
}
