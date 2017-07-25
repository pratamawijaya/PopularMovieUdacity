package com.pratamawijaya.popularmovieudacity.data;

import com.pratamawijaya.popularmovieudacity.BuildConfig;
import com.pratamawijaya.popularmovieudacity.data.model.response.MovieResponse;
import com.pratamawijaya.popularmovieudacity.data.model.response.MovieReviewResponse;
import com.pratamawijaya.popularmovieudacity.data.model.response.MovieTrailersResponse;

import io.reactivex.Observable;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by pratama
 * Date : Jun - 6/14/17
 * Project Name : PopularMovieUdacity
 */

public interface TheMovieDbServices {

    @GET("movie/popular")
    Observable<MovieResponse> getPopularMovie();

    @GET("movie/top_rated")
    Observable<MovieResponse> getTopRatedMovie();

    @GET("movie/{id}/videos")
    Observable<MovieTrailersResponse> getMovieTrailers(@Path("id") int movieId);

    @GET("movie/{id}/reviews")
    Observable<MovieReviewResponse> getMovieReview(@Path("id") int movieId, @Query("page") int page);

    class Creator {

        public static TheMovieDbServices instance() {
            final HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            final TheMovieDbInterceptor theMovieDbInterceptor = new TheMovieDbInterceptor();

            final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .addInterceptor(loggingInterceptor)
                    .addInterceptor(theMovieDbInterceptor)
                    .build();

            final Retrofit retrofit = new Retrofit.Builder()
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient)
                    .baseUrl(BuildConfig.THEMOVIEDB_API)
                    .build();
            return retrofit.create(TheMovieDbServices.class);
        }
    }
}
