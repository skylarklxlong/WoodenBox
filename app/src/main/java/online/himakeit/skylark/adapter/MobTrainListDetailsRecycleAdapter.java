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
import online.himakeit.skylark.model.mob.MobTrainNoEntity;

/**
 * Created by：LiXueLong 李雪龙 on 2017/12/7 8:26
 * <p>
 * Mail : skylarklxlong@outlook.com
 * <p>
 * Description:
 */
public class MobTrainListDetailsRecycleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private ArrayList<MobTrainNoEntity> mDatas;
    private LayoutInflater layoutInflater;

    public MobTrainListDetailsRecycleAdapter(Context context, ArrayList<MobTrainNoEntity> mDatas) {
        this.context = context;
        this.mDatas = mDatas;
        layoutInflater = LayoutInflater.from(context);
    }

    public void updateDatas(ArrayList<MobTrainNoEntity> mDatas) {
        this.mDatas = mDatas;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = layoutInflater.inflate(R.layout.item_mob_train_list_details, parent, false);
        return new MobTrainListDetailsViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MobTrainListDetailsViewHolder) {
            MobTrainListDetailsViewHolder viewHolder = (MobTrainListDetailsViewHolder) holder;

            if (position == 0) {
                viewHolder.tv_train_01.setTextColor(context.getResources().getColor(R.color.colorPrimary));
                viewHolder.tv_train_02.setTextColor(context.getResources().getColor(R.color.colorPrimary));
                viewHolder.tv_train_03.setTextColor(context.getResources().getColor(R.color.colorPrimary));
                viewHolder.tv_train_04.setTextColor(context.getResources().getColor(R.color.colorPrimary));
                viewHolder.tv_train_05.setTextColor(context.getResources().getColor(R.color.colorPrimary));
                viewHolder.tv_train_01.setText("站序");
                viewHolder.tv_train_02.setText("站名");
                viewHolder.tv_train_03.setText("到时");
                viewHolder.tv_train_04.setText("发时");
                viewHolder.tv_train_05.setText("停留");
            } else {
                MobTrainNoEntity mobTrainNoEntity = mDatas.get(position - 1);
                viewHolder.tv_train_01.setTextColor(context.getResources().getColor(R.color.black_text2_color));
                viewHolder.tv_train_02.setTextColor(context.getResources().getColor(R.color.black_text2_color));
                viewHolder.tv_train_03.setTextColor(context.getResources().getColor(R.color.black_text2_color));
                viewHolder.tv_train_04.setTextColor(context.getResources().getColor(R.color.black_text2_color));
                viewHolder.tv_train_05.setTextColor(context.getResources().getColor(R.color.black_text2_color));
                viewHolder.tv_train_01.setText(mobTrainNoEntity.getStationNo());
                viewHolder.tv_train_02.setText(mobTrainNoEntity.getStationName());
                viewHolder.tv_train_03.setText(mobTrainNoEntity.getArriveTime());
                viewHolder.tv_train_04.setText(mobTrainNoEntity.getStartTime());
                viewHolder.tv_train_05.setText(mobTrainNoEntity.getStopoverTime());
            }
        }
    }

    @Override
    public int getItemCount() {
        return mDatas.size() + 1;
    }

    class MobTrainListDetailsViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.tv_train_01)
        TextView tv_train_01;
        @Bind(R.id.tv_train_02)
        TextView tv_train_02;
        @Bind(R.id.tv_train_03)
        TextView tv_train_03;
        @Bind(R.id.tv_train_04)
        TextView tv_train_04;
        @Bind(R.id.tv_train_05)
        TextView tv_train_05;

        public MobTrainListDetailsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
