package online.himakeit.skylark.fragment;

import android.content.Context;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import online.himakeit.skylark.R;
import online.himakeit.skylark.common.BaseFragment;

/**
 * Created by：double on 17-2-21
 * <p>
 * Mail : skylarklxlong@outlook.com
 * <p>
 * Description:
 * 正常工作天数：
 * 正常工作时间工资：
 * 平日加班小时/工资（原G1）：
 * 休息日上班未补休小时/工资（原G2）：
 * 补项：
 * 事假天数/减项：
 * 应发总额：
 * 宿舍水电/物业代扣：
 * 住宿费代扣：
 * 伙食费代扣：
 * 社保代缴款：
 * 住房公积金代缴款：
 * 个人所得税代扣：
 * 实发薪资：
 *
 * http://jsq.9170.com
 *
 */
public class ToolsFragment extends BaseFragment {

    private static final String TAG = "ToolsFragment";

    @Bind(R.id.edit_dixin)
    EditText edit_dixin;
    @Bind(R.id.edit_g1)
    EditText edit_g1;
    @Bind(R.id.edit_g2)
    EditText edit_g2;
    @Bind(R.id.edit_buxiang)
    EditText edit_buxiang;
    @Bind(R.id.edit_qingjia)
    EditText edit_qingjia;
    @Bind(R.id.edit_zscf)
    EditText edit_zscf;
    @Bind(R.id.btn_work)
    Button btn_work;
    @Bind(R.id.btn_clear)
    Button btn_clear;
    @Bind(R.id.tv_sqyfje)
    TextView tv_sqyfje;
    @Bind(R.id.tv_sbdj)
    TextView tv_sbdj;
    @Bind(R.id.tv_zfgjj)
    TextView tv_zfgjj;
    @Bind(R.id.tv_grsds)
    TextView tv_grsds;
    @Bind(R.id.tv_shsfze)
    TextView tv_shsfze;

    String dixinStr = null;
    String g1Str = null;
    String g2Str = null;
    String buxiangStr = null;
    String qingjiaStr = null;
    String zscfStr = null;

    int dixin = 0;
    float g1 = 0;
    float g2 = 0;
    float buxiang = 0;
    float qingjia = 0;
    float zscf = 0;


    double sqyfze = 0;
    double sbdj = 0;
    double zfgjj = 0;
    double grsds = 0;
    double shsfze = 0;

    @Override
    public View initViews() {
        View view = View.inflate(mActivity, R.layout.fragment_four, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void initData() {

    }

    private void getTextFromEditText() {
        dixinStr = edit_dixin.getText().toString();
        g1Str = edit_g1.getText().toString();
        g2Str = edit_g2.getText().toString();
        buxiangStr = edit_buxiang.getText().toString();
        qingjiaStr = edit_qingjia.getText().toString();
        zscfStr = edit_zscf.getText().toString();
    }

    /**
     * 税前应发总额
     *
     * @param dx 底薪
     * @param g1 加班一时长（底薪1.5倍每小时）
     * @param g2 加班二时长（底薪2倍每小时）
     * @return ((g1 * 1.5) + (g2 * 2)) * (dx / 21.75 / 8) + dx
     */
    private double doSqyfze(int dx, float g1, float g2, float buxiang, float qingjia) {
        double result = 0;

        result = (((g1 - qingjia) * 1.5) + (g2 * 2)) * (dx / 21.75 / 8) + dx + buxiang;
        Log.e(TAG, "doSqyfze: " + result);
        Log.e(TAG, "doSqyfze 四舍五入 " + String.format("%.2f", result));

        return result;
    }

    /**
     * 社保代缴
     * <p>
     * 缴纳比例：公司  个人
     * 养老：   19%   8%
     * 医疗：   8%    2%
     * 工伤：         0
     * 生育：   0.7%  0
     * 失业：   0.7%  0.3%
     *
     * http://wuhan.chashebao.com/ziliao/17777.html
     ①养老保险 单位缴纳比例19%，职工个人缴纳比例为8%；
     ②医疗保险单位缴纳比例8%，职工个人缴纳比例为2%；
     ③工伤保险单位按照自己公司所在的行业缴纳比例，职工个人不缴纳；
     ④生育保险单位缴纳比例0.70%；职工个人不缴纳；
     ⑤失业保险单位缴纳比例为0.70%，职工个人比例为0.30%；
     *
     * @param dx
     * @return
     */
    private double doSbdj(int dx) {
        double result = 0;
        result = dx * 0.103;
        return result;
    }

    /**
     * 住房公积金 按5%计算
     *
     * @param dx
     * @return
     */
    private double doZfgjj(int dx) {
        double result = 0;
        result = dx * 0.05;
        return result;
    }

    /**
     * 个人所得税
     * 应纳税所得额 = 工资收入金额 － 各项社会保险费 － 起征点(3500元)
     * 应纳税额 = 应纳税所得额 × 税率 － 速算扣除数
     * 级数 当月应纳税所得额	            税率(%)	速算扣除数
     * 1   不超过1,500元                3	    0
     * 2	 超过1,500元至4,500元的部分	    10	    105
     * 3	 超过4,500元至9,000元的部分	    20	    555
     * 4	 超过9,000元至35,000元的部分	25	    1,005
     * 5	 超过35,000元至55,000元的部分	30	    2,755
     * 6	 超过55,000元至80,000元的部分	35	    5,505
     * 7	 超过80,000元的部分	        45	    13,505
     *
     * http://jsq.9170.com/
     *
     * @param sqyfze
     * @param sbdj
     * @param zfgjj
     * @return
     */
    private double doGrses(double sqyfze, double sbdj, double zfgjj) {
        double result = 0;
        double ynssde = sqyfze - sbdj - zfgjj - 3500;
        if (ynssde > 0 && ynssde <= 1500) {
            result = ynssde * 0.03;
        } else if (ynssde > 1500 && ynssde <= 4500) {
            result = ynssde * 0.1 - 105;
        } else if (ynssde > 4500 && ynssde <= 9000) {
            result = ynssde * 0.2 - 555;
        } else if (ynssde > 9000 && ynssde <= 35000) {
            result = ynssde * 0.25 - 1005;
        } else if (ynssde > 35000 && ynssde <= 55000) {
            result = ynssde * 0.3 - 2755;
        } else if (ynssde > 55000 && ynssde <= 80000) {
            result = ynssde * 0.35 - 5505;
        } else if (ynssde > 80000) {
            result = ynssde * 0.45 - 13505;
        }
        return result;
    }

    /**
     * 税后实发总额
     *
     * @param sqyfze
     * @param sbdj
     * @param zfgjj
     * @param grsds
     * @return
     */
    private double doShsfze(double sqyfze, double sbdj, double zfgjj, double grsds, float zscf) {
        double result = 0;
        result = sqyfze - sbdj - zfgjj - grsds - zscf;
        return result;
    }

    private void resetResult() {
        tv_sqyfje.setText("");
        tv_sbdj.setText("");
        tv_zfgjj.setText("");
        tv_grsds.setText("");
        tv_shsfze.setText("");

        dixinStr = null;
        g1Str = null;
        g2Str = null;
        buxiangStr = null;
        qingjiaStr = null;
        zscfStr = null;

        dixin = 0;
        g1 = 0;
        g2 = 0;
        buxiang = 0;
        qingjia = 0;
        zscf = 0;

        sqyfze = 0;
        sbdj = 0;
        zfgjj = 0;
        grsds = 0;
        shsfze = 0;
    }

    private void resetEditText() {
        edit_dixin.setText("");
        edit_g1.setText("");
        edit_g2.setText("");
        edit_buxiang.setText("");
        edit_qingjia.setText("");
        edit_zscf.setText("");
    }

    @OnClick({R.id.btn_work, R.id.btn_clear})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.btn_work:
                hideKeyBoard();
                resetResult();
                getTextFromEditText();

                if (!dixinStr.equals("")) {
                    dixin = Integer.parseInt(dixinStr);
                }
                if (!g1Str.equals("")) {
                    g1 = Float.parseFloat(g1Str);
                }
                if (!g2Str.equals("")) {
                    g2 = Float.parseFloat(g2Str);
                }
                if (!buxiangStr.equals("")) {
                    buxiang = Float.parseFloat(buxiangStr);
                }
                if (!qingjiaStr.equals("")) {
                    qingjia = Float.parseFloat(qingjiaStr);
                }
                if (!zscfStr.equals("")) {
                    zscf = Float.parseFloat(zscfStr);
                }

                sqyfze = doSqyfze(dixin, g1, g2, buxiang, qingjia);
                sbdj = doSbdj(dixin);
                zfgjj = doZfgjj(dixin);
                grsds = doGrses(sqyfze, sbdj, zfgjj);
                shsfze = doShsfze(sqyfze, sbdj, zfgjj, grsds, zscf);

                tv_sqyfje.setText(String.format("%.2f", sqyfze));
                tv_sbdj.setText(String.format("%.2f", sbdj));
                tv_zfgjj.setText(String.format("%.2f", zfgjj));
                tv_grsds.setText(String.format("%.2f", grsds));
                tv_shsfze.setText(String.format("%.2f", shsfze));
                break;
            case R.id.btn_clear:
                resetEditText();

                resetResult();

                break;
        }
    }

    /**
     * 获取InputMethodManager，隐藏软键盘
     *
     * @param token
     */
    private void hideKeyboard(IBinder token) {
        if (token != null) {
            InputMethodManager im = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    // 隐藏键盘
    private void hideKeyBoard() {
        InputMethodManager imm = (InputMethodManager) getActivity()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        // 得到InputMethodManager的实例
        if (imm.isActive()) {
            // 如果开启
            imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT,
                    InputMethodManager.HIDE_NOT_ALWAYS);
            // 关闭软键盘，开启方法相同，这个方法是切换开启与关闭状态的
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

}
