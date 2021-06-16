package cc.douquan.com.doutu.delegate;

import android.support.v7.app.ActionBar;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;
import android.widget.TextView;

import butterknife.Bind;
import cc.douquan.com.doutu.R;
import cc.douquan.com.doutu.view.DividerGridItemDecoration;

/**
 * Created by feq on 2016/11/2.
 */

public class MycollectionDelegate extends BaseRecyclerViewDelegate {
    private GridLayoutManager mGridViewLayoutManager;//recycleview视图样式管理器
    @Bind(R.id.title)
    TextView title;

    @Override
    void initRecyclerView() {
        appBarLayout.setVisibility(View.VISIBLE);
        mGridViewLayoutManager = new GridLayoutManager(this.getActivity(), 3);
        recyclerview.addItemDecoration(new DividerGridItemDecoration(getActivity()));
        recyclerview.setLayoutManager(mGridViewLayoutManager);
    }

    @Override
    boolean setFloatingActionMenuVisible() {
        return false;
    }
    public void setToolbar(ActionBar supportActionBar, String titile) {
        supportActionBar.setDisplayHomeAsUpEnabled(true);
        supportActionBar.setDisplayShowTitleEnabled(false);
        title.setText(titile);
    }
}
