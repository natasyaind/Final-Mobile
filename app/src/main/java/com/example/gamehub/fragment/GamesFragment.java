package com.example.gamehub.fragment;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Looper;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gamehub.R;
import com.example.gamehub.adapter.GamesAdapter;
import com.example.gamehub.list_games.Games;
import com.example.gamehub.list_games.GamesResponse;
import com.example.gamehub.retrofit.ApiService;
import com.example.gamehub.retrofit.RetrofitClient;

import java.lang.reflect.Executable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class GamesFragment extends Fragment {

    private ApiService apiService;
    private RecyclerView recyclerView;
    private GamesAdapter gamesAdapter;
    private List<Games> gameslist;
    private List<Games> allGameslist;
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
        return inflater.inflate(R.layout.fragment_games, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recycler_view);
        progressBar = view.findViewById(R.id.progressBar);
        btn_retry = view.findViewById(R.id.retry);
        connection = view.findViewById(R.id.connection);
        androidx.appcompat.widget.SearchView search = view.findViewById(R.id.search_view);

        search.setFocusable(true);
        search.setQueryHint("Find your games...");
        search.setIconifiedByDefault(false);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        apiService = RetrofitClient.getClient().create(ApiService.class);

        gameslist = new ArrayList<>();
        allGameslist = new ArrayList<>();
        gamesAdapter = new GamesAdapter(gameslist);
        recyclerView.setAdapter(gamesAdapter);

        ExecutorService executor = Executors.newSingleThreadExecutor();

        if (!isNetworkAvailable()) {
            btn_retry.setVisibility(View.VISIBLE);
            connection.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);

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
                                    GamesData();
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
            progressBar.setVisibility(View.VISIBLE);
            executor.execute(() -> {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                requireActivity().runOnUiThread(() -> {
                    progressBar.setVisibility(View.GONE);
                    GamesData();
                });
            });
        }

        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (!isNetworkAvailable()) {
                    progressBar.setVisibility(View.GONE);
                    connection.setVisibility(View.VISIBLE);
                    btn_retry.setVisibility(View.VISIBLE);
                    gameslist.clear();
                    gamesAdapter.notifyDataSetChanged();

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
                                            GamesData();
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
                    return true;
                }
                if (isNetworkAvailable()) {
                    progressBar.setVisibility(View.VISIBLE);
                    btn_retry.setVisibility(View.GONE);
                    connection.setVisibility(View.GONE);
                    requireActivity().runOnUiThread(() -> {
                        progressBar.setVisibility(View.GONE);
                        filterGames(newText);
                    });
                }
                return true;
            }
        });
    }

    private void GamesData() {
        Call<List<Games>> call = apiService.getGames();

        call.enqueue(new Callback<List<Games>>() {
            @Override
            public void onResponse(Call<List<Games>> call, Response<List<Games>> response) {
                if (response.isSuccessful()) {
                    gameslist = response.body();
                    allGameslist.clear();
                    allGameslist.addAll(gameslist); // Copy to the original list
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

    // untuk search
    private void filterGames(String newText) {
        progressBar.setVisibility(View.VISIBLE);

        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executor.execute(() -> {
            List<Games> filterList = new ArrayList<>();
            if (!newText.isEmpty()) {
                for (Games game : allGameslist) {
                    if (game.getTitle().toLowerCase().contains(newText.toLowerCase())) {
                        filterList.add(game);
                    }
                }
            } else {
                filterList.addAll(allGameslist); // kalo tidak ada inputan search, ttp ditampilkan semua game nya
            }
            try {
                Thread.sleep(600);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            handler.post(() -> {
                progressBar.setVisibility(View.GONE);
                gameslist.clear();
                if (filterList.isEmpty() && !newText.isEmpty()) {
                    Toast.makeText(getContext(), "Games not found.", Toast.LENGTH_SHORT).show();
                } else {
                    gameslist.addAll(filterList);
                }
                gamesAdapter.notifyDataSetChanged();
            });
        });
    }
}
