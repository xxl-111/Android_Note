package com.example.android_note;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.android_note.DB.DataBase_Manage;

public class EditableActivity extends AppCompatActivity {
    Intent intent=null;
    private String mode= String.valueOf(0);
    private Notes notes=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editable_activity);
        intent=getIntent();
        if(intent!=null){
            mode= intent.getStringExtra("mode");
            notes=new Notes(Long.valueOf(intent.getStringExtra("id")),
                    intent.getStringExtra("content"),
                    intent.getStringExtra("time"),
                    intent.getStringExtra("title"),
                    Integer.valueOf(intent.getStringExtra("tag")));
            EditText title=findViewById(R.id.title_new);
            title.setText(notes.getTitle());
            EditText context=findViewById(R.id.context_new);
            context.setText(notes.getContent());
            EditText tag=findViewById(R.id.tag_new);
            tag.setText(String.valueOf(notes.getTag()));

        }




        Button btn_bacck=findViewById(R.id.button_back);
        btn_bacck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });
        Button btn_save=findViewById(R.id.button_save);
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText title=findViewById(R.id.title_new);
                EditText context=findViewById(R.id.context_new);
                EditText tag=findViewById(R.id.tag_new);
                if(!title.getText().toString().equals("")){
                    if(tag.getText().toString().equals(""))
                    {
                        Toast.makeText(EditableActivity.this, "当前笔记类别为空，默认为0", Toast.LENGTH_SHORT).show();
                        tag.setText("0");
                    }
                    DataBase_Manage dataBase_manage = new DataBase_Manage(EditableActivity.this);
                    dataBase_manage.db_open_write();
                    if(mode.equals("3")){
                        dataBase_manage.db_update(notes.getId(),title.getText().toString(), context.getText().toString(), Integer.valueOf(tag.getText().toString()));
                    }else {
                        dataBase_manage.db_insert(title.getText().toString(), context.getText().toString(), Integer.valueOf(tag.getText().toString()));
                    }
                    dataBase_manage.db_close();
                    startActivity(new Intent(EditableActivity.this, MainActivity.class));
                }else {
                    Toast.makeText(EditableActivity.this, "当前笔记主题不能为空", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void showDialog()
    {
        final boolean[] tip = {false};
        final int[] key = {0};
        AlertDialog.Builder builder=new AlertDialog.Builder(EditableActivity.this);
        builder.setTitle("!!!");
        builder.setMessage("是否保存当前笔记");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface p1, int p2) {
                EditText title=findViewById(R.id.title_new);
                EditText context=findViewById(R.id.context_new);
                EditText tag=findViewById(R.id.tag_new);
                if(!title.getText().toString().equals("")){
                    if(tag.getText().toString().equals(""))
                    {
                        Toast.makeText(EditableActivity.this, "当前笔记类别为空，默认为0", Toast.LENGTH_SHORT).show();
                        tag.setText("0");
                    }
                    DataBase_Manage dataBase_manage = new DataBase_Manage(EditableActivity.this);
                    dataBase_manage.db_open_write();
                    if(mode.equals("3")){
                        dataBase_manage.db_update(notes.getId(),title.getText().toString(), context.getText().toString(), Integer.valueOf(tag.getText().toString()));
                    }else {
                        dataBase_manage.db_insert(title.getText().toString(), context.getText().toString(), Integer.valueOf(tag.getText().toString()));
                    }
                    dataBase_manage.db_close();
                    startActivity(new Intent(EditableActivity.this, MainActivity.class));
                }else {
                    Toast.makeText(EditableActivity.this, "当前笔记主题不能为空", Toast.LENGTH_SHORT).show();
                }

            }
        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(EditableActivity.this, "当前笔记未保存", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(EditableActivity.this, MainActivity.class));
            }
        });
        builder.create();

        builder.show();



    }
}

