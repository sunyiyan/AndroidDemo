package com.haipeng.demo.utils.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.view.KeyboardShortcutGroup;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

import com.haipeng.demo.R;
import com.haipeng.demo.utils.event.SelectImageEvent;

import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by wanin on 2017/9/7.
 */

public class SelectImageDialog extends Dialog implements View.OnClickListener {

    public Button btnAlbum, btnCarmera;

    public SelectImageDialog(@NonNull Context context) {
        super(context);
    }

    public SelectImageDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, R.style.dialog_normal_theme);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_select);
        btnAlbum = (Button) findViewById(R.id.btn_album);
        btnCarmera = (Button) findViewById(R.id.btn_camera);

        btnAlbum.setOnClickListener(this);
        btnCarmera.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_album:
                EventBus.getDefault().post(new SelectImageEvent(SelectImageEvent.TYPE_ALBUM));
                break;
            case R.id.btn_camera:
                EventBus.getDefault().post(new SelectImageEvent(SelectImageEvent.TYPE_CAMERA));
                break;
        }
    }
}
