package com.example.practica_final.ui.products

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.practica_final.R
import com.example.practica_final.domain.Product
import com.example.practica_final.ui.adapter.ProductAdapterNew
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ShoppingCartActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ProductAdapterNew

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_shoppingcart)

        recyclerView = findViewById(R.id.lst_shopingcart)
        recyclerView.layoutManager = LinearLayoutManager(this)
        fetchCartProducts()
    }

    private fun fetchCartProducts() {
        val firebaseDatabase = FirebaseDatabase.getInstance()
        val user = FirebaseAuth.getInstance().currentUser
        val userId = user?.uid
        val cartRef = firebaseDatabase.getReference("carritos").child(userId.toString())

        cartRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val productList: MutableList<Product> = mutableListOf()

                for (productSnapshot in snapshot.children) {
                    val product = productSnapshot.getValue(Product::class.java)
                    product?.let {
                        productList.add(it)
                    }
                }

                adapter = ProductAdapterNew(productList)
                recyclerView.adapter = adapter as RecyclerView.Adapter<RecyclerView.ViewHolder>
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@ShoppingCartActivity, "Error al leer los datos de carrito", Toast.LENGTH_SHORT).show()
            }
        })
    }
}