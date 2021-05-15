package cc.douquan.com.doutu.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cc.douquan.com.doutu.R;
import cc.douquan.com.doutu.model.HomeImgEntity;

/**
 * Created by qef on 2016/9/11.
 * 瀑布流适配器11
 */
public class FindPictureAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<HomeImgEntity.DataBean> mNews = new ArrayList<>();
    private Context mContext;
    private final static int NOMAL = 0;
    private final static int VISIBLEFOOTER = 1;
    private OnItemClickListener mOnImageClickListener;

    public FindPictureAdapter(List<HomeImgEntity.DataBean> mNews, Context mContext) {
        this.mNews = mNews;
        this.mContext = mContext;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new MyHolder(View.inflate(mContext, R.layout.item_home_pic, null));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int type = getItemViewType(position);
            Glide.with(mContext).load(mNews.get(position).getPicPath()).into(((MyHolder) holder).imageView);
            Logger.d("11111111111111" + mNews.get(position));
    }

    @Override
    public int getItemCount() {
        return mNews.size();
    }

    /**
     * 点击条目图片接口
     */
    public interface OnItemClickListener {
        void onItemClick(View v, int position);

        void onItemLongClick(View view, int position);
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.iv_img)
        ImageView imageView;

        public MyHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnImageClickListener.onItemClick(v, getPosition());
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                   mOnImageClickListener.onItemLongClick(v,getPosition());
                    return true;
                }
            });
        }

    }

    public void setOnImageClickListener(OnItemClickListener onItemClickListener) {
        this.mOnImageClickListener = onItemClickListener;
    }

    public class FooterHolder extends RecyclerView.ViewHolder {
        View itemView1;

        public FooterHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView1 = itemView;
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    if (position == mNews.size() - 1) {
                        return 3;
                    } else {
                        return 1;
                    }
                }
            });
        }
    }
}
