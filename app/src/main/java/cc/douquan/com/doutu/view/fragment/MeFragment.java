package cc.douquan.com.doutu.view.fragment;

import android.view.View;

import cc.douquan.com.doutu.R;

/**
 * Created by qef on 2016/8/27.
 */
public class MeFragment extends BaseFragment {
    public static MeFragment newInstance() {
        MeFragment meFragment = new MeFragment();
        return meFragment;
    }

    @Override
    void initControls(View view) {

    }

    @Override
    void initData() {

    }

    @Override
    int setContentView() {
        return R.layout.base_fragment;
    }
}
