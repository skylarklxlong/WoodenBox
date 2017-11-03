package online.himakeit.skylark.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import online.himakeit.skylark.R;
import online.himakeit.skylark.listeners.OnItemClickListener;
import online.himakeit.skylark.model.mob.MobCookCategoryEntity;

/**
 * Created by：LiXueLong 李雪龙 on 2017/11/2 19:56
 * <p>
 * Mail : skylarklxlong@outlook.com
 * <p>
 * Description:
 */
public class MobCookMenuRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;
    List<MobCookCategoryEntity> entityList;
    int flag = 0; // 0左边 ， 1右边
    LayoutInflater layoutInflater;
    int currentPosition;

    OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public MobCookMenuRecyclerAdapter(Context context, List<MobCookCategoryEntity> entityList, int flag) {
        this.context = context;
        this.entityList = entityList;
        this.flag = flag;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = layoutInflater.inflate(R.layout.item_mob_cook_menu,parent,false);
        return new CookMenuViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        MobCookCategoryEntity categoryEntity = entityList.get(position);

        CookMenuViewHolder viewHolder = (CookMenuViewHolder) holder;

        viewHolder.tv_title.setText(categoryEntity.getCategoryInfo().getName());
        if (onItemClickListener != null){
            viewHolder.tv_title.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(holder.itemView,position);
                    if (flag == 0){
                        currentPosition = position;
                        // TODO: 2017/11/3 这里很重要
                        notifyDataSetChanged();
                    }
                }
            });
        }

        if (flag == 0){
            viewHolder.tv_title.setTextColor(context.getResources().getColor(R.color.black_text2_color));
            viewHolder.rl_item.setBackgroundResource(R.color.itemGrayBg);
            viewHolder.tv_line.setVisibility(View.GONE);
            if (currentPosition == position){
                viewHolder.tv_title.setTextColor(context.getResources().getColor(R.color.colorPrimary));
                viewHolder.rl_item.setBackgroundResource(R.color.white);
                viewHolder.tv_line.setVisibility(View.VISIBLE);
            }
        }else {
            viewHolder.rl_item.setBackgroundResource(R.color.white);
            viewHolder.tv_line.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return entityList.size();
    }

    class CookMenuViewHolder extends RecyclerView.ViewHolder{

        @Bind(R.id.tv_mob_cook_menu_line)
        TextView tv_line;
        @Bind(R.id.tv_mob_cook_menu_title)
        TextView tv_title;
        @Bind(R.id.rl_mob_cook_menu)
        RelativeLayout rl_item;

        public CookMenuViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
