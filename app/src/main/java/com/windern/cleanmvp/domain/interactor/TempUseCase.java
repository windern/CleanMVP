package com.windern.cleanmvp.domain.interactor;

import com.windern.cleanmvp.data.cache.TempCache;
import com.windern.cleanmvp.data.disk.TempDisk;
import com.windern.cleanmvp.data.net.TempRemoteRepository;
import com.windern.cleanmvp.data.repository.TempRepository;
import com.windern.cleanmvp.data.model.Temp;

import java.util.List;

import rx.Observable;

/**
 * Created by wenxinlin on 2016/10/25.
 */

public class TempUseCase {
    private final TempCache tempCache;
    private final TempDisk tempDisk;
    private final TempRemoteRepository tempRemoteRepository;

    public TempUseCase(TempCache tempCache, TempDisk tempDisk, TempRemoteRepository tempRemoteRepository) {
        super();
        this.tempCache = tempCache;
        this.tempDisk = tempDisk;
        this.tempRemoteRepository = tempRemoteRepository;
    }

    public Observable<Temp> getTemp(int id) {
        return Observable.concat(tempCache.getTemp(id), tempDisk.getTemp(id), tempRemoteRepository.getTemp(id))
                .first();
    }

    public Observable<List<Temp>> getTemps() {
        return Observable.concat(tempCache.getTemps(), tempDisk.getTemps(), tempRemoteRepository.getTemps())
                .first();
    }
}
