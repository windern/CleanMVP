package com.windern.cleanmvp.presentation.di;

import java.lang.annotation.Retention;
import javax.inject.Scope;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by wenxinlin on 2016/10/25.
 */
@Scope
@Retention(RUNTIME)
public @interface ActivityScoped {}
