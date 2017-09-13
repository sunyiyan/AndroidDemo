package com.haipeng.demo.utils.intent;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.Toast;

import com.haipeng.demo.utils.event.SelectImageEvent;
import com.haipeng.demo.utils.image.ImagePathFromUriUtils;
import com.haipeng.demo.utils.listener.OnActivityResultListener;

/**
 * Created by Administrator on 2017/9/13.
 */

public class ActivityResultUtils {

    OnActivityResultListener listener;

    public void setOnActivityResultListener(OnActivityResultListener listener) {
        this.listener = listener;
    }

    public void onActivityResult(Context context,int requestCode, Intent data) {
        if (requestCode == SelectImageEvent.TYPE_ALBUM) {

            if (data == null) {
                Toast.makeText(context, "未获取到图片", Toast.LENGTH_SHORT).show();
            } else {
                listener.activityResultTypeAlbum(data);
//                startActivityForResult(ImageCropUtils.cropPicture(this, PhotoClipperUtils.getPath(this, data.getData())), SelectImageEvent.TYPE_CROP);
            }
        } else if (requestCode == SelectImageEvent.TYPE_CROP) {
            if (data == null) {
                Toast.makeText(context, "未获取到图片", Toast.LENGTH_SHORT).show();
            } else {
                listener.activityResultTypeCrop(data);
            }
        } else if (requestCode == SelectImageEvent.TYPE_CAMERA) {
            listener.activityResultTypeCamera(data);

        }
    }
}
