package com.example.test;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class ShowPageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_page);
        TextView textView = findViewById(R.id.text);
        Uri uri = Uri.parse("content://com.example.test.provider/person");
        Cursor cursor = getContentResolver().query(uri,null,null,null,null);
        StringBuilder sb = new StringBuilder();
        if(cursor!=null){
            while(cursor.moveToNext()){
                int id = cursor.getInt(cursor.getColumnIndex("id"));
                String name = cursor.getString(cursor.getColumnIndex("name"));
                String age = cursor.getString(cursor.getColumnIndex("age"));
                String result = "id:"+id+" name:"+name+" age:"+age;
                Log.i("test", "personid="+ id + ",name="+ name+ ",age="+ age);
                sb.append(result);
                sb.append("\r\n");
            }
            textView.setText(sb.toString());
            cursor.close();
        }
    }
}
