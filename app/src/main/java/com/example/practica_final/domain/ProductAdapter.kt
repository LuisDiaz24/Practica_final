package com.example.practica_final.domain

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.practica_final.R

class ProductAdapter (private val product: List<Product>):RecyclerView.Adapter<ProductViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ProductViewHolder(layoutInflater.inflate(R.layout.item_product, parent,false))
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val item = product[position]
        holder.render(item)
    }

    override fun getItemCount(): Int = product.size
}
