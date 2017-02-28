package com.windern.cleanmvp.presentation.proconsumer.bluetooth;

import com.windern.cleanmvp.presentation.proconsumer.Consumer;
import com.windern.cleanmvp.presentation.proconsumer.Storage;

/**
 * Created by windern on 2017/2/28.
 */

public class SendConsumer implements Runnable {
    public static SendConsumer getInstance(){
        return SendConsumerHolder.sendConsumer;
    }

    private static class SendConsumerHolder{
        private final static SendConsumer sendConsumer = new SendConsumer();
    }

    private Integer lock = 1;
    private BtMessage nowHandle;
    private SendStorage storage;

    public void setStorage(SendStorage storage) {
        this.storage = storage;
    }

    public void run() {
        try {
            while (true) {
                sendMessage();
                Thread.sleep(100);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(){
        try {
            synchronized (lock) {
                System.out.println(Thread.currentThread().getId() + ":准备底层发送蓝牙消息");
                if (nowHandle == null) {
                    nowHandle = storage.pop();
                }
                System.out.println(Thread.currentThread().getId() + ":底层发送蓝牙消息：" + nowHandle.getId());
                System.out.println(Thread.currentThread().getId() + ":" + "===============");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void handleBackMessage(long backId){
        synchronized (lock){
            System.out.println(Thread.currentThread().getId()+":handleBackMessage：收到返回处理backId:"+backId);
            if(nowHandle!=null) {
                if (backId == nowHandle.getId()) {
                    System.out.println(Thread.currentThread().getId() + ":handleBackMessage：准备开始处理");
                    BtMessage last = nowHandle;
                    nowHandle = null;
                    //这个写会死锁
                    //sendMessage()
                    //函数整个执行完才会解锁
                    last.getPublishSubject().onNext("完成" + last.getId() + "," + last.getMesssage());
                    System.out.println(Thread.currentThread().getId() + ":handleBackMessage：处理成功：id:" + last.getId());
                } else {
                    System.out.println(Thread.currentThread().getId() + ":handleBackMessage：编号不等不处理");
                }
            }else{
                System.out.println(Thread.currentThread().getId() + ":handleBackMessage：nowHandle==null不处理");
            }
        }
    }
}
