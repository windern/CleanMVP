package com.windern.cleanmvp.presentation.proconsumer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.windern.cleanmvp.R;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;

public class ProconsumerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proconsumer);

        Storage s = new Storage();

        Producer p = new Producer("小张",s);
        Producer p2 = new Producer("小李",s);
        Consumer c = Consumer.getInstance();
        c.setName("消费者");
        c.setStorage(s);

        Operator op = new Operator("操作者1");
        Operator op2 = new Operator("操作者2");

        new Thread(p).start();
        new Thread(p2).start();
        new Thread(c).start();
        new Thread(op).start();
        new Thread(op2).start();
    }
}
