package com.tengsen.ossdemo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.alibaba.sdk.android.oss.ClientConfiguration;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSCustomSignerCredentialProvider;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private String AccessId = "5Ml1pqrg9s9Z8lbI";
    private String AccessKey = "EeAjwQfyOiejZp8NKtTdcuepC3RWTR";
    private String EndPoint = "oss-cn-shanghai.aliyuncs.com";
    private String Bucket = "dtbnews";

    ImageView image;
    OSS oss;
    OssService ossService;

    //初始化一个OssService用来上传下载
    private void initOSS(String endpoint, String bucket) {
        //如果希望直接使用accessKey来访问的时候，可以直接使用OSSPlainTextAKSKCredentialProvider来鉴权。
        //OSSCredentialProvider credentialProvider = new OSSPlainTextAKSKCredentialProvider(accessKeyId, accessKeySecret);

        //自签名模式
        OSSCredentialProvider credentialProvider = new OSSCustomSignerCredentialProvider() {
            @Override
            public String signContent(String s) {
                return "OSS " + AccessId + ":" + HmacSha1Utils.hmacSha1(s, AccessKey);
            }
        };

        ClientConfiguration conf = new ClientConfiguration();
        conf.setConnectionTimeout(15 * 1000); // 连接超时，默认15秒
        conf.setSocketTimeout(15 * 1000); // socket超时，默认15秒
        conf.setMaxConcurrentRequest(5); // 最大并发请求书，默认5个
        conf.setMaxErrorRetry(2); // 失败后最大重试次数，默认2次

        oss = new OSSClient(getApplicationContext(), endpoint, credentialProvider, conf);
        ossService = new OssService(oss, bucket);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //初始化
        initOSS(EndPoint, Bucket);
        image = (ImageView) findViewById(R.id.imageView);
        //sd卡读写权限手动添加
        SdCardReadWiterPermissions.verifyStoragePermissions(this);
    }

    //上传
    public void onUpload(View v) {
        String path1 = "/storage/emulated/0/fj.jpg";
        String path2 = "/storage/emulated/0/xxts.png";
//        saveBitmap();   //在指定文件提供图片
        putImage("demo/demo2333.png", path2);
        //提供url
//        getUrl();
        //图片透明化0-255
        Bitmap bitmap = BitmapFactory.decodeFile(path2);
//        bitmap = ImageUtils.alphaBitmap(bitmap, 20);

        image.setImageBitmap(bitmap);

        //水印图片实现
//        waterMarkImage(path1,path2,300f, 200f, 100f, 100f);
    }

    /**
     * @param name      自己定义在oss上面是名字,例如xxx.jpg,可以通过 包名/xxx.jpg 创建文件夹
     * @param imagePath 要上传的图片或者说文件的绝对路径
     */
    private void putImage(String name, String imagePath) {
        File file = new File(imagePath);
        //将文件同过路径put到oss上
        ossService.asyncPut(name, imagePath);

        //将图片显示到ImageView中
        if (file.exists()) {
            Bitmap bm = BitmapFactory.decodeFile(imagePath);
            image.setImageBitmap(bm);
        }
    }

    //添加图片到指定路径Demo
    private void saveBitmap() {
        Log.e("TAG", "保存图片");

        File file1 = new File(Environment.getExternalStorageDirectory(), "fj.jpg");
        File file2 = new File(Environment.getExternalStorageDirectory(), "xxts.png");
        try {
            FileOutputStream out = new FileOutputStream(file1);
            Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.fj);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();

            out = new FileOutputStream(file2);
            bmp = BitmapFactory.decodeResource(getResources(), R.drawable.xxts);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void getUrl() {
        String url = oss.presignPublicObjectURL(Bucket, "fj.jpg");
        Log.e("urL", url);
    }

    /**
     *
     * @param path1 图片
     * @param path2 添加到图片的水印图片
     * @param w1 图片的宽
     * @param h1 图片的高
     * @param w2 添加到图片的水印图片的宽
     * @param h2 添加到图片的水印图片的高
     */
    private Bitmap waterMarkImage(String path1, String path2, float w1, float h1, float w2, float h2) {

        //压缩显示图片
        Bitmap bm1 = BitmapFactory.decodeFile(path1);
        bm1 = WaterMarkUtils.scaleWithWH(bm1, DipPxUtils.dipChangepx(this, w1), DipPxUtils.dipChangepx(this, h1));

        //压缩水印
        Bitmap bm2 = BitmapFactory.decodeFile(path2);
        bm2 = WaterMarkUtils.scaleWithWH(bm2, DipPxUtils.dipChangepx(this, w2), DipPxUtils.dipChangepx(this, h2));

        //水印图片
        Bitmap bitmap = WaterMarkUtils.waterMarkRightBottom(bm1, bm2, 0, 0);
        return bitmap;
    }


}
