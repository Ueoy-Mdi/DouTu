package cc.douquan.com.doutu.delegate;

import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;

import br.com.mauker.materialsearchview.MaterialSearchView;
import butterknife.Bind;
import cc.douquan.com.doutu.R;
import cc.douquan.com.doutu.mvp_frame.view.AppDelegate;

/**
 * Created by qef on 2016/8/28.
 */
public class MainActivityDelegate extends AppDelegate {
    @Bind(R.id.vp_FindFragment_pager)
    ViewPager vpMain;
    @Bind(R.id.tab_layout)
    TabLayout tab_title;//定义tablayout
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.search_view)
    MaterialSearchView searchView;

    @Override
    public int getRootLayoutId() {
        return R.layout.activity_main;
    }

    public void setViewPagerAdapter(FragmentPagerAdapter adapter) {
        vpMain.setAdapter(adapter);
    }

    public void setTabMode(int mode) {
        tab_title.setTabMode(mode);
    }

    public void setupWithViewPager() {
        vpMain.setCurrentItem(1);
        tab_title.setupWithViewPager(vpMain);

    }

    @Override
    public Toolbar getToolbar() {
        return toolbar;
    }

    public void setOnQueryTextListener() {
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    public void setSearchViewListener() {
        searchView.setSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewOpened() {

            }

            @Override
            public void onSearchViewClosed() {

            }
        });
    }

    public MaterialSearchView getSearchView() {
        return searchView;
    }
}
