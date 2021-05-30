package cc.douquan.com.doutu.view.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.orhanobut.logger.Logger;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cc.douquan.com.doutu.Constants;
import cc.douquan.com.doutu.MyApplication;
import cc.douquan.com.doutu.R;
import cc.douquan.com.doutu.adapter.HomePictureAdapter;
import cc.douquan.com.doutu.adapter.SearchPictureAdapter;
import cc.douquan.com.doutu.delegate.SearchActivitDelegate;
import cc.douquan.com.doutu.delegate.SwipeRefreshAndLoadMoreCallBack;
import cc.douquan.com.doutu.model.HomeImgEntity;
import cc.douquan.com.doutu.model.HomeImgModel;
import cc.douquan.com.doutu.model.OnNetRequestListener;
import cc.douquan.com.doutu.model.SearchEntity;
import cc.douquan.com.doutu.model.SearchImgModel;
import cc.douquan.com.doutu.model.SearchImgModelImpl;
import cc.douquan.com.doutu.mvp_frame.presenter.ActivityPresenter;
import cc.douquan.com.doutu.utils.BitmapUtil;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by feq on 2016/10/30.
 */

public class SearchActivity extends ActivityPresenter<SearchActivitDelegate> implements SwipeRefreshAndLoadMoreCallBack {
    private SearchPictureAdapter mAdapter;
    //图片数据列表
    private List<SearchEntity.DataBean> mNews = new ArrayList<>();
    private int mPageNum = 0;
    private SearchImgModel searchImgModel;
    private String query;

    @Override
    protected Class<SearchActivitDelegate> getDelegateClass() {
        return SearchActivitDelegate.class;
    }

    @Override
    protected void initData() {
        super.initData();
        Intent intent = getIntent();
        Bundle extras = intent.getBundleExtra("bundle");
        query = extras.getString("query");
        Logger.i("query", extras.getString("query"));

        searchImgModel = new SearchImgModelImpl();
        mAdapter = new SearchPictureAdapter(mNews, this);
        mAdapter.setOnImageClickListener(new SearchPictureAdapter.OnItemClickListener() {
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
                Toast.makeText(MyApplication.getContext(), "长按", Toast.LENGTH_SHORT).show();
            }
        });
        viewDelegate.setListAdapter(mAdapter);
        viewDelegate.registerLoadMoreCallBack(this, mAdapter);
        //注册下拉刷新
        viewDelegate.registerSwipeRefreshCallBack(this);
        netNewsList(true);
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

    private void netNewsList(final boolean isRefresh) {
        if (isRefresh) {
            mPageNum = 0;
        } else {
            mPageNum++;
        }
        searchImgModel.netLoadNewsList(mPageNum, 48, query, new OnNetRequestListener<SearchEntity>() {
            @Override
            public void onStart() {

            }

            @Override
            public void onFinish() {

            }

            @Override
            public void onSuccess(SearchEntity data) {
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
