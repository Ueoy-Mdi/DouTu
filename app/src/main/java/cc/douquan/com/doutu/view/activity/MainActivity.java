package cc.douquan.com.doutu.view.activity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.speech.RecognizerIntent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentPagerAdapter;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;

import java.util.ArrayList;

import br.com.mauker.materialsearchview.MaterialSearchView;
import cc.douquan.com.doutu.R;
import cc.douquan.com.doutu.adapter.TabViewPageAdapter;
import cc.douquan.com.doutu.db.MyDb;
import cc.douquan.com.doutu.delegate.MainActivityDelegate;
import cc.douquan.com.doutu.mvp_frame.presenter.ActivityPresenter;

public class MainActivity extends ActivityPresenter<MainActivityDelegate> {
    private FragmentPagerAdapter fAdapter;//定义adapter

    protected Class<MainActivityDelegate> getDelegateClass() {
        return MainActivityDelegate.class;
    }

    @Override
    protected void initData() {
        super.initData();
        //设置TabLayout的模式
        initFragment();
        setSwipeBackEnable(false);
    }


    private void initFragment() {
        //设置TabLayout的模式
        viewDelegate.setTabMode(TabLayout.MODE_FIXED);
        fAdapter = new TabViewPageAdapter(getSupportFragmentManager(), this);
        //viewpager加载adapter
        viewDelegate.setViewPagerAdapter(fAdapter);
        viewDelegate.setupWithViewPager();
        viewDelegate.setOnQueryTextListener(this);
        viewDelegate.setSearchViewListener();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        } else if (id == R.id.action_search) {
            viewDelegate.getSearchView().openSearch();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == MaterialSearchView.REQUEST_VOICE && resultCode == RESULT_OK) {
            ArrayList<String> matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            if (matches != null && matches.size() > 0) {
                String searchWrd = matches.get(0);
                if (!TextUtils.isEmpty(searchWrd)) {
                    viewDelegate.getSearchView().setQuery(searchWrd, false);
                }
            }
            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onBackPressed() {
        if (viewDelegate.getSearchView().isOpen()) {
            viewDelegate.getSearchView().closeSearch();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (viewDelegate.getSearchView().isOpen()) {
            viewDelegate.getSearchView().closeSearch();
        }
        return super.dispatchTouchEvent(ev);
    }
}
