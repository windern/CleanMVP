package com.windern.cleanmvp.presentation.note;

import com.windern.cleanmvp.data.database.Note;
import com.windern.cleanmvp.data.net.ApiService;
import com.windern.cleanmvp.data.net.ApiServiceImpl;

import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by wenxinlin on 2016/12/12.
 */

public class NotePresenter implements NoteContract.Presenter{
    private NoteContract.View view;

    public NotePresenter(NoteContract.View view){
        this.view = view;
    }

    @Override
    public void loadNotes() {
        ApiService apiService = new ApiServiceImpl();
        apiService.getNotes()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Note>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        view.showError(e.getMessage());
                    }

                    @Override
                    public void onNext(List<Note> notes) {
                        view.showNotes(notes);
                    }
                });
    }

    @Override
    public void addNote(Note note) {
        ApiService apiService = new ApiServiceImpl();
        apiService.saveNote(note)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Note>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        view.showError(e.getMessage());
                    }

                    @Override
                    public void onNext(Note note) {
                        view.showNote(note);
                    }
                });
    }

    @Override
    public void loadNote(long id) {
        ApiService apiService = new ApiServiceImpl();
        apiService.getNote(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Note>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        view.showError(e.getMessage());
                    }

                    @Override
                    public void onNext(Note note) {
                        view.showNote(note);
                    }
                });
    }
}
