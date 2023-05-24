package com.example.practica_final

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentTransaction
import com.example.practica_final.databinding.ActivityMainBinding
import com.example.practica_final.ui.login.LoginFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        openFragment()
    }
    private fun openFragment(){

        val fragmentTransaction : FragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frameContainer, LoginFragment())
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()

    }
}
