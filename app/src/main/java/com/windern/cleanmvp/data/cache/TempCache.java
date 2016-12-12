package com.windern.cleanmvp.data.cache;

import com.windern.cleanmvp.data.model.Temp;
import com.windern.cleanmvp.data.repository.TempRepository;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by wenxinlin on 2016/11/9.
 */

public class TempCache implements TempRepository {
    @Override
    public Observable<List<Temp>> getTemps() {
        List<Temp> list = new ArrayList<>();
        list.add(new Temp(1, "11111"));
        list.add(new Temp(2, "22222"));
        list.add(new Temp(3, "33333"));
        return Observable.just(list);
    }

    @Override
    public Observable<Temp> getTemp(final int id) {
        return Observable.create(new Observable.OnSubscribe<Temp>() {

            @Override
            public void call(Subscriber<? super Temp> subscriber) {
                Temp temp = new Temp(id, String.valueOf(id) + String.valueOf(id) + String.valueOf(id) + String.valueOf(id) + String.valueOf(id));
                if (id % 3 == 0) {
                    temp.name = "from cache:" + temp.name;
                    subscriber.onNext(temp);
                } else {
                    subscriber.onCompleted();
                }
            }
        });
    }
}
