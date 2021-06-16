package cc.douquan.com.doutu.delegate;

import android.support.v7.app.ActionBar;
import android.view.View;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.OnClick;
import cc.douquan.com.doutu.R;
import cc.douquan.com.doutu.mvp_frame.view.AppDelegate;
import cc.douquan.com.doutu.utils.ToastUtils;
import cc.douquan.com.doutu.utils.UiHelper;

/**
 * Created by feq on 2016/11/2.
 */

public class MeDelegate extends AppDelegate {
    @Bind(R.id.tv_collection)
    TextView tvCollection;
    @Override
    public int getRootLayoutId() {
        return R.layout.base_fragment;
    }

    @Override
    public void initWidget() {
        super.initWidget();
    }

    @OnClick(R.id.tv_collection)
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_collection:
                ToastUtils.showShort("我的收藏");
                UiHelper.goCollectionActivity(this.getActivity(), null);
                break;
        }
    }

}
