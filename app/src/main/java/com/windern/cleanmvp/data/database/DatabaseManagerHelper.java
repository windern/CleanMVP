package com.windern.cleanmvp.data.database;

import android.content.Context;

import org.greenrobot.greendao.database.Database;

/**
 * Created by wenxinlin on 2016/12/12.
 */

public class DatabaseManagerHelper {
    private static class Holder{
        private static final DatabaseManagerHelper INSTANCE = new DatabaseManagerHelper();
    }

    public static DatabaseManagerHelper getInstance(){
        return Holder.INSTANCE;
    }

    private Context context;
    public DaoSession daoSession;

    public void init(Context context){
        this.context = context;
        DaoMaster.DevOpenHelper openHelper = new DaoMaster.DevOpenHelper(this.context, "mvp.db");
        Database db = openHelper.getWritableDb();
        daoSession = new DaoMaster(db).newSession();
    }

}
