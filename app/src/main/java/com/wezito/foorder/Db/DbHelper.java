package com.wezito.foorder.Db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "Ordenes.db";
    public static final String TABLE_ORDENES = "t_ordenes";

    public DbHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_ORDENES + "(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "dishes TEXT NOT NULL," +
                "quantity INTEGER NOT NULL," +
                "price INTEGER NOT NULL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE " + TABLE_ORDENES);
        onCreate(sqLiteDatabase);
    }
}
