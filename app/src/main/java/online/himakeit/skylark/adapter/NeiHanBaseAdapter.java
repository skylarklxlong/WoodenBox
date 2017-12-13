package online.himakeit.skylark.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import online.himakeit.skylark.MainActivity;
import online.himakeit.skylark.R;
import online.himakeit.skylark.activity.WebActivity;
import online.himakeit.skylark.model.gank.Gank;
import online.himakeit.skylark.model.neihan.NeiHanDataDataEntity;

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

        holder.mTvDesc.setText(dataEntity.getGroup().getContent());
        holder.mTvTime.setText(dataEntity.getGroup().getCreate_time()+"");
        holder.mTvWho.setText(dataEntity.getGroup().getUser().getName());

        /*//图片展示
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
                    .load(imageUrl)
                    .placeholder(R.drawable.nav_icon)
                    .error(R.drawable.imageview_loading)
                    .into(holder.mIvShow);
        }
*/
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startDescribeActivity(dataEntity, holder);
            }
        });

    }

    private void startDescribeActivity(Gank gank, RecyclerView.ViewHolder holder) {
        Intent intent = WebActivity.newTntent(mContext, gank.getUrl(), gank.getDesc());
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

        public NeiHanBaseViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
