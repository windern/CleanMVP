package com.windern.cleanmvp.data.net;

import com.windern.cleanmvp.data.repository.TempRepository;
import com.windern.cleanmvp.data.model.Temp;

import java.util.List;

import rx.Observable;

/**
 * Created by wenxinlin on 2016/10/25.
 */

public class TempRemoteRepository implements TempRepository{
    private ApiService service;

    public TempRemoteRepository(ApiService service){
        this.service = service;
    }

    @Override
    public Observable<List<Temp>> getTemps() {
        return service.getTemps();
    }

    @Override
    public Observable<Temp> getTemp(int id) {
        return service.getTemp(id);
    }
}
