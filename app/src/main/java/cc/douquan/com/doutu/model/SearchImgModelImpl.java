package cc.douquan.com.doutu.model;

import cc.douquan.com.doutu.server.RetrofitService;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;

/**
 * Created by feq on 2016/10/30.
 */

public class SearchImgModelImpl implements SearchImgModel {
    @Override
    public void netLoadNewsList(int pageNum, int pageSize, String keyWord, final OnNetRequestListener<SearchEntity> listListener) {
        Observable<SearchEntity> observable = RetrofitService.getInstance().
                createShowApi().getSearchList(RetrofitService.getCacheControl(), keyWord, pageNum, pageSize);

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        listListener.onStart();
                    }
                })
                .subscribe(new Subscriber<SearchEntity>() {
                    @Override
                    public void onCompleted() {
                        listListener.onFinish();
                    }

                    @Override
                    public void onError(Throwable e) {
                        listListener.onFailure(e);
                        listListener.onFinish();
                    }

                    @Override
                    public void onNext(SearchEntity showApiPicturesShowApiResponse) {
                        if (showApiPicturesShowApiResponse != null) {
                            listListener.onSuccess(showApiPicturesShowApiResponse);
                        } else {
                            listListener.onFailure(new Exception());
                        }
                    }
                });
    }
}
