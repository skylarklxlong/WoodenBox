package online.himakeit.skylark.adapter;

import android.app.Activity;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;

import org.xutils.ex.DbException;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import online.himakeit.skylark.R;
import online.himakeit.skylark.api.NeiHanApiImpl;
import online.himakeit.skylark.callback.LoadFinishCallBack;
import online.himakeit.skylark.callback.LoadResultCallBack;
import online.himakeit.skylark.callback.MobCallBack;
import online.himakeit.skylark.model.neihan.NeiHanDataDataEntity;
import online.himakeit.skylark.model.neihan.NeiHanDataEntity;
import online.himakeit.skylark.util.DateUtils;
import online.himakeit.skylark.util.Toasts;

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

    public TestAdapter(Activity mActivity, LoadFinishCallBack mLoadFinisCallBack, LoadResultCallBack mLoadResultCallBack) throws DbException {
        this.mActivity = mActivity;
        this.mLoadResultCallBack = mLoadResultCallBack;
        this.mLoadFinisCallBack = mLoadFinisCallBack;
        neiHanArrayList = new ArrayList<NeiHanDataDataEntity>();
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
    public void onBindViewHolder(TestViewHolder holder, int position) {
        final NeiHanDataDataEntity dataEntity = neiHanArrayList.get(position);
        if (dataEntity.getGroup() != null) {
            holder.tv_content.setText(dataEntity.getGroup().getContent());
            holder.tv_author.setText(dataEntity.getGroup().getUser().getName());
            holder.tv_time.setText(DateUtils.Millis2Date(dataEntity.getGroup().getCreate_time()));
            holder.tv_like.setText(dataEntity.getGroup().getDigg_count() + "");
            holder.tv_comment_count.setText(dataEntity.getGroup().getComment_count());
            holder.tv_unlike.setText(dataEntity.getGroup().getBury_count() + "");
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

//        if (NetUtils.isNetWorkConnected(mActivity)) {
        loadData();
//        } else {
//            loadCache();
//        }

    }

    private void loadData() {
        NeiHanApiImpl.getNeiHanData(mActivity, "-102", 10, 0x001, new MobCallBack() {
            @Override
            public void onSuccessList(int what, List results) {
            }

            @Override
            public void onSuccess(int what, Object result) {
                switch (what) {
                    case 0x001:
                        if (result == null) {
                            return;
                        }
                        try {
                            mLoadFinisCallBack.loadFinish(null);
//                            JSONObject object = new JSONObject(responseInfo.result);
//                            JSONObject data = object.getJSONObject("data");
//
//                            Joke joke = GsonUtil.jsonToBean(data.toString(), Joke.class);
                            if (page == 1) {
                                neiHanArrayList.clear();
                            }

                            neiHanArrayList.addAll(((NeiHanDataEntity) result).getData());
                            notifyDataSetChanged();
                            mLoadResultCallBack.onSuccess();


                            //缓存
//                            SaveDataBase(data.toString());
                        } catch (Exception e) {
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

    private void loadCache() throws DbException {
        /*if (dataBaseCrete == null) {
            dataBaseCrete = new DataBaseCrete(mActivity);
        }
        DataBase db = dataBaseCrete.findPage(page, Constants.menu4);
        if (null != db) {
            String request = db.getRequest();
            Joke joke = GsonUtil.jsonToBean(request, Joke.class);


            list.addAll(joke.getData());
            notifyDataSetChanged();
            mLoadResultCallBack.onSuccess();
            mLoadFinisCallBack.loadFinish(null);
        }*/

    }

    /**
     * 缓存数据,保存数据库
     *
     * @param request
     * @throws DbException
     */
    private void SaveDataBase(String request) throws DbException {
        /*dataBaseCrete = new DataBaseCrete(mActivity);
        dataBaseCrete.delete(page,Constants.menu4);

        DataBase data = new DataBase();
        data.setId(page);
        data.setRequest(request);
        data.setPage(page);
        data.setMenuNumber(Constants.menu4);
        dataBaseCrete.sava(data);*/
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

        public TestViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
