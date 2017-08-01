package com.pratamawijaya.popularmovieudacity.di.module;

import android.app.Application;
import android.content.ContentResolver;
import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pratamawijaya.popularmovieudacity.BuildConfig;
import com.pratamawijaya.popularmovieudacity.data.TheMovieDbInterceptor;
import com.pratamawijaya.popularmovieudacity.data.TheMovieDbServices;
import com.pratamawijaya.popularmovieudacity.di.ApplicationContext;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by pratama on 8/1/17.
 */

@Module
public class AppModule {
    private Application application;

    public AppModule(Application application) {
        this.application = application;
    }

    @Provides
    @ApplicationContext
    public Context provideAppContext() {
        return application;
    }

    @Provides
    @Singleton
    public Gson provideGson() {
        final Gson gson = new GsonBuilder().create();
        return gson;
    }

    @Provides
    @Singleton
    public OkHttpClient provideOkHttpClient() {
        final HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        final TheMovieDbInterceptor theMovieDbInterceptor = new TheMovieDbInterceptor();

        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(theMovieDbInterceptor)
                .addInterceptor(httpLoggingInterceptor)
                .build();

        return okHttpClient;
    }

    @Provides
    @Singleton
    public Retrofit provideRetrofit(Gson gson, OkHttpClient okHttpClient) {
        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.THEMOVIEDB_API)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient)
                .build();
        return retrofit;
    }

    @Provides
    @Singleton
    public TheMovieDbServices provideServices(Retrofit retrofit) {
        return retrofit.create(TheMovieDbServices.class);
    }

    @Provides
    @Singleton
    public ContentResolver provideContentResolver(@ApplicationContext Context context) {
        return context.getContentResolver();
    }

}
