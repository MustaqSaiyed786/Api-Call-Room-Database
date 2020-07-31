package com.mustaq.roomdatabaseapicall.repo;

import android.app.Application;
import android.os.AsyncTask;


import androidx.lifecycle.LiveData;

import com.mustaq.roomdatabaseapicall.dao.ActorDao;
import com.mustaq.roomdatabaseapicall.databse.ActorDatabse;
import com.mustaq.roomdatabaseapicall.model.Actor;

import java.util.List;

public class ActorRepository {

    private ActorDao actorDao;
    private ActorDatabse databse;
    private LiveData<List<Actor>> getAllActors;

    public ActorRepository(Application application) {
        databse = ActorDatabse.getInstance(application);
        actorDao = databse.actorDao();
        getAllActors = actorDao.getAllActor();
    }

    public void insert(List<Actor> actorList) {
        new InsertAsyncTask(databse).execute(actorList);

    }

    public LiveData<List<Actor>> getGetAllActors() {
        return getAllActors;
    }

    static class InsertAsyncTask extends AsyncTask<List<Actor>, Void, Void> {

        private ActorDao actorDao;

        InsertAsyncTask(ActorDatabse actorDatabse) {
            actorDao = actorDatabse.actorDao();
        }

        @Override
        protected Void doInBackground(List<Actor>... lists) {
            actorDao.insert(lists[0]);
            return null;
        }
    }
}
