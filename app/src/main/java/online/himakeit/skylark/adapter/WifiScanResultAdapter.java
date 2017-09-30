package online.himakeit.skylark.adapter;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import online.himakeit.skylark.R;
import online.himakeit.skylark.common.CommonAdapter;

/**
 * Created by：LiXueLong 李雪龙 on 17-7-5 上午9:12
 * <p>
 * Mail : skylarklxlong@outlook.com
 * <p>
 * Description:
 */
public class WifiScanResultAdapter extends CommonAdapter<ScanResult> {

    public WifiScanResultAdapter(Context mContext, List<ScanResult> mDataList) {
        super(mContext, mDataList);
    }

    @Override
    public View convertView(int position, View convertView) {
        ScanResultHolder viewHolder = null;
        if (convertView == null){
            convertView = View.inflate(getmContext(), R.layout.item_wifi_scan_result,null);
            viewHolder = new ScanResultHolder();
            viewHolder.iv_device = (ImageView) convertView.findViewById(R.id.iv_device);
            viewHolder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            viewHolder.tv_mac = (TextView) convertView.findViewById(R.id.tv_mac);

            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ScanResultHolder) convertView.getTag();
        }

        ScanResult scanResult = getmDataList().get(position);
        if(scanResult != null){
            viewHolder.tv_name.setText(scanResult.SSID);
            viewHolder.tv_mac.setText(scanResult.BSSID);
        }

        return convertView;
    }
    static class ScanResultHolder{
        ImageView iv_device;
        TextView tv_name;
        TextView tv_mac;
    }
}
