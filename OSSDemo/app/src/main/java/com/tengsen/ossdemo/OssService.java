package com.tengsen.ossdemo;

import android.util.Log;

import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.callback.OSSProgressCallback;
import com.alibaba.sdk.android.oss.internal.OSSAsyncTask;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;

import java.io.File;

/**支持普通上传(同步、异步)
 * Created by pt on 2017/11/30.
 */

public class OssService {

    private OSS oss;
    private String bucket;

    public OssService(OSS oss,String bucket){
        this.oss = oss;
        this.bucket = bucket;
    }


    /**
     * 异步上传（filePath）
     * @param object
     * @param localFile
     */
    public void asyncPut(String object,String localFile){

        if (object.equals("")) {
            Log.e("AsyncPut", "ObjectNull");
            return;
        }

        File file = new File(localFile);
        if (!file.exists()) {
            Log.e("AsyncPut", "FileNotExist");
            return;
        }

        PutObjectRequest put = new PutObjectRequest(bucket, object, localFile);

        //上传时的回调
        put.setProgressCallback(new OSSProgressCallback<PutObjectRequest>() {
            @Override
            public void onProgress(PutObjectRequest request, long currentSize, long totalSize) {
                Log.d("PutObject", "currentSize: " + currentSize + " totalSize: " + totalSize);
            }
        });

        //异步上传请求
        OSSAsyncTask task = oss.asyncPutObject(put, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
            @Override
            public void onSuccess(PutObjectRequest request, PutObjectResult result) {
                Log.e("ossRequest", "success");
            }

            @Override
            public void onFailure(PutObjectRequest request, ClientException clientException, ServiceException serviceException) {

                if (clientException != null) {
                    clientException.printStackTrace();
                    Log.e("ClientException", "ClientException");
                }
                if (serviceException != null) {
                    serviceException.printStackTrace();
                    Log.e("ServiceException", "ServiceException");
                }
            }
        });
    }

    /**
     * 异步上传(byte[])
     * @param object
     * @param localData
     */
    public OSSAsyncTask asyncPut(String object,byte[] localData){

        if (object.equals("")) {
            Log.e("AsyncPut", "ObjectNull");
            return null;
        }

        if(localData == null){
            Log.e("AsyncPut", "dataNo");
        }

        PutObjectRequest put = new PutObjectRequest(bucket, object, localData);

        //上传时的回调
        put.setProgressCallback(new OSSProgressCallback<PutObjectRequest>() {
            @Override
            public void onProgress(PutObjectRequest request, long currentSize, long totalSize) {
                Log.d("PutObject", "currentSize: " + currentSize + " totalSize: " + totalSize);
            }
        });

        //异步上传请求
        OSSAsyncTask task = oss.asyncPutObject(put, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
            @Override
            public void onSuccess(PutObjectRequest request, PutObjectResult result) {
                Log.e("ossRequest", "success");
                Log.e("result",result.toString());
            }

            @Override
            public void onFailure(PutObjectRequest request, ClientException clientException, ServiceException serviceException) {

                if (clientException != null) {
                    clientException.printStackTrace();
                    Log.e("ClientException", "ClientException");
                }
                if (serviceException != null) {
                    serviceException.printStackTrace();
                    Log.e("ServiceException", "ServiceException");
                }
            }
        });
        return task;
    }
}
