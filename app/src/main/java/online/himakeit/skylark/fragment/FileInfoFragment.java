package online.himakeit.skylark.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ProgressBar;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import online.himakeit.skylark.AppContext;
import online.himakeit.skylark.R;
import online.himakeit.skylark.common.BaseFragment;
import online.himakeit.skylark.activity.ChooseFileActivity;
import online.himakeit.skylark.adapter.FileInfoAdapter;
import online.himakeit.skylark.model.kuaichuan.FileInfo;
import online.himakeit.skylark.util.AnimationUtils;
import online.himakeit.skylark.util.FileUtils;
import online.himakeit.skylark.util.Toasts;

/**
 * Created by：LiXueLong 李雪龙 on 17-6-19 下午7:35
 * <p>
 * Mail : skylarklxlong@outlook.com
 * <p>
 * Description:
 */
public class FileInfoFragment extends BaseFragment {

    private int mType = 0;
    private List<FileInfo> mFileInfoList;
    FileInfoAdapter mFileInfoAdapter;

    @Bind(R.id.gv)
    GridView gv;
    @Bind(R.id.pb)
    ProgressBar pb;

    public FileInfoFragment() {
        super();
    }
    @SuppressLint("ValidFragment")
    public FileInfoFragment(int type) {
        super();
        this.mType = type;

    }

    public static FileInfoFragment newInstance(int type){
        FileInfoFragment fileInfoFragment = new FileInfoFragment(type);
        return fileInfoFragment;
    }

    @Override
    public View initViews() {
        View rootView = View.inflate(mActivity,R.layout.fragment_apk,null);

        ButterKnife.bind(this,rootView);

        if(mType == FileInfo.TYPE_APK){ //应用
            gv.setNumColumns(4);
        }else if(mType == FileInfo.TYPE_JPG){ //图片
            gv.setNumColumns(3);
        }else if(mType == FileInfo.TYPE_MP3){ //音乐
            gv.setNumColumns(1);
        }else if(mType == FileInfo.TYPE_MP4){ //视频
            gv.setNumColumns(1);
        }
        init();

        return rootView;
    }

    @Override
    public void initData() {

    }

    private void init() {

        if (mType == FileInfo.TYPE_APK){
            new GetFileInfoListTask(getContext(),FileInfo.TYPE_APK).executeOnExecutor(AppContext.MAIN_EXECUTOR);
        }else if (mType == FileInfo.TYPE_JPG){
            new GetFileInfoListTask(getContext(),FileInfo.TYPE_JPG).executeOnExecutor(AppContext.MAIN_EXECUTOR);

        }else if (mType ==FileInfo.TYPE_MP3){
            new GetFileInfoListTask(getContext(),FileInfo.TYPE_MP3).executeOnExecutor(AppContext.MAIN_EXECUTOR);

        }else if (mType == FileInfo.TYPE_MP4){
            new GetFileInfoListTask(getContext(),FileInfo.TYPE_MP4).executeOnExecutor(AppContext.MAIN_EXECUTOR);

        }
        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FileInfo fileInfo = mFileInfoList.get(position);
                if (AppContext.getAppContext().isExist(fileInfo)){
                    AppContext.getAppContext().delFileInfo(fileInfo);
                    //更新一下视图
                    updateSelectedView();
                }else {
                    //1、添加任务
                    AppContext.getAppContext().addFileInfo(fileInfo);
                    //2、添加任务  动画
                    View startView = null;
                    View targetView = null;

                    startView = view.findViewById(R.id.iv_shortcut);
                    if (getActivity() != null && (getActivity() instanceof ChooseFileActivity)){
                        ChooseFileActivity chooseFileActivity = (ChooseFileActivity) getActivity();
                        targetView = chooseFileActivity.getSelectedView();
                    }
                    AnimationUtils.setAddTaskAnimation(getActivity(),startView,targetView,null);

                }
                mFileInfoAdapter.notifyDataSetChanged();
            }
        });

    }

    @Override
    public void onResume() {
        updateFileInfoAdapter();
        super.onResume();
    }

    /**
     * 更新FileInfoAdapter
     */
    public void updateFileInfoAdapter() {

        if (mFileInfoAdapter != null){
            mFileInfoAdapter.notifyDataSetChanged();
        }

    }

    /**
     * 更新ChooseActivity选中的View
     */
    private void updateSelectedView() {
        if (getActivity() != null && (getActivity() instanceof ChooseFileActivity)){
            ChooseFileActivity chooseFileActivity = (ChooseFileActivity) getActivity();
            chooseFileActivity.getSelectedView();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
    /**
     * 显示进度
     */
    public void showProgressBar(){
        if (pb != null){
            pb.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 隐藏进度
     */
    public void hideProgressBar(){
        if (pb != null && pb.isShown()){
            pb.setVisibility(View.GONE);
        }
    }

    /**
     * 获取ApkInfo任务列表
     */
    class GetFileInfoListTask extends AsyncTask<String,Integer,List<FileInfo>>{
        Context mContext = null;
        int type = FileInfo.TYPE_APK;
        List<FileInfo> fileInfoList = null;

        public GetFileInfoListTask(Context context,int type){
            this.mContext = context;
            this.type = type;
        }

        @Override
        protected void onPreExecute() {
            showProgressBar();
            super.onPreExecute();
        }

        @Override
        protected List<FileInfo> doInBackground(String... params) {
            if (type == FileInfo.TYPE_APK){
                fileInfoList = FileUtils.getSpecificTypeFiles(mContext,new String[]{FileInfo.EXTEND_APK});
                fileInfoList = FileUtils.getDetailFileInfos(mContext,fileInfoList,FileInfo.TYPE_APK);
            }else if (type == FileInfo.TYPE_JPG){
                fileInfoList = FileUtils.getSpecificTypeFiles(mContext,new String[]{FileInfo.EXTEND_JPG,FileInfo.EXTEND_JPEG});
                fileInfoList = FileUtils.getDetailFileInfos(mContext,fileInfoList,FileInfo.TYPE_JPG);
            }else if (type == FileInfo.TYPE_MP3){
                fileInfoList = FileUtils.getSpecificTypeFiles(mContext,new String[]{FileInfo.EXTEND_MP3});
                fileInfoList = FileUtils.getDetailFileInfos(mContext,fileInfoList,FileInfo.TYPE_MP3);
            }else if (type == FileInfo.TYPE_MP4){
                fileInfoList = FileUtils.getSpecificTypeFiles(mContext,new String[]{FileInfo.EXTEND_MP4});
                fileInfoList = FileUtils.getDetailFileInfos(mContext,fileInfoList,FileInfo.TYPE_MP4);
            }

            mFileInfoList = fileInfoList;

            return fileInfoList;
        }

        @Override
        protected void onPostExecute(List<FileInfo> fileInfos) {
            hideProgressBar();
            if (fileInfoList != null && fileInfoList.size() > 0 ){
                if (type == FileInfo.TYPE_APK){
                    mFileInfoAdapter = new FileInfoAdapter(mContext,fileInfoList,FileInfo.TYPE_APK);
                    gv.setAdapter(mFileInfoAdapter);
                }else if (type == FileInfo.TYPE_JPG){
                    mFileInfoAdapter = new FileInfoAdapter(mContext,fileInfoList,FileInfo.TYPE_JPG);
                    gv.setAdapter(mFileInfoAdapter);
                }else if (type == FileInfo.TYPE_MP3){
                    mFileInfoAdapter = new FileInfoAdapter(mContext,fileInfoList,FileInfo.TYPE_MP3);
                    gv.setAdapter(mFileInfoAdapter);
                }else if (type == FileInfo.TYPE_MP4){
                    mFileInfoAdapter = new FileInfoAdapter(mContext,fileInfoList,FileInfo.TYPE_MP4);
                    gv.setAdapter(mFileInfoAdapter);
                }
            }else {
                Toasts.showLong(R.string.tip_has_no_apk_info);
            }


            super.onPostExecute(fileInfos);
        }
    }

}
