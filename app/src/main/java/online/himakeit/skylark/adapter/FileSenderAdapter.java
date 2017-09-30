package online.himakeit.skylark.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import online.himakeit.skylark.AppContext;
import online.himakeit.skylark.Constant;
import online.himakeit.skylark.R;
import online.himakeit.skylark.model.kuaichuan.FileInfo;
import online.himakeit.skylark.util.FileUtils;

/**
 * Created by：LiXueLong 李雪龙 on 2017/7/22 15:34
 * <p>
 * Mail : skylarklxlong@outlook.com
 * <p>
 * Description:
 */
public class FileSenderAdapter extends BaseAdapter {

    private Context mContext;
    private Map<String,FileInfo> mDataHashMap;
    private String[] keys;
    List<Map.Entry<String,FileInfo>> fileInfoMapList;

    public FileSenderAdapter(Context mContext) {
        this.mContext = mContext;
        mDataHashMap = AppContext.getAppContext().getFileInfoMap();
        fileInfoMapList = new ArrayList<Map.Entry<String, FileInfo>>(mDataHashMap.entrySet());
        //排序
        Collections.sort(fileInfoMapList, Constant.DEFAULT_COMPARATOR);
    }

    @Override
    public int getCount() {
        return fileInfoMapList.size();
    }

    @Override
    public Object getItem(int position) {
        return fileInfoMapList.get(position).getValue();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        FileInfo fileInfo = (FileInfo) getItem(position);
        FileSenderHolder viewHolder = null;
        if (convertView == null){
            convertView = View.inflate(mContext, R.layout.item_transfer,null);
            viewHolder = new FileSenderHolder();
            viewHolder.iv_shortcut = (ImageView) convertView.findViewById(R.id.iv_shortcut);
            viewHolder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            viewHolder.tv_progress = (TextView) convertView.findViewById(R.id.tv_progress);
            viewHolder.pb_file = (ProgressBar) convertView.findViewById(R.id.pb_file);
            viewHolder.btn_operation = (Button) convertView.findViewById(R.id.btn_operation);
            viewHolder.iv_tick = (ImageView) convertView.findViewById(R.id.iv_tick);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (FileSenderHolder) convertView.getTag();
        }
        if (fileInfo != null){
            //初始化
            viewHolder.pb_file.setVisibility(View.VISIBLE);
            viewHolder.iv_tick.setVisibility(View.GONE);

            if (FileUtils.isApkFile(fileInfo.getFilePath())
                    || FileUtils.isMp4File(fileInfo.getFilePath())){//APK格式或者MP4格式需要缩略图
                viewHolder.iv_shortcut.setImageBitmap(fileInfo.getBitmap());
            }else if (FileUtils.isJpgFile(fileInfo.getFilePath())){//图片格式
                Glide.with(mContext)
                        .load(fileInfo.getFilePath())
                        .centerCrop()
                        .placeholder(R.mipmap.icon_jpg)
                        .crossFade()
                        .into(viewHolder.iv_shortcut);
            }else if (FileUtils.isMp3File(fileInfo.getFilePath())){//音乐格式
                viewHolder.iv_shortcut.setImageDrawable(mContext.getResources()
                        .getDrawable(R.mipmap.icon_mp3));
            }

            viewHolder.tv_name.setText(FileUtils.getFileName(fileInfo.getFilePath()));

            if (fileInfo.getResult() == FileInfo.FLAG_SUCCESS){//文件传输成功
                long total = fileInfo.getSize();
                viewHolder.pb_file.setVisibility(View.GONE);
                viewHolder.tv_progress.setText(FileUtils.getFileSize(total)
                        + "/" + FileUtils.getFileSize(total));

                viewHolder.btn_operation.setVisibility(View.INVISIBLE);
                viewHolder.iv_tick.setVisibility(View.VISIBLE);
            }else if (fileInfo.getResult() == FileInfo.FLAG_FAILURE){//文件传输失败
                viewHolder.pb_file.setVisibility(View.GONE);
            }else {//文件传输中
                long progress = fileInfo.getProcceed();
                long total = fileInfo.getSize();
                viewHolder.tv_progress.setText(FileUtils.getFileSize(total)
                        + "/" + FileUtils.getFileSize(total));

                int precent = (int) (progress * 100 /total);
                viewHolder.pb_file.setMax(100);
                viewHolder.pb_file.setProgress(precent);

                //TODO 传输过程中取消的问题
                viewHolder.btn_operation.setText(mContext.getString(R.string.str_cancel));
                viewHolder.btn_operation.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        //可否通过广播来实现？
                    }
                });
            }
        }


        return convertView;
    }
    static class FileSenderHolder{
        ImageView iv_shortcut;
        TextView tv_name;
        TextView tv_progress;
        ProgressBar pb_file;
        Button btn_operation;
        ImageView iv_tick;
    }
}
