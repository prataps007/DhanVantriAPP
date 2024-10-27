package com.example.plantimagerecognitionusingml.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.plantimagerecognitionusingml.R
import com.example.plantimagerecognitionusingml.model.FAQItem

class FAQAdapter(private val faqList: List<FAQItem>) : RecyclerView.Adapter<FAQAdapter.FAQViewHolder>() {

    class FAQViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val questionTextView: TextView = view.findViewById(R.id.faq_question)
        val answerTextView: TextView = view.findViewById(R.id.faq_answer)
        val expandButton: ImageView = view.findViewById(R.id.faq_expand_button)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FAQViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_faq, parent, false)
        return FAQViewHolder(view)
    }

    override fun onBindViewHolder(holder: FAQViewHolder, position: Int) {
        val faqItem = faqList[position]
        holder.questionTextView.text = faqItem.question
        holder.answerTextView.text = faqItem.answer

        // Initially hide the answer
        holder.answerTextView.visibility = View.GONE

        // Handle expand/collapse
        holder.itemView.setOnClickListener {
            if (holder.answerTextView.visibility == View.GONE) {
                holder.answerTextView.visibility = View.VISIBLE
                holder.expandButton.setImageResource(R.drawable.ic_collapse)
            } else {
                holder.answerTextView.visibility = View.GONE
                holder.expandButton.setImageResource(R.drawable.ic_expand)
            }
        }
    }

    override fun getItemCount(): Int {
        return faqList.size
    }
}
