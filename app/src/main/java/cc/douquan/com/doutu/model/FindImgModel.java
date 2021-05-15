package cc.douquan.com.doutu.model;

/**
 * Created by feq on 2016/10/16.
 */

public interface FindImgModel {
    void netLoadDataList(int page, int totalPage, int tagId, OnNetRequestListener<HomeImgEntity> listListener);
}
