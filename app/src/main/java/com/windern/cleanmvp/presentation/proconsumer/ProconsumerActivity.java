package com.windern.cleanmvp.presentation.proconsumer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.windern.cleanmvp.R;
import com.windern.cleanmvp.presentation.proconsumer.bluetooth.BtMessage;
import com.windern.cleanmvp.presentation.proconsumer.bluetooth.SendConsumer;
import com.windern.cleanmvp.presentation.proconsumer.bluetooth.SendProducer;
import com.windern.cleanmvp.presentation.proconsumer.bluetooth.SendStorage;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ProconsumerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proconsumer);

        SendStorage sendStorage = new SendStorage();
        SendProducer.getInstance().setSendStorage(sendStorage);
        SendConsumer.getInstance().setStorage(sendStorage);

        new Thread(SendConsumer.getInstance()).start();

//        loopSendMessage();
        new Thread(new LoopSendMessage()).start();

//        Observable.interval(100, TimeUnit.MILLISECONDS)
//                .observeOn(Schedulers.io())
//                .subscribe(new Subscriber<Long>() {
//                    @Override
//                    public void onCompleted() {
//
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//
//                    }
//
//                    @Override
//                    public void onNext(Long aLong) {
//                        sendMessageOne();
//                    }
//                });

        new Thread(new OperatorOne()).start();
        new Thread(new OperatorTwo()).start();

        new Thread(new SendOneRunnable()).start();

//        Observable.interval(200,TimeUnit.MILLISECONDS)
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Subscriber<Long>() {
//                    @Override
//                    public void onCompleted() {
//
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//
//                    }
//
//                    @Override
//                    public void onNext(Long aLong) {
//                        handleId++;
//                        System.out.println(Thread.currentThread().getId() + ":200-time:"+aLong+",handleId:"+handleId);
//                        SendConsumer.getInstance().handleBackMessage(handleId);
//                    }
//                });
//        Observable.interval(300,TimeUnit.MILLISECONDS)
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Subscriber<Long>() {
//                    @Override
//                    public void onCompleted() {
//
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//
//                    }
//
//                    @Override
//                    public void onNext(Long aLong) {
//                        handleId--;
//                        System.out.println(Thread.currentThread().getId() + ":300-time:"+aLong+",handleId:"+handleId);
//                        SendConsumer.getInstance().handleBackMessage(handleId);
//                    }
//                });
    }

    class OperatorOne implements Runnable {

        @Override
        public void run() {
            try {
                while (true) {
                    handleId++;
                    System.out.println(Thread.currentThread().getId() + ":200-handleId:" + handleId);
                    SendConsumer.getInstance().handleBackMessage(handleId);
                    Thread.sleep(200);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    class OperatorTwo implements Runnable {

        @Override
        public void run() {
            try {
                while (true) {
                    handleId--;
                    System.out.println(Thread.currentThread().getId() + ":300-handleId:" + handleId);
                    SendConsumer.getInstance().handleBackMessage(handleId);
                    Thread.sleep(300);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    class SendOneRunnable implements Runnable{
        @Override
        public void run() {
            try {
                while (true) {
                    sendMessageOne();
                    Thread.sleep(100);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private long size = 0;
    private long handleId = 0;

    class LoopSendMessage implements Runnable{
        @Override
        public void run() {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            loopSendMessage();
        }
    }

    public void loopSendMessage() {
        size++;
        String message = "消息" + size + "消息";
        System.out.println(Thread.currentThread().getId() + ":loopSendMessage(" + size + ").");
        BtMessage btMessage = SendProducer.getInstance().sendMessage(message);
        btMessage.getPublishSubject()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(String s) {
                        System.out.println(Thread.currentThread().getId() + ":loop消息执行成功返回(" + s + ").");
                        new Thread(new LoopSendMessage()).start();
                    }
                });
    }

    public void sendMessageOne() {
        size++;
        String message = "消息" + size + "消息";
        System.out.println(Thread.currentThread().getId() + ":sendMessageOne(" + size + ").");
        BtMessage btMessage = SendProducer.getInstance().sendMessage(message);
        btMessage.getPublishSubject()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(String s) {
                        System.out.println(Thread.currentThread().getId() + ":one消息执行成功返回(" + s + ").");
                    }
                });
    }

    private void old() {
        Storage s = new Storage();

        Producer p = new Producer("小张", s);
        Producer p2 = new Producer("小李", s);
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
