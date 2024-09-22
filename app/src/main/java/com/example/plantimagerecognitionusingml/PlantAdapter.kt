package com.example.plantimagerecognitionusingml

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

class PlantAdapter(private val plantList: List<Plant>, private val itemClickListener: OnItemClickListener) : RecyclerView.Adapter<PlantAdapter.PlantViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(plant: Plant)
        abstract fun Plant(name: String, info: String, imageUrl: String): Plant
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlantViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_plant, parent, false)
        return PlantViewHolder(view)
    }

    override fun onBindViewHolder(holder: PlantViewHolder, position: Int) {
        // for explore activity

        val plant = plantList[position]
        holder.bind(plant, itemClickListener)

        // Adjust the height of the images dynamically
        val layoutParams = holder.plantImage.layoutParams
        if (position % 2 == 0) {
            // Left column
            layoutParams.height = holder.itemView.resources.getDimensionPixelSize(R.dimen.left_column_height)
        } else {
            // Right column
            layoutParams.height = holder.itemView.resources.getDimensionPixelSize(R.dimen.right_column_height)
        }
        holder.plantImage.layoutParams = layoutParams

    }

    override fun getItemCount(): Int {
        return plantList.size
    }

    class PlantViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // for explore activity
        val plantImage: ImageView = itemView.findViewById(R.id.plant_image)
        private val plantName: TextView = itemView.findViewById(R.id.plant_name)

        fun bind(plant: Plant, clickListener: OnItemClickListener) {
            plantImage.setImageResource(plant.imageResId)
            plantName.text = plant.commonName
            itemView.setOnClickListener {
                clickListener.onItemClick(plant)
            }
        }

    }
}
