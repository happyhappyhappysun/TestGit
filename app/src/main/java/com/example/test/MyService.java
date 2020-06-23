package com.example.test;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class MyService extends Service {

    public MyService() {
    }

    /**
     * 动态注册广播接收器
     */
    private IntentFilter intentFilter;
    private MyReceiver myReceiver;

    /**
     * 在你的这个类里com.stv.main.CheckUpdateActivity注册了广播，
     * 必须在页面销毁时进行解注册，需要调用在onDesdroy()方法里调用unregisterReceiver()。
     */
    @Override
    public void onDestroy() {
        unregisterReceiver(myReceiver);
        super.onDestroy();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        intentFilter = new IntentFilter();
        intentFilter.addAction("com.example.callMethod");
        myReceiver = new MyReceiver();
        registerReceiver(myReceiver, intentFilter);
    }

    private MyBinder myBinder = new MyBinder();

    class MyBinder extends Binder {
        public void method() {
            Log.d("text", "binder通信成功~~~");
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return myBinder;
    }

    /*
        广播接收器：记得要注册。（这里采用动态注册的方式）
     */
    private ArrayList<Person> list;
    class MyReceiver extends BroadcastReceiver {


        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d("text", "接收到广播啦~~~~");
            try {
                callMethod();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        /**
         * 解析xml文件并且显示
         */
        private void callMethod() throws IOException {
            Log.d("text", "方法调用成功啦~~~~");
            //获取文件资源建立输入流对象
            //InputStream is = getAssets().open("person.xml");
            InputStream is = getResources().openRawResource(R.raw.person);
            //①创建XML解析处理器
            SaxHelper ss = new SaxHelper();
            //②得到SAX解析工厂
            SAXParserFactory factory = SAXParserFactory.newInstance();
            //③创建SAX解析器
            SAXParser parser = null;
            try {
                parser = factory.newSAXParser();
                //④将xml解析处理器分配给解析器,对文档进行解析,将事件发送给处理器
                parser.parse(is, ss);
                is.close();
                list  = ss.getPersons();
                //接下来就是显示问题，想想该怎么显示（先用textview显示）
                StringBuilder sb = new StringBuilder();
                for (Person person : list) {
                    String result = "id:"+person.getId()+" name:"+person.getName()+" age:"+person.getAge();
                    sb.append(result);
                    sb.append("\r\n");
                }
                //发送广播
                Intent broadIntent = new Intent();
                broadIntent.setAction("com.example.content");
                broadIntent.putExtra("data",sb.toString());
                sendBroadcast(broadIntent);

                //sendContentBroadcast(sb.toString());

                //解析出来的数据进行保存，根据provider内容提供者保存数据
                Uri uri = Uri.parse("content://com.example.test.provider/person");
                for(Person person:list){
                    ContentValues values = new ContentValues();
                    values.put("id",person.getId());
                    values.put("name",person.getName());
                    values.put("age",person.getAge());
                    getContentResolver().insert(uri,values);
                    Log.d("test",person.getName().toString());
                }
            } catch (ParserConfigurationException e) {
                e.printStackTrace();
            } catch (SAXException e) {
                e.printStackTrace();
            }


        }

        /**
         * 发送广播
         *
         * @param data
         */
//        protected void sendContentBroadcast(String data) {
//            // TODO Auto-generated method stub
//            Intent intent = new Intent();
//            intent.setAction("com.example.content");
//            intent.putExtra("person", data);
//            sendBroadcast(intent);
//        }
    }
}