package online.himakeit.skylark.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import online.himakeit.skylark.R;
import online.himakeit.skylark.callback.OnItemClickListener;

/**
 * @author：LiXueLong
 * @date:2018/1/3
 * @mail1:skylarklxlong@outlook.com
 * @mail2:li_xuelong@126.com
 * @des:
 */
public class MobLotteryCategoryRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<String> mDatas;
    private LayoutInflater layoutInflater;

    public MobLotteryCategoryRecyclerAdapter(Context context, List<String> mDatas) {
        this.context = context;
        this.mDatas = mDatas;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = layoutInflater.inflate(R.layout.item_mob_lottery_category, parent, false);
        return new MyViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof MyViewHolder) {
            MyViewHolder viewHolder = (MyViewHolder) holder;
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnItemClickLitener != null) {
                        mOnItemClickLitener.onItemClick(v, position);
                    }
                }
            });

            /**
             * "双色球","大乐透","3D","排列3","排列5","七星彩","七乐彩","胜负彩","任选九","六场半全场","四场进球"
             */
            String name = mDatas.get(position);
            viewHolder.tv_name.setText(name);
            if ("双色球".equals(name)) {
                viewHolder.iv_pic.setBackgroundResource(R.drawable.gank_icon_lottery_ssq);
            } else if ("大乐透".equals(name)) {
                viewHolder.iv_pic.setBackgroundResource(R.drawable.gank_icon_lottery_dlt);
            } else if ("3D".equals(name)) {
                viewHolder.iv_pic.setBackgroundResource(R.drawable.gank_icon_lottery_welfare3d);
            } else if ("排列3".equals(name)) {
                viewHolder.iv_pic.setBackgroundResource(R.drawable.gank_icon_lottery_pl3);
            } else if ("排列5".equals(name)) {
                viewHolder.iv_pic.setBackgroundResource(R.drawable.gank_icon_lottery_pl5);
            } else if ("七星彩".equals(name)) {
                viewHolder.iv_pic.setBackgroundResource(R.drawable.gank_icon_lottery_sevenstar);
            } else if ("七乐彩".equals(name)) {
                viewHolder.iv_pic.setBackgroundResource(R.drawable.gank_icon_lottery_sevenle);
            } else if ("胜负彩".equals(name)) {
                viewHolder.iv_pic.setBackgroundResource(R.drawable.gank_icon_lottery_sfzc14);
            } else if ("任选九".equals(name)) {
                viewHolder.iv_pic.setBackgroundResource(R.drawable.gank_icon_lottery_sfzc9);
            } else if ("六场半全场".equals(name)) {
                viewHolder.iv_pic.setBackgroundResource(R.drawable.gank_icon_lottery_6);
            } else if ("四场进球".equals(name)) {
                viewHolder.iv_pic.setBackgroundResource(R.drawable.gank_icon_lottery_4);
            }
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

    class MyViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.iv_pic)
        ImageView iv_pic;
        @Bind(R.id.tv_name)
        TextView tv_name;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
