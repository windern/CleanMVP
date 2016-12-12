package com.windern.cleanmvp.data.net;

import android.content.Context;

import com.windern.cleanmvp.data.database.DaoSession;
import com.windern.cleanmvp.data.database.DatabaseManagerHelper;
import com.windern.cleanmvp.data.database.Note;
import com.windern.cleanmvp.data.database.NoteDao;
import com.windern.cleanmvp.data.model.Temp;

import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.rx.RxDao;
import org.greenrobot.greendao.rx.RxQuery;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;

/**
 * Created by wenxinlin on 2016/10/26.
 */

public class ApiServiceImpl implements ApiService{
    @Override
    public Observable<List<Temp>> getTemps() {
        List<Temp> list = new ArrayList<>();
        list.add(new Temp(1,"11111"));
        list.add(new Temp(2,"22222"));
        list.add(new Temp(3,"33333"));
        return Observable.just(list);
    }

    @Override
    public Observable<Temp> getTemp(int id) {
        Temp temp = new Temp(id,String.valueOf(id)+String.valueOf(id)+String.valueOf(id)+String.valueOf(id)+String.valueOf(id));
        return Observable.just(temp);
    }

    @Override
    public Observable<List<Note>> getNotes() {
        DaoSession daoSession = DatabaseManagerHelper.getInstance().daoSession;
        RxQuery<Note> query = daoSession.getNoteDao().queryBuilder().orderAsc(NoteDao.Properties.Id).rx();
        return query.list();
    }

    @Override
    public Observable<Note> saveNote(Note note) {
        DaoSession daoSession = DatabaseManagerHelper.getInstance().daoSession;
        RxDao<Note,Long> noteDao = daoSession.getNoteDao().rx();
        return noteDao.insert(note);
    }

    @Override
    public Observable<Note> getNote(long id) {
        DaoSession daoSession = DatabaseManagerHelper.getInstance().daoSession;
        RxDao<Note,Long> noteDao = daoSession.getNoteDao().rx();
        return noteDao.load(id);
    }

}
