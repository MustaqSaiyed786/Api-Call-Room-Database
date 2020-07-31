package com.mustaq.roomdatabaseapicall.databse;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.mustaq.roomdatabaseapicall.dao.ActorDao;
import com.mustaq.roomdatabaseapicall.model.Actor;


@Database(entities = {Actor.class}, version = 1, exportSchema = false)
public abstract class ActorDatabse extends RoomDatabase {

    private static final String DATABASE_NAME = "ActorDatabase";

    public abstract ActorDao actorDao();

    private static volatile ActorDatabse INSTANCE = null;


    public static ActorDatabse getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (ActorDatabse.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context, ActorDatabse.class, DATABASE_NAME)
                            .allowMainThreadQueries()
                            .addCallback(callback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }


    static Callback callback = new Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateAsyncTask(INSTANCE);
        }
    };

    static class PopulateAsyncTask extends AsyncTask<Void, Void, Void> {
        private ActorDao actorDao;
        PopulateAsyncTask(ActorDatabse actorDatabse) {
            actorDao = actorDatabse.actorDao();
        }
        @Override
        protected Void doInBackground(Void... voids) {
            actorDao.deleteAll();
            return null;
        }
    }

}