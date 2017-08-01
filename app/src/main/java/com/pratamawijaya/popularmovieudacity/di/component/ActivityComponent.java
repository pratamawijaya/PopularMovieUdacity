package com.pratamawijaya.popularmovieudacity.di.component;

import com.pratamawijaya.popularmovieudacity.di.PerActivity;
import com.pratamawijaya.popularmovieudacity.di.module.ActivityModule;
import com.pratamawijaya.popularmovieudacity.ui.home.MainActivity;

import dagger.Subcomponent;

/**
 * Created by pratama on 8/1/17.
 */

@PerActivity
@Subcomponent(modules = {ActivityModule.class})
public interface ActivityComponent {

    @Subcomponent.Builder
    interface Builder {

        Builder activityModule(ActivityModule module);

        ActivityComponent build();
    }

    // inject to activity
    void inject(MainActivity activity);
}
