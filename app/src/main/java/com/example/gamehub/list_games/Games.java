package com.example.gamehub.list_games;

import android.os.Parcel;
import android.os.Parcelable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

public class Games implements Parcelable {
   private int id;
   private String title;
   private String thumbnail;
   private String short_description;
   private String game_url;
   private String genre;
   private String platform;
   private String publisher;
   private String developer;
   private String release_date;
   private String freetogame_profile_url;


   public Games(){}

    protected Games(Parcel in) {
        id = in.readInt();
        title = in.readString();
        thumbnail = in.readString();
        short_description = in.readString();
        game_url = in.readString();
        genre = in.readString();
        platform = in.readString();
        publisher = in.readString();
        developer = in.readString();
        release_date = in.readString();
        freetogame_profile_url = in.readString();
    }

    public static final Creator<Games> CREATOR = new Creator<Games>() {
        @Override
        public Games createFromParcel(Parcel in) {
            return new Games(in);
        }

        @Override
        public Games[] newArray(int size) {
            return new Games[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeString(thumbnail);
        dest.writeString(short_description);
        dest.writeString(game_url);
        dest.writeString(genre);
        dest.writeString(platform);
        dest.writeString(publisher);
        dest.writeString(developer);
        dest.writeString(release_date);
        dest.writeString(freetogame_profile_url);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getShort_description() {
        return short_description;
    }

    public void setShort_description(String short_description) {
        this.short_description = short_description;
    }

    public String getGame_url() {
        return game_url;
    }

    public void setGame_url(String game_url) {
        this.game_url = game_url;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getDeveloper() {
        return developer;
    }

    public void setDeveloper(String developer) {
        this.developer = developer;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public String getFreetogame_profile_url() {
        return freetogame_profile_url;
    }

    public void setFreetogame_profile_url(String freetogame_profile_url) {
        this.freetogame_profile_url = freetogame_profile_url;
    }
}
