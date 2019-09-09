package com.example.mysc60cameraapp;

import android.app.Application;
import android.os.Handler;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;


public class ApplicationInfo extends Application {
    public static DrawView view;// = new DrawView(this);
    public static FrameLayout con;//= findViewById(R.id.container);

    public static RelativeLayout laout;
    public static  FrameLayout frameLayout;
    public static Handler mHandler;
    //con.addView(view);
    public static int screenCenterX = 0;
    public static int screenCenterY = 0;

    public static int lasarPositionX;
    public static int lasarPositionY;
    public FrameLayout getView(){
        return con;
    }

    public void setView(FrameLayout view){
        this.con = view;
    }

    public void setDraw(DrawView view){
        this.view = view;
    }

    public DrawView getDraw(){
        return view;
    }

    public void setCoordinate(int x, int y){
        this.screenCenterX = x;
        this.screenCenterY = y;
    }

    public void setHandler(Handler handler){
        mHandler = handler;
    }

    public Handler getHandler(){
        if(mHandler != null)
            return mHandler;
        return mHandler;
    }
}
