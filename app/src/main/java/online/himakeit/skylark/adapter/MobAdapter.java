package online.himakeit.skylark.adapter;

import android.content.Context;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import online.himakeit.skylark.R;
import online.himakeit.skylark.listeners.OnItemClickListener;

/**
 * Created by：LiXueLong 李雪龙 on 2017/10/17 19:59
 * <p>
 * Mail : skylarklxlong@outlook.com
 * <p>
 * Description:
 */
public class MobAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context mContext;
    List<String> mDatas;
    LayoutInflater mLayoutInflater;

    public MobAdapter(Context mContext, List<String> mDatas) {
        this.mContext = mContext;
        this.mDatas = mDatas;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.item_mob, parent, false);
        return new MobViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        /**
         * instanceof 运算符是用来在运行时指出对象是否是特定类的一个实例。
         */
        if (holder instanceof MobViewHolder) {
            final MobViewHolder mobViewHolder = (MobViewHolder) holder;
            mobViewHolder.mTvTitle.setText(mDatas.get(position));

            GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 3);
            mobViewHolder.recyclerViewItem.setLayoutManager(gridLayoutManager);
            mobViewHolder.recyclerViewItem.setItemAnimator(new DefaultItemAnimator());

            ArrayList<String> mDatasItem = new ArrayList<>();
            if (position == 0) {
                mDatasItem.add("手机号码归属地");
                mDatasItem.add("邮编查询");
                mDatasItem.add("菜谱查询");
                mDatasItem.add("身份证查询");
                mDatasItem.add("IP地址");
                mDatasItem.add("中国彩票开奖结果");
                mDatasItem.add("微信精选");
            } else if (position == 1) {
                mDatasItem.add("银行卡信息");
                mDatasItem.add("货币汇率");
                mDatasItem.add("黄金数据");
                mDatasItem.add("白银数据");
                mDatasItem.add("国内现货交易所贵金属");
                mDatasItem.add("全球股指查询");
            } else if (position == 2) {
                mDatasItem.add("周公解梦");
                mDatasItem.add("婚姻匹配");
                mDatasItem.add("手机号码查吉凶");
                mDatasItem.add("八字算命");
                mDatasItem.add("老黄历");
                mDatasItem.add("电影票房");
                mDatasItem.add("足球五大联赛");
                mDatasItem.add("火车票查询");
                mDatasItem.add("航班信息查询");
            } else if (position == 3) {
                mDatasItem.add("健康知识");
                mDatasItem.add("历史上的今天");
                mDatasItem.add("成语大全");
                mDatasItem.add("新华字典");
                mDatasItem.add("全国省市今日油价");
                mDatasItem.add("汽车信息查询");
                mDatasItem.add("驾考题库");
            }

            final List<String> mDatasTitle = mDatasItem;
            MobItemAdapter mobItemAdapter = new MobItemAdapter(mContext, mDatasItem);
            mobViewHolder.recyclerViewItem.setAdapter(mobItemAdapter);
            mobItemAdapter.setmOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    String title = mDatasTitle.get(position);
                    if (title.equals("手机号码归属地")) {
                    } else if (title.equals("邮编查询")) {
                    } else if (title.equals("菜谱查询")) {
                    } else if (title.equals("身份证查询")) {
                    } else if (title.equals("IP地址")) {
                    } else if (title.equals("中国彩票开奖结果")) {
                    } else if (title.equals("微信精选")) {
//-------------------------------------------------------------------
                    } else if (title.equals("银行卡信息")) {
                    } else if (title.equals("白银数据")) {
                    } else if (title.equals("黄金数据")) {
                    } else if (title.equals("货币汇率")) {
                    } else if (title.equals("国内现货交易所贵金属")) {
                    } else if (title.equals("全球股指查询")) {
//-------------------------------------------------------------------
                    } else if (title.equals("周公解梦")) {
                    } else if (title.equals("婚姻匹配")) {
                    } else if (title.equals("手机号码查吉凶")) {
                    } else if (title.equals("八字算命")) {
                    } else if (title.equals("老黄历")) {
                    } else if (title.equals("电影票房")) {
                    } else if (title.equals("火车票查询")) {
                    } else if (title.equals("航班信息查询")) {
                    } else if (title.equals("足球五大联赛")) {

//-------------------------------------------------------------------
                    } else if (title.equals("健康知识")) {
                    } else if (title.equals("历史上的今天")) {
                    } else if (title.equals("成语大全")) {
                    } else if (title.equals("新华字典")) {
                    } else if (title.equals("全国省市今日油价")) {
                    } else if (title.equals("汽车信息查询")) {
                    } else if (title.equals("驾考题库")) {
                    }
                }
            });

        }
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public static class MobViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tv_mob_item_title)
        TextView mTvTitle;
        @Bind(R.id.recycler_view_mob_item)
        RecyclerView recyclerViewItem;

        public MobViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
