package com.mustaq.roomdatabaseapicall.dao;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.mustaq.roomdatabaseapicall.model.Actor;

import java.util.List;


@Dao
public interface ActorDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<Actor> actorslist);


    @Query("SELECT * FROM ActorTable")
    LiveData<List<Actor>> getAllActor();

    @Query("DELETE  FROM ActorTable")
    void deleteAll();

    @Query("DELETE FROM ActorTable WHERE id = :userId")
    void deleteByUserId(long userId);
}
