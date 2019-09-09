package com.example.mysc60cameraapp;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import java.io.IOException;


public class MainActivity extends AppCompatActivity implements Runnable{

    private IntentFilter intentFilter;
    private FrameLayout frameLayout;
    private DrawView view;
    private int MeasureFlag = 0;
    private int receiveNum = 0;

    private long LastCheckTime;
    private long tempTime = 0;
    private TextureFinishedBroadcast myBroadcastReceiver;

    public Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    receiveNum++;
                    if(receiveNum == 1){
                        Log.d("MainActivity", "x:"+ApplicationInfo.screenCenterX+"  y:"+ApplicationInfo.screenCenterY );

                        ApplicationInfo.lasarPositionX = ApplicationInfo.screenCenterX/2;
                        ApplicationInfo.lasarPositionY = ApplicationInfo.screenCenterY/2;
                        ApplicationInfo.frameLayout.addView(view,ApplicationInfo.screenCenterX,ApplicationInfo.screenCenterY);

                        Intent intent =new Intent("com.example.mysc60cameraapp.TEXTURE_BROADCAST");
                        intent.putExtra("info_key", "TEXTURE");//发送带参数的广播；
                        sendBroadcast(intent);
                    }
                case 2:
                    view.invalidate();
                break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        if (null == savedInstanceState) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, Camera2BasicFragment.newInstance())
                    .commit();
        }

       // AutoFitTextureView.context = this;
        // 注册广播
        registerBroadcast();

        frameLayout = findViewById(R.id.container);
        view = new DrawView(this);
        view.setClickable(false);

        ApplicationInfo applicationInfo = (ApplicationInfo)this.getApplication();
        applicationInfo.setDraw(view);

        applicationInfo.setHandler(mHandler);
      // AutoFitTextureView mTextureView = (AutoFitTextureView) view.findViewById(R.id.texture);
/*
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int screenWidth = dm.widthPixels;
        int screenHeight = dm.heightPixels;
        int px = screenWidth / 2;
        int py = screenHeight / 2;
       // int px = mTextureView.getWidth()/2;
        //int py = mTextureView.getHeight()/2;
        applicationInfo.setCoordinate(px, py);*/

       Thread mThread = new Thread(MainActivity.this);
        mThread.start(); // 开线程测试
    }


    @Override
    public void run() {

        for(int i=0; i<10; i++){
            ApplicationInfo.lasarPositionX += 10;
            ApplicationInfo.lasarPositionY += 10;
            mHandler.sendEmptyMessage(2);
            try{
                Thread.sleep(500);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
        ApplicationInfo.lasarPositionX = ApplicationInfo.screenCenterX/2;
        ApplicationInfo.lasarPositionY = ApplicationInfo.screenCenterY/2;
        mHandler.sendEmptyMessage(2);
    }
    private void registerBroadcast(){
        intentFilter = new IntentFilter();
        intentFilter.addAction("com.example.mysc60cameraapp.TEXTURE_BROADCAST");
        myBroadcastReceiver = new TextureFinishedBroadcast(this);
        registerReceiver(myBroadcastReceiver, intentFilter);
       // sendBroadcast();
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        unregisterReceiver(myBroadcastReceiver);
    }
    }

