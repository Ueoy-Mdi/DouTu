package cc.douquan.com.doutu.view.fragment;

import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import cc.douquan.com.doutu.R;
import cc.douquan.com.doutu.adapter.FindPictureAdapter;
import cc.douquan.com.doutu.adapter.HomePictureAdapter;
import cc.douquan.com.doutu.delegate.FindDelegate;
import cc.douquan.com.doutu.model.FindImgModel;
import cc.douquan.com.doutu.model.FindImgModelImpl;
import cc.douquan.com.doutu.model.HomeImgEntity;
import cc.douquan.com.doutu.model.OnNetRequestListener;
import cc.douquan.com.doutu.mvp_frame.presenter.FragmentPresenter;

/**
 * Created by qef on 2016/8/27.
 */
public class FindFragment extends FragmentPresenter<FindDelegate> {
    private FindPictureAdapter mAdapter;
    private List<HomeImgEntity.DataBean> mNews;
    private int mPageNum = 1;
    private FindImgModel findImgModel;
    private int totalpage = 48;

    public static FindFragment newInstance() {
        FindFragment findFragment = new FindFragment();
        return findFragment;
    }

    @Override
    protected Class<FindDelegate> getDelegateClass() {
        return FindDelegate.class;
    }

    @Override
    protected void initData() {
        super.initData();
        findImgModel = new FindImgModelImpl();
        mNews = new ArrayList<>();
        mAdapter = new FindPictureAdapter(mNews, getActivity());
        viewDelegate.setListAdapter(mAdapter);
        viewDelegate.addHeadView(R.layout.rc_head_view);
        netDataList(true);
    }

    private void netDataList(boolean isRefresh) {
        if (isRefresh) {
            mPageNum = 1;
        } else {
            mPageNum++;
        }
        findImgModel.netLoadDataList(mPageNum, totalpage, 5, new OnNetRequestListener<HomeImgEntity>() {
            @Override
            public void onStart() {
                Logger.i("netLoadDataList", "onStart");
            }

            @Override
            public void onFinish() {
                Logger.i("netLoadDataList", "onFinish");
            }

            @Override
            public void onSuccess(HomeImgEntity data) {
                Logger.i("netLoadDataList", "onSuccess" + data.getData().toString());
                if (null != data) {
                    mNews.addAll(data.getData());
                    mAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Logger.i("netLoadDataList", "onFailure");
            }
        });
    }
}
