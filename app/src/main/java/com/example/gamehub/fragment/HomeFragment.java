package com.example.gamehub.fragment;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

public class HomeFragment extends Fragment {
    private ApiService apiService;
    private RecyclerView recyclerView;
    private GamesAdapter gamesAdapter;
    private List<Games> gameslist;
    private ConstraintLayout home_layout;
    private ProgressBar progressBar;
    private Button btn_retry;
    private TextView connection;

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
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.rv_shooterCategory);
        home_layout = view.findViewById(R.id.fhome_layout);
        progressBar = view.findViewById(R.id.progressBar);
        connection = view.findViewById(R.id.connection);
        btn_retry = view.findViewById(R.id.retry);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        apiService = RetrofitClient.getClient().create(ApiService.class);

        btn_retry.setVisibility(View.GONE);
        connection.setVisibility(View.GONE);

        gameslist = new ArrayList<>();

        ExecutorService executor = Executors.newSingleThreadExecutor();

        if (!isNetworkAvailable()) {
            btn_retry.setVisibility(View.VISIBLE);
            connection.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
            home_layout.setVisibility(View.GONE);

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
                                    home_layout.setVisibility(View.VISIBLE);
                                    shooterCategory();
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
            home_layout.setVisibility(View.GONE);
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
                            home_layout.setVisibility(View.VISIBLE);
                            shooterCategory();
                        }
                    });
                }
            });
        }
    }

    private void shooterCategory() {
        Call<List<Games>> call = apiService.getCategoryShooter();

        call.enqueue(new Callback<List<Games>>() {
            @Override
            public void onResponse(Call<List<Games>> call, Response<List<Games>> response) {
                if (response.isSuccessful()) {
                    gameslist = response.body();
                    gamesAdapter = new GamesAdapter(gameslist);
                    recyclerView.setAdapter(gamesAdapter);
                } else {
                    Toast.makeText(getContext(), "Failed to fetch games data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Games>> call, Throwable t) {
                Toast.makeText(getContext(), "Network error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}