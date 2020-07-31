package com.mustaq.roomdatabaseapicall

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mustaq.roomdatabaseapicall.adapter.ActorAdapter
import com.mustaq.roomdatabaseapicall.interfaces.ActorApiService
import com.mustaq.roomdatabaseapicall.interfaces.onActorClick
import com.mustaq.roomdatabaseapicall.model.Actor
import com.mustaq.roomdatabaseapicall.repo.ActorRepository
import com.mustaq.roomdatabaseapicall.viewmodel.ActorViewModel
import io.reactivex.disposables.CompositeDisposable
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity(), onActorClick {

    lateinit var actorViewModel: ActorViewModel
    lateinit var getActorlist: List<Actor>
    lateinit var actorAdapter: ActorAdapter
    lateinit var recyclerView: RecyclerView
    lateinit var repository: ActorRepository
    lateinit var compositeDisposable: CompositeDisposable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        repository = ActorRepository(application)
        getActorlist = ArrayList()
        recyclerView = findViewById(R.id.rvActor)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)
        actorViewModel = ViewModelProviders.of(this).get(ActorViewModel::class.java)
        actorAdapter = ActorAdapter(this, getActorlist, this)

        compositeDisposable = CompositeDisposable()

        actorViewModel.getAllActor
            .observe(this,
                Observer { actorList ->
                    //actorAdapter.getAllActors(actorList)
                    recyclerView.adapter = ActorAdapter(this, actorList, this)
                })
        getMyCall()
    }

    private fun getMyCall() {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://www.codingwithjks.tech/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService = retrofit.create(ActorApiService::class.java)

        val call=apiService.getActor()
        call.enqueue(object :retrofit2.Callback<List<Actor>>{
            override fun onFailure(call: Call<List<Actor>>, t: Throwable) {
                centerToast(
                    this@MainActivity,
                    "Something Went Wrong"
                )
                Log.e("TAG", "onFailure: $t" )
            }

            override fun onResponse(call: Call<List<Actor>>, response: Response<List<Actor>>) {
                if (response.isSuccessful) {
                    if (response.body()!!.isNotEmpty()) {
                        Log.e("TAG", "onResponse: ${response.code()}" )
                        repository.insert(response.body())
                        //actorAdapter = ActorAdapter(this@MainActivity, response.body()!!, this@MainActivity)

                    } else {
                        centerToast(
                            this@MainActivity,
                            "Data Not Available"
                        )
                        Log.e("TAG", "onResponse: Data Not Available" )
                    }
                } else {
                    centerToast(
                        this@MainActivity,
                        "Something went wrong"
                    )
                    Log.e("TAG", "onResponse: ${response.code()}" )
                }
            }

        })
    }

    override fun onStop() {
        super.onStop()
        compositeDisposable.clear()

    }

    override fun actorClick(id: Int) {


    }

    private fun centerToast(context: Context, message: String) {
        Toast.makeText(context,message,Toast.LENGTH_SHORT).show()
    }
}