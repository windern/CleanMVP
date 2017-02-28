package com.windern.cleanmvp.presentation.proconsumer;

import rx.Subscriber;

/**
 * Created by windern on 2017/2/28.
 */

public class Producer implements Runnable {
    private String name;
    private Storage s = null;

    public Producer(String name, Storage s) {
        this.name = name;
        this.s = s;
    }

    public void run() {
        try {
            while (true) {
                Product product = new Product((int) (Math.random() * 10000)); // 产生0~9999随机整数
                product.createPublishSubject();
                product.getPublishSubject()
                        .subscribe(new Subscriber<String>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onNext(String s) {
                                System.out.println(Thread.currentThread().getId()+":成功返回("+s+").");
                            }
                        });
                System.out.println(Thread.currentThread().getId()+":"+name + "-准备生产(" + product.toString() + ").");
                s.push(product);
                System.out.println(Thread.currentThread().getId()+":"+name + "-已生产(" + product.toString() + ").");
                System.out.println(Thread.currentThread().getId()+":"+"===============");
                Thread.sleep(500);
            }
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }

    }
}
