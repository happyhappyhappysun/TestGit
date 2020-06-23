package com.example.test;
/**
 * 创建完该内容提供器，并且在清单中配置好provider，那么该项目就具备了共享数据的功能。
 * 然后再activity中利用resolver就可以操作书库啦~~~
 */

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class MyContentProvider extends ContentProvider {
    private Context mContext;
    private SQLiteDatabase mDataBase;
    private final static UriMatcher sUriMatcher;
    private final static String AUTHORITY = "com.example.test.provider";
    private final static int URI_CODE_1 =0;
    private final static int URI_CODE_2 =1;
    static{
        sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        //只需要对一个表进行CURD
        sUriMatcher.addURI(AUTHORITY,"person",URI_CODE_1);
        //sUriMatcher.addURI(AUTHORITY,"person",URI_CODE_2);
    }
    @Override
    public boolean onCreate() {
        //创建数据库以及初始化操作
        mContext = getContext();
        mDataBase = new DBOpenHelper(mContext).getWritableDatabase();
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        int uriType = sUriMatcher.match(uri);
        Cursor cursor;
        switch (uriType){
            case URI_CODE_1:
                cursor = mDataBase.query(DBOpenHelper.DATABASE_PERSON_TABLE_NAME,projection,selection,selectionArgs,null,null,sortOrder);

                break;
            default:
                throw new IllegalArgumentException("UnSupport Uri : " + uri);
        }
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        int uriType = sUriMatcher.match(uri);
        long row;
        Uri retrunUri;
        switch (uriType){
            case URI_CODE_1:
                row = mDataBase.insert(DBOpenHelper.DATABASE_PERSON_TABLE_NAME,null,values);
                //返回uri
                retrunUri = Uri.parse("content://"+AUTHORITY+"/person"+row);
                break;
            default:
                throw new IllegalArgumentException("UnSupport Uri : " + uri);
        }
        return retrunUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        int uriType = sUriMatcher.match(uri);
        int deleteRow;
        switch (uriType){
            case URI_CODE_1:
                deleteRow = mDataBase.delete(DBOpenHelper.DATABASE_PERSON_TABLE_NAME,selection,selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("UnSupport Uri : " + uri);
        }
        return deleteRow;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        int uriType = sUriMatcher.match(uri);
        int updateRow;
        switch (uriType){
            case URI_CODE_1:
                updateRow = mDataBase.update(DBOpenHelper.DATABASE_PERSON_TABLE_NAME,values,selection,selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("UnSupport Uri : " + uri);
        }
        return updateRow;
    }
}
