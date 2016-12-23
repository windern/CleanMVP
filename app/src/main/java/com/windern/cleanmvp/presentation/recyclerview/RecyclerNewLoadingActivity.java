package com.windern.cleanmvp.presentation.recyclerview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.windern.cleanmvp.R;
import com.windern.cleanmvp.data.database.Note;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bingoogolapple.refreshlayout.BGAMeiTuanRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import cn.bingoogolapple.refreshlayout.BGARefreshViewHolder;
import ezy.ui.layout.LoadingLayout;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import timber.log.Timber;

public class RecyclerNewLoadingActivity extends AppCompatActivity
        implements RecyclerViewPracticeContract.View
        , BGARefreshLayout.BGARefreshLayoutDelegate {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rcv_article_origin)
    RecyclerView rcvArticleOrigin;
    @BindView(R.id.refresh_layout)
    BGARefreshLayout refreshLayout;
    @BindView(R.id.loading)
    LoadingLayout loading;

    private RecyclerViewPracticeContract.Presenter presenter;

    private List<Note> noteList = new ArrayList<>();
    private NoteRecyclerAdapter noteRecyclerAdapter;

    private LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_new_loading);
        ButterKnife.bind(this);

        presenter = new RecyclerNewLoadingPresenter(this);

        toolbar.setTitle("测试列表");
        setSupportActionBar(toolbar);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_add:
                        add();
                        break;
                    case R.id.menu_sync:
                        refresh();
                        break;
                }
                return false;
            }
        });

        initRefreshLayout();

        linearLayoutManager = new LinearLayoutManager(this);
        rcvArticleOrigin.setLayoutManager(linearLayoutManager);

        noteRecyclerAdapter = new NoteRecyclerAdapter(noteList);
        rcvArticleOrigin.setAdapter(noteRecyclerAdapter);

        loading.showContent();

        refresh();
    }

    public void add() {
        Note note = new Note();
        note.setText("hello:" + String.valueOf(Calendar.getInstance().getTime().getTime()));
        presenter.add(note);
    }

    private void refresh() {
        //beginRefreshing的时候会调用onBGARefreshLayoutBeginRefreshing
        refreshLayout.beginRefreshing();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.recycler_practice, menu);
        return true;
    }

    @Override
    public void showError(String tip) {
        showToast(tip);
    }

    @Override
    public void showRefreshLoading() {

    }

    @Override
    public void hideRefreshLoading() {
        refreshLayout.endRefreshing();
    }

    @Override
    public void showMoreLoading() {

    }

    @Override
    public void hideMoreLoading() {
        noteRecyclerAdapter.notifyDataSetChanged();
        showToast("隐藏加载更多");
        refreshLayout.endLoadingMore();
    }

    @Override
    public void addFinish() {
        refresh();
    }

    @Override
    public void updateList(List<Note> list) {
        noteList.addAll(list);
        noteRecyclerAdapter.notifyDataSetChanged();
    }

    private void showToast(String tip) {
        Toast.makeText(this, tip, Toast.LENGTH_SHORT).show();
    }

    private void initRefreshLayout() {
        refreshLayout.setDelegate(this);
        BGARefreshViewHolder refreshViewHolder = new BGANormalRefreshViewHolder(RecyclerNewLoadingActivity.this, true);
        refreshLayout.setRefreshViewHolder(refreshViewHolder);
        refreshLayout.setIsShowLoadingMoreView(true);
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        noteList.clear();
        noteRecyclerAdapter.notifyDataSetChanged();
        presenter.refresh();
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        presenter.getMore();
        return true;
    }
}
