package online.himakeit.skylark.fragment;

import android.view.View;
import android.widget.Button;

import online.himakeit.skylark.R;
import online.himakeit.skylark.common.BaseFragment;
import online.himakeit.skylark.util.NetWorks;
import online.himakeit.skylark.util.LogUtils;
import rx.Observer;

/**
 * Created by：double on 17-2-21
 * <p>
 * Mail : skylarklxlong@outlook.com
 * <p>
 * Description:
 */
public class FiveFragment extends BaseFragment implements View.OnClickListener {

    View view;
    Button btn_getdata;
    Button btn_getdesdata;

    @Override
    public View initViews() {
        view = View.inflate(mActivity, R.layout.fragment_five, null);

        return view;
    }

    @Override
    public void initData() {
        btn_getdata = (Button) view.findViewById(R.id.btn_getdata);
        btn_getdesdata = (Button) view.findViewById(R.id.btn_getdesdata);

        btn_getdata.setOnClickListener(this);
        btn_getdesdata.setOnClickListener(this);

    }

    public void getData() {
        NetWorks.getData(0, new Observer<Object>() {
            @Override
            public void onCompleted() {
                LogUtils.e("onCompleted------>");

            }

            @Override
            public void onError(Throwable e) {
                LogUtils.e("onError------>" + e.toString());

            }

            @Override
            public void onNext(Object o) {
                LogUtils.e("onNext------>" + o.toString());
            }
        });
    }

    public void getDesData() {
        NetWorks.getDetailData("CQBHH411000189FH", new Observer<Object>() {
            @Override
            public void onCompleted() {
                LogUtils.e("onCompleted------>");

            }

            @Override
            public void onError(Throwable e) {
                LogUtils.e("onError------>" + e.toString());

            }

            @Override
            public void onNext(Object o) {
                LogUtils.e("onNext------>" + o.toString());
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_getdata:
                getData();
                break;
            case R.id.btn_getdesdata:
                getDesData();
                break;
        }
    }
}
