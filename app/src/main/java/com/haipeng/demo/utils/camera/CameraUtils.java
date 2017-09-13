package com.haipeng.demo.utils.camera;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.widget.Toast;

import com.haipeng.demo.MainActivity;
import com.haipeng.demo.utils.memory.SDCardUtils;
import com.haipeng.demo.utils.image.ImageCropUtils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Created by wanin on 2017/9/8.
 */

public class CameraUtils {

    //7.0  ContentUri
    public static final String APP_NAME = "bao";
    //相机拍照默认存储路径
    public static final String PICTURE_DIR = Environment.getExternalStorageDirectory() + "/haipeng/pictures/";
    public static String DATE = "";
    //照片图片名
    private static String photo_image;
    //截图图片名
    private static String crop_image;


    /**
     * 打开系统相机
     */
    public static Intent takePhoto(Activity activity) {
        DATE = new SimpleDateFormat("yyyy_MMdd_hhmmss").format(new Date());
        Intent intent = null;

        if (SDCardUtils.isSdCardExist()) {
            photo_image = ImageCropUtils.createImagePath(APP_NAME + DATE);
            File file = new File(photo_image);
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            intent = new Intent();
            intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
            //Android7.0以上URI
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                //添加这一句表示对目标应用临时授权该Uri所代表的文件
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                //通过FileProvider创建一个content类型的Uri
                MainActivity.uri = setFilePath(activity);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, MainActivity.uri);
            } else {
                MainActivity.uri = Uri.fromFile(file);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, MainActivity.uri);
            }

        } else {
            intent = null;
        }
        return intent;
    }

    /**
     * 指定图片存储路径
     *
     * @return
     */
    private static Uri setFilePath(Activity context) {

        String parentPath = Environment.getExternalStorageDirectory()
                .getAbsoluteFile() + "/takePic/";
        String outPath = parentPath + System.currentTimeMillis() + ".jpg";
        Toast.makeText(context, "" + outPath, Toast.LENGTH_LONG).show();

        File parentFile = new File(parentPath);
        if (!parentFile.exists()) {
            parentFile.mkdirs();
        }

        File outFile = new File(outPath);
        try {
            outFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        MainActivity.imagePath = outFile.getAbsolutePath();   // 保存图片的地址</span>

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(context, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            ActivityCompat.requestPermissions(context, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        }

        return FileProvider.getUriForFile(context, ImageCropUtils.FILE_CONTENT_FILEPROVIDER, outFile)
                != null ? FileProvider.getUriForFile(context, ImageCropUtils.FILE_CONTENT_FILEPROVIDER, outFile) : null;
//        return Uri.fromFile(outFile) != null ? Uri.fromFile(outFile) : null;
    }

    /**
     * 自动获取相机权限
     */
    private void autoObtainCameraPermission(Activity activity) {

//        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
//                || ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
//
//            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.CAMERA)) {
//                Toast.makeText(activity, "您已经拒绝过一次",Toast.LENGTH_LONG).show();
//            }
//            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE}, CAMERA_PERMISSIONS_REQUEST_CODE);
//        } else {//有权限直接调用系统相机拍照
//            if (hasSdcard()) {
//                imageUri = Uri.fromFile(fileUri);
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
//                    imageUri = FileProvider.getUriForFile(activity, "com.zz.fileprovider", fileUri);//通过FileProvider创建一个content类型的Uri
//                PhotoUtils.takePicture(this, imageUri, CODE_CAMERA_REQUEST);
//            } else {
//                ToastUtils.showShort(this, "设备没有SD卡！");
//            }
//        }
    }

}
