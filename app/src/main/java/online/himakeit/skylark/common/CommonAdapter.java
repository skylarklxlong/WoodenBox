package online.himakeit.skylark.common;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by：LiXueLong 李雪龙 on 17-6-19 下午7:46
 * <p>
 * Mail : skylarklxlong@outlook.com
 * <p>
 * Description:共有的Adapter
 */
public abstract class CommonAdapter<T> extends BaseAdapter {
    Context mContext;
    List<T> mDataList;

    public CommonAdapter(Context mContext , List<T> mDataList){
        this.mContext = mContext;
        this.mDataList = mDataList;
    }

    public Context getmContext() {
        return mContext;
    }

    public List<T> getmDataList() {
        return mDataList;
    }

    /**
     * 添加数据源
     * @param mDataList
     */
    public void addDataList(List<T> mDataList){
        this.mDataList.addAll(mDataList);
        notifyDataSetChanged();
    }

    /**
     * 清除数据
     */
    public void clear(){
        this.mDataList.clear();
    }

    @Override
    public int getCount() {
        return mDataList.size();
    }

    @Override
    public Object getItem(int position) {
        return mDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        convertView = convertView(position,convertView);

        return convertView;
    }

    /**
     * 重写contertView方法
     * @param position
     * @param convertView
     * @return
     */
    public abstract View convertView(int position,View convertView);
}
