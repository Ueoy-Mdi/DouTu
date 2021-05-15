package cc.douquan.com.doutu.delegate;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.orhanobut.logger.Logger;

import cc.douquan.com.doutu.R;
import cc.douquan.com.doutu.adapter.HomePictureAdapter;
import cc.douquan.com.doutu.mvp_frame.view.AppDelegate;
import cc.douquan.com.doutu.mvp_frame.view.LoadingView;
import cc.douquan.com.doutu.view.Decoration;
import cc.douquan.com.doutu.view.DividerGridItemDecoration;

/**
 * Created by qef on 2016/8/28.
 */
public class HomeFragmentDelegate extends BaseRecyclerViewDelegate implements LoadingView {
    private GridLayoutManager mGridViewLayoutManager;//recycleview视图样式管理器

    @Override
    void initRecyclerView() {
        mGridViewLayoutManager = new GridLayoutManager(this.getActivity(), 3);
        recyclerview.addItemDecoration(new DividerGridItemDecoration(getActivity()));
        recyclerview.setLayoutManager(mGridViewLayoutManager);
    }

    @Override
    boolean setFloatingActionMenuVisible() {
        return false;
    }

//    @Override
//    public int getRootLayoutId() {
//        return R.layout.base_fragment;
//    }

    @Override
    public void showLoading() {

    }


    @Override
    public void showError(int messageId, View.OnClickListener listener) {

    }

    @Override
    public Context getContext() {
        return null;
    }

    /**
     * init loadMore
     */
    public void registerLoadMoreCallBack(final SwipeRefreshAndLoadMoreCallBack callBack, final HomePictureAdapter mAdapter) {
        recyclerview.addOnScrollListener(new RecyclerView.OnScrollListener() {
            private int lastVisibleItem;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
//                lastVisibleItem = mGridViewLayoutManager.findLastVisibleItemPositions(null)[1];
                lastVisibleItem = mGridViewLayoutManager.findLastVisibleItemPosition();
                if (lastVisibleItem == mAdapter.getItemCount()-1) {
                    Logger.d("loadMore", "loadMore加载更多");
                    if (!swipe_refresh_layout.isRefreshing()) {
                        callBack.loadMore();
                    }
                }
                Logger.d("lastVisibleItem", lastVisibleItem + "");
            }
        });

    }

}
