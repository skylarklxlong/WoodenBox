package online.himakeit.skylark.activity.mob;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import online.himakeit.skylark.R;
import online.himakeit.skylark.adapter.MobCookMenuRecyclerAdapter;
import online.himakeit.skylark.api.MobApiImpl;
import online.himakeit.skylark.callback.MobCallBack;
import online.himakeit.skylark.callback.OnItemClickListener;
import online.himakeit.skylark.common.OtherBaseActivity;
import online.himakeit.skylark.model.mob.MobCookCategoryEntity;

/**
 * Created by：LiXueLong 李雪龙 on 2017/10/24 18:40
 * <p>
 * Mail : skylarklxlong@outlook.com
 * <p>
 * Description:
 */
public class CookMenuActivity extends OtherBaseActivity {

    @Bind(R.id.tv_back)
    ImageView tv_back;
    @Bind(R.id.tv_title)
    TextView tv_title;

    @Bind(R.id.recycler_cook_menu_left)
    RecyclerView recycler_left;
    @Bind(R.id.recycler_cook_menu_right)
    RecyclerView recycler_right;

    MobCookMenuRecyclerAdapter leftAdapter;
    MobCookMenuRecyclerAdapter rightAdapter;
    List<MobCookCategoryEntity> entityListLeft;
    List<MobCookCategoryEntity> entityListRight;
    MobCookCategoryEntity entity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mob_cook_menu);
        ButterKnife.bind(this);

        init();
        initDatas();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
    private void initDatas() {
        showProgressDialog();
        MobApiImpl.queryMobCookCategory(0x001, new MobCallBack() {
            @Override
            public void onSuccess(int what, Object result) {
                dissmissProgressDialog();
                entity = (MobCookCategoryEntity) result;
                if (entity != null){
                    entityListLeft = entity.getChilds();
                    initAdapterLeft();
                    updateRightList(0);
                }

            }

            @Override
            public void onSuccessList(int what, List results) {

            }

            @Override
            public void onFail(int what, String result) {
                dissmissProgressDialog();
                Snackbar.make(tv_back, result, Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    private void init() {
        tv_title.setText("我的厨房");

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recycler_left.setLayoutManager(linearLayoutManager);
        recycler_left.setItemAnimator(new DefaultItemAnimator());

        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recycler_right.setLayoutManager(linearLayoutManager1);
        recycler_right.setItemAnimator(new DefaultItemAnimator());
    }

    private void initAdapterLeft() {
        leftAdapter = new MobCookMenuRecyclerAdapter(mContext,entityListLeft,0);
        recycler_left.setAdapter(leftAdapter);
        leftAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                updateRightList(position);
            }
        });
    }

    private void initAdapterRight() {
        rightAdapter = new MobCookMenuRecyclerAdapter(mContext,entityListRight,1);
        recycler_right.setAdapter(rightAdapter);
        rightAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                MobCookCategoryEntity mobCookCategoryEntity = entityListRight.get(position);
                Intent intent = new Intent(CookMenuActivity.this,CookListActivity.class);
                intent.putExtra(CookListActivity.IntentKey_Cook,mobCookCategoryEntity);
                startActivity(intent);
            }
        });
    }

    private void updateRightList(int position) {
        entityListRight = entityListLeft.get(position).getChilds();
        initAdapterRight();
    }

    @OnClick(R.id.tv_back)
    public void onClick(View view){
        switch (view.getId()){
            case R.id.tv_back:
                finish();
                break;
        }
    }
}
