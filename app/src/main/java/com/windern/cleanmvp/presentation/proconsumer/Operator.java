package com.windern.cleanmvp.presentation.proconsumer;

/**
 * Created by windern on 2017/2/28.
 */

public class Operator implements Runnable {
    private String name;

    public Operator(String name){
        this.name = name;
    }

    @Override
    public void run() {
        try {
            while (true) {
                System.out.println(Thread.currentThread().getId()+":"+name + "-准备操作产品.");
                Consumer.getInstance().handleFinish();
                System.out.println(Thread.currentThread().getId()+":"+name + "-已操作产品.");
                Thread.sleep(400);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
