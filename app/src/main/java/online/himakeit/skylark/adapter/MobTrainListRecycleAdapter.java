package online.himakeit.skylark.adapter;

import android.content.Context;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import online.himakeit.skylark.R;
import online.himakeit.skylark.activity.mob.TrainListActivity;
import online.himakeit.skylark.callback.OnItemClickListener;
import online.himakeit.skylark.model.mob.MobTrainEntity;
import online.himakeit.skylark.model.mob.MobTrainNoEntity;

/**
 * Created by：LiXueLong 李雪龙 on 2017/12/6 16:55
 * <p>
 * Mail : skylarklxlong@outlook.com
 * <p>
 * Description:
 */
public class MobTrainListRecycleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private ArrayList<MobTrainEntity> mDatas;
    private LayoutInflater layoutInflater;

    public MobTrainListRecycleAdapter(Context context, ArrayList<MobTrainEntity> mDatas) {
        this.context = context;
        this.mDatas = mDatas;
        layoutInflater = LayoutInflater.from(context);
    }

    public void updateDatas(ArrayList<MobTrainEntity> mDatas) {
        this.mDatas = mDatas;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = layoutInflater.inflate(R.layout.item_mob_train_list, parent, false);
        return new MobTrainListViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof MobTrainListViewHolder) {
            MobTrainListViewHolder viewHolder = (MobTrainListViewHolder) holder;
            if (mOnItemClickLitener != null) {
                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mOnItemClickLitener.onItemClick(v, position);
                    }
                });
            }

            MobTrainEntity mobTrainEntity = mDatas.get(position);
            viewHolder.tv_start_station.setText(mobTrainEntity.getStartStationName());
            viewHolder.tv_start_time.setText(mobTrainEntity.getStartTime());
            viewHolder.tv_end_station.setText(mobTrainEntity.getEndStationName());
            viewHolder.tv_end_time.setText(mobTrainEntity.getArriveTime());
            viewHolder.tv_station_train_code.setText(mobTrainEntity.getStationTrainCode());
            viewHolder.tv_lishi.setText(mobTrainEntity.getLishi());

            //判断是高铁还是非高铁
            String priceContent = "";
            if (!TextUtils.isEmpty(mobTrainEntity.getPriceyd())) {//高铁
                //商务
                String pricesw = mobTrainEntity.getPricesw();
                if (!TextUtils.isEmpty(pricesw)) {
                    priceContent += "商务:" + pricesw + "         ";
                }
                //特等座
                String pricetd = mobTrainEntity.getPricetd();
                if (!TextUtils.isEmpty(pricetd)) {
                    priceContent += "特等:" + pricetd + "         ";
                }
                //一等
                String priceyd = mobTrainEntity.getPriceyd();
                if (!TextUtils.isEmpty(priceyd)) {
                    priceContent += "一等:" + priceyd + "         ";
                }
                //二等
                String priceed = mobTrainEntity.getPriceed();
                if (!TextUtils.isEmpty(priceed)) {
                    priceContent += "二等:" + priceed + "         ";
                }
                //无座
                String pricewz = mobTrainEntity.getPricewz();
                if (!TextUtils.isEmpty(pricewz)) {
                    priceContent += "无座:" + pricewz + "         ";
                }
            } else {
                //高级软卧票价
                String pricegrw = mobTrainEntity.getPricegrw();
                if (!TextUtils.isEmpty(pricegrw)) {
                    priceContent += "高软:" + pricegrw + "        ";
                }
                //软卧票价
                String pricerw = mobTrainEntity.getPricerw();
                if (!TextUtils.isEmpty(pricerw)) {
                    priceContent += "软卧:" + pricerw + "         ";
                }
                //硬卧票价
                String priceyw = mobTrainEntity.getPriceyw();
                if (!TextUtils.isEmpty(priceyw)) {
                    priceContent += "硬卧:" + priceyw + "         ";
                }
                //软座票价
                String pricerz = mobTrainEntity.getPricerz();
                if (!TextUtils.isEmpty(pricerz)) {
                    priceContent += "软座:" + pricerz + "         ";
                }
                //硬座票价
                String priceyz = mobTrainEntity.getPriceyz();
                if (!TextUtils.isEmpty(priceyz)) {
                    priceContent += "硬座:" + priceyz + "         ";
                }
                //无座票价
                String pricewz = mobTrainEntity.getPricewz();
                if (!TextUtils.isEmpty(pricewz)) {
                    priceContent += "无座:" + pricewz + "         ";
                }

            }
            viewHolder.tv_price.setText(priceContent);

            if (mobTrainEntity.isShowDetails()) {
                viewHolder.iv_arrow_down.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_icon_arrow_up));
                viewHolder.rl_mob_train_list_details.setVisibility(View.VISIBLE);
                viewHolder.recycler_train_list_detail.setVisibility(View.VISIBLE);
                ArrayList<MobTrainNoEntity> trainDetails = mobTrainEntity.getTrainDetails();
                if (trainDetails != null && trainDetails.size() > 0) {
                    viewHolder.recycler_train_list_detail.setVisibility(View.VISIBLE);
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
                    viewHolder.recycler_train_list_detail.setLayoutManager(linearLayoutManager);
                    viewHolder.recycler_train_list_detail.setItemAnimator(new DefaultItemAnimator());

                    MobTrainListDetailsRecycleAdapter mobTrainListDetailsRecycleAdapter = new MobTrainListDetailsRecycleAdapter(context, trainDetails);
                    viewHolder.recycler_train_list_detail.setAdapter(mobTrainListDetailsRecycleAdapter);
                } else {
                    viewHolder.recycler_train_list_detail.setVisibility(View.GONE);
                }
            } else {
                viewHolder.iv_arrow_down.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_icon_arrow_down));
                viewHolder.rl_mob_train_list_details.setVisibility(View.GONE);
                viewHolder.recycler_train_list_detail.setVisibility(View.GONE);
            }

            viewHolder.rl_mob_train_list_more.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((TrainListActivity) context).queryTrainNo(position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    private OnItemClickListener mOnItemClickLitener;

    public void setOnItemClickLitener(OnItemClickListener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }

    class MobTrainListViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.tv_start_station)
        TextView tv_start_station;
        @Bind(R.id.tv_start_time)
        TextView tv_start_time;
        @Bind(R.id.tv_station_train_code)
        TextView tv_station_train_code;
        @Bind(R.id.tv_lishi)
        TextView tv_lishi;
        @Bind(R.id.tv_end_station)
        TextView tv_end_station;
        @Bind(R.id.tv_end_time)
        TextView tv_end_time;
        @Bind(R.id.rl_mob_train_list_more)
        RelativeLayout rl_mob_train_list_more;
        @Bind(R.id.iv_arrow_down)
        ImageView iv_arrow_down;
        @Bind(R.id.tv_price)
        TextView tv_price;
        @Bind(R.id.rl_mob_train_list_details)
        RelativeLayout rl_mob_train_list_details;
        @Bind(R.id.recycler_train_list_detail)
        RecyclerView recycler_train_list_detail;

        public MobTrainListViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
