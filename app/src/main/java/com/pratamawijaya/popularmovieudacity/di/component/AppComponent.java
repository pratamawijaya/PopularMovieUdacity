package com.pratamawijaya.popularmovieudacity.di.component;

import com.pratamawijaya.popularmovieudacity.di.module.AppModule;
import com.pratamawijaya.popularmovieudacity.di.module.DataModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by pratama on 8/1/17.
 */

@Singleton
@Component(modules = {AppModule.class, DataModule.class})
public interface AppComponent {

    ActivityComponent.Builder activityComponent();

}
