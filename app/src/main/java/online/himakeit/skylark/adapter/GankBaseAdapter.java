package online.himakeit.skylark.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableStringBuilder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

import online.himakeit.skylark.MainActivity;
import online.himakeit.skylark.R;
import online.himakeit.skylark.activity.WebActivity;
import online.himakeit.skylark.model.gank.Gank;
import online.himakeit.skylark.util.StringStyles;

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

        SpannableStringBuilder builder = new SpannableStringBuilder(gank.getDesc())
                .append(StringStyles.format(holder.mTvDesc.getContext(),
                        " (via. " + (gank.getWho() != null ?
                                gank.getWho() : "这一定是一个很棒的人！") + ")",
                        R.style.ViaTextAppearance));

        CharSequence gankText = builder.subSequence(0, builder.length());

        holder.mTvDesc.setText(gankText);

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
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

        TextView mTvDesc;
        LinearLayout linearLayout;

        public GankBaseViewHolder(View itemView) {
            super(itemView);
            mTvDesc = (TextView) itemView.findViewById(R.id.tv_gank_desc);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.ll_gank_base);
        }
    }
}
