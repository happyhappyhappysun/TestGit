package com.example.test;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DBOpenHelper extends SQLiteOpenHelper {
    private final static String TAG = "DBOpenHelper";

    private final static String DATABASE_NAME = "test.db";
    public final static String DATABASE_PERSON_TABLE_NAME = "person";
    private final static int DATABASE_VERSION = 3;
    private final static String CREATE_PERSON_TABLE = "CREATE TABLE IF NOT EXISTS "
            + DATABASE_PERSON_TABLE_NAME
            + "(id INTEGER PRIMARY KEY autoincrement,"
            + "name TEXT,"
            + "age TEXT)";
    private Context mContext;
    public DBOpenHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext = context;
    }
    public DBOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        mContext = context;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG, "onCreate");
        db.execSQL(CREATE_PERSON_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(TAG, "onUpgrade o = " + oldVersion + " , n = " + newVersion);
    }
}
