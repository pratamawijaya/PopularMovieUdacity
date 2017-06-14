package com.pratamawijaya.popularmovieudacity.data;

import com.pratamawijaya.popularmovieudacity.BuildConfig;
import com.pratamawijaya.popularmovieudacity.data.model.MovieResponse;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

/**
 * Created by pratama
 * Date : Jun - 6/14/17
 * Project Name : PopularMovieUdacity
 */

public interface TheMovieDbServices {

    @GET("movie/popular")
    Call<MovieResponse> getPopularMovie();

    @GET("movie/top_rated")
    Call<MovieResponse> getTopRatedMovie();

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
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient)
                    .baseUrl(BuildConfig.THEMOVIEDB_API)
                    .build();
            return retrofit.create(TheMovieDbServices.class);
        }
    }
}
