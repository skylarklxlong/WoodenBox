package online.himakeit.skylark.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import online.himakeit.skylark.R;
import online.himakeit.skylark.model.mob.MobCookDetailEntity;
import online.himakeit.skylark.model.mob.MobCookStepEntity;

/**
 * Created by：LiXueLong 李雪龙 on 2017/11/9 17:21
 * <p>
 * Mail : skylarklxlong@outlook.com
 * <p>
 * Description:
 */
public class MobCookDetailsRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private MobCookDetailEntity.MobCookDetailListBean mData;
    private LayoutInflater layoutInflater;
    private ArrayList<MobCookStepEntity> steps = new ArrayList<>();
    private ArrayList<String> imagesList = new ArrayList<>();

    public MobCookDetailsRecyclerAdapter(Context context, MobCookDetailEntity.MobCookDetailListBean mData) {
        this.context = context;
        this.mData = mData;
        layoutInflater = LayoutInflater.from(context);

        String method = mData.getRecipe().getMethod();
        if (!TextUtils.isEmpty(method)) {
            //解析数据
            try {
                Type type = new TypeToken<ArrayList<MobCookStepEntity>>() {
                }.getType();
                steps = new Gson().fromJson(method, type);
            } catch (Exception e) {

            }
        }

        //图片集合
        imagesList.add(mData.getRecipe().getImg());
        for (int i = 0; i < steps.size(); i++) {
            String img = steps.get(i).getImg();
            imagesList.add(img);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 0) {
            View inflate = layoutInflater.inflate(R.layout.item_mob_cook_details_01, parent, false);
            return new CookDetailViewHolder01(inflate);
        } else {
            View inflate = layoutInflater.inflate(R.layout.item_mob_cook_details_02, parent, false);
            return new CookDetailViewHolder02(inflate);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return 0;
        }
        return 1;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof CookDetailViewHolder01) {
            final CookDetailViewHolder01 viewHolder01 = (CookDetailViewHolder01) holder;
            //图片展示
            Glide.with(context)
                    .load(mData.getRecipe().getImg())
                    .placeholder(R.drawable.imageview_loading)//设置加载中的图片
                    .error(R.drawable.imageview_loading)//设置加载错误时的图片
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .into(viewHolder01.iv_show);
            //大标题
            viewHolder01.tv_name.setText(mData.getName());
            //介绍
            viewHolder01.tv_sumary.setText(mData.getRecipe().getSumary());
            //食物清单
            viewHolder01.tv_ingredients.setText(mData.getRecipe().getIngredients());

            viewHolder01.iv_show.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (imagesList.size() > 0) {
                        // TODO: 2017/11/9 浏览图片操作
                    }
                }
            });
        } else if (holder instanceof CookDetailViewHolder02) {
            final CookDetailViewHolder02 viewHolder02 = (CookDetailViewHolder02) holder;
            MobCookStepEntity mobCookStepEntity = steps.get(position - 1);
            //图片展示
            Glide.with(context)
                    .load(mobCookStepEntity.getImg())
                    .placeholder(R.drawable.imageview_loading)//设置加载中的图片
                    .error(R.drawable.imageview_loading)//设置加载错误时的图片
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .into(viewHolder02.iv_step);
            //文字步骤
            viewHolder02.tv_step.setText(mobCookStepEntity.getStep());

            viewHolder02.iv_step.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (imagesList.size() > 0){
                        // TODO: 2017/11/9 浏览图片操作
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return steps.size() + 1;
    }

    class CookDetailViewHolder01 extends RecyclerView.ViewHolder {
        @Bind(R.id.iv_show)
        ImageView iv_show;
        @Bind(R.id.tv_name)
        TextView tv_name;
        @Bind(R.id.tv_sumary)
        TextView tv_sumary;
        @Bind(R.id.tv_ingredients)
        TextView tv_ingredients;

        public CookDetailViewHolder01(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class CookDetailViewHolder02 extends RecyclerView.ViewHolder {
        @Bind(R.id.iv_step)
        ImageView iv_step;
        @Bind(R.id.tv_step)
        TextView tv_step;

        public CookDetailViewHolder02(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
