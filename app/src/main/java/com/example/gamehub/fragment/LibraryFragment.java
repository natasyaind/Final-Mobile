package com.example.gamehub.fragment;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gamehub.R;
import com.example.gamehub.adapter.GamesAdapter;
import com.example.gamehub.list_games.Games;
import com.example.gamehub.sqlite.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LibraryFragment extends Fragment {
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private TextView connection, no_games;
    private Button btn_retry;
    private List<Games> libraryGames;
    private GamesAdapter adapter;

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
        return inflater.inflate(R.layout.fragment_library, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recycler_view_library);
        progressBar = view.findViewById(R.id.progressBar);
        connection = view.findViewById(R.id.connection);
        btn_retry = view.findViewById(R.id.retry);
        no_games = view.findViewById(R.id.no_games);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        libraryGames = new ArrayList<>();
        adapter = new GamesAdapter(libraryGames);
        recyclerView.setAdapter(adapter);

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
                                    no_games.setVisibility(libraryGames.isEmpty() ? View.VISIBLE : View.GONE);
                                    displayLibraryGames();
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
                    displayLibraryGames();
                });
            });
        }
    }

    private void displayLibraryGames() {
        DatabaseHelper databaseHelper = new DatabaseHelper(getContext(), null, null, 1);
        libraryGames.clear();
        libraryGames.addAll(databaseHelper.getAllGames());
        no_games.setVisibility(libraryGames.isEmpty() ? View.VISIBLE : View.GONE);

        // utk refresh tampilan RecyclerView
        adapter.notifyDataSetChanged();
    }
}