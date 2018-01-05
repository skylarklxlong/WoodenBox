package online.himakeit.skylark.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kelin.scrollablepanel.library.PanelAdapter;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import online.himakeit.skylark.R;
import online.himakeit.skylark.model.mob.MobOilPriceEntity;

/**
 * @author：LiXueLong
 * @date：2018/1/5
 * @mail1：skylarklxlong@outlook.com
 * @mail2：li_xuelong@126.com
 * @des:
 */
public class MobOilPricePanelAdapter extends PanelAdapter {

    private Context context;
    private LayoutInflater layoutInflater;
    private HashMap<String, MobOilPriceEntity.MobOilPriceDetailBean> modelAttributeAndValueMap;
    private ArrayList<String> proviceNames;

    public MobOilPricePanelAdapter(Context context, HashMap<String, MobOilPriceEntity.MobOilPriceDetailBean> modelAttributeAndValueMap, ArrayList<String> proviceNames) {
        this.context = context;
        this.modelAttributeAndValueMap = modelAttributeAndValueMap;
        this.proviceNames = proviceNames;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getRowCount() {
        return modelAttributeAndValueMap.size() + 1;
    }

    @Override
    public int getColumnCount() {
        return 5;
    }

    @Override
    public int getItemViewType(int row, int column) {
        if (column == 0 && row == 0) {
            //左上角标题
            return 0;
        }
        if (column == 0) {
            //左边省市标题
            return 1;
        }
        if (row == 0) {
            //顶部类型标题
            return 2;
        }
        //油价
        return 3;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int row, int column) {
        if (holder instanceof MyViewHolder){
            MyViewHolder viewHolder = (MyViewHolder) holder;
            viewHolder.rl_root.setBackgroundColor(context.getResources().getColor(R.color.white));
            int viewType = getItemViewType(row, column);
            if (viewType == 0){//左上角标题
                viewHolder.tv_content.setText("省份/类型");
                viewHolder.rl_root.setBackgroundColor(context.getResources().getColor(R.color.itemGrayBg));
            }else if (viewType == 1){//左边省市标题
                String name = proviceNames.get(row - 1);
                viewHolder.tv_content.setText(name);
                viewHolder.rl_root.setBackgroundColor(context.getResources().getColor(R.color.itemGrayBg));
            }else if (viewType == 2){//上边类型标题
                String name = "类型";
                if (column == 1) {
                    name = "0号柴油";
                } else if (column == 2) {
                    name = "90号汽油";
                } else if (column == 3) {
                    name = "93号汽油";
                } else if (column == 4) {
                    name = "97号汽油";
                }
                viewHolder.tv_content.setText(name);
                viewHolder.rl_root.setBackgroundColor(context.getResources().getColor(R.color.itemGrayBg));
            }else if (viewType == 3){//油价
                String name = proviceNames.get(row - 1);
                MobOilPriceEntity.MobOilPriceDetailBean oilBean = modelAttributeAndValueMap.get(name);
                String content = "";
                if (column == 1) {
                    content = oilBean.getDieselOil0();
                } else if (column == 2) {
                    content = oilBean.getGasoline90();
                } else if (column == 3) {
                    content = oilBean.getGasoline93();
                } else if (column == 4) {
                    content = oilBean.getGasoline97();
                }
                viewHolder.tv_content.setText(content);
            }
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 0) {  //左上角标题

        } else if (viewType == 1) {  //左边省市标题

        } else if (viewType == 2) {  //左边省市标题

        } else if (viewType == 3) {  //左边省市标题

        }
        View inflate = layoutInflater.inflate(R.layout.item_mob_oil_price, parent, false);
        return new MyViewHolder(inflate);
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tv_content)
        public TextView tv_content;
        @Bind(R.id.rl_root)
        public RelativeLayout rl_root;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
