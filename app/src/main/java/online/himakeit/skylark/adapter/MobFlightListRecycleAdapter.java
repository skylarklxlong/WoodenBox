package online.himakeit.skylark.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import online.himakeit.skylark.R;
import online.himakeit.skylark.callback.OnItemClickListener;
import online.himakeit.skylark.model.mob.MobFlightEntity;

/**
 * Created by：LiXueLong 李雪龙 on 2017/12/6 16:55
 * <p>
 * Mail : skylarklxlong@outlook.com
 * <p>
 * Description:
 */
public class MobFlightListRecycleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private ArrayList<MobFlightEntity> mDatas;
    private LayoutInflater layoutInflater;

    public MobFlightListRecycleAdapter(Context context, ArrayList<MobFlightEntity> mDatas) {
        this.context = context;
        this.mDatas = mDatas;
        layoutInflater = LayoutInflater.from(context);
    }

    public void updateDatas(ArrayList<MobFlightEntity> mDatas) {
        this.mDatas = mDatas;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = layoutInflater.inflate(R.layout.item_mob_flight_list, parent, false);
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

            MobFlightEntity mobFlightEntity = mDatas.get(position);
            viewHolder.tv_start_station.setText(mobFlightEntity.getFromCityName());
            viewHolder.tv_start_time.setText(mobFlightEntity.getPlanTime());
            viewHolder.tv_end_station.setText(mobFlightEntity.getToCityName());
            viewHolder.tv_end_time.setText(mobFlightEntity.getPlanArriveTime());
            viewHolder.tv_flightNo.setText(mobFlightEntity.getFlightNo());
            viewHolder.tv_lishi.setText(mobFlightEntity.getFlightTime());
            viewHolder.tv_air_lines.setText(mobFlightEntity.getAirLines());
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
        @Bind(R.id.tv_flightNo)
        TextView tv_flightNo;
        @Bind(R.id.tv_lishi)
        TextView tv_lishi;
        @Bind(R.id.tv_end_station)
        TextView tv_end_station;
        @Bind(R.id.tv_end_time)
        TextView tv_end_time;
        @Bind(R.id.tv_air_lines)
        TextView tv_air_lines;

        public MobTrainListViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
