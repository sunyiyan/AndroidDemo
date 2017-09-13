package com.haipeng.demo.utils.image;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;

import java.io.File;

/**
 * Created by wanin on 2017/9/8.
 */

public class ImageCropUtils {

    //7.0  ContentUri
    public static final String FILE_CONTENT_FILEPROVIDER = "com.haipeng.demo.fileprovider";
    public static final String APP_NAME = "bao";
    //相机拍照默认存储路径
    public static final String PICTURE_DIR = Environment.getExternalStorageDirectory() + "/haipeng/pictures/";
    public static String DATE = "";
    //照片图片名
    private String photo_image;
    //截图图片名
    private static String crop_image;

    /**
     * 调用系统剪裁功能
     */
    public static Intent cropPicture(Activity activity, String path) {
        File file = new File(path);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        Uri imageUri;
        Uri outputUri;
        crop_image = createImagePath(APP_NAME + "_crop_" + DATE);

        Intent intent = new Intent("com.android.camera.action.CROP");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            //添加这一句表示对目标应用临时授权该Uri所代表的文件
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            //通过FileProvider创建一个content类型的Uri
            imageUri = FileProvider.getUriForFile(activity, FILE_CONTENT_FILEPROVIDER, file);
            outputUri = Uri.fromFile(new File(crop_image));
            //TODO:outputUri不需要ContentUri,否则失败
            //outputUri = FileProvider.getUriForFile(activity, "com.solux.furniture.fileprovider", new File(crop_image));
        } else {
            imageUri = Uri.fromFile(file);
            outputUri = Uri.fromFile(new File(crop_image));
        }
        intent.setDataAndType(imageUri, "image/*");
        intent.putExtra("crop", "true");
        //设置宽高比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        //设置裁剪图片宽高
        intent.putExtra("outputX", 300);
        intent.putExtra("outputY", 300);
        intent.putExtra("scale", true);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, outputUri);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true);

        return intent;
    }


    /**
     * 创建图片的存储路径
     */
    public static String createImagePath(String imageName)
    {
        String dir = PICTURE_DIR;
        File destDir = new File(dir);
        if (!destDir.exists())
        {
            destDir.mkdirs();
        }
        File file = null;
        if (!TextUtils.isEmpty(imageName))
        {
            file = new File(dir, imageName + ".jpeg");
        }
        return file.getAbsolutePath();
    }
}
