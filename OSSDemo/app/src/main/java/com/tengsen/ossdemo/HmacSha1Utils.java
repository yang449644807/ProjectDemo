package com.tengsen.ossdemo;

import android.util.Base64;
import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by pt on 2017/11/30.
 */

public class HmacSha1Utils {

    private static final String LOG_TAG = "HMACSha1";

    public static String hmacSha1(String data,String key) {
        try {
            Mac mac = Mac.getInstance("HmacSHA1");
            SecretKeySpec secret = new SecretKeySpec(
                    key.getBytes("UTF-8"), mac.getAlgorithm());
            mac.init(secret);
            return Base64.encodeToString(mac.doFinal(data.getBytes()), Base64.NO_WRAP);
        } catch (NoSuchAlgorithmException e) {
            Log.e(LOG_TAG, "Hash algorithm SHA-1 is not supported", e);
        } catch (UnsupportedEncodingException e) {
            Log.e(LOG_TAG, "Encoding UTF-8 is not supported", e);
        } catch (InvalidKeyException e) {
            Log.e(LOG_TAG, "Invalid key", e);
        }
        return "";
    }
}
