package com.windern.cleanmvp.presentation.temp;

import com.windern.cleanmvp.data.model.Temp;
import com.windern.cleanmvp.data.repository.TempRepository;
import com.windern.cleanmvp.domain.interactor.TempUseCase;

import javax.inject.Inject;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by wenxinlin on 2016/10/25.
 */

public class TempPresenter implements TempContract.Presenter{
    private TempContract.View view;
    private TempUseCase tempUseCase;

    @Inject
    TempPresenter(TempUseCase tempUseCase, TempContract.View view){
        this.tempUseCase = tempUseCase;
        this.view = view;
    }

    @Override
    public void loadTemp(int id) {
        tempUseCase.getTemp(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Temp>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Temp temp) {
                        view.showTemp(temp);
                    }
                });
    }
}