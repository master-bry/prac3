package com.example.practicle3;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "UserData.db";
    public static final String TABLE_REVIEWS = "reviews";
        // Common Column Names
    public static final String COLUMN_ID = "id";
      // Reviews Table Columns
    public static final String COLUMN_REVIEW_TEXT = "review_text";
    public static final String COLUMN_TIMESTAMP = "timestamp";
    //Reviews Table Query
    private static final String CREATE_TABLE_REVIEWS = "CREATE TABLE " + TABLE_REVIEWS + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_REVIEW_TEXT + " TEXT,"
            + COLUMN_TIMESTAMP + " DATETIME DEFAULT CURRENT_TIMESTAMP"
            + ")";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create the reviews table
        db.execSQL(CREATE_TABLE_REVIEWS);
        Log.d("DatabaseHelper", "Reviews table created.");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older tables if they exist
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_REVIEWS);

        // Create tables again
        onCreate(db);
    }

    // Insert a review into the database
    public long addReview(String reviewText) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_REVIEW_TEXT, reviewText);

        // Insert row
        long id = db.insert(TABLE_REVIEWS, null, values);
        db.close();
        return id;
    }

    // Get all reviews from the database
    public Cursor getAllReviews() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(TABLE_REVIEWS, null, null, null, null, null, COLUMN_TIMESTAMP + " DESC");
    }

    // Delete a review from the database
    public void deleteReview(long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_REVIEWS, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
    }
}