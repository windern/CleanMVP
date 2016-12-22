package com.windern.cleanmvp.presentation.search;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.blankj.utilcode.utils.ToastUtils;
import com.windern.cleanmvp.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchActivity extends AppCompatActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.tv_test)
    TextView tvTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);

        toolbar.setTitle("搜索");
        setSupportActionBar(toolbar);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.action_search:
                        ToastUtils.showShortToast("搜索菜单点击");
                        break;
                    case R.id.action_setting:
                        Intent intent = new Intent(SearchActivity.this,SearchResultsActivity.class);
                        startActivity(intent);
                        break;
                }
                return false;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_search_activity, menu);

        MenuItem menuItemSearch = menu.findItem(R.id.action_search);//在菜单中找到对应控件的item
        //监听菜单展开关闭的事件
        MenuItemCompat.setOnActionExpandListener(menuItemSearch, new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                refresh();
                return true;
            }
        });

        SearchView searchView = (SearchView) menuItemSearch.getActionView();
//        SearchView searchView =
//                (SearchView) menu.findItem(R.id.action_search).getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                search(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return true;
    }

    public void search(String text) {
        tvTest.setText(text);
    }

    public void refresh(){
        tvTest.setText("默认");
    }

}
