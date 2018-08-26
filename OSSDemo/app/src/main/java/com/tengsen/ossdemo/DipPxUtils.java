package com.tengsen.ossdemo;

import android.content.Context;

/**
 * Created by pt on 2017/12/1.
 * dip和px转换
 */

public class DipPxUtils {
    public static int dipChangepx(Context context, float dipValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }


    public static int pxChangedip(Context context, float pxValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }
}
