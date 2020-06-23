package com.example.test;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.widget.TextView;

public class ShowActivity extends AppCompatActivity {

    private IntentFilter intentFilter;
    private DataReceiver dataReceiver;
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
        textView = findViewById(R.id.text);
        Intent intent = getIntent();
        String data = intent.getStringExtra("data");
        textView.setText(data);
//        intentFilter = new IntentFilter();
//        intentFilter.addAction("com.example.content");
//        dataReceiver = new DataReceiver();
//        registerReceiver(dataReceiver, intentFilter);


    }
//    class DataReceiver extends BroadcastReceiver {
//
//        @Override
//        public void onReceive(Context context, Intent intent) {
//
//            String person = intent.getStringExtra("data");
//            textView.setText(person);
//        }
//    }
}
