package com.windern.cleanmvp.presentation.loading;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.windern.cleanmvp.R;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ezy.ui.layout.LoadingLayout;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;

public class LoadingActivity extends AppCompatActivity {

    @BindView(R.id.loading)
    LoadingLayout loadingLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        ButterKnife.bind(this);

        showContent();
        loadingLayout.setRetryListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showContent();
            }
        });
    }


    public void showContent() {
        Observable.just(1)
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        loadingLayout.showLoading();
                    }
                })
                .delay(2, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        loadingLayout.showContent();
                    }
                });
    }

    public void showError() {
        Observable.just(1)
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        loadingLayout.showLoading();
                    }
                })
                .delay(2, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        loadingLayout.showError();
                    }
                });
    }

    public void showEmpty() {
        Observable.just(1)
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        loadingLayout.showLoading();
                    }
                })
                .delay(2, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        loadingLayout.showEmpty();
                    }
                });
    }

    @OnClick({R.id.btn_show_content, R.id.btn_show_error, R.id.btn_show_empty})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_show_content:
                showContent();
                break;
            case R.id.btn_show_error:
                showError();
                break;
            case R.id.btn_show_empty:
                showEmpty();
                break;
        }
    }
}
