package cc.douquan.com.doutu.view.fragment;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.orhanobut.logger.Logger;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cc.douquan.com.doutu.Constants;
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
    private MyUmlitener uListener;
    private Context context;

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
//                ShareUtils.share(getActivity(),
//                        mNews.get(position).getGifPath(),
//                        new MyUmlitener());
                v = v.findViewById(R.id.iv_img);
                shareGirl(v);
            }

            @Override
            public void onItemLongClick(View view, int position) {
                Toast.makeText(getActivity(), "长按", Toast.LENGTH_SHORT).show();
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

    class MyUmlitener implements UMShareListener {
        @Override
        public void onResult(SHARE_MEDIA share_media) {

        }

        @Override
        public void onError(SHARE_MEDIA share_media, Throwable throwable) {

        }

        @Override
        public void onCancel(SHARE_MEDIA share_media) {

        }
    }

    public void shareGirl(View v) {
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
                                startActivity(Intent.createChooser(shareIntent, "分享MeiZhi到"));
                            } else {
//                                Snackbar.make(mRoot, "大爷，分享出错了哦~", Snackbar.LENGTH_LONG).show();
                            }
                        }
                    });
        }
    }

}
