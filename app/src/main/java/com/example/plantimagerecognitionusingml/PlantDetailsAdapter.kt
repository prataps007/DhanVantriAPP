package com.example.plantimagerecognitionusingml

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class PlantDetailsAdapter(private var plantDetails: MutableList<String>) :
    RecyclerView.Adapter<PlantDetailsAdapter.PlantDetailViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlantDetailViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_plant_details, parent, false)
        return PlantDetailViewHolder(view)
    }

    override fun onBindViewHolder(holder: PlantDetailViewHolder, position: Int) {
        holder.detailTextView.text = plantDetails[position]
    }

    override fun getItemCount(): Int = plantDetails.size

    fun updatePlantDetails(newDetail: String) {
        plantDetails.add(newDetail) // Add new detail to the list
        notifyItemInserted(plantDetails.size - 1) // Notify RecyclerView of the new item
    }

    class PlantDetailViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val detailTextView: TextView = itemView.findViewById(R.id.detailTextView)
    }
}
