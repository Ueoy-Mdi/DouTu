package cc.douquan.com.doutu.utils;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;

import com.orhanobut.logger.Logger;

import cc.douquan.com.doutu.MyApplication;
import cc.douquan.com.doutu.R;
import cc.douquan.com.doutu.db.DBManager;
import cc.douquan.com.doutu.entity.PicPath;

/**
 * Created by feq on 2016/11/1.
 */

public class CollectionDialog {
    private static AlertDialog mDialog;
    private static DBManager dbManager;
    private static final String DB_NAME = "pic_path.db";
    private static final int DB_VERSION = 1;

    public static void showDialog(final Context context, final View ivImg, final String picPath) {

        mDialog = new AlertDialog.Builder(context, R.style.dialog).create();
        View mAlertView = View.inflate(MyApplication.getContext(), R.layout.share_dialog, null);
        mAlertView.findViewById(R.id.iv_collection).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.showShort("收藏成功");
                Logger.i("picpath---", picPath);
                mDialog.dismiss();
                dbManager = new DBManager(context, DB_NAME, DB_VERSION, PicPath.class);
                PicPath path = new PicPath();
                path.setPicPath(picPath);
                dbManager.insert(path);
            }
        });
        mAlertView.findViewById(R.id.iv_share).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShareUtils.shareGirl(ivImg, context);
                mDialog.dismiss();
            }
        });
        mAlertView.findViewById(R.id.tv_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
            }
        });
        mDialog.setView(mAlertView);
        mDialog.show();
        int width = ((Activity) context).getWindowManager().getDefaultDisplay().getWidth();//得到当前显示设备的宽度，单位是像素
        WindowManager.LayoutParams params = mDialog.getWindow().getAttributes();//得到这个dialog界面的参数对象
        params.width = width - (width / 6);//设置dialog的界面宽度
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;//设置dialog高度为包裹内容
        params.gravity = Gravity.CENTER;//设置dialog的重心
        //dialog.getWindow().setLayout(width-(width/6),  LayoutParams.WRAP_CONTENT);//用这个方法设置dialog大小也可以，但是这个方法不能设置重心之类的参数，推荐用Attributes设置
        mDialog.getWindow().setAttributes(params);//最后把这个参数对象设置进去，即与dialog绑定
    }
}
