package com.example.gamehub.sqlite;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.gamehub.list_games.Games;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "Library";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "games";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_THUMBNAIL = "thumbnail";
    private static final String COLUMN_GENRE = "genre";
    private static final String COLUMN_PUBLISHER = "publisher";
    private static final String COLUMN_DEVELOPER = "developer";
    private static final String COLUMN_RELEASE_DATE = "release_date";
    private static final String COLUMN_PLATFORM = "platform";
    private static final String COLUMN_SHORT_DESCRIPTION = "short_description";
    private static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    COLUMN_TITLE + " TEXT," +
                    COLUMN_THUMBNAIL + " TEXT," +
                    COLUMN_GENRE + " TEXT," +
                    COLUMN_PUBLISHER + " TEXT," +
                    COLUMN_DEVELOPER + " TEXT," +
                    COLUMN_RELEASE_DATE + " TEXT," +
                    COLUMN_PLATFORM + " TEXT," +
                    COLUMN_SHORT_DESCRIPTION + " TEXT)";

    public DatabaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    // method utk tambahkan game ke library
    public void addGame(Games game) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_TITLE, game.getTitle());
        values.put(COLUMN_THUMBNAIL, game.getThumbnail());
        values.put(COLUMN_GENRE, game.getGenre());
        values.put(COLUMN_PUBLISHER, game.getPublisher());
        values.put(COLUMN_DEVELOPER, game.getDeveloper());
        values.put(COLUMN_RELEASE_DATE, game.getRelease_date());
        values.put(COLUMN_PLATFORM, game.getPlatform());
        values.put(COLUMN_SHORT_DESCRIPTION, game.getShort_description());

        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    // method utk ambil daftar game yg ada di library
    @SuppressLint("Range")
    public List<Games> getAllGames() {
        List<Games> gamesList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Games game = new Games();
                for (String columnName : cursor.getColumnNames()) {
                    Log.d("DatabaseHelper", "Column: " + columnName);
                }
                game.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));
                game.setTitle(cursor.getString(cursor.getColumnIndex(COLUMN_TITLE)));
                game.setThumbnail(cursor.getString(cursor.getColumnIndex(COLUMN_THUMBNAIL)));
                game.setGenre(cursor.getString(cursor.getColumnIndex(COLUMN_GENRE)));
                game.setPublisher(cursor.getString(cursor.getColumnIndex(COLUMN_PUBLISHER)));
                game.setDeveloper(cursor.getString(cursor.getColumnIndex(COLUMN_DEVELOPER)));
                game.setRelease_date(cursor.getString(cursor.getColumnIndex(COLUMN_RELEASE_DATE)));
                game.setPlatform(cursor.getString(cursor.getColumnIndex(COLUMN_PLATFORM)));
                game.setShort_description(cursor.getString(cursor.getColumnIndex(COLUMN_SHORT_DESCRIPTION)));
                gamesList.add(game);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return gamesList;
    }

    // method utk hapus game dari library
    public void removeGame(Games game) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, COLUMN_TITLE + " = ?", new String[]{game.getTitle()});
        db.close();
    }

    // method utk periksa apakah game sudah ada di library
    public boolean isGameInLibrary(Games game) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_TITLE + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{game.getTitle()});
        boolean isInLibrary = cursor.getCount() > 0;
        cursor.close();
        db.close();
        return isInLibrary;
    }
}
