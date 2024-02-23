package com.example.newsapp_c2.ui.category

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp_c2.R
import com.example.newsapp_c2.model.Category
import com.google.android.material.card.MaterialCardView

class CategoriesAdapter (val categoryList:List<Category>) : RecyclerView.Adapter<CategoriesAdapter.ViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(if(viewType==LEFT_SIDE) R.layout.left_side_category
            else R.layout.right_side_category , parent,false)

        return ViewHolder(view)
    }

    val LEFT_SIDE =10
    val RIGHT_SIDE = 20
    override fun getItemViewType(position: Int): Int {

        if(position%2==0){
            return LEFT_SIDE
        }else{
            return RIGHT_SIDE
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val item = categoryList[position]

        holder.title.setText(item.title)
        holder.image.setImageResource(item.imageResId)
        holder.materialCard.setCardBackgroundColor(ContextCompat.getColor(holder.itemView.context,
            item.backgroundColor))

        onItemClickListner?.let {
            holder.itemView.setOnClickListener {
                onItemClickListner?.onItemClick(item,position)
            }
        }

    }

    var onItemClickListner:OnItemClickListner?=null
    interface OnItemClickListner {
        fun onItemClick(category: Category , position: Int)
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }

    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){

        val title:TextView = itemView.findViewById(R.id.title)
        val image:ImageView = itemView.findViewById(R.id.image)
        val materialCard:MaterialCardView = itemView.findViewById(R.id.material_card)
    }
}