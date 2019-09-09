package com.example.mysc60cameraapp;

/*
 * Copyright 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.util.Log;
import android.view.TextureView;
import android.widget.RelativeLayout;

/**
 * A {@link TextureView} that can be adjusted to a specified aspect ratio.
 */
public class AutoFitTextureView extends TextureView {

    private int mRatioWidth = 0;
    private int mRatioHeight = 0;
    private RelativeLayout relativeLayout;
    //public static Context context;

    public AutoFitTextureView(Context context) {
        this(context, null);
    }

    public AutoFitTextureView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AutoFitTextureView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    /**
     * Sets the aspect ratio for this view. The size of the view will be measured based on the ratio
     * calculated from the parameters. Note that the actual sizes of parameters don't matter, that
     * is, calling setAspectRatio(2, 3) and setAspectRatio(4, 6) make the same result.
     *
     * @param width  Relative horizontal size
     * @param height Relative vertical size
     */
    public void setAspectRatio(int width, int height) {
        if (width < 0 || height < 0) {
            throw new IllegalArgumentException("Size cannot be negative.");
        }
        mRatioWidth = width;
        mRatioHeight = height;
        requestLayout();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);

        relativeLayout = findViewById(R.id.cameraLayout);

        if (0 == mRatioWidth || 0 == mRatioHeight) {
            setMeasuredDimension(width, height);
        } else {
            if (width < height * mRatioWidth / mRatioHeight) {
                setMeasuredDimension(width, width * mRatioHeight / mRatioWidth);
                Log.d("camera","The final carema1 is "+width+" "+(width * mRatioHeight / mRatioWidth)
                +" width="+width
                +" height="+height
                +" mRatioWidth="+mRatioWidth
                +" mRatioHeight"+mRatioHeight);
                ApplicationInfo.screenCenterX = width;
                ApplicationInfo.screenCenterY = (width * mRatioHeight / mRatioWidth);
                ApplicationInfo.mHandler.sendEmptyMessage(1);
            } else {
                setMeasuredDimension(height * mRatioWidth / mRatioHeight, height);
               // Log.e("camera","The final carema2 is "+(height * mRatioWidth / mRatioHeight)+" "+height);
                Log.d("liujian","The final carema1 is "+(height * mRatioWidth / mRatioHeight)+" "+height
                        +" width="+width
                        +" height="+height
                        +" mRatioWidth="+mRatioWidth
                        +" mRatioHeight"+mRatioHeight);
                ApplicationInfo.screenCenterX = (height * mRatioWidth / mRatioHeight);
                ApplicationInfo.screenCenterY = height;
                ApplicationInfo.mHandler.sendEmptyMessage(1);
               // relativeLayout.addView(ApplicationInfo.view,ApplicationInfo.screenCenterX,ApplicationInfo.screenCenterY/2+60);
            }
        }


       // ApplicationInfo app =
        /*
        Intent intent =new Intent("com.example.mysc60cameraapp.TEXTURE_BROADCAST");
        intent.putExtra("info_key", "TEXTURE");//发送带参数的广播；
        context.sendBroadcast(intent);
*/
       // Log.e("liujian", "send over");
        //ApplicationInfo.con.addView(ApplicationInfo.view,ApplicationInfo.screenCenterX,ApplicationInfo.screenCenterY/2+60);
    }

}
