package com.mustaq.roomdatabaseapicall.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;


import com.mustaq.roomdatabaseapicall.model.Actor;
import com.mustaq.roomdatabaseapicall.repo.ActorRepository;

import java.util.List;

public class ActorViewModel extends AndroidViewModel {

    private ActorRepository actorRepository;
    private LiveData<List<Actor>> getAllActor;

    public ActorViewModel(@NonNull Application application) {
        super(application);
        actorRepository = new ActorRepository(application);
        getAllActor = actorRepository.getGetAllActors();
    }

    public void insert(List<Actor> list) {
        actorRepository.insert(list);
    }

    public LiveData<List<Actor>> getGetAllActor() {
        return getAllActor;
    }
}
