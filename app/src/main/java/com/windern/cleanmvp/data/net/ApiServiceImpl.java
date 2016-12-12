package com.windern.cleanmvp.data.net;

import com.windern.cleanmvp.data.model.Temp;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;

/**
 * Created by wenxinlin on 2016/10/26.
 */

public class ApiServiceImpl implements ApiService{
    @Override
    public Observable<List<Temp>> getTemps() {
        List<Temp> list = new ArrayList<>();
        list.add(new Temp(1,"11111"));
        list.add(new Temp(2,"22222"));
        list.add(new Temp(3,"33333"));
        return Observable.just(list);
    }

    @Override
    public Observable<Temp> getTemp(int id) {
        Temp temp = new Temp(id,String.valueOf(id)+String.valueOf(id)+String.valueOf(id)+String.valueOf(id)+String.valueOf(id));
        return Observable.just(temp);
    }
}
