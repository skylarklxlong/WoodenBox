package online.himakeit.skylark.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import online.himakeit.skylark.R;
import online.himakeit.skylark.listeners.OnItemClickListener;
import online.himakeit.skylark.model.mob.MobWxArticleEntity;

/**
 * Created by：LiXueLong 李雪龙 on 2017/11/11 15:08
 * <p>
 * Mail : skylarklxlong@outlook.com
 * <p>
 * Description:
 */
public class MobWXArticleRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<MobWxArticleEntity.MobWxArticleListBean> mDatas;
    private LayoutInflater layoutInflater;

    public MobWXArticleRecyclerAdapter(Context context, List<MobWxArticleEntity.MobWxArticleListBean> mDatas) {
        this.mDatas = mDatas;
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }

    public void updateDatas(List<MobWxArticleEntity.MobWxArticleListBean> mDatas) {
        this.mDatas = mDatas;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = layoutInflater.inflate(R.layout.item_mob_wx_article, parent, false);
        return new MyViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof MyViewHolder) {
            MyViewHolder myViewHolder = (MyViewHolder) holder;

            if (mOnItemClickLitener != null) {
                myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mOnItemClickLitener.onItemClick(view, position);
                    }
                });
            }

            MobWxArticleEntity.MobWxArticleListBean mobWxArticleListBean = mDatas.get(position);

            myViewHolder.tv_title_wx.setText(mobWxArticleListBean.getTitle());
            myViewHolder.tv_time_wx.setText(mobWxArticleListBean.getPubTime());

            //图片处理
            myViewHolder.iv_show_wx.setVisibility(View.VISIBLE);
            myViewHolder.iv_show_wx.setImageResource(R.drawable.pic_gray_bg);
            String thumbnails = mobWxArticleListBean.getThumbnails();

            if (!TextUtils.isEmpty(thumbnails)) {
                String[] images = thumbnails.split("$");
                if (images.length > 0) {
                    Glide.with(context)
                            .load(images[0])
                            .placeholder(R.drawable.imageview_loading)//设置加载中的图片
                            .error(R.drawable.imageview_loading)//设置加载错误时的图片
                            .centerCrop()
                            .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                            .into(myViewHolder.iv_show_wx);
                } else {
                    myViewHolder.iv_show_wx.setVisibility(View.GONE);
                }
            } else {
                myViewHolder.iv_show_wx.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.tv_title_wx)
        TextView tv_title_wx;
        @Bind(R.id.tv_time_wx)
        TextView tv_time_wx;
        @Bind(R.id.iv_show_wx)
        ImageView iv_show_wx;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    private OnItemClickListener mOnItemClickLitener;

    public void setOnItemClickLitener(OnItemClickListener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }
}
