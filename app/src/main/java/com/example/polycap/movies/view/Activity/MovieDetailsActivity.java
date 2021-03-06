package com.example.polycap.movies.view.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.example.polycap.movies.R;
import com.example.polycap.movies.model.MovieDetailsModel;
import com.example.polycap.movies.model.VideoItem;
import com.example.polycap.movies.presenter.MovieDetailsPresenter;
import com.example.polycap.movies.view.YouTubeAdapter;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Polycap on 1/31/2016.
 */
public class MovieDetailsActivity extends AppCompatActivity implements MovieDetailsI {

    private final static String IMG_ENDPOINT = "https://image.tmdb.org/t/p/w500/";

    public Toolbar toolbar;
    public CollapsingToolbarLayout mcollapsingtoolbar;
    protected MovieDetailsPresenter movieDetailsPresenter;
    private YouTubeAdapter adapter;
    private RecyclerView recyclerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_entertainment);
        int id;
        id = getIntent().getExtras().getInt("id");

        recyclerView = (RecyclerView) findViewById(R.id.YoutubeRecyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        adapter = new YouTubeAdapter();
        recyclerView.setAdapter(adapter);

        movieDetailsPresenter = new MovieDetailsPresenter(this);
        movieDetailsPresenter.getMoviesDetailsData(id);


        toolbar = (Toolbar) findViewById(R.id.detailstoolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


    }

    @Override
    public void displayMovieData(MovieDetailsModel movieDetailsModel) {
//        Setting up view with data

        String title = movieDetailsModel.getTitle();
        mcollapsingtoolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        mcollapsingtoolbar.setTitle(title);
        mcollapsingtoolbar.setExpandedTitleTextAppearance(R.style.CollapsedAppBar);
        mcollapsingtoolbar.setExpandedTitleTextAppearance(R.style.CollapsedAppBar);


        ImageView mbackdrop = (ImageView) findViewById(R.id.backdrop);
        Picasso.with(this).setLoggingEnabled(true);
        String mbackdropUrl = IMG_ENDPOINT + movieDetailsModel.getBackdropPath();
        Picasso.with(this).load(mbackdropUrl).into(mbackdrop);

        adapter.setOverViewText(movieDetailsModel.getOverview());
        movieDetailsPresenter.searchOnYoutube(title);

    }

    public void getYoutubeData(List<VideoItem> youtubeVideos) {
//        Get live YouTube Data
        adapter.setSearchResults(youtubeVideos);
        adapter.notifyDataSetChanged();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent myIntent = new Intent(getApplicationContext(), EntertainmentActivity.class);
        startActivityForResult(myIntent, 0);
        return true;
    }

}
