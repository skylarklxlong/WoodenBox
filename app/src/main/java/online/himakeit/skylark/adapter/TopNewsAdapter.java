package online.himakeit.skylark.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.ArrayList;

import online.himakeit.skylark.MainActivity;
import online.himakeit.skylark.R;
import online.himakeit.skylark.activity.TopNewsDescribeActivity;
import online.himakeit.skylark.model.topnews.NewsBean;
import online.himakeit.skylark.util.DensityUtils;

/**
 * Created by：LiXueLong 李雪龙 on 2017/7/28 8:09
 * <p>
 * Mail : skylarklxlong@outlook.com
 * <p>
 * Description:
 */
public class TopNewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements MainActivity.LoadingMore {

    private static final int TYPE_LOADING_MORE = -1;
    private static final int TYPE_NORMAL = 1;

    private boolean loadingMore;
    private int mImageViewWidth;
    private int mImageViewHeight;

    private ArrayList<NewsBean> topNewsItems = new ArrayList<NewsBean>();
    private Context mContext;

    public TopNewsAdapter(Context context){
        this.mContext = context;
        float width = mContext.getResources().getDimension(R.dimen.image_width);
        mImageViewWidth = DensityUtils.dip2px(mContext,width);
        mImageViewHeight = mImageViewWidth * 3 / 4;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType){
            case TYPE_NORMAL:

                return new TopNewsViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_top_news,parent,false));
            case TYPE_LOADING_MORE:

                return new LoadingMoreViewHolder(LayoutInflater.from(mContext).inflate(R.layout.infinite_loading,parent,false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int type = getItemViewType(position);
        switch (type){
            case TYPE_NORMAL:
                bindViewHolderNormal((TopNewsViewHolder) holder,position);
                break;
            case TYPE_LOADING_MORE:
                bindLoadingViewHold((LoadingMoreViewHolder) holder,position);
                break;
        }
    }

    private void bindLoadingViewHold(LoadingMoreViewHolder holder,int position){
        holder.progressBar.setVisibility(loadingMore ? View.VISIBLE : View.GONE);
    }

    private void bindViewHolderNormal(final TopNewsViewHolder holder,final int position){
        final NewsBean newsBeanItem = topNewsItems.get(holder.getAdapterPosition());
        // TODO: 2017/7/28 这里可以添加对数据库的判断
        holder.textView.setTextColor(Color.BLACK);
        holder.sourceTextView.setTextColor(Color.BLACK);
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: 2017/7/28 数据库操作
                holder.textView.setTextColor(Color.GRAY);
                holder.sourceTextView.setTextColor(Color.GRAY);
                startTopNewsActivity(newsBeanItem,holder);
            }
        });
        holder.textView.setText(newsBeanItem.getTitle());
        holder.sourceTextView.setText(newsBeanItem.getSource());
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTopNewsActivity(newsBeanItem,holder);
            }
        });
        Glide.with(mContext)
                .load(newsBeanItem.getImgsrc()+"?imageView&thumbnail=80x0")
                .placeholder(R.drawable.imageview_loading)
                .error(R.drawable.imageview_loading)
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        // TODO: 2017/8/23 暂时不开发特效
                        return false;
                    }
                }).diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .centerCrop()
                .override(mImageViewWidth,mImageViewHeight)
                .into(holder.imageView);
    }

    private void startTopNewsActivity(NewsBean newsBeanItem,RecyclerView.ViewHolder viewHolder){
        /**
         * 新闻详细界面是通过第一次访问请求新闻列表时得到的NewsBean中的docid重新拼接一个链接获得的
         */
        Intent intent = new Intent(mContext, TopNewsDescribeActivity.class);
        intent.putExtra("docid",newsBeanItem.getDocid());
        intent.putExtra("title",newsBeanItem.getTitle());
        intent.putExtra("image",newsBeanItem.getImgsrc());
        intent.putExtra("source",newsBeanItem.getSource());
        // TODO: 2017/7/28 在这里要做些什么呢？
        mContext.startActivity(intent);

        //https://c.m.163.com/news/a/CSEEFE0T000189FH.html?spss=newsapp&spsw=1
        /*Intent intent = WebActivity.newTntent(mContext,
                Config.TOPNEWS_BASE_URL + "/news/a/"+newsBeanItem.getDocid()+".html?spss=newsapp&spsw=1",
                newsBeanItem.getTitle());
        mContext.startActivity(intent);*/
    }

    @Override
    public int getItemCount() {
        return topNewsItems.size();
    }

    // TODO: 2017/7/28 添加数据
    public void addItem(ArrayList<NewsBean> list){
        /**
         * 这里移除掉第一个是因为从第二项开始的数据的格式才是NewsBean,
         * 第一条的新闻内容与后面的JSON格式都不同
         */
        list.remove(0);
        topNewsItems.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        if (position < getDataItemCount()
                && getDataItemCount() > 0){
            return TYPE_NORMAL;
        }
        return TYPE_LOADING_MORE;
    }

    private int getDataItemCount(){return topNewsItems.size();}

    private int getLoadingMoreItemPosition(){
        return loadingMore ? getItemCount() - 1 : RecyclerView.NO_POSITION;
    }

    @Override
    public void loadingStart() {
        if (loadingMore) return;
        loadingMore = true;
        notifyItemInserted(getLoadingMoreItemPosition());
    }

    @Override
    public void loadingFinish() {
        if (!loadingMore) return;
        final int loadingPos = getLoadingMoreItemPosition();
        loadingMore = false;
        notifyItemRemoved(loadingPos);
    }

    public void clearData(){
        topNewsItems.clear();
        notifyDataSetChanged();
    }
    private static class LoadingMoreViewHolder extends RecyclerView.ViewHolder{
        ProgressBar progressBar;
        public LoadingMoreViewHolder(View itemView) {
            super(itemView);
            progressBar = (ProgressBar) itemView;
        }
    }
    private static class TopNewsViewHolder extends RecyclerView.ViewHolder{
        LinearLayout linearLayout;
        ImageView imageView;
        TextView textView;
        TextView sourceTextView;
        public TopNewsViewHolder(View itemView) {
            super(itemView);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.top_news_item_layout);
            imageView = (ImageView) itemView.findViewById(R.id.item_image_id);
            textView = (TextView) itemView.findViewById(R.id.item_text_id);
            sourceTextView = (TextView) itemView.findViewById(R.id.item_text_source_id);
        }
    }
}
