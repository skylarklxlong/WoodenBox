package online.himakeit.skylark.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ms.square.android.expandabletextview.ExpandableTextView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import online.himakeit.skylark.R;
import online.himakeit.skylark.model.mob.MobHealthEntity;

/**
 * @author：LiXueLong
 * @date：2018/1/5
 * @mail1：skylarklxlong@outlook.com
 * @mail2：li_xuelong@126.com
 * @des:
 */
public class MobHealthRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private ArrayList<MobHealthEntity.MobHealthListBean> mDatas;
    private LayoutInflater layoutInflater;

    public MobHealthRecyclerAdapter(Context context, ArrayList<MobHealthEntity.MobHealthListBean> mDatas) {
        this.context = context;
        this.mDatas = mDatas;
        layoutInflater = LayoutInflater.from(context);
    }

    public void updateDatas(ArrayList<MobHealthEntity.MobHealthListBean> mDatas) {
        this.mDatas = mDatas;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = layoutInflater.inflate(R.layout.item_mob_health, parent, false);
        return new MyViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MyViewHolder) {
            MyViewHolder viewHolder = (MyViewHolder) holder;
            MobHealthEntity.MobHealthListBean mobHealthListBean = mDatas.get(position);
            viewHolder.tv_title.setText(mobHealthListBean.getTitle());
            SparseBooleanArray sparseBooleanArray = new SparseBooleanArray();
            viewHolder.expandableTextView.setText(mobHealthListBean.getContent(), sparseBooleanArray, position);
        }
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tv_health_title)
        TextView tv_title;
        @Bind(R.id.expand_text_view)
        ExpandableTextView expandableTextView;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
