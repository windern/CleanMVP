package com.windern.cleanmvp.presentation.recyclerview;

import com.windern.cleanmvp.data.database.DatabaseManagerHelper;
import com.windern.cleanmvp.data.database.Note;
import com.windern.cleanmvp.data.database.NoteDao;

import org.greenrobot.greendao.database.Database;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by wenxinlin on 2016/12/15.
 */

public class RecyclerViewPracticePresenter implements RecyclerViewPracticeContract.Presenter {
    private RecyclerViewPracticeContract.View view;
    private int page = 0;
    private int pageSize = 20;

    public RecyclerViewPracticePresenter(RecyclerViewPracticeContract.View view) {
        this.view = view;
    }

    @Override
    public void add(Note note) {
        NoteDao noteDao = DatabaseManagerHelper.getInstance().daoSession.getNoteDao();
        noteDao.rx().insert(note)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Note>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showError(e.getMessage());
                    }

                    @Override
                    public void onNext(Note note) {
                        view.addFinish();
                    }
                });
    }

    @Override
    public void refresh() {
        view.showRefreshLoading();
        page = 0;

        NoteDao noteDao = DatabaseManagerHelper.getInstance().daoSession.getNoteDao();
        Observable<List<Note>> observerable = noteDao.queryBuilder()
                .offset(page * pageSize)
                .limit(pageSize)
                .orderDesc(NoteDao.Properties.Id)
                .rx().list();
        observerable.subscribeOn(Schedulers.io())
                .delay(3, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Note>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        view.hideRefreshLoading();
                        view.showError(e.getMessage());
                    }

                    @Override
                    public void onNext(List<Note> notes) {
                        view.hideRefreshLoading();
                        view.updateList(notes);
                    }
                });
    }

    @Override
    public void getMore() {
        page++;
        view.showMoreLoading();

        NoteDao noteDao = DatabaseManagerHelper.getInstance().daoSession.getNoteDao();
        Observable<List<Note>> observerable = noteDao.queryBuilder()
                .offset(page * pageSize)
                .limit(pageSize)
                .orderDesc(NoteDao.Properties.Id)
                .rx().list();
        observerable.subscribeOn(Schedulers.io())
                .delay(3, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Note>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        view.hideMoreLoading();
                        view.showError(e.getMessage());
                    }

                    @Override
                    public void onNext(List<Note> notes) {
                        view.hideMoreLoading();
                        view.updateList(notes);
                    }
                });
    }
}
