package cc.douquan.com.doutu.server;


import cc.douquan.com.doutu.common.BizInterface;
import cc.douquan.com.doutu.model.HomeImgEntity;
import cc.douquan.com.doutu.model.SearchEntity;
import cc.douquan.com.doutu.model.ShowApiResponse;
import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Headers;
import retrofit.http.Query;
import rx.Observable;

import static cc.douquan.com.doutu.common.BizInterface.HOT_URL;

/**
 * <Pre>
 * 易源api
 * </Pre>
 *
 * @author 刘阳
 * @version 1.0
 *          <p/>
 *          Create by 2016/1/27 15:22
 */
public interface ShowApi {
    /**
     * 新闻列表
     *
     * @param cacheControl
     * @param page
     * @param channelId
     * @param channelName
     * @return
     */
    @GET(BizInterface.NEWS_URL)
    @Headers("apikey: " + BizInterface.API_KEY)
    Observable<HomeImgEntity> getNewsList(@Header("Cache-Control") String cacheControl,
                                          @Query("pageNum") int page,
                                          @Query("pageSize") int totalPage);

    @GET(BizInterface.HOT_URL)
    Observable<HomeImgEntity> getHotList(@Header("Cache-Control") String cacheControl,
                                         @Query("pageNum") int page,
                                         @Query("tagId") int tagId,
                                         @Query("pageSize") int totalPage);

    @GET(BizInterface.SEARCH_URL)
    Observable<SearchEntity> getSearchList(@Header("Cache-Control") String cacheControl,
                                           @Query("keyWord") String keyWord,
                                           @Query("pageNum") int pageNum,
                                           @Query("pageSize") int pageSize);

}
