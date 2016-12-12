package com.windern.cleanmvp.presentation;

import android.app.Application;

/**
 * Created by wenxinlin on 2016/10/25.
 */

public class AppApplication extends Application{
    private ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    public ApplicationComponent getApplicationComponent(){
        return applicationComponent;
    }
}
