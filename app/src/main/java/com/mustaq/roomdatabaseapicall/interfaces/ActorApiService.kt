package com.mustaq.roomdatabaseapicall.interfaces

import com.mustaq.roomdatabaseapicall.model.Actor
import retrofit2.Call
import retrofit2.http.GET

interface ActorApiService {

    @GET("data.php")
    fun getActor(): Call<List<Actor>>
}