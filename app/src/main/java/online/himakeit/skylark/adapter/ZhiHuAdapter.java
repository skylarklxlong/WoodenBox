package online.himakeit.skylark.adapter;

import android.content.Context;
import android.content.Intent;
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
import online.himakeit.skylark.activity.WebActivity;
import online.himakeit.skylark.model.zhuhu.ZhiHuDailyItem;
import online.himakeit.skylark.util.DensityUtils;

/**
 * Created by：LiXueLong 李雪龙 on 2017/8/23 19:30
 * <p>
 * Mail : skylarklxlong@outlook.com
 * <p>
 * Description:
 */
public class ZhiHuAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements MainActivity.LoadingMore {

    private static final int TYPE_LOADING_MORE = -1;
    private static final int TYPE_NORMAL = 1;

    private boolean loadingMore;
    private int mImageWidth;
    private int mImageHeigh;
    private ArrayList<ZhiHuDailyItem> zhiHuDailyItems = new ArrayList<ZhiHuDailyItem>();
    private Context context;

    public ZhiHuAdapter(Context context) {
        this.context = context;
        float width = context.getResources().getDimension(R.dimen.image_width);
        mImageWidth = DensityUtils.dip2px(context,width);
        mImageHeigh = mImageWidth * 3 / 4;

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        switch (viewType){
            case TYPE_NORMAL:
                return new ZhiHuViewHolder(LayoutInflater.from(context).inflate(R.layout.item_top_zhihu,parent,false));
            case TYPE_LOADING_MORE:
                return new ZhiHuViewHolder(LayoutInflater.from(context).inflate(R.layout.infinite_loading,parent,false));
        }

        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int itemViewType = getItemViewType(position);
        switch (itemViewType){
            case TYPE_NORMAL:
                bindViewHolderNormal((ZhiHuViewHolder) holder,position);
                break;
            case TYPE_LOADING_MORE:
                bindLoadingViewHold((LoadingMoreHolder) holder,position);
                break;
        }
    }

    private void bindLoadingViewHold(LoadingMoreHolder holder,int position){
        holder.progressBar.setVisibility(loadingMore == true ? View.VISIBLE : View.INVISIBLE);
    }

    private void bindViewHolderNormal(final ZhiHuViewHolder holder, final int position){
        final ZhiHuDailyItem zhiHuDailyItem = zhiHuDailyItems.get(holder.getAdapterPosition());

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startDescribeActivity(holder,zhiHuDailyItem);
            }
        });

        holder.textView.setText(zhiHuDailyItem.getTitle());

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startDescribeActivity(holder,zhiHuDailyItem);
            }
        });

        Glide.with(context)
                .load(zhiHuDailyItems.get(position).getImages()[0])
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
                })
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .centerCrop()
                .override(mImageWidth,mImageHeigh)
                .into(holder.imageView);


    }

    private void startDescribeActivity(ZhiHuViewHolder holder,ZhiHuDailyItem zhiHuDailyItem){
        /*Intent intent = new Intent(context, ZhiHuDescribeActivity.class);
        intent.putExtra("id",zhiHuDailyItem.getId());
        intent.putExtra("title",zhiHuDailyItem.getTitle());
        intent.putExtra("image",zhiHuDailyItem.getImages()[0]);
        context.startActivity(intent);*/
        Intent intent = WebActivity.newTntent(context,
                "http://daily.zhihu.com/story/"+zhiHuDailyItem.getId(),
                zhiHuDailyItem.getTitle());
        context.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        return zhiHuDailyItems.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (position < getDataItemCount()
                && getDataItemCount() > 0){
            return TYPE_NORMAL;
        }
        return TYPE_LOADING_MORE;
    }

    @Override
    public void loadingStart() {
        if (loadingMore)return;
        loadingMore = true;
        notifyItemInserted(getLoadingMoreItemPosition());
    }

    @Override
    public void loadingFinish() {
        if (!loadingMore)return;
        final int loadingMoreItemPosition = getLoadingMoreItemPosition();
        loadingMore = false;
        notifyItemRemoved(loadingMoreItemPosition);
    }

    private int getDataItemCount(){
        return zhiHuDailyItems.size();
    }

    private int getLoadingMoreItemPosition(){
        return loadingMore ? getItemCount() - 1 : RecyclerView.NO_POSITION;
    }

    public void addItems(ArrayList<ZhiHuDailyItem> list){
        zhiHuDailyItems.addAll(list);
        notifyDataSetChanged();
    }

    public void clearData(){
        zhiHuDailyItems.clear();
        notifyDataSetChanged();
    }

    private static class LoadingMoreHolder extends RecyclerView.ViewHolder{

        ProgressBar progressBar;

        public LoadingMoreHolder(View itemView) {
            super(itemView);
            progressBar = (ProgressBar) itemView;
        }
    }

    private static class ZhiHuViewHolder extends RecyclerView.ViewHolder{

        LinearLayout linearLayout;
        ImageView imageView;
        TextView textView;

        public ZhiHuViewHolder(View itemView) {
            super(itemView);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.zhihu_item_layout);
            imageView = (ImageView) itemView.findViewById(R.id.item_image_id);
            textView = (TextView) itemView.findViewById(R.id.item_text_id);
        }
    }
}
