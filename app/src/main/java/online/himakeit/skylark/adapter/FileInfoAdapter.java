package online.himakeit.skylark.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import online.himakeit.skylark.AppContext;
import online.himakeit.skylark.R;
import online.himakeit.skylark.common.CommonAdapter;
import online.himakeit.skylark.model.kuaichuan.FileInfo;


/**
 * Created by：LiXueLong 李雪龙 on 17-6-19 下午7:45
 * <p>
 * Mail : skylarklxlong@outlook.com
 * <p>
 * Description:
 */
public class FileInfoAdapter extends CommonAdapter<FileInfo> {

    /**
     * 文件类型的标识
     */
    private int mType = FileInfo.TYPE_APK;

    public FileInfoAdapter(Context mContext, List<FileInfo> mDataList) {
        super(mContext, mDataList);
    }

    public FileInfoAdapter(Context mContext, List<FileInfo> mDataList, int mType){
        super(mContext,mDataList);
        this.mType = mType;
    }

    @Override
    public View convertView(int position, View convertView) {
        FileInfo fileInfo = getmDataList().get(position);
        if (mType == FileInfo.TYPE_APK){
            ApkViewHolder viewHolder = null;
            if (convertView == null){
                convertView = View.inflate(getmContext(), R.layout.item_apk,null);
                viewHolder = new ApkViewHolder();
                viewHolder.iv_shortcut = (ImageView) convertView.findViewById(R.id.iv_shortcut);
                viewHolder.iv_ok_tick = (ImageView) convertView.findViewById(R.id.iv_ok_tick);
                viewHolder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
                viewHolder.tv_size = (TextView) convertView.findViewById(R.id.tv_size);
                convertView.setTag(viewHolder);
            }else {
                viewHolder = (ApkViewHolder) convertView.getTag();
            }
            if (getmDataList() != null && getmDataList().get(position) != null){
                viewHolder.iv_shortcut.setImageBitmap(fileInfo.getBitmap());
                viewHolder.tv_name.setText(fileInfo.getName() == null ? "" : fileInfo.getName());
                viewHolder.tv_size.setText(fileInfo.getSizeDesc() == null ? "" : fileInfo.getSizeDesc());

                //判断全局变量是否存在FileInfo
                if (AppContext.getAppContext().isExist(fileInfo)){
                    viewHolder.iv_ok_tick.setVisibility(View.VISIBLE);
                }else {
                    viewHolder.iv_ok_tick.setVisibility(View.GONE);
                }
            }

        }else if (mType == FileInfo.TYPE_JPG){
            JpgViewHolder viewHolder = null;
            if (convertView == null) {
                convertView = View.inflate(getmContext(), R.layout.item_jpg, null);
                viewHolder = new JpgViewHolder();
                viewHolder.iv_ok_tick = (ImageView) convertView.findViewById(R.id.iv_ok_tick);
                viewHolder.iv_shortcut = (ImageView) convertView.findViewById(R.id.iv_shortcut);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (JpgViewHolder) convertView.getTag();
            }

            if (getmDataList() != null && getmDataList().get(position) != null) {

//                viewHolder.iv_shortcut.setImageBitmap(fileInfo.getBitmap());
                Glide
                        .with(getmContext())
                        .load(fileInfo.getFilePath())
                        .centerCrop()
                        .placeholder(R.mipmap.icon_jpg)
                        .crossFade()
                        .into(viewHolder.iv_shortcut);

                //全局变量是否存在FileInfo
                if (AppContext.getAppContext().isExist(fileInfo)) {
                    viewHolder.iv_ok_tick.setVisibility(View.VISIBLE);
                } else {
                    viewHolder.iv_ok_tick.setVisibility(View.GONE);
                }
            }
        }else if (mType == FileInfo.TYPE_MP3){
            Mp3ViewHolder viewHolder = null;
            if (convertView == null) {
                convertView = View.inflate(getmContext(), R.layout.item_mp3, null);
                viewHolder = new Mp3ViewHolder();
                viewHolder.iv_ok_tick = (ImageView) convertView.findViewById(R.id.iv_ok_tick);
                viewHolder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
                viewHolder.tv_size = (TextView) convertView.findViewById(R.id.tv_size);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (Mp3ViewHolder) convertView.getTag();
            }

            if (getmDataList() != null && getmDataList().get(position) != null) {
                viewHolder.tv_name.setText(fileInfo.getName() == null ? "" : fileInfo.getName());
                viewHolder.tv_size.setText(fileInfo.getSizeDesc() == null ? "" : fileInfo.getSizeDesc());

                //全局变量是否存在FileInfo
                if (AppContext.getAppContext().isExist(fileInfo)) {
                    viewHolder.iv_ok_tick.setVisibility(View.VISIBLE);
                } else {
                    viewHolder.iv_ok_tick.setVisibility(View.GONE);
                }
            }
        }else if (mType == FileInfo.TYPE_MP4){
            Mp4ViewHolder viewHolder = null;
            if (convertView == null) {
                convertView = View.inflate(getmContext(), R.layout.item_mp4, null);
                viewHolder = new Mp4ViewHolder();
                viewHolder.iv_shortcut = (ImageView) convertView.findViewById(R.id.iv_shortcut);
                viewHolder.iv_ok_tick = (ImageView) convertView.findViewById(R.id.iv_ok_tick);
                viewHolder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
                viewHolder.tv_size = (TextView) convertView.findViewById(R.id.tv_size);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (Mp4ViewHolder) convertView.getTag();
            }

            if (getmDataList() != null && getmDataList().get(position) != null) {
                viewHolder.iv_shortcut.setImageBitmap(fileInfo.getBitmap());
                viewHolder.tv_name.setText(fileInfo.getName() == null ? "" : fileInfo.getName());
                viewHolder.tv_size.setText(fileInfo.getSizeDesc() == null ? "" : fileInfo.getSizeDesc());

                //全局变量是否存在FileInfo
                if (AppContext.getAppContext().isExist(fileInfo)) {
                    viewHolder.iv_ok_tick.setVisibility(View.VISIBLE);
                } else {
                    viewHolder.iv_ok_tick.setVisibility(View.GONE);
                }
            }
        }

        return convertView;
    }
    static class ApkViewHolder {
        ImageView iv_shortcut;
        ImageView iv_ok_tick;
        TextView tv_name;
        TextView tv_size;
    }

    static class JpgViewHolder {
        ImageView iv_shortcut;
        ImageView iv_ok_tick;
    }

    static class Mp3ViewHolder {
        ImageView iv_ok_tick;
        TextView tv_name;
        TextView tv_size;
    }

    static class Mp4ViewHolder {
        ImageView iv_shortcut;
        ImageView iv_ok_tick;
        TextView tv_name;
        TextView tv_size;
    }
}
