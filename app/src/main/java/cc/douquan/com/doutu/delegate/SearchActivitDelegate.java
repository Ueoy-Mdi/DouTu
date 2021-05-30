package cc.douquan.com.doutu.delegate;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.orhanobut.logger.Logger;

import cc.douquan.com.doutu.adapter.SearchPictureAdapter;
import cc.douquan.com.doutu.view.DividerGridItemDecoration;

/**
 * Created by feq on 2016/10/30.
 */

public class SearchActivitDelegate extends BaseRecyclerViewDelegate {
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

    /**
     * init loadMore
     */
    public void registerLoadMoreCallBack(final SwipeRefreshAndLoadMoreCallBack callBack, final SearchPictureAdapter mAdapter) {
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
                if (lastVisibleItem == mAdapter.getItemCount() - 1) {
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
