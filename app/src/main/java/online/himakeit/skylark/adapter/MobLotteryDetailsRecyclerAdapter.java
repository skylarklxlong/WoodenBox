package online.himakeit.skylark.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import online.himakeit.skylark.R;
import online.himakeit.skylark.model.mob.MobLotteryEntity;

/**
 * @author：LiXueLong
 * @date：2018/1/4
 * @mail1：skylarklxlong@outlook.com
 * @mail2：li_xuelong@126.com
 * @des:
 */
public class MobLotteryDetailsRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<MobLotteryEntity.MobLotteryDetailsBean> mDatas;
    private LayoutInflater layoutInflater;

    public MobLotteryDetailsRecyclerAdapter(Context context, List<MobLotteryEntity.MobLotteryDetailsBean> mDatas) {
        this.context = context;
        this.mDatas = mDatas;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = layoutInflater.inflate(R.layout.item_mob_lottery_details, parent, false);
        return new MyViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MyViewHolder) {
            MyViewHolder viewHolder = (MyViewHolder) holder;
            viewHolder.ll_header.setVisibility(View.GONE);
            if (position == 0) {
                viewHolder.ll_header.setVisibility(View.VISIBLE);
            }

            MobLotteryEntity.MobLotteryDetailsBean mobLotteryDetailsBean = mDatas.get(position);
            viewHolder.tv_awards.setText(mobLotteryDetailsBean.getAwards());
            if (TextUtils.isEmpty(mobLotteryDetailsBean.getType())) {
                viewHolder.tv_award_type.setText("基本");
            } else {
                viewHolder.tv_award_type.setText(mobLotteryDetailsBean.getType());
            }
            viewHolder.tv_award_num.setText(mobLotteryDetailsBean.getAwardNumber() + "");
            viewHolder.tv_award_price.setText(mobLotteryDetailsBean.getAwardPrice() + "");
        }
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.ll_header)
        LinearLayout ll_header;
        @Bind(R.id.tv_awards)
        TextView tv_awards;
        @Bind(R.id.tv_award_type)
        TextView tv_award_type;
        @Bind(R.id.tv_award_num)
        TextView tv_award_num;
        @Bind(R.id.tv_award_price)
        TextView tv_award_price;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
