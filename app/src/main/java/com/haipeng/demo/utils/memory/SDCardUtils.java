package com.haipeng.demo.utils.memory;

import android.os.Environment;

/**
 * Created by wanin on 2017/9/8.
 */

public class SDCardUtils {


    // "/mnt/usbhost1"; 这个也是U盘的路径
    /**
     * 检查SD卡是否存在
     */
    public static boolean isSdCardExist() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

}
