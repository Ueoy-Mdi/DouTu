package cc.douquan.com.doutu.model;

/**
 * Created by feq on 2016/10/30.
 */

public interface SearchImgModel {
    void netLoadNewsList(int pageNum, int pageSize, String keyWord,
                         OnNetRequestListener<SearchEntity> listListener);
}
