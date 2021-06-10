package cc.douquan.com.doutu.view.fragment;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.orhanobut.logger.Logger;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cc.douquan.com.doutu.Constants;
import cc.douquan.com.doutu.MyApplication;
import cc.douquan.com.doutu.R;
import cc.douquan.com.doutu.adapter.HomePictureAdapter;
import cc.douquan.com.doutu.delegate.HomeFragmentDelegate;
import cc.douquan.com.doutu.delegate.SwipeRefreshAndLoadMoreCallBack;
import cc.douquan.com.doutu.model.HomeImgEntity;
import cc.douquan.com.doutu.model.HomeImgModel;
import cc.douquan.com.doutu.model.HomeImgModelImpl;
import cc.douquan.com.doutu.model.OnNetRequestListener;
import cc.douquan.com.doutu.mvp_frame.presenter.FragmentPresenter;
import cc.douquan.com.doutu.utils.BitmapUtil;
import cc.douquan.com.doutu.utils.CollectionDialog;
import cc.douquan.com.doutu.utils.ShareUtils;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by qef on 2016/8/27.
 */
public class HomeFragment extends FragmentPresenter<HomeFragmentDelegate> implements SwipeRefreshAndLoadMoreCallBack {
    private HomeImgModel homeImgModel;
    private HomePictureAdapter mAdapter;
    private int mPageNum = 1;
    //新闻数据列表
    private List<HomeImgEntity.DataBean> mNews = new ArrayList<>();
    private Context context;
    private View mAlertView;
    private AlertDialog mDialog;

    public static HomeFragment newInstance() {
        HomeFragment homeFragment = new HomeFragment();
        return homeFragment;
    }

    @Override
    protected Class getDelegateClass() {
        return HomeFragmentDelegate.class;
    }

    @Override
    protected void initData() {
        super.initData();
        context = getActivity();
        homeImgModel = new HomeImgModelImpl();
        mAdapter = new HomePictureAdapter(mNews, getActivity());
        mAdapter.setOnImageClickListener(new HomePictureAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                v = v.findViewById(R.id.iv_img);
                ShareUtils.shareGirl(v, getActivity());
            }

            @Override
            public void onItemLongClick(View view, int position) {
                view = view.findViewById(R.id.iv_img);
                CollectionDialog.showDialog(context, view, mNews.get(position).getPicPath());
            }
        });
        viewDelegate.setListAdapter(mAdapter);
        viewDelegate.registerLoadMoreCallBack(this, mAdapter);
        //注册下拉刷新
        viewDelegate.registerSwipeRefreshCallBack(this);
        netNewsList(true);
    }

    private void netNewsList(final boolean isRefresh) {
        if (isRefresh) {
            mPageNum = 1;
        } else {
            mPageNum++;
        }
        homeImgModel.netLoadNewsList(mPageNum, 48, new OnNetRequestListener<HomeImgEntity>() {
            @Override
            public void onStart() {

            }

            @Override
            public void onFinish() {

            }

            @Override
            public void onSuccess(HomeImgEntity data) {
                Log.i("onSuccesss", "onSuccess" + data.getData());
                viewDelegate.showContent();
                if (isRefresh) {
                    if (!mNews.isEmpty()) {
                        mNews.clear();
                    }
                }
                mNews.addAll(data.getData());
                mAdapter.notifyDataSetChanged();
                Log.i("mNewsSize", mNews.size() + "");
            }

            @Override
            public void onFailure(Throwable t) {
                Log.i("onFailuree", "onFailure");
            }
        });
    }

    @Override
    public void refresh() {
        netNewsList(true);
    }

    @Override
    public void loadMore() {
        Logger.d("loadMore", "加载更多接口");
        netNewsList(false);
    }
}
