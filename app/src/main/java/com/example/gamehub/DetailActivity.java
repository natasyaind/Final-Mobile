package com.example.gamehub;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.gamehub.list_games.Games;
import com.squareup.picasso.Picasso;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DetailActivity extends AppCompatActivity {

    private LinearLayout short_desc_layout;
    private RelativeLayout detail;
    private ImageView thumbnail;
    private TextView title, publisher, developer, release_date, short_desc, genre, platform, connection;
    private ProgressBar progressBarDetail;
    private Button btn_retry;
    private ImageButton btn_back;

    // untuk cek koneksi internet
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            return activeNetworkInfo != null && activeNetworkInfo.isConnected();
        }
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        thumbnail = findViewById(R.id.detail_thumbnail);
        title = findViewById(R.id.detail_title_games);
        publisher = findViewById(R.id.publisher);
        developer = findViewById(R.id.developer);
        release_date = findViewById(R.id.release_date);
        genre = findViewById(R.id.detail_genre);
        platform = findViewById(R.id.platform);
        short_desc = findViewById(R.id.short_desc);
        progressBarDetail = findViewById(R.id.progressBarDetail);
        btn_retry = findViewById(R.id.retry);
        btn_back = findViewById(R.id.btn_back);
        connection = findViewById(R.id.connection);
        detail = findViewById(R.id.detail);
        short_desc_layout = findViewById(R.id.short_desc_layout);

        // btn_back
        btn_back.setOnClickListener(v -> {
            finish();
        });

        Games games = getIntent().getParcelableExtra("games");

        // kalo ada jaringan
        if (isNetworkAvailable()) {
            if (games != null) {
                progressBarDetail.setVisibility(View.VISIBLE);
                thumbnail.setVisibility(View.GONE);
                title.setVisibility(View.GONE);
                publisher.setVisibility(View.GONE);
                developer.setVisibility(View.GONE);
                release_date.setVisibility(View.GONE);
                genre.setVisibility(View.GONE);
                platform.setVisibility(View.GONE);
                short_desc.setVisibility(View.GONE);
                detail.setVisibility(View.GONE);
                short_desc_layout.setVisibility(View.GONE);

                ExecutorService executor = Executors.newSingleThreadExecutor();
                executor.execute(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                progressBarDetail.setVisibility(View.GONE);
                                Picasso.get().load(games.getThumbnail()).into(thumbnail);
                                title.setText(games.getTitle());
                                publisher.setText(games.getPublisher());
                                developer.setText(games.getDeveloper());
                                release_date.setText(games.getRelease_date());
                                genre.setText(games.getGenre());
                                platform.setText(games.getPlatform());
                                short_desc.setText(games.getShort_description());

                                // visibility
                                thumbnail.setVisibility(View.VISIBLE);
                                title.setVisibility(View.VISIBLE);
                                publisher.setVisibility(View.VISIBLE);
                                developer.setVisibility(View.VISIBLE);
                                release_date.setVisibility(View.VISIBLE);
                                genre.setVisibility(View.VISIBLE);
                                platform.setVisibility(View.VISIBLE);
                                short_desc.setVisibility(View.VISIBLE);
                                detail.setVisibility(View.VISIBLE);
                                short_desc_layout.setVisibility(View.VISIBLE);
                            }
                        });
                    }
                });
            } else {
                Toast.makeText(this, "Games data is null", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
        // kalo ndd jaringan
        if (!isNetworkAvailable()) {
            if (games != null) {
                btn_retry.setVisibility(View.VISIBLE);
                connection.setVisibility(View.VISIBLE);
                progressBarDetail.setVisibility(View.GONE);
                thumbnail.setVisibility(View.GONE);
                title.setVisibility(View.GONE);
                publisher.setVisibility(View.GONE);
                developer.setVisibility(View.GONE);
                release_date.setVisibility(View.GONE);
                genre.setVisibility(View.GONE);
                platform.setVisibility(View.GONE);
                short_desc.setVisibility(View.GONE);
                detail.setVisibility(View.GONE);
                short_desc_layout.setVisibility(View.GONE);

                btn_retry.setOnClickListener(v -> {
                    if (isNetworkAvailable()) {
                        btn_retry.setVisibility(View.GONE);
                        connection.setVisibility(View.GONE);
                        progressBarDetail.setVisibility(View.VISIBLE);

                        ExecutorService executor = Executors.newSingleThreadExecutor();
                        executor.execute(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    Thread.sleep(2000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        progressBarDetail.setVisibility(View.GONE);
                                        Picasso.get().load(games.getThumbnail()).into(thumbnail);
                                        title.setText(games.getTitle());
                                        publisher.setText(games.getPublisher());
                                        developer.setText(games.getDeveloper());
                                        release_date.setText(games.getRelease_date());
                                        genre.setText(games.getGenre());
                                        platform.setText(games.getPlatform());
                                        short_desc.setText(games.getShort_description());

                                        // visibility
                                        thumbnail.setVisibility(View.VISIBLE);
                                        title.setVisibility(View.VISIBLE);
                                        publisher.setVisibility(View.VISIBLE);
                                        developer.setVisibility(View.VISIBLE);
                                        release_date.setVisibility(View.VISIBLE);
                                        genre.setVisibility(View.VISIBLE);
                                        platform.setVisibility(View.VISIBLE);
                                        short_desc.setVisibility(View.VISIBLE);
                                        detail.setVisibility(View.VISIBLE);
                                        short_desc_layout.setVisibility(View.VISIBLE);
                                    }
                                });
                            }
                        });
                    } else {
                        progressBarDetail.setVisibility(View.VISIBLE);
                        btn_retry.setVisibility(View.GONE);
                        connection.setVisibility(View.GONE);

                        ExecutorService executor = Executors.newSingleThreadExecutor();
                        executor.execute(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    Thread.sleep(300);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        progressBarDetail.setVisibility(View.GONE);
                                        btn_retry.setVisibility(View.VISIBLE);
                                        connection.setVisibility(View.VISIBLE);
                                    }
                                });
                            }
                        });
                    }
                });
            }
        }
    }
}