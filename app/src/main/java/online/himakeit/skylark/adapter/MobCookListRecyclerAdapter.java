package online.himakeit.skylark.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import online.himakeit.skylark.R;
import online.himakeit.skylark.listeners.OnItemClickListener;
import online.himakeit.skylark.model.mob.MobCookDetailEntity;

/**
 * Created by：LiXueLong 李雪龙 on 2017/11/7 20:05
 * <p>
 * Mail : skylarklxlong@outlook.com
 * <p>
 * Description:
 */
public class MobCookListRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<MobCookDetailEntity.MobCookDetailListBean> mDatas;
    private LayoutInflater layoutInflater;

    public MobCookListRecyclerAdapter(Context context, List<MobCookDetailEntity.MobCookDetailListBean> mDatas) {
        this.context = context;
        this.mDatas = mDatas;
        layoutInflater = LayoutInflater.from(this.context);
    }

    private OnItemClickListener mOnItemClickLitener;

    public void setOnItemClickLitener(OnItemClickListener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }

    public void updateDatas(List<MobCookDetailEntity.MobCookDetailListBean> mDatas){
        this.mDatas = mDatas;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_mob_cook_list,parent,false);
        return new CookListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        CookListViewHolder viewHolder = (CookListViewHolder) holder;
        MobCookDetailEntity.MobCookDetailListBean mobCookDetailListBean = mDatas.get(position);

        Picasso.with(context)
                .load(mobCookDetailListBean.getThumbnail())
                .into(viewHolder.iv_show);

        viewHolder.tv_title.setText(mobCookDetailListBean.getName());

        viewHolder.tv_ingredients.setText(mobCookDetailListBean.getRecipe().getIngredients());

        viewHolder.tv_tag.setText(mobCookDetailListBean.getCtgTitles());

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickLitener != null){
                    mOnItemClickLitener.onItemClick(v,position);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    class CookListViewHolder extends RecyclerView.ViewHolder{
        @Bind(R.id.iv_mob_cook_list_show)
        ImageView iv_show;
        @Bind(R.id.tv_mob_cook_list_title)
        TextView tv_title;
        @Bind(R.id.tv_mob_cook_list_ingredients)
        TextView tv_ingredients;
        @Bind(R.id.tv_mob_cook_list_tag)
        TextView tv_tag;
        public CookListViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
