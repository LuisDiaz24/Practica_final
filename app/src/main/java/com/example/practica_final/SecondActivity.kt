package com.example.practica_final

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.practica_final.databinding.ActivitySecondBinding
import com.example.practica_final.ui.productdetail.ProductDetailFragment
import com.example.practica_final.ui.home.HomeFragment
import com.example.practica_final.ui.login.LoginActivity
import com.example.practica_final.ui.products.ShoppingCartActivity
import com.google.firebase.auth.FirebaseAuth

class SecondActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySecondBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(HomeFragment())
        binding.navView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.navigation_home -> replaceFragment(HomeFragment())
                R.id.navigation_shoppingcat -> {
                    val intent = Intent(this@SecondActivity, ShoppingCartActivity::class.java)
                    startActivity(intent)
                }

                R.id.navigation_detail -> replaceFragment(ProductDetailFragment())
                R.id.navigation_logout -> {
                    FirebaseAuth.getInstance().signOut()
                    val intent = Intent(this@SecondActivity, LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                }

                else -> {

                }
            }
            true
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_container_view, fragment)
        fragmentTransaction.commit()
    }
}