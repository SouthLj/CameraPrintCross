package com.example.mysc60cameraapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class TextureFinishedBroadcast extends BroadcastReceiver {
    Context context;
    public TextureFinishedBroadcast(Context context){
        this.context = context;
    }

    @Override
    public void onReceive(Context context, Intent intent){
        String info = intent.getStringExtra("info_key");//接受广播带的参数
        if(info.equals("TEXTURE")){
        }
    }
}
