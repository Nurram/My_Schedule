package com.github.nurram.myschedule.view.home

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.github.nurram.myschedule.R
import com.github.nurram.myschedule.model.Activities
import kotlinx.android.synthetic.main.item_list.view.*

class HomeAdapter(private val context: Context) : RecyclerView.Adapter<HomeAdapter.HomeHolder>() {
    private lateinit var onClickActivities: OnClickActivities

    private val activities = arrayListOf<Activities>()
    private val colors = arrayListOf(
        android.R.color.holo_blue_dark,
        android.R.color.holo_red_dark,
        android.R.color.holo_orange_dark,
        android.R.color.holo_green_dark,
        android.R.color.holo_purple
    )

    inner class HomeHolder(view: View) : RecyclerView.ViewHolder(view) {
        @SuppressLint("SetTextI18n")
        fun bind(activities: Activities) {
            with(itemView) {
                item_name.text = activities.name
                item_time.text = "Starts at: ${activities.time}"
                item_desc.text = activities.desc
                item_delete.setOnClickListener {
                    onClickActivities.onDeleteClick(activities)
                }

                item_circle.setImageResource(colors[activities.category])
            }

            itemView.setOnClickListener {
                onClickActivities.onItemClick(activities)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeHolder =
        HomeHolder(LayoutInflater.from(context).inflate(R.layout.item_list, parent, false))

    override fun getItemCount(): Int = activities.size

    override fun onBindViewHolder(holder: HomeHolder, position: Int) {
        if (activities.size > 0) {
            holder.bind(activities[position])
        }
    }

    fun setData(activities: ArrayList<Activities>) {
        this.activities.clear()
        this.activities.addAll(activities)
        notifyDataSetChanged()
    }

    fun setDeleteClick(onClickActivities: OnClickActivities) {
        this.onClickActivities = onClickActivities
    }

    interface OnClickActivities {
        fun onDeleteClick(activities: Activities)
        fun onItemClick(activities: Activities)
    }
}