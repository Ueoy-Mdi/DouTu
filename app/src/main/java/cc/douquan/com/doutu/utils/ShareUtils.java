package cc.douquan.com.doutu.utils;

import android.app.Activity;

import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;

/**
 * 2016 逛街购（北京）网络科技有限公司，版权所有
 * guangjiegou - Android客户端
 * Context:分享工具类
 */
public class ShareUtils {
    /**
     * 分享
     */
    public static void share(Activity activity, String shareInfo, UMShareListener umShareListener) {
        //分享模块
        UMImage image = new UMImage(activity, shareInfo);
        new ShareAction(activity).setDisplayList(SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE, SHARE_MEDIA.WEIXIN,
                SHARE_MEDIA.WEIXIN_CIRCLE)
                .withMedia(image)
                .setCallback(umShareListener)
                .open();

    }
}
