package com.example.practica_final.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.practica_final.R
import com.example.practica_final.domain.Product

class ProductAdapterNew (private val productList: List<Product>) : RecyclerView.Adapter<ProductAdapterNew.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

            val productNameTextView: TextView = itemView.findViewById(R.id.txt_product_name)
            val productPriceTextView: TextView = itemView.findViewById(R.id.txt_product_price)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_shopping, parent, false)
            return ViewHolder(itemView)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val product = productList[position]

            holder.productNameTextView.text = product.title
            holder.productPriceTextView.text = "$${product.price}"
        }

        override fun getItemCount(): Int {
            return productList.size
        }
    }

