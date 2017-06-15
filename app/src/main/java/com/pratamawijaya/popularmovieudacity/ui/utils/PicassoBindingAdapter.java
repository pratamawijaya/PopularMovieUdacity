package com.pratamawijaya.popularmovieudacity.ui.utils;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * Created by pratama
 * Date : Jun - 6/14/17
 * Project Name : PopularMovieUdacity
 */

public class PicassoBindingAdapter {
    @BindingAdapter({"bind:posterURL"})
    public static void loadImage(ImageView view, String imageUrl) {
        final String finalUrl = "http://image.tmdb.org/t/p/w342/" + imageUrl;
        Picasso.with(view.getContext())
                .load(finalUrl)
                .into(view);
    }

    @BindingAdapter({"bind:backdropURL"})
    public static void loadBackdrop(ImageView view, String imageUrl) {
        final String finalUrl = "http://image.tmdb.org/t/p/w780" + imageUrl;
        Picasso.with(view.getContext())
                .load(finalUrl)
                .into(view);
    }
}
