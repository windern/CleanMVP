package com.windern.cleanmvp.presentation;

import com.windern.cleanmvp.data.cache.TempCache;
import com.windern.cleanmvp.data.disk.TempDisk;
import com.windern.cleanmvp.data.net.ApiService;
import com.windern.cleanmvp.presentation.AppApplication;
import com.windern.cleanmvp.presentation.ApplicationModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by wenxinlin on 2016/10/25.
 */
@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {
    void inject(AppApplication appApplication);

    AppApplication getAppApplication();
    ApiService getApiService();
    TempCache getTempCache();
    TempDisk getTempDisk();
}
