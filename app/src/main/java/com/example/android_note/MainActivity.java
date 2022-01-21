package com.example.android_note;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.android_note.DB.DataBase_Manage;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{

    final String TAG="TAG_NOTE";
    private ListView lv;
    private NoteAdapter adapter;
    private ArrayList<Notes> noteList =new ArrayList<>();//笔记集合
    private DataBase_Manage dataBase_manage=new DataBase_Manage(MainActivity.this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //界面跳转
                Intent intent=new Intent(MainActivity.this,EditableActivity.class);
                startActivity(intent);

            }
        });

        ///////////////////////////////////////////////
        //ListView在主页显示的具体实现
        lv=findViewById(R.id.lv);
        dataBase_manage.db_open_read();
        Cursor cursor = dataBase_manage.db_selectAll();

        while (cursor.moveToNext())
        {
            @SuppressLint("Range")
            long _id =cursor.getInt(cursor.getColumnIndex("_id"));
            @SuppressLint("Range")
            String context=cursor.getString(cursor.getColumnIndex("content"));
            @SuppressLint("Range")
            String title=cursor.getString(cursor.getColumnIndex("title"));
            @SuppressLint("Range")
            int tag=cursor.getInt(cursor.getColumnIndex("tag"));
            @SuppressLint("Range")
            String datetime=cursor.getString(cursor.getColumnIndex("time"));
            noteList.add(new Notes(_id,context, datetime, title, tag));



        }
        dataBase_manage.db_close();
        adapter=new NoteAdapter(getApplicationContext(),noteList);
        //refreshListView();//刷新ListView，数据库操作
        //lv.setAdapter(adapter);
        ///////////////////////////////////////////////


        ///////////////////////////////////////////////
        Toolbar toolbar = findViewById(R.id.toolbar);

        toolbar.setTitle("我的笔记");
        toolbar.setTitleTextColor(Color.BLACK);
        toolbar.inflateMenu(R.menu.menu_main);

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                return true;
            }
        });
        Log.e("xxl","null");

    }

    @SuppressLint("NonConstantResourceId")
    @Override//必须从AdapterView类中实现的接口内容
    // parent是识别是哪个listview
    // view是当前listview的item的view的布局，用这个view获取里面的控件的id后操作控件
    // position是当前item在listview中适配器里的位置
    // id 是 当前 item 在 listview 里的第几行的位置
    public void onItemClick(AdapterView<?> parent,View view,int position,long id){
        switch(parent.getId()){//根据id访问parent
            case R.id.lv:{
                Notes curNote=(Notes)parent.getItemAtPosition(position);//Object类型->Notes类型
                Intent intent=new Intent(MainActivity.this,EditableActivity.class);
                intent.putExtra("content",curNote.getContent());
                intent.putExtra("id",curNote.getId());
                intent.putExtra("time",curNote.getTime());
                intent.putExtra("title",curNote.getTitle());
                intent.putExtra("mode",3);
                intent.putExtra("tag",curNote.getTag());
                //以上putExtra内容分别表示将content、id、time、tag、mode（笔记当前的
                // 状态：可编辑、空笔记、点开未编辑，3代表已编辑）
                startActivityForResult(intent,1);
                Log.d(TAG, "onItemClick: "+position);
                break;
            }
        }
    }
}