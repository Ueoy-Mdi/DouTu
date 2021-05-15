package cc.douquan.com.doutu.model;

import java.util.List;

/**
 * Created by qef on 2016/8/28.
 */
public interface HomeImgModel {
    /**
     * 加载新闻列表
     * @param page 页数
     * @param channelId 频道id 来自api
     * @param channelName 频道名称
     */
    void netLoadNewsList(int page, int totalPage, OnNetRequestListener<HomeImgEntity> listListener);
}
