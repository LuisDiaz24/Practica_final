package com.example.practica_final

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.practica_final.databinding.ActivitySecondBinding
import com.example.practica_final.ui.productdetail.ProductDetailFragment
import com.example.practica_final.ui.home.HomeFragment
import com.example.practica_final.ui.products.ShoppingCartFragment

class SecondActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySecondBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(HomeFragment())
        binding.navView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.navigation_home -> replaceFragment(HomeFragment())
                R.id.navigation_shoppingcat -> replaceFragment(ShoppingCartFragment())
                R.id.navigation_detail -> replaceFragment(ProductDetailFragment())
                else -> {

                }
            }
            true
        }
    }

    private fun replaceFragment(fragment: Fragment){

        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_container_view,fragment)
        fragmentTransaction.commit()
    }
}