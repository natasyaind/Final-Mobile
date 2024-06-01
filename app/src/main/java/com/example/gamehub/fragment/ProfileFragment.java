package com.example.gamehub.fragment;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gamehub.R;
import com.example.gamehub.adapter.GamesAdapter;
import com.example.gamehub.list_games.Games;
import com.example.gamehub.retrofit.ApiService;
import com.example.gamehub.retrofit.RetrofitClient;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ProfileFragment extends Fragment {
    private ImageView bg, top_bg, profile;
    private TextView  connection, usn;
    private LinearLayout ig_layout, fav_layout;
    private ProgressBar progressBar;
    private Button btn_retry;

    // untuk cek koneksi internet
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) requireContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            return activeNetworkInfo != null && activeNetworkInfo.isConnected();
        }
        return false;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        bg = view.findViewById(R.id.bg);
        top_bg = view.findViewById(R.id.top_bg);
        profile = view.findViewById(R.id.profile);
        usn = view.findViewById(R.id.usn);
        ig_layout = view.findViewById(R.id.ig_layout);
        fav_layout = view.findViewById(R.id.fav_layout);
        progressBar = view.findViewById(R.id.progressBar);
        connection = view.findViewById(R.id.connection);
        btn_retry = view.findViewById(R.id.retry);

        ExecutorService executor = Executors.newSingleThreadExecutor();

        // background thread
        if (!isNetworkAvailable()) {
            btn_retry.setVisibility(View.VISIBLE);
            connection.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
            bg.setVisibility(View.GONE);
            top_bg.setVisibility(View.GONE);
            profile.setVisibility(View.GONE);
            usn.setVisibility(View.GONE);
            ig_layout.setVisibility(View.GONE);
            fav_layout.setVisibility(View.GONE);

            btn_retry.setOnClickListener(v -> {
                if (isNetworkAvailable()) {
                    btn_retry.setVisibility(View.GONE);
                    connection.setVisibility(View.GONE);
                    progressBar.setVisibility(View.VISIBLE);
                    executor.execute(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Thread.sleep(2000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            requireActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    progressBar.setVisibility(View.GONE);
                                    bg.setVisibility(View.VISIBLE);
                                    top_bg.setVisibility(View.VISIBLE);
                                    profile.setVisibility(View.VISIBLE);
                                    usn.setVisibility(View.VISIBLE);
                                    ig_layout.setVisibility(View.VISIBLE);
                                    fav_layout.setVisibility(View.VISIBLE);
                                }
                            });
                        }
                    });
                } else {
                    progressBar.setVisibility(View.VISIBLE);
                    btn_retry.setVisibility(View.GONE);
                    connection.setVisibility(View.GONE);
                    executor.execute(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Thread.sleep(300);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            requireActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    progressBar.setVisibility(View.GONE);
                                    btn_retry.setVisibility(View.VISIBLE);
                                    connection.setVisibility(View.VISIBLE);
                                }
                            });
                        }
                    });
                }
            });
        }
        if (isNetworkAvailable()) {
            btn_retry.setVisibility(View.GONE);
            connection.setVisibility(View.GONE);
            bg.setVisibility(View.GONE);
            top_bg.setVisibility(View.GONE);
            profile.setVisibility(View.GONE);
            usn.setVisibility(View.GONE);
            ig_layout.setVisibility(View.GONE);
            fav_layout.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    requireActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            progressBar.setVisibility(View.GONE);
                            bg.setVisibility(View.VISIBLE);
                            top_bg.setVisibility(View.VISIBLE);
                            profile.setVisibility(View.VISIBLE);
                            usn.setVisibility(View.VISIBLE);
                            ig_layout.setVisibility(View.VISIBLE);
                            fav_layout.setVisibility(View.VISIBLE);
                        }
                    });
                }
            });
        }
    }
}