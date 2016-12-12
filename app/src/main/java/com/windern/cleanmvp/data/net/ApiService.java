package com.windern.cleanmvp.data.net;

import android.content.Context;

import com.windern.cleanmvp.data.database.Note;
import com.windern.cleanmvp.data.model.Temp;

import java.util.List;

import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by wenxinlin on 2016/10/25.
 */

public interface ApiService {
    Observable<List<Temp>> getTemps();
    Observable<Temp> getTemp(int id);
    Observable<List<Note>> getNotes();
    Observable<Note> saveNote(Note note);
    Observable<Note> getNote(long id);
}
