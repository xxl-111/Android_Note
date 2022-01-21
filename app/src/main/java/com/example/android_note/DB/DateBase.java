package com.example.android_note.DB;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class DateBase extends SQLiteOpenHelper {
    private String tablename="myrecord";
    private Context mycontext=null;
    private String sql="create table if not exists "+tablename
            +"(_id integer primary key autoincrement,"
            +"title varchar,"
            +"content text,"
            +"time varchar,"
            +"tag integer)";

    public DateBase(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
