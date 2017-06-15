package com.pratamawijaya.popularmovieudacity.ui.home;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.github.nitrico.lastadapter.BR;
import com.github.nitrico.lastadapter.Holder;
import com.github.nitrico.lastadapter.ItemType;
import com.github.nitrico.lastadapter.LastAdapter;
import com.pratamawijaya.popularmovieudacity.R;
import com.pratamawijaya.popularmovieudacity.data.TheMovieDbServices;
import com.pratamawijaya.popularmovieudacity.data.model.Movie;
import com.pratamawijaya.popularmovieudacity.data.repository.MovieRepository;
import com.pratamawijaya.popularmovieudacity.data.repository.MovieRepositoryImpl;
import com.pratamawijaya.popularmovieudacity.databinding.ItemMovieBinding;
import com.pratamawijaya.popularmovieudacity.ui.detailmovie.DetailMovieActivity;

import org.parceler.Parcels;

import java.util.List;

public class MainActivity extends AppCompatActivity implements MainView, SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    public static final int POPULAR = 0;
    public static final int TOP_RATED = 1;

    private SwipeRefreshLayout refreshLayout;
    private RecyclerView recyclerView;
    private TextView errorText;
    private ProgressBar loading;

    private MovieRepository movieRepository;
    private TheMovieDbServices movieDbServices;

    private MainPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        // init retrofit
        movieDbServices = TheMovieDbServices.Creator.instance();
        // init repository
        movieRepository = new MovieRepositoryImpl(movieDbServices);

        // init presenter
        presenter = new MainPresenter(movieRepository, this);

        // get movie data
        presenter.getMovie(POPULAR);

    }

    private void initView() {
        loading = (ProgressBar) findViewById(R.id.loading);
        errorText = (TextView) findViewById(R.id.tv_error);
        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.content);
        recyclerView = (RecyclerView) findViewById(R.id.rv_list_movies);

        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        refreshLayout.setOnRefreshListener(this);
    }

    @Override
    public void showLoading(boolean show) {
        errorText.setVisibility(View.GONE);

        if (show) {
            recyclerView.setVisibility(View.VISIBLE);
            loading.setVisibility(View.VISIBLE);
            refreshLayout.setVisibility(View.GONE);
        } else {
            loading.setVisibility(View.GONE);
            refreshLayout.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void displayMovies(List<Movie> results) {
        new LastAdapter(results, BR.movie)
                .map(Movie.class, new ItemType<ItemMovieBinding>(R.layout.item_movie) {
                    @Override
                    public void onCreate(final Holder<ItemMovieBinding> holder) {
                        super.onCreate(holder);
                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Log.d(TAG, "onClick: " + holder.getBinding().getMovie().getTitle());
                                Bundle data = new Bundle();
                                data.putParcelable("movie", Parcels.wrap(holder.getBinding().getMovie()));
                                startActivity(new Intent(MainActivity.this, DetailMovieActivity.class).putExtras(data));
                            }
                        });
                    }
                })
                .into(recyclerView);
    }

    @Override
    public void showError(String localizedMessage) {
        Toast.makeText(MainActivity.this, localizedMessage, Toast.LENGTH_SHORT).show();
        errorText.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        presenter.detachView();
    }

    @Override
    public void onRefresh() {
        refreshLayout.setRefreshing(false);
        presenter.getMovie(POPULAR);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_sort:
                showSortMovieMenu();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showSortMovieMenu() {
        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this)
                .setTitle("Sort Movie")
                .setItems(R.array.sort_movie, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == POPULAR) {
                            presenter.getMovie(POPULAR);
                        } else {
                            presenter.getMovie(TOP_RATED);
                        }
                    }
                });
        dialogBuilder.create();
        dialogBuilder.show();
    }
}
