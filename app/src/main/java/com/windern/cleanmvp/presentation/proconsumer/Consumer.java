package com.windern.cleanmvp.presentation.proconsumer;

import android.content.Context;

/**
 * Created by windern on 2017/2/28.
 */

public class Consumer implements Runnable {
    public static Consumer getInstance(){
        return ConsumerHolder.consumer;
    }

    private static class ConsumerHolder{
        private final static Consumer consumer = new Consumer();
    }

    private String name;
    private Storage storage = null;
    private Product nowHandleProduct = null;
    private Integer lock = 1;

    private Consumer(){

    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStorage(Storage storage) {
        this.storage = storage;
    }

    public void run() {
        try {
            while (true) {
                System.out.println(Thread.currentThread().getId()+":"+name + "-准备消费产品.");
                if(nowHandleProduct==null){
                    nowHandleProduct = storage.pop();
                }
                System.out.println(Thread.currentThread().getId()+":"+name + "-已消费(" + nowHandleProduct.toString() + ").");
                System.out.println(Thread.currentThread().getId()+":"+"===============");
                Thread.sleep(100);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void handleFinish(){
        synchronized (lock) {
            if (nowHandleProduct != null) {
                System.out.println(Thread.currentThread().getId() + ":" + name + "正在消费(" + nowHandleProduct.toString() + ").");
                Product last = nowHandleProduct;
                nowHandleProduct = null;
                System.out.println(Thread.currentThread().getId() + ":" + name + "消费成功(" + last.toString() + ").");
            } else{
                System.out.println(Thread.currentThread().getId() + ":" + name + "正在消费(null产品).");
            }
        }
    }
}
