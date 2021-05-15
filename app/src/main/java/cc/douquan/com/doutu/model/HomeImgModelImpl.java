package cc.douquan.com.doutu.model;

import android.util.Log;

import com.orhanobut.logger.Logger;

import java.util.List;

import cc.douquan.com.doutu.server.RetrofitService;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;

/**
 * Created by qef on 2016/8/28.
 */
public class HomeImgModelImpl implements HomeImgModel {
    @Override
    public void netLoadNewsList(int page, int totalPage, final OnNetRequestListener<HomeImgEntity> listListener) {
        //注意，此处采用Retrofit的官方响应方式，天气预报采用RxJava，学习一下两种用法
//        Call<ShowApiResponse<HomeImgEntity>> call = RetrofitService.getInstance()
//                .createShowApi()
//                .getNewsList(RetrofitService.getCacheControl(), page, totalPage);
//
//        call.enqueue(new Callback<ShowApiResponse<HomeImgEntity>>() {
//            @Override
//            public void onResponse(Response<ShowApiResponse<HomeImgEntity>> response, Retrofit retrofit) {
//                if (response.body() != null) {
//                    Logger.d(response.message() + response.code() + response.body().showapi_res_code
//                            + response.body().showapi_res_error);
//                    listListener.onSuccess(response.body().showapi_res_body.getData());
//                } else {
//                    listListener.onFailure(new Exception());
//                }
//            }
//
//            @Override
//            public void onFailure(Throwable t) {
//                listListener.onFailure(t);
//            }
//        });
        Observable<HomeImgEntity> observable = RetrofitService.getInstance().
                createShowApi().getNewsList(RetrofitService.getCacheControl(), page, totalPage);

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        listListener.onStart();
                    }
                })
                .subscribe(new Subscriber<HomeImgEntity>() {
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
                    public void onNext(HomeImgEntity showApiPicturesShowApiResponse) {
                        if (showApiPicturesShowApiResponse != null) {
                            listListener.onSuccess(showApiPicturesShowApiResponse);
                        } else {
                            listListener.onFailure(new Exception());
                        }
                    }
                });
    }
}
