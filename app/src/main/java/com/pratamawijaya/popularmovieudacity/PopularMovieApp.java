package com.pratamawijaya.popularmovieudacity;

import android.app.Application;
import android.content.Context;

import com.pratamawijaya.popularmovieudacity.di.component.AppComponent;
import com.pratamawijaya.popularmovieudacity.di.component.DaggerAppComponent;
import com.pratamawijaya.popularmovieudacity.di.module.AppModule;
import com.pratamawijaya.popularmovieudacity.di.module.DataModule;

/**
 * Created by pratama on 8/1/17.
 */

public class PopularMovieApp extends Application {

    public static PopularMovieApp get(Context context) {
        return (PopularMovieApp) context.getApplicationContext();
    }

    protected AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .dataModule(new DataModule())
                .build();
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }
}
