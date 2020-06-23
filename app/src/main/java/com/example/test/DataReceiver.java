package com.example.test;
/**
 * 如何跳转从service到activity显示~~~
 */

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class DataReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent dataIntent = new Intent(context,ShowActivity.class);
        dataIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        dataIntent.putExtra("data",intent.getStringExtra("data"));
        context.startActivity(dataIntent);
    }
}
