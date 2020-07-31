package com.mustaq.roomdatabaseapicall.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

import com.mustaq.roomdatabaseapicall.R
import com.mustaq.roomdatabaseapicall.interfaces.onActorClick
import com.mustaq.roomdatabaseapicall.model.Actor


class ActorAdapter(
    private val mContext: Context,
    private var actorList: List<Actor>,
    val onActorClick: onActorClick
) : RecyclerView.Adapter<ActorAdapter.ActorHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActorHolder {
        return ActorHolder(
            LayoutInflater.from(mContext).inflate(R.layout.row_actor_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ActorHolder, position: Int) {
        holder.bind(actorList)
    }

    fun getAllActors(geActorList: List<Actor>) {
        actorList = geActorList
    }

    override fun getItemCount(): Int {
        return actorList.size
    }

    inner class ActorHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        var id: AppCompatTextView = itemView.findViewById(R.id.tvActorId)
        var name: AppCompatTextView = itemView.findViewById(R.id.tvActorName)
        var age: AppCompatTextView = itemView.findViewById(R.id.tvActorAge)
        var imageView: AppCompatImageView = itemView.findViewById(R.id.imgActor)

        @SuppressLint("SetTextI18n", "ClickableViewAccessibility")
        fun bind(actorList: List<Actor>) {
            id.text = "Actor Id:-" + actorList[position].id
            name.text = "Actor Name:-" + actorList[position].name
            age.text = "Actor Age:-" + actorList[position].age
            Glide.with(mContext).load(actorList[position].image).into(imageView)

            /*itemView.setOnClickListener {
                onActorItemClick.actorClick(actorList[position].id)
            }*/

            itemView.setOnLongClickListener {
                onActorClick.actorClick(actorList[position].id)
                true
            }
        }
    }

}