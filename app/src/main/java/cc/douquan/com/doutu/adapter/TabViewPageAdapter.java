package cc.douquan.com.doutu.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

import cc.douquan.com.doutu.view.fragment.FindFragment;
import cc.douquan.com.doutu.view.fragment.HomeFragment;
import cc.douquan.com.doutu.view.fragment.MeFragment;

/**
 * tablayout viewpager adapter
 * Created by qef on 2016/8/27.
 */
public class TabViewPageAdapter extends FragmentPagerAdapter {
    private Context context;

    public TabViewPageAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return FindFragment.newInstance();
            case 1:
                return HomeFragment.newInstance();
            case 2:
                return MeFragment.newInstance();
            default:
                return HomeFragment.newInstance();
        }

    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "发现";
            case 1:
                return "首页";
            case 2:
                return "我的";
            default:
                return "首页";
        }
    }

}
