package com.windern.cleanmvp.presentation;

import android.app.Application;
import android.content.Context;

import com.windern.cleanmvp.data.cache.TempCache;
import com.windern.cleanmvp.data.disk.TempDisk;
import com.windern.cleanmvp.data.net.ApiService;
import com.windern.cleanmvp.data.net.ApiServiceImpl;
import com.windern.cleanmvp.presentation.AppApplication;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by wenxinlin on 2016/10/25.
 */

@Module
public class ApplicationModule {
    private final AppApplication appApplication;

    public ApplicationModule(AppApplication appApplication){
        this.appApplication = appApplication;
    }

    @Provides
    AppApplication provideAppApplication(){
        return appApplication;
    }

    @Singleton
    @Provides
    ApiService provideApiService(){
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl("https://api.github.com/")
//                //String和Gson的支持不能写反，否则String还是解析不了
//                //增加返回值为String的支持
//                .addConverterFactory(ScalarsConverterFactory.create())
//                //增加返回值为Gson的支持(以实体类返回)
//                .addConverterFactory(GsonConverterFactory.create())
//                //增加返回值为Oservable<T>的支持
//                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
//                .build();
//
//        ApiService apiService = retrofit.create(ApiService.class);
        //替代retrofit的
        ApiService apiService = new ApiServiceImpl();
        return apiService;
    }

    @Singleton
    @Provides
    TempCache provideTempCache(){
        return new TempCache();
    }

    @Singleton
    @Provides
    TempDisk provideTempDisk(){
        return new TempDisk();
    }
}
