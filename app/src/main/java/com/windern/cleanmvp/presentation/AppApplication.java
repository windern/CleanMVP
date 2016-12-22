package com.windern.cleanmvp.presentation;

import android.app.Application;

import com.blankj.utilcode.utils.Utils;
import com.windern.cleanmvp.data.database.DaoMaster;
import com.windern.cleanmvp.data.database.DaoSession;
import com.windern.cleanmvp.data.database.DatabaseManagerHelper;

import org.greenrobot.greendao.database.Database;

import timber.log.Timber;

/**
 * Created by wenxinlin on 2016/10/25.
 */

public class AppApplication extends Application {
    private ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        Timber.plant(new Timber.DebugTree());
        Timber.tag("windern");
        Utils.init(this);

        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();

        DatabaseManagerHelper.getInstance().init(this);
    }

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }
}
