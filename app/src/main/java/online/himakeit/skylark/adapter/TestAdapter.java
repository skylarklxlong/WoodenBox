package online.himakeit.skylark.adapter;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.litesuits.orm.db.assit.QueryBuilder;
import com.litesuits.orm.db.model.ConflictAlgorithm;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import org.xutils.ex.DbException;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.jzvd.JZVideoPlayerStandard;
import online.himakeit.skylark.AppContext;
import online.himakeit.skylark.R;
import online.himakeit.skylark.api.NeiHanApiImpl;
import online.himakeit.skylark.callback.LoadFinishCallBack;
import online.himakeit.skylark.callback.LoadResultCallBack;
import online.himakeit.skylark.callback.MobCallBack;
import online.himakeit.skylark.model.neihan.NeiHanDataBase;
import online.himakeit.skylark.model.neihan.NeiHanDataDataEntity;
import online.himakeit.skylark.model.neihan.NeiHanDataDataGroupEntity;
import online.himakeit.skylark.model.neihan.NeiHanDataEntity;
import online.himakeit.skylark.util.DateUtils;
import online.himakeit.skylark.util.ImageLoadProxy;
import online.himakeit.skylark.util.JsonUtils;
import online.himakeit.skylark.util.NetUtils;
import online.himakeit.skylark.util.Toasts;
import online.himakeit.skylark.view.ShowMaxImageView;

/**
 * Created by：LiXueLong 李雪龙 on 2017/12/16 13:41
 * <p>
 * Mail : skylarklxlong@outlook.com
 * <p>
 * Description:
 */
public class TestAdapter extends RecyclerView.Adapter<TestAdapter.TestViewHolder> {

    private int page;
    private int lastPosition = -1;
    ArrayList<NeiHanDataDataEntity> neiHanArrayList;
    private Activity mActivity;
    private LoadResultCallBack mLoadResultCallBack;
    private LoadFinishCallBack mLoadFinisCallBack;
    private String mType;

    public TestAdapter(String mType, Activity mActivity, LoadFinishCallBack mLoadFinisCallBack, LoadResultCallBack mLoadResultCallBack) throws DbException {
        this.mType = mType;
        this.mActivity = mActivity;
        this.mLoadResultCallBack = mLoadResultCallBack;
        this.mLoadFinisCallBack = mLoadFinisCallBack;
        neiHanArrayList = new ArrayList<NeiHanDataDataEntity>();
        ImageLoadProxy.initImageLoader(mActivity);
    }

    protected void setAnimation(View viewToAnimate, int position) {
        if (position > lastPosition) {
            Animation animation = AnimationUtils.loadAnimation(viewToAnimate.getContext(), R
                    .anim.item_bottom_in);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }

    @Override
    public void onViewDetachedFromWindow(TestViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        holder.card.clearAnimation();
    }

    @Override
    public TestViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_test, parent, false);
        return new TestViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final TestViewHolder holder, int position) {
        final NeiHanDataDataEntity dataEntity = neiHanArrayList.get(position);
        if (dataEntity.getGroup() != null) {
            if (dataEntity.getGroup().getContent() != null && !dataEntity.getGroup().getContent().equals("")) {
                holder.tv_content.setVisibility(View.VISIBLE);
                holder.tv_content.setText(dataEntity.getGroup().getContent());
            } else {
                holder.tv_content.setVisibility(View.GONE);
            }
            holder.tv_author.setText(dataEntity.getGroup().getUser().getName());
            holder.tv_time.setText(DateUtils.Millis2Date(dataEntity.getGroup().getCreate_time()));
            holder.tv_like.setText(dataEntity.getGroup().getDigg_count() + "");
            holder.tv_comment_count.setText(dataEntity.getGroup().getComment_count());
            holder.tv_unlike.setText(dataEntity.getGroup().getBury_count() + "");

            ImageLoadProxy.displayImageList(dataEntity.getGroup().getUser().getAvatar_url(),
                    holder.iv_user, R.drawable.pic_gray_bg, new
                            SimpleImageLoadingListener() {
                                @Override
                                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                                    super.onLoadingComplete(imageUri, view, loadedImage);
                                }
                            },
                    new ImageLoadingProgressListener() {
                        @Override
                        public void onProgressUpdate(String imageUri, View view, int current, int total) {
                        }
                    });

            //普通图片
            NeiHanDataDataGroupEntity.Mimage middle_image = dataEntity.getGroup().getMiddle_image();
            if (middle_image != null) {
                String middle_imageUri = middle_image.getUri();
                if (!TextUtils.isEmpty(middle_imageUri)) {

                    holder.iv_gif.setVisibility(View.GONE);
                    //动图
                    NeiHanDataDataGroupEntity.GifVideo gifvideo = dataEntity.getGroup().getGifvideo();
                    if (gifvideo != null) {
                        final String mp4_url = gifvideo.getMp4_url();
                        if (!TextUtils.isEmpty(mp4_url)) {
                            // TODO: 2017/12/14 表明是动图
                            holder.iv_gif.setVisibility(View.VISIBLE);
                            holder.iv_gif.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    holder.iv_gif.setVisibility(View.GONE);
                                    holder.iv_img_show.setVisibility(View.GONE);
                                    holder.videoplayer.setVisibility(View.VISIBLE);
                                    holder.videoplayer.setUp(mp4_url, JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL);
                                }
                            });
                        }
                    }

                    holder.pb_gif.setProgress(0);
                    holder.pb_gif.setVisibility(View.VISIBLE);
                    ImageLoadProxy.displayImageList(middle_image.getUrl() + middle_imageUri + middle_image.getSuffix(),
                            holder.iv_img_show, R.drawable.pic_gray_bg, new
                                    SimpleImageLoadingListener() {
                                        @Override
                                        public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                                            super.onLoadingComplete(imageUri, view, loadedImage);
                                            holder.pb_gif.setVisibility(View.GONE);
                                        }
                                    },
                            new ImageLoadingProgressListener() {
                                @Override
                                public void onProgressUpdate(String imageUri, View view, int current, int total) {
                                    holder.pb_gif.setProgress((int) (current * 100f / total));
                                }
                            });
                }
            }

            //视频
            NeiHanDataDataGroupEntity.MCover medium_cover = dataEntity.getGroup().getMedium_cover();
            String mp4_url = dataEntity.getGroup().getMp4_url();
            if (mp4_url != null && !mp4_url.equals("")) {
                holder.iv_gif.setVisibility(View.GONE);
                holder.iv_img_show.setVisibility(View.GONE);
                holder.videoplayer.setVisibility(View.VISIBLE);
                holder.videoplayer.setUp(mp4_url, JZVideoPlayerStandard.SCREEN_WINDOW_LIST);
                ImageLoadProxy.displayHeadIcon(medium_cover.getUrl() + medium_cover.getUri() + medium_cover.getSuffix(), holder.videoplayer.thumbImageView);
            }
        }

        holder.img_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MaterialDialog.Builder(mActivity)
                        .title(R.string.app_name)
                        .titleColor(Color.BLACK)
//                        .items(R.array.joke_dialog)
                        .backgroundColor(mActivity.getResources().getColor(R.color.colorPrimary))
                        .contentColor(Color.BLACK)
                        .itemsCallback(new MaterialDialog.ListCallback() {
                            @Override
                            public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {

//                                switch (which) {
//                                    //分享
//                                    case 0:
//                                        ShareUtil.shareText(mActivity, bean.getGroup().getContent().trim());
//                                        break;
//                                    //复制
//                                    case 1:
//                                        TextUtil.copy(mActivity, bean.getGroup().getContent());
//                                        break;
//                                }

                            }
                        }).show();
            }
        });
        setAnimation(holder.card, position);
    }

    @Override
    public int getItemCount() {
        return neiHanArrayList.size();
    }

    public void loadFirst() throws DbException {
        page = 1;
        loadDataByNetworkType();
    }

    public void loadNextPage() throws DbException {
        page++;
        loadDataByNetworkType();
    }

    private void loadDataByNetworkType() throws DbException {

        if (NetUtils.isNetWorkConnected(mActivity)) {
            loadData();
        } else {
            loadCache();
        }
    }

    private void loadData() {
        new NeiHanApiImpl().getNeiHanData(mActivity, mType, 20, 0x001, new MobCallBack() {
            @Override
            public void onSuccessList(int what, List results) {
            }

            @Override
            public void onSuccess(int what, Object result) {
                switch (what) {
                    case 0x001:
                        if (result == null) {
                            mLoadResultCallBack.onError();
                            return;
                        }
                        try {
                            mLoadFinisCallBack.loadFinish(null);
                            String data = JsonUtils.serialize((NeiHanDataEntity) result);
                            if (page == 1) {
                                neiHanArrayList.clear();
                            }
                            neiHanArrayList.addAll(((NeiHanDataEntity) result).getData());
                            notifyDataSetChanged();
                            mLoadResultCallBack.onSuccess();

                            //缓存
                            SaveDataBase(data);
                        } catch (Exception e) {
                            mLoadResultCallBack.onError();
                            e.printStackTrace();
                        }
                        break;
                }
            }

            @Override
            public void onFail(int what, String result) {
                if (!TextUtils.isEmpty(result)) {
                    Toasts.showShort(result);
                }
                mLoadResultCallBack.onError();
                mLoadFinisCallBack.loadFinish(null);
            }
        });

    }

    /**
     * 从数据库中加载缓存
     *
     * @throws DbException
     */
    private void loadCache() throws DbException {
        QueryBuilder queryBuilder = new QueryBuilder(NeiHanDataBase.class);
        queryBuilder.whereEquals("type", mType);
        queryBuilder.limit(0, 20);
        if (AppContext.liteOrmDB.query(queryBuilder).size() > 0) {
            ArrayList<NeiHanDataBase> neiHanDataBaseArrayList = AppContext.liteOrmDB.query(queryBuilder);
            NeiHanDataEntity neiHanDataEntity = JsonUtils.deserialize(neiHanDataBaseArrayList.get(0).getData(), NeiHanDataEntity.class);
            neiHanArrayList.addAll(neiHanDataEntity.getData());
            notifyDataSetChanged();
            mLoadResultCallBack.onSuccess();
            mLoadFinisCallBack.loadFinish(null);
        } else {
            mLoadResultCallBack.onError();
        }
    }

    /**
     * 缓存数据,保存数据库
     *
     * @param request
     * @throws DbException
     */
    private void SaveDataBase(String request) throws DbException {
        NeiHanDataBase data = new NeiHanDataBase();
        if (mType.equals("-101")) {
            data.setId(101);
        } else if (mType.equals("-102")) {
            data.setId(102);
        } else if (mType.equals("-103")) {
            data.setId(103);
        } else if (mType.equals("-104")) {
            data.setId(104);
        } else if (mType.equals("-301")) {
            data.setId(301);
        }
        data.setData(request);
        data.setPage(page);
        data.setType(mType);
        AppContext.liteOrmDB.insert(data, ConflictAlgorithm.Replace);
    }

    class TestViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.tv_author)
        TextView tv_author;
        @Bind(R.id.tv_time)
        TextView tv_time;
        @Bind(R.id.tv_content)
        TextView tv_content;
        @Bind(R.id.tv_like)
        TextView tv_like;
        @Bind(R.id.tv_unlike)
        TextView tv_unlike;
        @Bind(R.id.tv_comment_count)
        TextView tv_comment_count;
        @Bind(R.id.img_share)
        ImageView img_share;
        @Bind(R.id.card)
        CardView card;
        @Bind(R.id.ll_comment)
        LinearLayout ll_comment;
        @Bind(R.id.pb_gif)
        ProgressBar pb_gif;
        @Bind(R.id.iv_img_show)
        ShowMaxImageView iv_img_show;
        @Bind(R.id.iv_gif)
        ImageView iv_gif;
        @Bind(R.id.iv_user)
        ImageView iv_user;
        @Bind(R.id.videoplayer)
        JZVideoPlayerStandard videoplayer;

        public TestViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
