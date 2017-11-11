package online.himakeit.skylark.adapter;

import android.content.Context;
import android.content.Intent;
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
import online.himakeit.skylark.activity.WebActivity;
import online.himakeit.skylark.activity.mob.BankCardActivity;
import online.himakeit.skylark.activity.mob.CarListActivity;
import online.himakeit.skylark.activity.mob.ChineseCalendarActivity;
import online.himakeit.skylark.activity.mob.CookMenuActivity;
import online.himakeit.skylark.activity.mob.CurrencyRateActivity;
import online.himakeit.skylark.activity.mob.DictionaryActivity;
import online.himakeit.skylark.activity.mob.DrivingExamActivity;
import online.himakeit.skylark.activity.mob.FlightActivity;
import online.himakeit.skylark.activity.mob.FootballLeagueActivity;
import online.himakeit.skylark.activity.mob.GlobalStockActivity;
import online.himakeit.skylark.activity.mob.GoldDataActivity;
import online.himakeit.skylark.activity.mob.HealthActivity;
import online.himakeit.skylark.activity.mob.HistoryTodayActivity;
import online.himakeit.skylark.activity.mob.IDCardQueryActivity;
import online.himakeit.skylark.activity.mob.IPQueryActivity;
import online.himakeit.skylark.activity.mob.IdiomActivity;
import online.himakeit.skylark.activity.mob.LotteryCategoryActivity;
import online.himakeit.skylark.activity.mob.OilPriceActivity;
import online.himakeit.skylark.activity.mob.PhoneAddressActivity;
import online.himakeit.skylark.activity.mob.PiaoFangVipActivity;
import online.himakeit.skylark.activity.mob.PostCodeActivity;
import online.himakeit.skylark.activity.mob.PreciousMetalActivity;
import online.himakeit.skylark.activity.mob.SilverDataActivity;
import online.himakeit.skylark.activity.mob.TrainActivity;
import online.himakeit.skylark.activity.mob.WXArticleActivity;
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
                        mContext.startActivity(new Intent(mContext, PhoneAddressActivity.class));
                    } else if (title.equals("邮编查询")) {
                        mContext.startActivity(new Intent(mContext, PostCodeActivity.class));
                    } else if (title.equals("菜谱查询")) {
                        mContext.startActivity(new Intent(mContext, CookMenuActivity.class));
                    } else if (title.equals("身份证查询")) {
                        mContext.startActivity(new Intent(mContext, IDCardQueryActivity.class));
                    } else if (title.equals("IP地址")) {
                        mContext.startActivity(new Intent(mContext, IPQueryActivity.class));
                    } else if (title.equals("中国彩票开奖结果")) {
                        mContext.startActivity(new Intent(mContext, LotteryCategoryActivity.class));
                    } else if (title.equals("微信精选")) {
                        mContext.startActivity(new Intent(mContext, WXArticleActivity.class));
//-------------------------------------------------------------------
                    } else if (title.equals("银行卡信息")) {
                        mContext.startActivity(new Intent(mContext, BankCardActivity.class));
                    } else if (title.equals("白银数据")) {
                        mContext.startActivity(new Intent(mContext, SilverDataActivity.class));
                    } else if (title.equals("黄金数据")) {
                        mContext.startActivity(new Intent(mContext, GoldDataActivity.class));
                    } else if (title.equals("货币汇率")) {
                        mContext.startActivity(new Intent(mContext, CurrencyRateActivity.class));
                    } else if (title.equals("国内现货交易所贵金属")) {
                        mContext.startActivity(new Intent(mContext, PreciousMetalActivity.class));
                    } else if (title.equals("全球股指查询")) {
                        mContext.startActivity(new Intent(mContext, GlobalStockActivity.class));
//-------------------------------------------------------------------
                    } else if (title.equals("周公解梦")) {
                        mContext.startActivity(WebActivity.newTntent(mContext,"http://tools.2345.com/zhgjm.htm","周公解梦"));
                    } else if (title.equals("婚姻匹配")) {
                        mContext.startActivity(WebActivity.newTntent(mContext,"http://www.jjdzc.com/peidui/hehun.html","婚姻匹配"));
                    } else if (title.equals("手机号码查吉凶")) {
                    } else if (title.equals("八字算命")) {
                        mContext.startActivity(WebActivity.newTntent(mContext,"http://www.jjdzc.com/sm/bz.html","八字算命"));
                    } else if (title.equals("老黄历")) {
                        mContext.startActivity(new Intent(mContext, ChineseCalendarActivity.class));
                    } else if (title.equals("电影票房")) {
                        mContext.startActivity(new Intent(mContext, PiaoFangVipActivity.class));
                    } else if (title.equals("火车票查询")) {
                        mContext.startActivity(new Intent(mContext, TrainActivity.class));
                    } else if (title.equals("航班信息查询")) {
                        mContext.startActivity(new Intent(mContext, FlightActivity.class));
                    } else if (title.equals("足球五大联赛")) {
                        mContext.startActivity(new Intent(mContext, FootballLeagueActivity.class));
//-------------------------------------------------------------------
                    } else if (title.equals("健康知识")) {
                        mContext.startActivity(new Intent(mContext, HealthActivity.class));
                    } else if (title.equals("历史上的今天")) {
                        mContext.startActivity(new Intent(mContext, HistoryTodayActivity.class));
                    } else if (title.equals("成语大全")) {
                        mContext.startActivity(new Intent(mContext, IdiomActivity.class));
                    } else if (title.equals("新华字典")) {
                        mContext.startActivity(new Intent(mContext, DictionaryActivity.class));
                    } else if (title.equals("全国省市今日油价")) {
                        mContext.startActivity(new Intent(mContext, OilPriceActivity.class));
                    } else if (title.equals("汽车信息查询")) {
                        mContext.startActivity(new Intent(mContext, CarListActivity.class));
                    } else if (title.equals("驾考题库")) {
                        mContext.startActivity(new Intent(mContext, DrivingExamActivity.class));
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
