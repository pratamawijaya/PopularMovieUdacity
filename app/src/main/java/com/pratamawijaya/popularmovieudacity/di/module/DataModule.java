package com.pratamawijaya.popularmovieudacity.di.module;

import com.pratamawijaya.popularmovieudacity.data.repository.MovieRepository;
import com.pratamawijaya.popularmovieudacity.data.repository.MovieRepositoryImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by pratama on 8/1/17.
 */

@Module
public class DataModule {
    @Provides
    @Singleton
    public MovieRepository provideMovieRepo(MovieRepositoryImpl movieRepository) {
        return movieRepository;
    }
}
