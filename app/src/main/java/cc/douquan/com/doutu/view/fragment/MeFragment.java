package cc.douquan.com.doutu.view.fragment;

import android.view.View;

import butterknife.OnClick;
import cc.douquan.com.doutu.R;
import cc.douquan.com.doutu.delegate.MeDelegate;
import cc.douquan.com.doutu.mvp_frame.presenter.FragmentPresenter;
import cc.douquan.com.doutu.utils.ToastUtils;

/**
 * Created by qef on 2016/8/27.
 */
public class MeFragment extends FragmentPresenter<MeDelegate> {

    public static MeFragment newInstance() {
        MeFragment MeFragment = new MeFragment();
        return MeFragment;
    }

    @Override
    protected Class getDelegateClass() {
        return MeDelegate.class;
    }
}
