package cc.douquan.com.doutu;

import android.app.Application;
import android.content.Context;
import android.os.StrictMode;

import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;
import com.squareup.leakcanary.LeakCanary;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;


import cc.douquan.com.doutu.utils.CrashHandler;
import cc.douquan.com.doutu.utils.ToastUtils;

import static android.os.Build.VERSION.SDK_INT;
import static android.os.Build.VERSION_CODES.GINGERBREAD;

/**
 * <Pre>
 * </Pre>
 *
 * @author fanenqian
 * @version 1.0
 *          <p/>
 *          Create by 2016年9月30日21:12:59
 */
public class MyApplication extends Application {
    private static MyApplication instance;

    public static String cacheDir = "";
//    private RefWatcher refWatcher;

    @Override
    public void onCreate() {
        super.onCreate();
        Logger.init().setLogLevel(LogLevel.FULL);
        instance = this;
//        this.enabledStrictMode();
        ToastUtils.register(this);
        //LeakCanary检测OOM
        LeakCanary.install(this);
        //初始化日志输出工具
        CrashHandler.init(new CrashHandler(getApplicationContext()));
        /**
         * 如果存在SD卡则将缓存写入SD卡,否则写入手机内存
         */

        if (getApplicationContext().getExternalCacheDir() != null && isExistSDCard()) {
            cacheDir = getApplicationContext().getExternalCacheDir().toString();

        } else {
            cacheDir = getApplicationContext().getCacheDir().toString();
        }

        /**
         * 初始化分享数据
         */
        initShare();
    }

    private boolean isExistSDCard() {
        if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)) {
            return true;
        } else {
            return false;
        }
    }

    private void enabledStrictMode() {
        if (SDK_INT >= GINGERBREAD) {
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder() //
                    .detectAll()  //
                    .penaltyLog() //
                    .penaltyDeath() //
                    .build());
        }
    }

    // 获取ApplicationContext
    public static Context getContext() {
        return instance;
    }

    public void initShare() {
        UMShareAPI.get(this);
        PlatformConfig.setWeixin("wx47f94c234b62e5fe", "bb823cf325e1122432bc53a8a1552483");
        PlatformConfig.setQQZone("1105124301", "wMCa9AGqpYsi4vLK");
    }
}
