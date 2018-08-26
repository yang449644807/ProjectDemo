package com.tengsen.ossdemo;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;

/**
 * Created by pt on 2017/12/1.
 */

public class WaterMarkUtils {

    /**
     * 缩放图片
     *
     * @param src 图片
     * @param w 压缩之后的宽
     * @param h 压缩之后的高
     * @return
     */
    public static Bitmap scaleWithWH(Bitmap src, double w, double h) {
        if (w == 0 || h == 0 || src == null) {
            return src;
        } else {
            // 记录src的宽高
            int width = src.getWidth();
            int height = src.getHeight();
            // 创建一个matrix容器
            Matrix matrix = new Matrix();
            // 计算缩放比例
            float scaleWidth = (float) (w / width);
            float scaleHeight = (float) (h / height);
            // 开始缩放
            matrix.postScale(scaleWidth, scaleHeight);
            // 创建缩放后的图片
            return Bitmap.createBitmap(src, 0, 0, width, height, matrix, true);
        }
    }

    //水印显示在右下角
    public static Bitmap waterMarkRightBottom(Bitmap image, Bitmap wmImage, int paddingLeft, int paddingTop) {
        return waterMark(image, wmImage, image.getWidth() -
                wmImage.getWidth() - paddingLeft, image.getHeight() - wmImage.getHeight() - paddingTop);
    }


    /**
     *
     * @param image 图片
     * @param wmImage 添加到图片的水印图片
     * @param paddingLeft x轴填充
     * @param paddingTop y轴填充
     * @return
     */
    public static Bitmap waterMark(Bitmap image, Bitmap wmImage, int paddingLeft, int paddingTop) {
        int width = image.getWidth();
        int height = image.getHeight();
        //创建一个bitmap
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        //将其图片作为画布
        Canvas canvas = new Canvas(bitmap);
        //在画布0,0坐标开始绘制原始图片
        canvas.drawBitmap(image, 0, 0, null);
        //在画布上绘制水印图片
        canvas.drawBitmap(wmImage, paddingLeft, paddingTop, null);
        //保存
        canvas.save(Canvas.ALL_SAVE_FLAG);
        //存储
        canvas.restore();
        return bitmap;
    }
}
