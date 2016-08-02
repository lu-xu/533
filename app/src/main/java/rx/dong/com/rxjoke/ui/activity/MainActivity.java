package rx.dong.com.rxjoke.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nineoldandroids.view.ViewHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.dong.com.rxjoke.R;
import rx.dong.com.rxjoke.common.AutoLoadRecylerView;
import rx.dong.com.rxjoke.common.AutoLoadRecylerView.loadMoreListener;
import rx.dong.com.rxjoke.common.DividerItemDecoration;
import rx.dong.com.rxjoke.model.ContentlistEntity;
import rx.dong.com.rxjoke.presenter.JokePresenter;
import rx.dong.com.rxjoke.ui.BaseActivity;
import rx.dong.com.rxjoke.ui.adapter.JokeAdapter;
import rx.dong.com.rxjoke.ui.view.JokeView;

/**
 * Created by JDD on 2016/4/8.
 */
public class MainActivity extends BaseActivity implements JokeView,
        SwipeRefreshLayout.OnRefreshListener, loadMoreListener {

    @Bind(R.id.record_recycleview)
    AutoLoadRecylerView recordRecycleview;
    @Bind(R.id.common_error_txt)
    TextView commonErrorTxt;
    @Bind(R.id.retry_btn)
    Button retryBtn;
    @Bind(R.id.common_error)
    RelativeLayout commonError;
    @Bind(R.id.joke_refresh_layout)
    SwipeRefreshLayout jokeRefreshLayout;
    @Bind(R.id.id_drawerLayout)
    DrawerLayout mDrawerLayout;
    private JokePresenter jokePresenter;
    private LinearLayoutManager layoutManager;
    private int page = 1;
    private List<ContentlistEntity> jokeList;
    private JokeAdapter jokeAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initView();
        initEvents();
        initData();
        loadData();


    }

    private void initView() {
        jokeRefreshLayout.setOnRefreshListener(this);
        layoutManager = new LinearLayoutManager(this);
        recordRecycleview.setLayoutManager(layoutManager);
        recordRecycleview.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration
                .VERTICAL_LIST));
        recordRecycleview.setLoadMoreListener(this);
        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED,
                Gravity.RIGHT);
    }
    private void initEvents()
    {
        mDrawerLayout.setDrawerListener(new DrawerLayout.DrawerListener()
        {
            @Override
            public void onDrawerStateChanged(int newState)
            {
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset)
            {
                View mContent = mDrawerLayout.getChildAt(0);
                View mMenu = drawerView;
                float scale = 1 - slideOffset;
                float rightScale = 0.8f + scale * 0.2f;

                if (drawerView.getTag().equals("LEFT"))
                {

                    float leftScale = 1 - 0.3f * scale;

                    ViewHelper.setScaleX(mMenu, leftScale);
                    ViewHelper.setScaleY(mMenu, leftScale);
                    ViewHelper.setAlpha(mMenu, 0.6f + 0.4f * (1 - scale));
                    ViewHelper.setTranslationX(mContent,
                            mMenu.getMeasuredWidth() * (1 - scale));
                    ViewHelper.setPivotX(mContent, 0);
                    ViewHelper.setPivotY(mContent,
                            mContent.getMeasuredHeight() / 2);
                    mContent.invalidate();
                    ViewHelper.setScaleX(mContent, rightScale);
                    ViewHelper.setScaleY(mContent, rightScale);
                } else
                {
                    ViewHelper.setTranslationX(mContent,
                            -mMenu.getMeasuredWidth() * slideOffset);
                    ViewHelper.setPivotX(mContent, mContent.getMeasuredWidth());
                    ViewHelper.setPivotY(mContent,
                            mContent.getMeasuredHeight() / 2);
                    mContent.invalidate();
                    ViewHelper.setScaleX(mContent, rightScale);
                    ViewHelper.setScaleY(mContent, rightScale);
                }

            }

            @Override
            public void onDrawerOpened(View drawerView)
            {
            }

            @Override
            public void onDrawerClosed(View drawerView)
            {
                mDrawerLayout.setDrawerLockMode(
                        DrawerLayout.LOCK_MODE_LOCKED_CLOSED, Gravity.RIGHT);
            }
        });
    }
    private void initData() {
        jokeList = new ArrayList<>();
        jokeAdapter = new JokeAdapter(this,jokeList);
        recordRecycleview.setAdapter(jokeAdapter);

        jokePresenter = new JokePresenter();
        jokePresenter.attachView(this);
    }

    private void loadData() {
        jokePresenter.loadList(page);
        page++;
    }

    @Override
    public void onRefresh() {
        page = 1;
        loadData();
    }


    @Override
    public void onLoadMore() {
        loadData();
    }

    /**
     * @param data 回调 以下三个方法是presenter回调的函数 用于更新界面
     */
    @Override
    public void refresh(List<ContentlistEntity> data) {
        commonError.setVisibility(View.GONE);
        jokeList.clear();
        jokeList.addAll(data);
        jokeAdapter.notifyDataSetChanged();
        jokeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void loadMore(List<ContentlistEntity> data) {
        commonError.setVisibility(View.GONE);
        jokeList.addAll(data);
        jokeAdapter.notifyDataSetChanged();
        recordRecycleview.setLoading(false);
    }

    @Override
    public void showError(String msg, View.OnClickListener onClickListener) {
        super.showError(msg, onClickListener);
        commonError.setVisibility(View.VISIBLE);
        recordRecycleview.setLoading(false);
        jokeRefreshLayout.setRefreshing(false);
    }

    @OnClick(R.id.retry_btn)
    void setRetryBtnonClick() {
        onRefresh();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        jokePresenter.detachView();
    }
}
