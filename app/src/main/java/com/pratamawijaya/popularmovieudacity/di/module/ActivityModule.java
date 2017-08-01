package com.pratamawijaya.popularmovieudacity.di.module;

import android.app.Activity;
import android.content.Context;

import com.pratamawijaya.popularmovieudacity.di.ActivityContext;
import com.pratamawijaya.popularmovieudacity.di.PerActivity;

import dagger.Module;
import dagger.Provides;

/**
 * Created by pratama on 7/31/17.
 */
@Module
public class ActivityModule {
    private Activity activity;

    public ActivityModule(Activity activity) {
        this.activity = activity;
    }

    @Provides
    @PerActivity
    Activity provideActivity() {
        return activity;
    }

    @Provides
    @ActivityContext
    public Context provideActivityContext() {
        return activity;
    }
}
