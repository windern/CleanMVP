package com.windern.cleanmvp.data.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

/**
 * Created by wenxinlin on 2016/10/25.
 */

public class Temp {
    public int id;
    public String name;

    public Temp(){

    }

    public Temp(int id,String name){
        this.id = id;
        this.name = name;
    }
}
