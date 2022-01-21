package com.example.android_note.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.*;
import android.database.sqlite.SQLiteOpenHelper;
import android.icu.text.SimpleDateFormat;
import android.util.Log;
import android.widget.Toast;

import com.example.android_note.MainActivity;

import java.sql.Date;
import java.text.DateFormat;

public class DataBase_Manage {
    private Context mycontext = null;
    private SQLiteDatabase sql_db = null;
    private DateBase db = null;
    private String db_name = "notepad.db";
    private int db_Version = 1;


    public DataBase_Manage(Context context) {
        mycontext = context;
    }

    public void db_open_write() {

        try {
            db = new DateBase(mycontext, db_name, null, db_Version);
            if (db == null) {
                Log.v("msg", "is null");
            }
            sql_db = db.getWritableDatabase();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void db_open_read() {
        try {
            db = new DateBase(mycontext, db_name, null, db_Version);
            if (db == null) {
                Log.v("msg", "is null");
            }
            sql_db = db.getReadableDatabase();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void db_close() {
        try {
            sql_db.close();
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //显示列表
    public Cursor db_selectAll() {
        Cursor cursor = null;

        try {
            String sql = "select * from myrecord";
            cursor = sql_db.rawQuery(sql, null);
        } catch (Exception e) {
            e.printStackTrace();
            cursor = null;

        }
        return cursor;
    }

    //插入
    public long db_insert(String title, String content, int tag) {
        Date nowTime = new Date(System.currentTimeMillis());
        SimpleDateFormat sdFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:dd");
        String datetime = sdFormatter.format(nowTime);

        long l = -1;

        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put("title", title);
            contentValues.put("content", content);
            contentValues.put("time", datetime);
            contentValues.put("tag", tag);
            l = sql_db.insert("myrecord", null, contentValues);
            Log.e("插入成功","第"+String.valueOf(l)+"行");
        } catch (Exception e) {
            e.printStackTrace();
            l = -1;

        }


        return l;
    }

    //修改
    public int db_update(int _id, String title, String content, int tag) {
        int result = 0;

        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put("title", title);
            contentValues.put("content", content);
            contentValues.put("tag", tag);
            String tmp[] = {_id + ""};
            result = sql_db.update("myrecord", contentValues, "_id=?", tmp);
            Log.e("修改成功","第"+String.valueOf(result)+"行");
        } catch (Exception e) {
            e.printStackTrace();
            result = -1;
        }

        return result;

    }

}


