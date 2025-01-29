package com.example.practicle3;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "UserData.db";
    public static final String TABLE_REVIEWS = "reviews";
    public static final String TABLE_FAVORITES = "favorites";
    public static final String COLUMN_ID = "id";

    public static final String COLUMN_FAVORITE_TEXT = "favorite_text";
    public static final String COLUMN_REVIEW_TEXT = "review_text";
    public static final String COLUMN_TIMESTAMP = "timestamp";

    private static final String CREATE_TABLE_REVIEWS = "CREATE TABLE " + TABLE_REVIEWS + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_REVIEW_TEXT + " TEXT,"
            + COLUMN_TIMESTAMP + " DATETIME DEFAULT CURRENT_TIMESTAMP"
            + ")";

    private static final String CREATE_TABLE_FAVORITES = "CREATE TABLE " + TABLE_FAVORITES + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_FAVORITE_TEXT + " TEXT,"
            + COLUMN_TIMESTAMP + " DATETIME DEFAULT CURRENT_TIMESTAMP"
            + ")";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_REVIEWS);
        db.execSQL(CREATE_TABLE_FAVORITES);
        Log.d("DatabaseHelper", "Reviews and Favorites tables created.");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_REVIEWS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FAVORITES);
        onCreate(db);
    }


    public long addReview(String reviewText) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_REVIEW_TEXT, reviewText);

       //row
        long id = db.insert(TABLE_REVIEWS, null, values);
        db.close();
        return id;
    }

    public long addFavorite(String favoriteText) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_FAVORITE_TEXT, favoriteText);

        // Insert row
        long id = db.insert(DatabaseHelper.TABLE_FAVORITES, null, values);
        db.close();
        return id;
    }



    public Cursor getAllReviews() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(TABLE_REVIEWS, null, null, null, null, null, COLUMN_TIMESTAMP + " DESC");
    }


    public Cursor getAllFavorites() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(TABLE_FAVORITES, null, null, null, null, null, COLUMN_TIMESTAMP + " DESC");
    }

    public void deleteFavorite(long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_FAVORITES, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
    }

    public void deleteReview(long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_REVIEWS, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
    }
}