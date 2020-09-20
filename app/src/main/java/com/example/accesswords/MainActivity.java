package com.example.accesswords;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private ContentResolver resolver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button buttonInsert=(Button)findViewById(R.id.zheng_jia);
        Button buttonDelete=(Button)findViewById(R.id.shan_chu);
        Button buttonUpdate=(Button)findViewById(R.id.geng_gai);
        Button buttonQuery=(Button)findViewById(R.id.cha_xun);
        resolver = this.getContentResolver();
        buttonInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final View viewDialog = LayoutInflater.from(MainActivity.this).inflate(R.layout.activity_instert, null, false);
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("增加")//标题
                        .setView(viewDialog)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                EditText e1=viewDialog.findViewById(R.id.insert_name_edit);
                                EditText e2=viewDialog.findViewById(R.id.insert_meaning_edit);
                                EditText e3=viewDialog.findViewById(R.id.insert_sample_edit);
                                ContentValues values = new ContentValues();
                                values.put(Words.Word.COLUMN_NAME_WORD, e1.getText().toString());
                                values.put(Words.Word.COLUMN_NAME_MEANING, e2.getText().toString());
                                values.put(Words.Word.COLUMN_NAME_SAMPLE, e3.getText().toString());

                                Uri newUri = resolver.insert(Words.Word.CONTENT_URI, values);
                            }
                        })
                        //取消按钮及其动作
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        })
                        .create()//创建对话框
                        .show();//显示对话框

            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final View viewDialog = LayoutInflater.from(MainActivity.this).inflate(R.layout.activity_delte, null, false);
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("删除")//标题
                        .setView(viewDialog)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                EditText e1=viewDialog.findViewById(R.id.delte_name_edit);
                                int result = resolver.delete(Words.Word.CONTENT_URI, "_id =?", new String[]{e1.getText().toString()});
                            }
                        })
                        //取消按钮及其动作
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        })
                        .create()//创建对话框
                        .show();//显示对话框
            }
        });

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final View viewDialog = LayoutInflater.from(MainActivity.this).inflate(R.layout.activity_updata, null, false);
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("更改")//标题
                        .setView(viewDialog)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                EditText e1=viewDialog.findViewById(R.id.updata_name_edit);
                                EditText e2=viewDialog.findViewById(R.id.updata_meaning_edit);
                                EditText e3=viewDialog.findViewById(R.id.updata_sample_edit);
                                EditText eid=viewDialog.findViewById(R.id.updata_id_edit);
                                ContentValues values = new ContentValues();
                                //values.put("_id",eid.getText().toString());
                                values.put(Words.Word.COLUMN_NAME_WORD, e1.getText().toString());
                                values.put(Words.Word.COLUMN_NAME_MEANING, e2.getText().toString());
                                values.put(Words.Word.COLUMN_NAME_SAMPLE, e3.getText().toString());
                                int result = resolver.update(Words.Word.CONTENT_URI, values, "_id=?", new String[]{eid.getText().toString() });
                            }
                        })
                        //取消按钮及其动作
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        })
                        .create()//创建对话框
                        .show();//显示对话框
            }
        });

        buttonQuery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor cursor = resolver.query(Words.Word.CONTENT_URI,
                        new String[] { Words.Word._ID, Words.Word.COLUMN_NAME_WORD,Words.Word.COLUMN_NAME_MEANING,Words.Word.COLUMN_NAME_SAMPLE},
                        null, null, null);
                if (cursor == null){
                    Toast.makeText(MainActivity.this,"没有找到记录",Toast.LENGTH_LONG).show();
                    return;
                }

                //找到记录，这里简单起见，使用Log输出

                String msg = "";
                if (cursor.moveToFirst()){
                    do{
                        msg += "ID:" + cursor.getString(cursor.getColumnIndex(Words.Word._ID)) + ",";
                        msg += "单词：" + cursor.getString(cursor.getColumnIndex(Words.Word.COLUMN_NAME_WORD))+ ",";
                        msg += "含义：" + cursor.getString(cursor.getColumnIndex(Words.Word.COLUMN_NAME_MEANING)) + ",";
                        msg += "示例" + cursor.getString(cursor.getColumnIndex(Words.Word.COLUMN_NAME_SAMPLE)) + "\n";
                    }while(cursor.moveToNext());
                }
                TextView textView1=findViewById(R.id.text);
                textView1.setText(msg);
                Log.v("myTag",msg);
            }
        });
    }
}