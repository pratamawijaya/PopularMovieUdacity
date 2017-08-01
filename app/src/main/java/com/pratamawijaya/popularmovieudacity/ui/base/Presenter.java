package com.pratamawijaya.popularmovieudacity.ui.base;

/**
 * Created by pratama on 8/1/17.
 */

public interface Presenter<V extends BaseView> {
    void attachView(V view);

    void detachView();
}
