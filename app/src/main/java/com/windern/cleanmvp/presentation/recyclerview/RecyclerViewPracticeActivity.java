package com.windern.cleanmvp.presentation.recyclerview;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.windern.cleanmvp.R;
import com.windern.cleanmvp.data.database.DatabaseManagerHelper;
import com.windern.cleanmvp.data.database.Note;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

public class RecyclerViewPracticeActivity extends AppCompatActivity
        implements RecyclerViewPracticeContract.View {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rcv_article_origin)
    RecyclerView rcvArticleOrigin;
    @BindView(R.id.swiperefreshlayout)
    SwipeRefreshLayout swiperefreshlayout;

    private RecyclerViewPracticeContract.Presenter presenter;

    private List<Note> noteList = new ArrayList<>();
    private NoteRecyclerAdapter noteRecyclerAdapter;

    private int lastVisibleItem = 0;
    private LinearLayoutManager linearLayoutManager;
    private boolean isLoadMore = false;
    private int loadState = LOAD_IDLE;
    private static final int LOAD_IDLE = 0;
    private static final int LOAD_FRESH = 1;
    private static final int LOAD_MORE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view_practice);
        ButterKnife.bind(this);

        presenter = new RecyclerViewPracticePresenter(this);

        toolbar.setTitle("测试");
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

        swiperefreshlayout.setColorSchemeColors(Color.RED, Color.BLUE, Color.GREEN);
        swiperefreshlayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (loadState == LOAD_IDLE) {
                    refresh();
                } else {
                    swiperefreshlayout.setRefreshing(false);
                }
            }
        });

        linearLayoutManager = new LinearLayoutManager(this);
        rcvArticleOrigin.setLayoutManager(linearLayoutManager);
        rcvArticleOrigin.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE
                        && lastVisibleItem + 1 == noteRecyclerAdapter.getItemCount()) {
                    Timber.d("getMore");
                    getMore();
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
            }
        });


        noteRecyclerAdapter = new NoteRecyclerAdapter(noteList);
        rcvArticleOrigin.setAdapter(noteRecyclerAdapter);

        refresh();
    }

    public void add() {
        Note note = new Note();
        note.setText("hello:" + String.valueOf(Calendar.getInstance().getTime().getTime()));
        presenter.add(note);
    }

    private void refresh() {
        if (getLoadState() == LOAD_IDLE) {
            setLoadState(LOAD_FRESH);
            noteList.clear();
            noteRecyclerAdapter.notifyDataSetChanged();
            presenter.refresh();
        }
    }

    private void getMore() {
        if (getLoadState() == LOAD_IDLE) {
            setLoadState(LOAD_MORE);
            presenter.getMore();
        }
    }

    private synchronized void setLoadState(int state) {
        loadState = state;
    }

    private synchronized int getLoadState() {
        return loadState;
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
        setLoadState(LOAD_FRESH);
        swiperefreshlayout.setRefreshing(true);
    }

    @Override
    public void hideRefreshLoading() {
        setLoadState(LOAD_IDLE);
        swiperefreshlayout.setRefreshing(false);
    }

    @Override
    public void showMoreLoading() {
        setLoadState(LOAD_MORE);
        noteList.add(null);
        noteRecyclerAdapter.notifyDataSetChanged();
        showToast("显示加载更多");
    }

    @Override
    public void hideMoreLoading() {
        setLoadState(LOAD_IDLE);
        noteList.remove(noteList.size() - 1);
        noteRecyclerAdapter.notifyDataSetChanged();
        showToast("隐藏加载更多");
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
}
