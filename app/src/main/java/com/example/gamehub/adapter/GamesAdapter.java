package com.example.gamehub.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gamehub.DetailActivity;
import com.example.gamehub.R;
import com.example.gamehub.list_games.Games;
import com.example.gamehub.sqlite.DatabaseHelper;
import com.squareup.picasso.Picasso;

import java.util.List;

public class GamesAdapter extends RecyclerView.Adapter<GamesAdapter.GamesViewHolder> {

    private List<Games> gamesList;

    public GamesAdapter(List<Games> gamesList) {
        this.gamesList = gamesList;
    }


    private boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            return activeNetworkInfo != null && activeNetworkInfo.isConnected();
        }
        return false;
    }


    @NonNull
    @Override
    public GamesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_games, parent, false);
        return new GamesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GamesAdapter.GamesViewHolder holder, int position) {
        Games games = gamesList.get(position);

        holder.bind(games);

        DatabaseHelper databaseHelper = new DatabaseHelper(holder.context, null, null, 1);
        boolean isInLibrary = databaseHelper.isGameInLibrary(games);

        holder.add_to_library.setText(isInLibrary ? "Remove" : "Add to Library");
        holder.addLayout.setBackgroundResource(isInLibrary ? R.drawable.remove_from_library : R.drawable.add_to_library);

        holder.add_to_library.setOnClickListener(v -> {
            if (!isNetworkAvailable(holder.context)) {
                Toast.makeText(holder.context, "Connection lost. Cannot remove the game.", Toast.LENGTH_SHORT).show();
                return;
            }

            if (isInLibrary) {
                removeFromLibrary(holder.context, games);
                holder.add_to_library.setText("Add to Library");
                holder.addLayout.setBackgroundResource(R.drawable.add_to_library);
            } else {
                addToLibrary(holder.context, games);
                holder.add_to_library.setText("Remove");
                holder.addLayout.setBackgroundResource(R.drawable.remove_from_library);
            }
            notifyDataSetChanged();
        });

        holder.list_games.setOnClickListener(v -> {
            Intent intent = new Intent(holder.context, DetailActivity.class);
            intent.putExtra("games", games);
            holder.context.startActivity(intent);
        });
    }


    private void addToLibrary(Context context, Games game) {
        DatabaseHelper databaseHelper = new DatabaseHelper(context, null, null, 1);
        databaseHelper.addGame(game);
        Toast.makeText(context, "Successfully added to library", Toast.LENGTH_SHORT).show();
    }

    private void removeFromLibrary(Context context, Games game) {
        DatabaseHelper databaseHelper = new DatabaseHelper(context, null, null, 1);
        databaseHelper.removeGame(game);
        Toast.makeText(context, "Removed from library", Toast.LENGTH_SHORT).show();
    }



    @Override
    public int getItemCount() {
        return gamesList.size();
    }

    public interface OnClickListener {
    }

    public static class GamesViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout list_games;
        private ImageView iv_thumbnail;
        private TextView tv_title, tv_genre, add_to_library;
        private LinearLayout addLayout;
        Context context;
        public GamesViewHolder(@NonNull View itemView) {
            super(itemView);
            list_games = itemView.findViewById(R.id.list_games_layout);
            iv_thumbnail = itemView.findViewById(R.id.thumbnail);
            tv_title = itemView.findViewById(R.id.title);
            tv_genre = itemView.findViewById(R.id.genre);
            addLayout = itemView.findViewById(R.id.add_layout);
            add_to_library = itemView.findViewById(R.id.add_games);
            context = itemView.getContext();
        }

        public void bind(Games games) {
            Picasso.get().load(games.getThumbnail()).into(iv_thumbnail);
            tv_title.setText(games.getTitle());
            tv_genre.setText(games.getGenre());
        }
    }
}
