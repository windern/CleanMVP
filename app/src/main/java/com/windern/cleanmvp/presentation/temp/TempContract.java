package com.windern.cleanmvp.presentation.temp;

import com.windern.cleanmvp.data.model.Temp;
import com.windern.cleanmvp.presentation.BasePresenter;
import com.windern.cleanmvp.presentation.BaseView;

/**
 * Created by wenxinlin on 2016/10/25.
 */

public interface TempContract {
    interface View extends BaseView<Presenter>{
        void showTemp(Temp temp);
    }

    interface Presenter extends BasePresenter{
        void loadTemp(int id);
    }
}
