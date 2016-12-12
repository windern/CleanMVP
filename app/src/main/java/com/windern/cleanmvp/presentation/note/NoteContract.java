package com.windern.cleanmvp.presentation.note;

import com.windern.cleanmvp.data.database.Note;
import com.windern.cleanmvp.data.model.Temp;
import com.windern.cleanmvp.presentation.BasePresenter;
import com.windern.cleanmvp.presentation.BaseView;
import com.windern.cleanmvp.presentation.temp.TempContract;

import java.util.List;

/**
 * Created by wenxinlin on 2016/12/12.
 */

public interface NoteContract {
    interface View extends BaseView<Presenter> {
        void showError(String tip);
        void showNotes(List<Note> notes);
        void showNote(Note note);
    }

    interface Presenter extends BasePresenter {
        void loadNotes();
        void addNote(Note note);
        void loadNote(long id);
    }
}
