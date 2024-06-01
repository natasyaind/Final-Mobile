package com.example.gamehub.retrofit;

import com.example.gamehub.list_games.Games;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {
    @GET("games?sort-by=alphabetical")
    Call<List<Games>> getGames();

    @GET("games?category=shooter")
    Call<List<Games>> getCategoryShooter();

    @GET("game?id=516")
    Call<List<Games>> getFavGame();
}
