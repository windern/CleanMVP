package com.windern.cleanmvp.presentation.temp;

import com.windern.cleanmvp.presentation.di.ActivityScoped;
import com.windern.cleanmvp.presentation.ApplicationComponent;

import dagger.Component;

/**
 * Created by wenxinlin on 2016/10/25.
 */
@ActivityScoped
@Component(dependencies = ApplicationComponent.class,modules = TempModule.class)
public interface TempComponent {
    void inject(TempActivity tempActivity);
}
