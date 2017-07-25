package com.pratamawijaya.popularmovieudacity.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
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
import com.pratamawijaya.popularmovieudacity.ui.utils.EndlessScrollListener;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements MainView, SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    public static final int POPULAR = 0;
    public static final int TOP_RATED = 1;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.content)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.rv_list_movies)
    RecyclerView recyclerView;
    @BindView(R.id.tv_error)
    TextView errorText;
    @BindView(R.id.loading)
    ProgressBar loading;

    private EndlessScrollListener endlessScrollListener;

    private MovieRepository movieRepository;
    private TheMovieDbServices movieDbServices;

    private MainPresenter presenter;
    private int selectedSort = 0;
    private int page = 1;
    private LastAdapter lastAdapter;
    private List<Movie> movies = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        initRecyclerView();

        // init retrofit
        movieDbServices = TheMovieDbServices.Creator.instance();
        // init repository
        movieRepository = new MovieRepositoryImpl(movieDbServices);

        // init presenter
        presenter = new MainPresenter(movieRepository, this);

        // get movie data
        presenter.getMovie(POPULAR, page);

    }

    private void initRecyclerView() {
        final int columnCount = getResources().getInteger(R.integer.grid_count);
        final LinearLayoutManager layoutManager = new GridLayoutManager(this, columnCount);
        recyclerView.setLayoutManager(layoutManager);
        refreshLayout.setOnRefreshListener(this);

        endlessScrollListener = new EndlessScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int currentPage) {
                Log.d(TAG, "onLoadMore: " + currentPage);
                presenter.getMovie(selectedSort, currentPage);
            }
        };

        recyclerView.addOnScrollListener(endlessScrollListener);

        lastAdapter = new LastAdapter(movies, BR.movie)
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
        movies.addAll(results);
        lastAdapter.notifyDataSetChanged();
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
    protected void onResume() {
        super.onResume();
        presenter.attachView();
    }

    @Override
    protected void onStop() {
        super.onStop();
        presenter.detachView();
    }

    @Override
    public void onRefresh() {
        refreshLayout.setRefreshing(false);
        page = 1;
        presenter.getMovie(selectedSort, 1);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        page = 1;
        switch (item.getItemId()) {
            case R.id.menu_sort_popular:
                selectedSort = POPULAR;
                presenter.getMovie(POPULAR, page);
                break;
            case R.id.menu_sort_toprated:
                selectedSort = TOP_RATED;
                presenter.getMovie(TOP_RATED, page);
                break;
            case R.id.menu_favorited:
                presenter.getFavoriteMovie();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
