package online.himakeit.skylark.activity.mob;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import online.himakeit.skylark.R;
import online.himakeit.skylark.api.MobApiImpl;
import online.himakeit.skylark.callback.MobCallBack;
import online.himakeit.skylark.common.OtherBaseActivity;
import online.himakeit.skylark.model.mob.MobIdiomEntity;
import online.himakeit.skylark.util.KeyboardUtils;

/**
 * Created by：LiXueLong 李雪龙 on 2017/10/24 19:21
 * <p>
 * Mail : skylarklxlong@outlook.com
 * <p>
 * Description:
 */
public class IdiomActivity extends OtherBaseActivity {

    @Bind(R.id.tv_back)
    ImageView tv_back;
    @Bind(R.id.tv_title)
    TextView tv_title;

    @Bind(R.id.btn_idiom_query)
    Button btn_query;
    @Bind(R.id.ed_idiom_query)
    EditText ed_query;
    @Bind(R.id.iv_idiom_query)
    ImageView iv_query;
    @Bind(R.id.tv_idiom_pinyin)
    TextView tv_pinyin;
    @Bind(R.id.tv_idiom_definition)
    TextView tv_definition;
    @Bind(R.id.tv_idiom_from)
    TextView tv_from;
    @Bind(R.id.tv_idiom_sample)
    TextView tv_sample;
    @Bind(R.id.tv_idiom_sample_from)
    TextView tv_sample_from;

    String keyWord;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mob_id_iom);
        ButterKnife.bind(this);

        initView();
    }

    private void initView() {
        tv_title.setText("成语大全");
    }

    @OnClick({R.id.tv_back, R.id.btn_idiom_query, R.id.iv_idiom_query})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.tv_back:
                finish();
                break;
            case R.id.iv_idiom_query:
                keyWord = ed_query.getText().toString().trim();
                if (!"".equals(keyWord)) {
                    ed_query.setText("");
                }
                break;
            case R.id.btn_idiom_query:
                KeyboardUtils.hideSoftInput(this);
                keyWord = ed_query.getText().toString().trim();
                if (TextUtils.isEmpty(keyWord)) {
                    Snackbar.make(btn_query, "输入内容不能为空", Snackbar.LENGTH_SHORT).show();
                    return;
                }
                showProgressDialog("正在查询。。。");
                MobApiImpl.queryMobIdiom(keyWord, 0x001, new MobCallBack() {
                    @Override
                    public void onSuccess(int what, Object result) {
                        dissmissProgressDialog();
                        if (result != null) {
                            MobIdiomEntity mobIdiomEntity = (MobIdiomEntity) result;
                            tv_pinyin.setText("拼音:" + mobIdiomEntity.getPinyin());
                            tv_definition.setText("释义:" + mobIdiomEntity.getPretation());
                            tv_from.setText("出自:" + mobIdiomEntity.getSource());
                            tv_sample.setText("示例:" + mobIdiomEntity.getSample());
                            tv_sample_from.setText("示例出自:" + mobIdiomEntity.getSampleFrom());
                        }
                    }

                    @Override
                    public void onSuccessList(int what, List results) {

                    }

                    @Override
                    public void onFail(int what, String result) {
                        dissmissProgressDialog();
                        Snackbar.make(btn_query, result, Snackbar.LENGTH_SHORT).show();
                    }
                });
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
