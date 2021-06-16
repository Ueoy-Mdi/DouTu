package cc.douquan.com.doutu.view.activity;

import cc.douquan.com.doutu.delegate.MycollectionDelegate;
import cc.douquan.com.doutu.mvp_frame.presenter.ActivityPresenter;

/**
 * Created by feq on 2016/11/2.
 */

public class MycollectionActivity extends ActivityPresenter<MycollectionDelegate> {
    @Override
    protected Class<MycollectionDelegate> getDelegateClass() {
        return MycollectionDelegate.class;
    }

    @Override
    protected void initView() {
        super.initView();
        viewDelegate.setToolbar(getSupportActionBar(), "我的收藏");
    }
}
