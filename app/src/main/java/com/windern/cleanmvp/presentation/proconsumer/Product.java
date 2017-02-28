package com.windern.cleanmvp.presentation.proconsumer;

import rx.subjects.PublishSubject;

/**
 * Created by windern on 2017/2/28.
 */

public class Product {
    private int id;
    private PublishSubject<String> publishSubject;

    public Product(int id) {
        this.id = id;
    }

    public String toString() {// 重写toString方法
        return "产品：" + this.id;
    }

    public PublishSubject<String> getPublishSubject() {
        return publishSubject;
    }

    public void createPublishSubject(){
        publishSubject = PublishSubject.create();
    }
}
