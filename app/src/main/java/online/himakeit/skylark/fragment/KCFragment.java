package online.himakeit.skylark.fragment;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Button;

import online.himakeit.skylark.R;
import online.himakeit.skylark.common.BaseFragment;
import online.himakeit.skylark.util.NavigatorUtils;
import online.himakeit.skylark.util.LogUtils;


/**
 * Created by：double on 17-2-21
 * <p>
 * Mail : skylarklxlong@outlook.com
 * <p>
 * Description:
 */
public class KCFragment extends BaseFragment implements View.OnClickListener {

    Button btnSend,btnGet,btnWebTransfer;
    Intent mIntent;

    @Override
    public View initViews() {
        View view = View.inflate(mActivity, R.layout.fragment_kuaichuan,null);
        btnSend = (Button) view.findViewById(R.id.btn_send);
        btnGet = (Button) view.findViewById(R.id.btn_get);
        btnWebTransfer = (Button) view.findViewById(R.id.web_transfer);

        return view;
    }

    @Override
    public void initData() {

        btnSend.setOnClickListener(this);
        btnGet.setOnClickListener(this);
        btnWebTransfer.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_send:
                LogUtils.e("kuaichuan send");
//                mIntent = new Intent(getActivity(), ChooseFileActivity.class);
//                startActivity(mIntent);
                NavigatorUtils.toChooseFileUI(getContext());
                break;
            case R.id.btn_get:
                LogUtils.e("kuaichuan get");
//                mIntent = new Intent(getActivity(), ReceiverWaitingActivity.class);
//                startActivity(mIntent);
                NavigatorUtils.toReceiverWaitingUI(getContext());
                break;
            case R.id.web_transfer:
                LogUtils.e("kuaichuan web transfer");
//                NavigatorUtils.toChooseFileUI(getContext(), true);
//                ToastUtils.show(getContext(),"此功能尚待开发！");
                Snackbar.make(getView(),"此功能尚待开发！",Snackbar.LENGTH_SHORT).show();
                // TODO: 2017/8/5 测试用的
//                Intent intent = new Intent(getActivity(), TopNewsDescribeActivity.class);
//                startActivity(intent);
                break;
        }
    }
}
