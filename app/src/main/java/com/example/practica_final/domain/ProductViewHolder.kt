package com.example.practica_final.domain

import android.content.Intent
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.practica_final.R
import com.example.practica_final.ui.products.ShoppingCartActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
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

        btnBuyView.setOnClickListener { view ->

            val producto = productModel

            val firebaseDatabase = FirebaseDatabase.getInstance()
            val user = FirebaseAuth.getInstance().currentUser
            val userId = user?.uid
            val cartRef = firebaseDatabase.getReference("carritos").child(userId.toString())
            val nuevoProductoRef = cartRef.push()
            nuevoProductoRef.setValue(producto)

            val intent = Intent(view.context, ShoppingCartActivity::class.java)
            view.context.startActivity(intent)
        }


        btnProductDetail.setOnClickListener {
            it.findNavController().navigate(R.id.navigation_detail, bundleOf("productModel" to  productModel))
        }
    }
}