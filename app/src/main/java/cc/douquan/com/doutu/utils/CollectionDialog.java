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
                ToastUtils.showShort("ζΆθζε");
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
        int width = ((Activity) context).getWindowManager().getDefaultDisplay().getWidth();//εΎε°ε½εζΎη€Ίθ?Ύε€ηε?½εΊ¦οΌεδ½ζ―εη΄ 
        WindowManager.LayoutParams params = mDialog.getWindow().getAttributes();//εΎε°θΏδΈͺdialogηι’ηεζ°ε―Ήθ±‘
        params.width = width - (width / 6);//θ?Ύη½?dialogηηι’ε?½εΊ¦
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;//θ?Ύη½?dialogι«εΊ¦δΈΊεθ£Ήεε?Ή
        params.gravity = Gravity.CENTER;//θ?Ύη½?dialogηιεΏ
        //dialog.getWindow().setLayout(width-(width/6),  LayoutParams.WRAP_CONTENT);//η¨θΏδΈͺζΉζ³θ?Ύη½?dialogε€§ε°δΉε―δ»₯οΌδ½ζ―θΏδΈͺζΉζ³δΈθ½θ?Ύη½?ιεΏδΉη±»ηεζ°οΌζ¨θη¨Attributesθ?Ύη½?
        mDialog.getWindow().setAttributes(params);//ζεζθΏδΈͺεζ°ε―Ήθ±‘θ?Ύη½?θΏε»οΌε³δΈdialogη»ε?
    }
}
