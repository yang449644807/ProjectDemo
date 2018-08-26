package com.tengsen.ossdemo;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Created by pt on 2017/11/30.
 */

public class ImageUtils {

    /**
     * 图片透明化处理
     * @param src 图片
     * @param alpha 透明程度(0-255)
     * @return
     */
    public static Bitmap alphaBitmap(Bitmap src,int alpha){
        Bitmap bitmap = Bitmap.createBitmap(src.getWidth(),src.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        paint.setAlpha(alpha);
        canvas.drawBitmap(src,0,0,paint);
        canvas.save(Canvas.ALL_SAVE_FLAG);
        canvas.restore();
        return bitmap;
    }
}
