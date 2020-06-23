package com.example.test;
/**
 * 问题：
 * service启动之后什么时候结束~unbind()方法调用在哪
 * 解析后的数据要传递出去显示。(用一下回调接口试试)
 * Myservice解析xml成功之后怎么显示，还有就是解析xml怎么解析复杂的。
 * 用listview怎么显示复杂的信息，分层显示（不同类别）
 */

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button_1 = findViewById(R.id.button_1);
        Button button_2 = findViewById(R.id.button_2);
        Button button_3 = findViewById(R.id.button_3);
        Button button_4 = findViewById(R.id.button_4);
        button_1.setOnClickListener(this);
        button_2.setOnClickListener(this);
        button_3.setOnClickListener(this);
        button_4.setOnClickListener(this);


        IntentFilter filter = new IntentFilter();
        filter.addAction("wangyibo");
        registerReceiver(mReceiver, filter);
        Intent intent = new Intent();
        intent.setAction("wangyibo");
        sendBroadcast(intent);
        Log.d("guoxia","广播发送成功了~~~~");
    }
    private MyService.MyBinder myBinder;
    private ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            myBinder = (MyService.MyBinder) service;
            myBinder.method();

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_1:
                Intent bindService = new Intent(MainActivity.this,MyService.class);
                bindService(bindService,conn,BIND_AUTO_CREATE);
                break;
            case R.id.button_2:
                Intent broadIntent = new Intent();
                broadIntent.setAction("com.example.callMethod");
                sendBroadcast(broadIntent);
                break;
            case R.id.button_3:
                Intent intent = new Intent(MainActivity.this,ShowPageActivity.class);
                startActivity(intent);
                break;
            case R.id.button_4:
                unbindService(conn);
                Log.d("text","停止服务~~~~");
                break;
            default:
                break;
        }

    }
    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d("guoxia","接收到消息了~~~~~~~");
        }
    };
}
