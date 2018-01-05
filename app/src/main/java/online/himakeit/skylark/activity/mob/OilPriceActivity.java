package online.himakeit.skylark.activity.mob;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.kelin.scrollablepanel.library.ScrollablePanel;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import online.himakeit.skylark.R;
import online.himakeit.skylark.adapter.MobOilPricePanelAdapter;
import online.himakeit.skylark.api.MobApiImpl;
import online.himakeit.skylark.callback.MobCallBack;
import online.himakeit.skylark.common.OtherBaseActivity;
import online.himakeit.skylark.model.mob.MobOilPriceEntity;

/**
 * Created by：LiXueLong 李雪龙 on 2017/10/24 19:22
 * <p>
 * Mail : skylarklxlong@outlook.com
 * <p>
 * Description:
 */
public class OilPriceActivity extends OtherBaseActivity {

    @Bind(R.id.tv_back)
    ImageView tv_back;
    @Bind(R.id.tv_title)
    TextView tv_title;

    @Bind(R.id.scrollable_panel)
    ScrollablePanel scrollablePanel;

    private MobOilPriceEntity mobOilPriceEntity;
    private HashMap<String, MobOilPriceEntity.MobOilPriceDetailBean> detailBeanHashMap;
    private ArrayList<String> proviceNames = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mob_oil_price);

        ButterKnife.bind(this);

        initView();

        //查询数据
        queryData();
    }

    private void queryData() {
        showProgressDialog("正在查询。。。");
        MobApiImpl.queryMobOilPrice(0x001, new MobCallBack() {
            @Override
            public void onSuccess(int what, Object result) {
                dissmissProgressDialog();
                if (result != null) {
                    try {
                        mobOilPriceEntity = (MobOilPriceEntity) result;
                        detailBeanHashMap = getModelAttributeAndValue(mobOilPriceEntity);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                    initAdapter();
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

    private void initAdapter() {
        MobOilPricePanelAdapter oilPricePanelAdapter= new MobOilPricePanelAdapter(this,detailBeanHashMap,proviceNames);
        scrollablePanel.setPanelAdapter(oilPricePanelAdapter);
    }

    private HashMap<String, MobOilPriceEntity.MobOilPriceDetailBean> getModelAttributeAndValue(MobOilPriceEntity entity) throws IllegalAccessException {
        HashMap<String, MobOilPriceEntity.MobOilPriceDetailBean> map = new HashMap<>();
        try {
            Class cls = entity.getClass();
            Field[] declaredFields = cls.getDeclaredFields();
            for (int i = 0; i < declaredFields.length; i++) {
                Field field = declaredFields[i];
                field.setAccessible(true);
                map.put(field.getName(), (MobOilPriceEntity.MobOilPriceDetailBean) field.get(entity));
                proviceNames.add(field.getName());
            }
        }catch (IllegalAccessException e){
            e.printStackTrace();
        }

        return map;
    }

    private void initView() {
        tv_title.setText("全国省市今日油价");
    }

    @OnClick({R.id.tv_back})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.tv_back:
                finish();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
