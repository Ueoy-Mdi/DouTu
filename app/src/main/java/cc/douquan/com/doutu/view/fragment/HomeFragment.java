package cc.douquan.com.doutu.view.fragment;


import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.orhanobut.logger.Logger;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.ArrayList;
import java.util.List;

import cc.douquan.com.doutu.adapter.HomePictureAdapter;
import cc.douquan.com.doutu.delegate.HomeFragmentDelegate;
import cc.douquan.com.doutu.delegate.SwipeRefreshAndLoadMoreCallBack;
import cc.douquan.com.doutu.model.HomeImgEntity;
import cc.douquan.com.doutu.model.HomeImgModel;
import cc.douquan.com.doutu.model.HomeImgModelImpl;
import cc.douquan.com.doutu.model.OnNetRequestListener;
import cc.douquan.com.doutu.mvp_frame.presenter.FragmentPresenter;
import cc.douquan.com.doutu.utils.ShareUtils;

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
        homeImgModel = new HomeImgModelImpl();
        mAdapter = new HomePictureAdapter(mNews, getActivity());
        mAdapter.setOnImageClickListener(new HomePictureAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                ShareUtils.share(getActivity(),
                        mNews.get(position).getGifPath(),
                        new MyUmlitener());
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
}
