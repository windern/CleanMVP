package com.windern.cleanmvp.presentation.temp;

import com.windern.cleanmvp.data.cache.TempCache;
import com.windern.cleanmvp.data.disk.TempDisk;
import com.windern.cleanmvp.data.net.ApiService;
import com.windern.cleanmvp.data.net.TempRemoteRepository;
import com.windern.cleanmvp.data.repository.TempRepository;
import com.windern.cleanmvp.domain.interactor.TempUseCase;
import com.windern.cleanmvp.presentation.temp.TempContract;

import dagger.Module;
import dagger.Provides;

/**
 * Created by wenxinlin on 2016/10/25.
 */

@Module
public class TempModule {
    private TempContract.View view;
    public TempModule(TempContract.View view){
        this.view = view;
    }

    @Provides
    TempContract.View provideView(){
        return view;
    }

    @Provides
    TempRemoteRepository provideTempRemoteRepository(ApiService apiService){
        return new TempRemoteRepository(apiService);
    }

    @Provides
    TempUseCase provideTempUseCase(TempCache tempCache,TempDisk tempDisk,TempRemoteRepository tempRemoteRepository){
        return new TempUseCase(tempCache,tempDisk,tempRemoteRepository);
    }
}
