package online.himakeit.skylark.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.ArrayList;

import online.himakeit.skylark.MainActivity;
import online.himakeit.skylark.R;
import online.himakeit.skylark.activity.GankMeiZhiAcitvity;
import online.himakeit.skylark.model.gank.GankMeiZhi;
import online.himakeit.skylark.util.DensityUtils;

/**
 * Created by：LiXueLong 李雪龙 on 2017/9/6 14:10
 * <p>
 * Mail : skylarklxlong@outlook.com
 * <p>
 * Description:
 */
public class GankMeiZhiAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements MainActivity.LoadingMore {

    private static final int TYPE_LOADING_MORE = -1;
    private static final int TYPE_NOMAL = 1;
    private boolean loadingMore;

    private int mImageWidth;
    private int mImageHeigh;

    ArrayList<GankMeiZhi> meiziItems = new ArrayList<GankMeiZhi>();
    Context context;

    public GankMeiZhiAdapter(Context context) {
        this.context = context;

        int[] deviceWidthHeight = DensityUtils.getDeviceInfo(context);
        mImageWidth = deviceWidthHeight[0];
        mImageHeigh = mImageWidth * 3 / 4;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType){
            case TYPE_NOMAL:
                return new GankMeiZhiViewHolder(
                        LayoutInflater.from(context).
                                inflate(R.layout.item_gank_meizhi,parent,false));
            case TYPE_LOADING_MORE:
                return new LoadingMoreHolder(
                        LayoutInflater.from(context).
                                inflate(R.layout.infinite_loading,parent,false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int type = getItemViewType(position);
        switch (type){
            case TYPE_NOMAL:
                bindViewHolderNormal((GankMeiZhiViewHolder) holder,position);
                break;
            case TYPE_LOADING_MORE:
                bindLoadingViewHold((LoadingMoreHolder) holder,position);
                break;
        }
    }

    private void bindLoadingViewHold(LoadingMoreHolder holder,int position){
        holder.progressBar.setVisibility(loadingMore ? View.VISIBLE : View.INVISIBLE);
    }

    private void bindViewHolderNormal(final GankMeiZhiViewHolder holder,final int position){
        final GankMeiZhi gankMeiZhi = meiziItems.get(holder.getAdapterPosition());
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startDescribeActivity(gankMeiZhi,holder);
            }
        });

        Glide.with(context)
                .load(gankMeiZhi.getUrl())
                .placeholder(R.drawable.nav_icon)
//                .placeholder(R.drawable.imageview_loading)
                .error(R.drawable.imageview_loading)
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        return false;
                    }
                })
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .centerCrop()
                .override(mImageWidth,mImageHeigh)
                .into(holder.imageView);
    }

    private void startDescribeActivity(GankMeiZhi gankMeiZhi,RecyclerView.ViewHolder holder){
        Intent intent = new Intent(context, GankMeiZhiAcitvity.class);

        int location[] = new int[2];
        ImageView imageView = ((GankMeiZhiViewHolder) holder).getBitmap();
        imageView.getLocationOnScreen(location);

        intent.putExtra("left",location[0]);
        intent.putExtra("top",location[1]);
        intent.putExtra("width",imageView.getWidth());
        intent.putExtra("height",imageView.getHeight());
        intent.putExtra("imageUrl",gankMeiZhi.getUrl());

        context.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        return meiziItems.size();
    }

    public void addItems(ArrayList<GankMeiZhi> list){
        meiziItems.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        if (position < getDataItemCount() && getDataItemCount() > 0){
            return TYPE_NOMAL;
        }
        return TYPE_LOADING_MORE;
    }

    private int getDataItemCount(){
        return meiziItems.size();
    }

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
        meiziItems.clear();
        notifyDataSetChanged();
    }

    public static class LoadingMoreHolder extends RecyclerView.ViewHolder{
        ProgressBar progressBar;
        public LoadingMoreHolder(View itemView) {
            super(itemView);
            progressBar = (ProgressBar) itemView;
        }
    }

    class GankMeiZhiViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        public GankMeiZhiViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.item_image_id);
        }
        public ImageView getBitmap(){
            return imageView;
        }
    }
}
