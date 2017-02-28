package com.windern.cleanmvp.presentation.proconsumer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.windern.cleanmvp.R;

public class ProconsumerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proconsumer);

        Storage s = new Storage();

        Producer p = new Producer("小张",s);
        Producer p2 = new Producer("小李",s);
        Consumer c = new Consumer("老王",s);
        Consumer c2 = new Consumer("老刘",s);
        Consumer c3 = new Consumer("老林",s);

        new Thread(p).start();
        new Thread(p2).start();
        new Thread(c).start();
        new Thread(c2).start();
        new Thread(c3).start();
    }
}
