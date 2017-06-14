package com.pratamawijaya.popularmovieudacity.data;

import com.pratamawijaya.popularmovieudacity.BuildConfig;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by pratama
 * Date : Jun - 6/14/17
 * Project Name : PopularMovieUdacity
 */

public class TheMovieDbInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {

        final HttpUrl url = chain.request()
                .url()
                .newBuilder()
                .addQueryParameter("api_key", BuildConfig.THEMOVIEDB_KEY)
                .build();
        final Request request = chain.request().newBuilder().url(url).build();
        return chain.proceed(request);
    }

}
