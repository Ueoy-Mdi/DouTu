package cc.douquan.com.doutu.utils;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import cc.douquan.com.doutu.view.activity.SearchActivity;

import static com.umeng.socialize.utils.DeviceConfig.context;

/**
 * activity跳转管理类
 */

public class UiHelper {
    public static void goSearchActivity(Context context, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(context, SearchActivity.class);
        intent.putExtra("bundle", bundle);
        context.startActivity(intent);
    }
}
