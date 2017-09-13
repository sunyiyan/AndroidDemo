package com.haipeng.demo.utils.album;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;

import com.haipeng.demo.utils.memory.SDCardUtils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by wanin on 2017/9/8.
 */

public class AlbumUtils {

    public static String IMAGE_FILE_NAME = "tfuirejhnoe.jpg";

    public static Intent OpenAlbums() {
        Intent intent;
        String DATE = new SimpleDateFormat("yyyy_MMdd_hhmmss").format(new Date());

        if (SDCardUtils.isSdCardExist()) {

            if (Build.VERSION.SDK_INT < 19) {
                intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
            } else {
                intent = new Intent(Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            }
        } else {
            intent = null;
        }
        return intent;
    }


    private static Uri getTempUri() {
        return Uri.fromFile(getTempFile());
    }

    /**
     * 临时图片保存路径
     *
     * @return
     */
    private static File getTempFile() {
        File file = new File(Environment.getExternalStorageDirectory(), IMAGE_FILE_NAME);
        if (file.exists()) file.delete();
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }


}
