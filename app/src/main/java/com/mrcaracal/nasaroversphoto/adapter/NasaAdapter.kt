package com.mrcaracal.nasaroversphoto.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mrcaracal.nasaroversphoto.R
import com.mrcaracal.nasaroversphoto.model.Photo
import kotlinx.android.synthetic.main.item_row.view.*

private const val TAG = "NasaAdapter"

class NasaAdapter(private val photos: List<Photo>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_row, parent, false)
        return PhotoHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is PhotoHolder -> {
                holder.bind(photos[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return photos.size
    }

    class PhotoHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(photo: Photo) {
            val image = "${photo.imgSrc.replace("http", "https")}"
            Glide.with(itemView.context).load(image).into(itemView.item_img)
            itemView.item_data1_carName.text = photo.rover.name
            itemView.item_data2_earthDate.text = photo.earthDate
            itemView.item_data3_cameraName.text = photo.camera.name
        }
    }

}