package online.himakeit.skylark.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import online.himakeit.skylark.MainActivity;
import online.himakeit.skylark.R;
import online.himakeit.skylark.activity.WebActivity;
import online.himakeit.skylark.model.neihan.NeiHanDataDataEntity;
import online.himakeit.skylark.model.neihan.NeiHanDataDataGroupEntity;
import online.himakeit.skylark.util.DateUtils;

/**
 * Created by：LiXueLong 李雪龙 on 2017/9/13 19:08
 * <p>
 * Mail : skylarklxlong@outlook.com
 * <p>
 * Description:
 */
public class NeiHanBaseAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements MainActivity.LoadingMore {

    private static final int TYPE_LOADING_MORE = -1;
    private static final int TYPE_NOMAL = 1;
    private boolean loadingMore;

    ArrayList<NeiHanDataDataEntity> neiHanArrayList = new ArrayList<NeiHanDataDataEntity>();
    Context mContext;

    public NeiHanBaseAdapter(Context context) {
        mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_NOMAL:
                return new NeiHanBaseViewHolder(
                        LayoutInflater.from(mContext).
                                inflate(R.layout.item_neihan_base, parent, false));
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
                bindViewHolderNomal((NeiHanBaseViewHolder) holder, position);
                break;
            case TYPE_LOADING_MORE:
                bindLoadingViewHolder((LoadingMoreHolder) holder, position);
                break;
        }
    }

    private void bindLoadingViewHolder(LoadingMoreHolder holder, int position) {
        holder.progressBar.setVisibility(loadingMore ? View.VISIBLE : View.INVISIBLE);
    }

    private void bindViewHolderNomal(final NeiHanBaseViewHolder holder, int position) {
        final NeiHanDataDataEntity dataEntity = neiHanArrayList.get(holder.getAdapterPosition());

        String content = dataEntity.getGroup().getContent();
        if (content != null && !content.equals("")) {
            holder.mTvDesc.setText(dataEntity.getGroup().getContent());
        } else {
            holder.mTvDesc.setVisibility(View.GONE);
        }
        holder.mTvCate.setText("#" + dataEntity.getGroup().getCategory_name() + "#");
        holder.mTvTime.setText(DateUtils.Millis2Date(dataEntity.getGroup().getCreate_time()) + "");
        holder.mTvWho.setText(dataEntity.getGroup().getUser().getName());

        Glide.with(mContext)
                .load(dataEntity.getGroup().getUser().getAvatar_url())
                .asBitmap()
                .placeholder(R.drawable.pic_gray_bg)
                .into(holder.mIvUser);

        //普通图片
        NeiHanDataDataGroupEntity.Mimage middle_image = dataEntity.getGroup().getMiddle_image();
        if (middle_image != null) {
            String middle_imageUri = middle_image.getUri();
            if (!TextUtils.isEmpty(middle_imageUri)) {
                holder.mIvShow.setVisibility(View.VISIBLE);
                Glide.with(mContext)
                        .load(middle_image.getUrl() + middle_imageUri + middle_image.getSuffix())
                        .asBitmap()
                        .placeholder(R.drawable.pic_gray_bg)
                        .into(holder.mIvShow);
            }
        }
        //动图
        NeiHanDataDataGroupEntity.GifVideo gifvideo = dataEntity.getGroup().getGifvideo();
        if (gifvideo != null) {
            String mp4_url = gifvideo.getMp4_url();
            if (!TextUtils.isEmpty(mp4_url)) {
                // TODO: 2017/12/14 表明是动图
            }
        }

        //视频
        NeiHanDataDataGroupEntity.MCover medium_cover = dataEntity.getGroup().getMedium_cover();
        if (medium_cover != null) {
            String medium_coverUri = medium_cover.getUri();
            if (!TextUtils.isEmpty(medium_coverUri)) {
                holder.mIvShow.setVisibility(View.VISIBLE);
                Glide.with(mContext)
                        .load(medium_cover.getUrl() + medium_coverUri + medium_cover.getSuffix())
                        .asBitmap()
                        .placeholder(R.drawable.pic_gray_bg)
                        .into(holder.mIvShow);
            }
        }


        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startDescribeActivity(dataEntity, holder);
            }
        });

    }

    private void startDescribeActivity(NeiHanDataDataEntity dataEntity, RecyclerView.ViewHolder holder) {
        Intent intent = WebActivity.newTntent(mContext, dataEntity.getGroup().getShare_url(), dataEntity.getGroup().getContent());
        mContext.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        return neiHanArrayList.size();
    }

    public void addItems(ArrayList<NeiHanDataDataEntity> list) {
        neiHanArrayList.addAll(list);
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
        return neiHanArrayList.size();
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
        neiHanArrayList.clear();
        notifyDataSetChanged();
    }

    public static class LoadingMoreHolder extends RecyclerView.ViewHolder {
        ProgressBar progressBar;

        public LoadingMoreHolder(View itemView) {
            super(itemView);
            progressBar = (ProgressBar) itemView;
        }
    }

    class NeiHanBaseViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.tv_neihan_desc)
        TextView mTvDesc;
        @Bind(R.id.tv_neihan_category)
        TextView mTvCate;
        @Bind(R.id.tv_neihan_who)
        TextView mTvWho;
        @Bind(R.id.iv_neihan_user)
        ImageView mIvUser;
        @Bind(R.id.tv_neihan_time)
        TextView mTvTime;
        @Bind(R.id.iv_neihan_show)
        ImageView mIvShow;
        @Bind(R.id.card)
        CardView cardView;

        public NeiHanBaseViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
