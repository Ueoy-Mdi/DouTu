package cc.douquan.com.doutu.view.fragment;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import java.util.List;

import cc.douquan.com.doutu.R;

/**
 * Created by qef on 2016/8/27.
 */
public class ContentFragment extends BaseFragment {
    private TabLayout tab_title;//定义tablayout
    private ViewPager vp_Fragment_pager;//  定义viewpager
    private FragmentPagerAdapter fAdapter;//定义adapter

    private List<Fragment> listFragment;//定义要封装的fragment
    private List<String> listTitle;//定义tab名称列表


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
