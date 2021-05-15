package cc.douquan.com.doutu.delegate;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.jcodecraeer.xrecyclerview.XRecyclerView;

import butterknife.Bind;
import cc.douquan.com.doutu.MyApplication;
import cc.douquan.com.doutu.R;
import cc.douquan.com.doutu.mvp_frame.view.AppDelegate;
import cc.douquan.com.doutu.view.DividerGridItemDecoration;

import static cc.douquan.com.doutu.R.id.recyclerview;

/**
 * Created by feq on 2016/10/16.
 */

public class FindDelegate extends AppDelegate {
    private GridLayoutManager mGridViewLayoutManager;//recycleview视图样式管理器
    @Bind(R.id.rc_rc)
    XRecyclerView xRecyclerView;

    @Override
    public int getRootLayoutId() {
        return R.layout.find_fragment;
    }

    @Override
    public void initWidget() {
        super.initWidget();
        mGridViewLayoutManager = new GridLayoutManager(this.getActivity(), 3);
        xRecyclerView.addItemDecoration(new DividerGridItemDecoration(getActivity()));
        xRecyclerView.setLayoutManager(mGridViewLayoutManager);
    }

    public void setListAdapter(RecyclerView.Adapter adapter) {
        xRecyclerView.setAdapter(adapter);
    }

    public void addHeadView(int rc_head_view) {
        View head = View.inflate(MyApplication.getContext(), rc_head_view, null);
        xRecyclerView.addHeaderView(head);
    }
}
