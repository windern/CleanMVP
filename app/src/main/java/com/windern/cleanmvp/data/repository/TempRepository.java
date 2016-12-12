package com.windern.cleanmvp.data.repository;

import com.windern.cleanmvp.data.model.Temp;

import java.util.List;

import rx.Observable;

/**
 * Created by wenxinlin on 2016/10/25.
 */

public interface TempRepository {
    Observable<List<Temp>> getTemps();
    Observable<Temp> getTemp(int id);
}
