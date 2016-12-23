package com.windern.cleanmvp.presentation.dialog;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.utils.ToastUtils;
import com.pnikosis.materialishprogress.ProgressWheel;
import com.windern.cleanmvp.R;

import java.util.concurrent.TimeUnit;

import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.functions.Action1;

public class DialogPracticeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog_practice);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_show_dialog)
    public void onClick() {
        showDialog();
    }

    private ProgressDialog progressDialog;

    @OnClick(R.id.btn_show_progress_dialog)
    public void showProgressDialog() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(DialogPracticeActivity.this);
            progressDialog.setMessage("正在处理，请稍后……");
            //设置进度条是否可以按退回键取消
            progressDialog.setCancelable(false);
            //设置点击进度对话框外的区域对话框不消失
            progressDialog.setCanceledOnTouchOutside(false);
        }
        progressDialog.show();

        Observable.just(1)
                .delay(3, TimeUnit.SECONDS)
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        progressDialog.dismiss();
                    }
                });
    }

    private AlertDialog selfDialog;
    @OnClick(R.id.btn_show_self_progress_dialog)
    public void showSelfProgressDialog(){
        if(selfDialog==null){
            LayoutInflater layoutInflater = LayoutInflater.from(this);
            View view = layoutInflater.inflate(R.layout.my_progress_dialog,null);
            ProgressWheel progressWheel = (ProgressWheel)view.findViewById(R.id.progress_wheel);
            progressWheel.spin();
            TextView tvMessage = (TextView)view.findViewById(R.id.tv_message);
            tvMessage.setText("正在进行中……");
            AlertDialog.Builder builder = new AlertDialog.Builder(DialogPracticeActivity.this);
            builder.setView(view);
            selfDialog = builder.create();
            selfDialog.setCancelable(false);
            selfDialog.setCanceledOnTouchOutside(false);
        }
        selfDialog.show();
        Observable.just(1)
                .delay(3, TimeUnit.SECONDS)
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        selfDialog.dismiss();
                    }
                });
    }

    private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(DialogPracticeActivity.this);
        builder.setTitle("测试标题")
                .setMessage("hello")
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ToastUtils.showShortToast("点击取消");
                    }
                })
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ToastUtils.showShortToast("点击确定");
                    }
                })
                .create()
                .show();
    }
}
