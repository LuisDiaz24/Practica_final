package com.example.practica_final.ui.products

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.practica_final.R
import com.example.practica_final.databinding.FragmentShoppingcartBinding
import com.example.practica_final.domain.Product
import com.example.practica_final.domain.ProductAdapter
import com.example.practica_final.domain.ProductProvider.Companion.productList
import com.example.practica_final.ui.adapter.ProductAdapterNew
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ShoppingCartActivity : AppCompatActivity() {

    private var _binding: FragmentShoppingcartBinding? = null
    private val binding get() = _binding!!

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ProductAdapterNew

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_shoppingcart)

        recyclerView = findViewById(R.id.lst_shopingcart)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Crear una instancia de ProductAdapterNew y pasar la lista de productos
        adapter = ProductAdapterNew(productList)
        recyclerView.adapter = adapter as RecyclerView.Adapter<RecyclerView.ViewHolder>

        // Llamar a la función para obtener los productos del carrito y mostrarlos en el RecyclerView
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

                // Pasar la lista de productos al adaptador y notificar los cambios
                adapter = ProductAdapterNew(productList)
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                // Manejar el error en caso de que la lectura de los productos del carrito falle
            }
        })
    }
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}