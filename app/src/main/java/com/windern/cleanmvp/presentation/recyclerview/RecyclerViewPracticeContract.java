package com.windern.cleanmvp.presentation.recyclerview;

import com.windern.cleanmvp.data.database.Note;

import java.util.List;

/**
 * Created by wenxinlin on 2016/12/15.
 */

public interface RecyclerViewPracticeContract {
    interface View {
        void showError(String tip);

        void showRefreshLoading();

        void hideRefreshLoading();

        void showMoreLoading();

        void hideMoreLoading();

        void addFinish();

        void updateList(List<Note> list);
    }

    interface Presenter {
        void add(Note note);

        void refresh();

        void getMore();
    }

}
