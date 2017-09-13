package com.haipeng.demo.utils.event;

/**
 * Created by wanin on 2017/9/8.
 */

public class SelectImageEvent {

    public static int TYPE_ALBUM = 001;
    public static int TYPE_CROP = 002;
    public static int TYPE_CAMERA = 003;


    int resutlt;

    public SelectImageEvent(int resutlt) {
        this.resutlt = resutlt;
    }

    public int getResutlt() {
        return resutlt;
    }

    public void setResutlt(int resutlt) {
        this.resutlt = resutlt;
    }
}
