package com.pratamawijaya.popularmovieudacity.ui.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.pratamawijaya.popularmovieudacity.PopularMovieApp;
import com.pratamawijaya.popularmovieudacity.di.component.ActivityComponent;
import com.pratamawijaya.popularmovieudacity.di.module.ActivityModule;

/**
 * Created by pratama on 8/1/17.
 */

public abstract class BaseInjectedActivity extends AppCompatActivity {

    protected ActivityComponent activityComponent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityComponent = PopularMovieApp.get(this)
                .getAppComponent()
                .activityComponent()
                .activityModule(new ActivityModule(this))
                .build();

        buildActivityComponent(activityComponent);
    }

    protected abstract void buildActivityComponent(ActivityComponent activityComponent);
}
