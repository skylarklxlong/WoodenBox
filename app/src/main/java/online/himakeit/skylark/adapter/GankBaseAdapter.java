package online.himakeit.skylark.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import online.himakeit.skylark.MainActivity;
import online.himakeit.skylark.R;
import online.himakeit.skylark.activity.WebActivity;
import online.himakeit.skylark.model.gank.Gank;

/**
 * Created by：LiXueLong 李雪龙 on 2017/9/13 19:08
 * <p>
 * Mail : skylarklxlong@outlook.com
 * <p>
 * Description:
 */
public class GankBaseAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements MainActivity.LoadingMore {

    private static final int TYPE_LOADING_MORE = -1;
    private static final int TYPE_NOMAL = 1;
    private boolean loadingMore;

    ArrayList<Gank> gankArrayList = new ArrayList<Gank>();
    Context mContext;

    public GankBaseAdapter(Context context) {
        mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_NOMAL:
                return new GankBaseViewHolder(
                        LayoutInflater.from(mContext).
                                inflate(R.layout.item_gank_base, parent, false));
            case TYPE_LOADING_MORE:
                return new LoadingMoreHolder(
                        LayoutInflater.from(mContext).
                                inflate(R.layout.infinite_loading, parent, false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case TYPE_NOMAL:
                bindViewHolderNomal((GankBaseViewHolder) holder, position);
                break;
            case TYPE_LOADING_MORE:
                bindLoadingViewHolder((LoadingMoreHolder) holder, position);
                break;
        }
    }

    private void bindLoadingViewHolder(LoadingMoreHolder holder, int position) {
        holder.progressBar.setVisibility(loadingMore ? View.VISIBLE : View.INVISIBLE);
    }

    private void bindViewHolderNomal(final GankBaseViewHolder holder, int position) {
        final Gank gank = gankArrayList.get(holder.getAdapterPosition());

        /*SpannableStringBuilder builder = new SpannableStringBuilder(gank.getDesc())
                .append(StringStyles.format(holder.mTvDesc.getContext(),
                        " (via. " + (gank.getWho() != null ?
                                gank.getWho() : "这一定是一个很棒的人！") + ")",
                        R.style.ViaTextAppearance));

        CharSequence gankText = builder.subSequence(0, builder.length());*/

        holder.mTvDesc.setText(gank.getDesc());
        holder.mTvTime.setText(gank.getPublicAt().split("T")[0]);
        holder.mTvWho.setText(gank.getWho());

        //图片展示
        String imageUrl = "";
        List<String> images = gank.getImages();
        if (images != null && images.size() > 0) {
            imageUrl = images.get(0);
        }
        if (TextUtils.isEmpty(imageUrl)) {
            holder.mIvShow.setVisibility(View.GONE);
        } else {
            holder.mIvShow.setVisibility(View.VISIBLE);
            Glide.with(mContext)
                    .load(imageUrl + "?imageView2/0/w/100")
                    .placeholder(R.drawable.pic_gray_bg)
                    .error(R.drawable.imageview_loading)
                    .into(holder.mIvShow);
        }

        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startDescribeActivity(gank, holder);
            }
        });

    }

    private void startDescribeActivity(Gank gank, RecyclerView.ViewHolder holder) {
        Intent intent = WebActivity.newTntent(mContext, gank.getUrl(), gank.getDesc());
        mContext.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        return gankArrayList.size();
    }

    public void addItems(ArrayList<Gank> list) {
        gankArrayList.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        if (position < getDataItemCount() && getDataItemCount() > 0) {
            return TYPE_NOMAL;
        }
        return TYPE_LOADING_MORE;
    }

    private int getDataItemCount() {
        return gankArrayList.size();
    }

    private int getLoadingMoreItemPosition() {
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

    public void clearData() {
        gankArrayList.clear();
        notifyDataSetChanged();
    }

    public static class LoadingMoreHolder extends RecyclerView.ViewHolder {
        ProgressBar progressBar;

        public LoadingMoreHolder(View itemView) {
            super(itemView);
            progressBar = (ProgressBar) itemView;
        }
    }

    class GankBaseViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.tv_gank_desc)
        TextView mTvDesc;
        @Bind(R.id.tv_gank_who)
        TextView mTvWho;
        @Bind(R.id.tv_gank_time)
        TextView mTvTime;
        @Bind(R.id.iv_gank_show)
        ImageView mIvShow;
        @Bind(R.id.rl_gank_base)
        RelativeLayout relativeLayout;

        public GankBaseViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
