package online.himakeit.skylark.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import online.himakeit.skylark.R;

/**
 * Created by：LiXueLong 李雪龙 on 2017/12/9 10:09
 * <p>
 * Mail : skylarklxlong@outlook.com
 * <p>
 * Description:
 */
public class SettingItemView extends FrameLayout {

    private Context context;

    //声明的控件
    private TextView tv_title;  //标题
    private TextView tv_right;  //右侧文字
    private ImageView iv_red_dot;   //小红点

    private String title;
    private String rightText;
    private boolean isShowRedDot;

    public SettingItemView(Context context) {
        super(context, null);
        this.context = context;
        //初始化View
        initView();
    }

    public SettingItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        //获取自定义属性
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SettingView,0,0);
        title = typedArray.getString(R.styleable.SettingView_setting_title);
        isShowRedDot = typedArray.getBoolean(R.styleable.SettingView_setting_red_dot, false);
        rightText = typedArray.getString(R.styleable.SettingView_setting_right_text);
        //销毁
        typedArray.recycle();

        //初始化View
        initView();
    }

    private void initView() {

        View.inflate(context, R.layout.setting_item_view, this);
        tv_title = (TextView) findViewById(R.id.setting_tv_title);
        tv_right = (TextView) findViewById(R.id.setting_tv_right);
        iv_red_dot = (ImageView) findViewById(R.id.setting_iv_red);

        //赋值
        setTitleText(title);
        setRedDot(isShowRedDot);
        setRightText(rightText);

    }

    public void setTitleText(String title){
        if (!TextUtils.isEmpty(title)){
            tv_title.setText(title);
        }
    }

    public String getTitleText(){
        return tv_title.getText().toString();
    }

    public void setRedDot(boolean flag) {
        if (flag) {
            iv_red_dot.setVisibility(View.VISIBLE);
        } else {
            iv_red_dot.setVisibility(View.GONE);
        }
    }

    public void setRightText(String rightText) {
        if (TextUtils.isEmpty(rightText)) {
            tv_right.setVisibility(View.GONE);
        } else {
            tv_right.setVisibility(View.VISIBLE);
            tv_right.setText(rightText);
        }
    }

    public String getRightText() {
        return tv_right.getText().toString();
    }


    public void setTitleColor(int resId) {
        tv_title.setTextColor(resId);
    }
}
