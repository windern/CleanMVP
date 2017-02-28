package com.windern.cleanmvp.presentation.proconsumer;

/**
 * Created by windern on 2017/2/28.
 */

public class Product {
    private int id;

    public Product(int id) {
        this.id = id;
    }

    public String toString() {// 重写toString方法
        return "产品：" + this.id;
    }
}
