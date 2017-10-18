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
import online.himakeit.skylark.listeners.OnItemClickListener;

/**
 * Created by：LiXueLong 李雪龙 on 2017/10/18 8:03
 * <p>
 * Mail : skylarklxlong@outlook.com
 * <p>
 * Description:
 */
public class MobItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context mContext;
    List<String> mDatas;
    LayoutInflater mLayoutInflater;

    public MobItemAdapter(Context mContext, List<String> mDatas) {
        this.mContext = mContext;
        this.mDatas = mDatas;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = mLayoutInflater.inflate(R.layout.item_mob_item, parent, false);
        return new MobItemViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MobItemViewHolder) {
            MobItemViewHolder mobItemViewHolder = (MobItemViewHolder) holder;

            String title = mDatas.get(position);
            mobItemViewHolder.mTvItemTitle.setText(title);

            if (title.equals("手机号码归属地")) {
                mobItemViewHolder.mIvItem.setImageResource(R.drawable.ic_icon_tools_phone_address);
            } else if (title.equals("邮编查询")) {
                mobItemViewHolder.mIvItem.setImageResource(R.drawable.ic_icon_tools_postcode);
            } else if (title.equals("菜谱查询")) {
                mobItemViewHolder.mIvItem.setImageResource(R.drawable.ic_icon_tools_cookbook);
            } else if (title.equals("身份证查询")) {
                mobItemViewHolder.mIvItem.setImageResource(R.drawable.ic_icon_tools_idcard_query);
            } else if (title.equals("IP地址")) {
                mobItemViewHolder.mIvItem.setImageResource(R.drawable.ic_icon_tools_ip);
            } else if (title.equals("中国彩票开奖结果")) {
                mobItemViewHolder.mIvItem.setImageResource(R.drawable.ic_icon_tools_lottery);
            } else if (title.equals("微信精选")) {
                mobItemViewHolder.mIvItem.setImageResource(R.drawable.ic_icon_tools_weixin);
//-------------------------------------------------------------------
            } else if (title.equals("银行卡信息")) {
                mobItemViewHolder.mIvItem.setImageResource(R.drawable.ic_icon_tools_bank);
            } else if (title.equals("白银数据")) {
                mobItemViewHolder.mIvItem.setImageResource(R.drawable.ic_icon_tools_baiyin);
            } else if (title.equals("黄金数据")) {
                mobItemViewHolder.mIvItem.setImageResource(R.drawable.ic_icon_tools_gold);
            } else if (title.equals("货币汇率")) {
                mobItemViewHolder.mIvItem.setImageResource(R.drawable.ic_icon_tools_money);
            } else if (title.equals("国内现货交易所贵金属")) {
                mobItemViewHolder.mIvItem.setImageResource(R.drawable.ic_icon_tools_guijinshu);
            } else if (title.equals("全球股指查询")) {
                mobItemViewHolder.mIvItem.setImageResource(R.drawable.ic_icon_tools_guzhi);
//-------------------------------------------------------------------
            } else if (title.equals("周公解梦")) {
                mobItemViewHolder.mIvItem.setImageResource(R.drawable.ic_icon_tools_zhougong);
            } else if (title.equals("婚姻匹配")) {
                mobItemViewHolder.mIvItem.setImageResource(R.drawable.ic_icon_tools_hunyin);
            } else if (title.equals("手机号码查吉凶")) {
                mobItemViewHolder.mIvItem.setImageResource(R.drawable.ic_icon_tools_jixiong);
            } else if (title.equals("八字算命")) {
                mobItemViewHolder.mIvItem.setImageResource(R.drawable.ic_icon_tools_suanming);
            } else if (title.equals("老黄历")) {
                mobItemViewHolder.mIvItem.setImageResource(R.drawable.ic_icon_tools_huangli);
            } else if (title.equals("电影票房")) {
                mobItemViewHolder.mIvItem.setImageResource(R.drawable.ic_icon_tools_movie);
            } else if (title.equals("火车票查询")) {
                mobItemViewHolder.mIvItem.setImageResource(R.drawable.ic_icon_tools_train);
            } else if (title.equals("航班信息查询")) {
                mobItemViewHolder.mIvItem.setImageResource(R.drawable.ic_icon_tools_plane);
            } else if (title.equals("足球五大联赛")) {
                mobItemViewHolder.mIvItem.setImageResource(R.drawable.ic_icon_tools_football);

//-------------------------------------------------------------------
            } else if (title.equals("健康知识")) {
                mobItemViewHolder.mIvItem.setImageResource(R.drawable.ic_icon_tools_jiankang);
            } else if (title.equals("历史上的今天")) {
                mobItemViewHolder.mIvItem.setImageResource(R.drawable.ic_icon_tools_history);
            } else if (title.equals("成语大全")) {
                mobItemViewHolder.mIvItem.setImageResource(R.drawable.ic_icon_tools_chengyu);
            } else if (title.equals("新华字典")) {
                mobItemViewHolder.mIvItem.setImageResource(R.drawable.ic_icon_tools_zidian);
            } else if (title.equals("全国省市今日油价")) {
                mobItemViewHolder.mIvItem.setImageResource(R.drawable.ic_icon_tools_youjia);
            } else if (title.equals("汽车信息查询")) {
                mobItemViewHolder.mIvItem.setImageResource(R.drawable.ic_icon_tools_car);
            } else if (title.equals("驾考题库")) {
                mobItemViewHolder.mIvItem.setImageResource(R.drawable.ic_icon_tools_tiku_car);
            }
        }
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public static class MobItemViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.iv_item_mob_item)
        ImageView mIvItem;
        @Bind(R.id.tv_item_mob_item_title)
        TextView mTvItemTitle;

        public MobItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    private OnItemClickListener mOnItemClickListener;

    public void setmOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }
}
