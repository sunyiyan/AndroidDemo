package com.haipeng.demo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.haipeng.demo.utils.permission.AutoObtainPermissionUtils;
import com.haipeng.demo.utils.album.AlbumUtils;
import com.haipeng.demo.utils.camera.CameraUtils;
import com.haipeng.demo.utils.event.SelectImageEvent;
import com.haipeng.demo.utils.image.BitmapUtils;
import com.haipeng.demo.utils.image.ImagePathFromUriUtils;
import com.haipeng.demo.utils.intent.ActivityResultUtils;
import com.haipeng.demo.utils.listener.OnActivityResultListener;
import com.haipeng.demo.utils.listener.OnRequestPermissionListener;
import com.haipeng.demo.utils.widget.dialog.SelectImageDialog;

import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;

public class MainActivity extends AppCompatActivity
        implements View.OnClickListener
        , OnRequestPermissionListener
        , OnActivityResultListener {

    private ImageView iv;
    private SelectImageDialog imageDialog;
    private Bitmap mBitmap;
    public static Uri uri;

    private int type = 000;
    private String toPath = "/sdcard/test.jpg";
    public static String imagePath;
    private String mImagePath;

    AutoObtainPermissionUtils obtainPermissionUtils;
    ActivityResultUtils activityResultUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        iv = (ImageView) findViewById(R.id.iv);
        imageDialog = new SelectImageDialog(this);
        iv.setOnClickListener(this);
        EventBus.getDefault().register(this);
        obtainPermissionUtils = new AutoObtainPermissionUtils();
        activityResultUtils = new ActivityResultUtils();

        obtainPermissionUtils.setOnClickListener(this);
        activityResultUtils.setOnActivityResultListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Subscribe(threadMode = ThreadMode.MainThread)
    public void fromAlbum(SelectImageEvent event) {
        type = event.getResutlt();
        if (event.getResutlt() == SelectImageEvent.TYPE_ALBUM) {
            if (obtainPermissionUtils.autoObtainStoragePermission(this)) {
                startActivityForResult(AlbumUtils.OpenAlbums(), SelectImageEvent.TYPE_ALBUM);
            }
        } else if (event.getResutlt() == SelectImageEvent.TYPE_CAMERA) {
            if (obtainPermissionUtils.autoObtainCameraPermission(this)) {
                startActivityForResult(CameraUtils.takePhoto(this), SelectImageEvent.TYPE_CAMERA);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
           activityResultUtils.onActivityResult(this,requestCode,data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        obtainPermissionUtils.requestPermissionResult(this, requestCode, grantResults);
    }

    @Override
    public void requestWriteExternal() {
        if (type == AutoObtainPermissionUtils.comparessType) {
            BitmapUtils.comparessImagePre(mImagePath, toPath, 200);
        }

    }

    @Override
    public void requestReadExternal() {
        if (type == SelectImageEvent.TYPE_ALBUM) {
            startActivityForResult(AlbumUtils.OpenAlbums(), SelectImageEvent.TYPE_ALBUM);
        }
    }

    @Override
    public void requestCameraExternal() {
        if (type == SelectImageEvent.TYPE_CAMERA) {
            startActivityForResult(CameraUtils.takePhoto(this), SelectImageEvent.TYPE_CAMERA);
        }
    }

    @Override
    public void activityResultTypeAlbum(Intent data) {
        mImagePath = ImagePathFromUriUtils.getPath(this, data.getData());
        setBitmap(data.getData());
    }

    @Override
    public void activityResultTypeCamera(Intent data) {
        mImagePath = imagePath;
        setBitmap(uri);
    }

    @Override
    public void activityResultTypeCrop(Intent data) {
        imageDialog.dismiss();
        if (data.getAction() != null) {
            mImagePath = data.getAction();
        } else {
            mImagePath = ImagePathFromUriUtils.getPath(this, uri);
        }
        Toast.makeText(this, mImagePath, Toast.LENGTH_LONG).show();
        Bitmap bitmap = BitmapFactory.decodeFile(mImagePath);
        iv.setImageBitmap(bitmap);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv:
                imageDialog.show();
                break;
        }
    }

    public void setBitmap(Uri uri) {
        imageDialog.dismiss();
        if (uri == null) return;
        mBitmap = ImagePathFromUriUtils.getBitmapFromUri(uri, this);
        mBitmap = BitmapUtils.compressByQuality(mBitmap, 200);
        iv.setImageBitmap(mBitmap);

        type = AutoObtainPermissionUtils.comparessType;
        if (obtainPermissionUtils.autoObtainWriteStoragePermission(this))
            BitmapUtils.comparessImagePre(mImagePath, toPath, 200);
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        if (null != mBitmap) {
            mBitmap.recycle();
            mBitmap = null;
        }
        super.onDestroy();
    }
}
