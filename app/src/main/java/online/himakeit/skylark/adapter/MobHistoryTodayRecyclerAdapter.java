package online.himakeit.skylark.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ms.square.android.expandabletextview.ExpandableTextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;
import online.himakeit.skylark.R;
import online.himakeit.skylark.model.mob.MobHistoryTodayEntity;

/**
 * Created by：LiXueLong 李雪龙 on 2017/11/25 15:17
 * <p>
 * Mail : skylarklxlong@outlook.com
 * <p>
 * Description:
 */
public class MobHistoryTodayRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private ArrayList<MobHistoryTodayEntity> mDatas;
    private LayoutInflater layoutInflater;

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
    private SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy年MM月dd日");

    public MobHistoryTodayRecyclerAdapter(Context context, ArrayList<MobHistoryTodayEntity> mDatas) {
        this.context = context;
        this.mDatas = mDatas;
        layoutInflater = LayoutInflater.from(this.context);
    }

    public void upddateDatas(ArrayList<MobHistoryTodayEntity> mDatas) {
        this.mDatas = mDatas;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = layoutInflater.inflate(R.layout.item_mob_historytoday, parent, false);

        return new MyViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MyViewHolder) {
            MyViewHolder myViewHolder = (MyViewHolder) holder;

            MobHistoryTodayEntity mobHistoryTodayEntity = mDatas.get(position);

            myViewHolder.tv_title.setText(mobHistoryTodayEntity.getTitle());

            String date = mobHistoryTodayEntity.getDate();
            try {
                Date parse = sdf.parse(date);
                date = sdf2.format(parse);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            myViewHolder.tv_time.setText(date);

            SparseBooleanArray mTogglePositions = new SparseBooleanArray();
            myViewHolder.expand_text_view.setText(mobHistoryTodayEntity.getEvent(), mTogglePositions, position);
        }
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.tv_title)
        TextView tv_title;
        @Bind(R.id.tv_time)
        TextView tv_time;
        @Bind(R.id.expand_text_view)
        ExpandableTextView expand_text_view;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
