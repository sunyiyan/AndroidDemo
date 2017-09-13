package com.haipeng.demo.utils.permission;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import com.haipeng.demo.utils.listener.OnRequestPermissionListener;

/**
 * Created by Administrator on 2017/9/12.
 */

public class AutoObtainPermissionUtils {

    public static final int REQUEST_WRITE_EXTERNAL = 0x01;
    public static final int REQUEST_READ_EXTERNAL = 0x02;
    public static final int REQUEST_CAMERA_EXTERNAL = 0x03;

    public static int comparessType = 0023;


    public boolean autoObtainWriteStoragePermission(Activity context) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(context, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_READ_EXTERNAL);
                return false;
            } else {
                return true;
            }
        } else {
            return true;
        }
    }

    public boolean autoObtainStoragePermission(Activity context) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(context, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_READ_EXTERNAL);
                return false;
            } else {
                return true;
            }
        } else {
            return true;
        }
    }

    public boolean autoObtainCameraPermission(Activity context) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(context, new String[]{Manifest.permission.CAMERA}, REQUEST_READ_EXTERNAL);
                return false;
            } else {
                return true;
            }
        } else {
            return true;
        }
    }

    public void requestPermissionResult(Activity activity, int requestCode, int[] grantResults) {
        switch (requestCode) {
            case AutoObtainPermissionUtils.REQUEST_READ_EXTERNAL:

                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    listener.requestReadExternal();
                } else {
                    Toast.makeText(activity, "", Toast.LENGTH_LONG).show();
                }
                break;
            case AutoObtainPermissionUtils.REQUEST_CAMERA_EXTERNAL:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    listener.requestCameraExternal();
                } else {
                    Toast.makeText(activity, "", Toast.LENGTH_LONG).show();
                }
                break;
            case AutoObtainPermissionUtils.REQUEST_WRITE_EXTERNAL:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    listener.requestWriteExternal();
                } else {

                }
                break;
        }
    }

    OnRequestPermissionListener listener;

    public void setOnClickListener(OnRequestPermissionListener listener) {
        this.listener = listener;
    }


}
