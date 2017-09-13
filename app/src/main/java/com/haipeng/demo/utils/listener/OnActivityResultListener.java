package com.haipeng.demo.utils.listener;

import android.content.Intent;

/**
 * Created by Administrator on 2017/9/13.
 */

public interface OnActivityResultListener {
      void activityResultTypeAlbum(Intent data);
      void activityResultTypeCamera(Intent data);
      void activityResultTypeCrop(Intent data);

}
