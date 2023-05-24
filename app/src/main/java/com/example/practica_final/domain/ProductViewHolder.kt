package com.example.practica_final.domain

import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.practica_final.R
import com.squareup.picasso.Picasso

class ProductViewHolder (view: View): RecyclerView.ViewHolder(view){

    private val expandedImageView: ImageView = view.findViewById(R.id.expandedImage)
    private val nameTextView: TextView = view.findViewById(R.id.txt_name_product)
    private val priceTextView: TextView = view.findViewById(R.id.txt_price)
    private val btnBuyView : Button = view.findViewById(R.id.btn_buy)
    private val btnProductDetail : Button = view.findViewById(R.id.btn_detail)

    fun render(productModel: Product){
        Picasso.get().load(productModel.thumbnail).into(expandedImageView)
        nameTextView.text = productModel.title
        priceTextView.text = "$${productModel.price}"

        btnBuyView.setOnClickListener {
            val action = R.id.navigation_shoppingcat
            findNavController(it).navigate(action)
        }

        btnProductDetail.setOnClickListener {
            val action = R.id.navigation_detail
            val actions = ProductListFragmentDirections.actionProductListFragmentToProductDetailFragment(productModel)
            it.findNavController().navigate(action,productModel)
        }
    }
}