package cc.douquan.com.doutu.model;

import com.orhanobut.logger.Logger;

import cc.douquan.com.doutu.server.RetrofitService;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;

 /**
 * Created by feq on 2016/10/16.
 * 发现页面网络请求
 */

public class FindImgModelImpl implements FindImgModel {

    @Override
    public void netLoadDataList(int page, int totalPage, int tagId, final OnNetRequestListener<HomeImgEntity> listListener) {
        Observable<HomeImgEntity> observable = RetrofitService.getInstance().createShowApi()
                .getHotList(RetrofitService.getCacheControl(), page, tagId, totalPage);
        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        listListener.onStart();
                    }
                }).subscribe(new Subscriber<HomeImgEntity>() {
            @Override
            public void onCompleted() {
                listListener.onFinish();
            }

            @Override
            public void onError(Throwable e) {
                Logger.i("onError", e.getMessage());
//                Logger.i("onError",e.get);
                listListener.onFailure(e);
                listListener.onFinish();
            }

            @Override
            public void onNext(HomeImgEntity homeImgEntity) {
                if (homeImgEntity != null) {
                    listListener.onSuccess(homeImgEntity);
                } else {
                    listListener.onFailure(new Exception());
                }
            }
        });
    }
}
