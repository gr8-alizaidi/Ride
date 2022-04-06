package com.alizaidi.aliabbasedvora.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.alizaidi.aliabbasedvora.R
import com.alizaidi.aliabbasedvora.Ride
import com.bumptech.glide.Glide

class RideListAdapter(): RecyclerView.Adapter<RideViewHolder>() {

    private val items: ArrayList<Ride> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RideViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.ride_item, parent, false)

        return RideViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: RideViewHolder, position: Int) {
        val currentItem = items[position]
        holder.ride.text = "Ride Id: ${currentItem.id}"
        holder.origin.text = "Origin Station: ${currentItem.origin_station_code.toString()}"

        holder.path.text = "station_path: ${currentItem.station_path.toString()}"
        holder.date.text = "Date :${currentItem.date}"

        holder.distance.text = "Distance :${currentItem.distance}"

        holder.city.text=currentItem.city
        holder.state.text=currentItem.state

//        Glide.with(holder.itemView.context).load(currentItem.imageUrl).into(holder.image)
    }

    fun updateRide(updatedRide: List<Ride>) {
        items.clear()
        items.addAll(updatedRide)

        notifyDataSetChanged()
    }
}

class RideViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val ride: TextView = itemView.findViewById(R.id.ride)
    val origin: TextView = itemView.findViewById(R.id.origin)
    val path: TextView = itemView.findViewById(R.id.path)
    val date: TextView = itemView.findViewById(R.id.date)
    val distance: TextView = itemView.findViewById(R.id.distance)
    val city:TextView=itemView.findViewById(R.id.cityname)
    val state:TextView=itemView.findViewById(R.id.statename)

}
