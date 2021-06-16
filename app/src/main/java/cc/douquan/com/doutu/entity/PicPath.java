package cc.douquan.com.doutu.entity;

/**
 * Created by feq on 2016/11/2.
 */

public class PicPath {
    String picPath;
    String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPicPath() {
        return picPath;
    }

    public void setPicPath(String picPath) {
        this.picPath = picPath;
    }

    @Override
    public String toString() {
        return "PicPath{" +
                "picPath='" + picPath + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}
