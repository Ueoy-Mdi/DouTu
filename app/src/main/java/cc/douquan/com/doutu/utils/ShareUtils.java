package cc.douquan.com.doutu.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;

import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;

import java.io.File;

import cc.douquan.com.doutu.Constants;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

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

    public static void shareGirl(View v, final Context context) {
        Drawable drawable = ((ImageView) v).getDrawable();
        if (drawable != null) {
            Bitmap bitmap = BitmapUtil.drawableToBitmap(drawable);

            Observable.just(BitmapUtil.saveBitmap(bitmap, Constants.dir, "share.jpg", false))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Action1<Boolean>() {
                        @Override
                        public void call(Boolean isSuccess) {
                            if (isSuccess) {
                                //由文件得到uri
                                Uri imageUri = Uri.fromFile(new File(Constants.dir + "/share.jpg"));
                                Intent shareIntent = new Intent();
                                shareIntent.setAction(Intent.ACTION_SEND);
                                shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri);
                                shareIntent.setType("image/*");
                                context.startActivity(Intent.createChooser(shareIntent, "分享MeiZhi到"));
                            } else {
//                                Snackbar.make(mRoot, "大爷，分享出错了哦~", Snackbar.LENGTH_LONG).show();
                            }
                        }
                    });
        }
    }

}
