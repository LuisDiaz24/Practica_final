package com.example.practica_final.ui.login

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.practica_final.databinding.RegisterFragmentBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class RegisterActivity : AppCompatActivity() {

    private var _binding: RegisterFragmentBinding? = null
    private val binding get() = _binding!!

    private var database : FirebaseDatabase= FirebaseDatabase.getInstance()
    private var auth : FirebaseAuth = FirebaseAuth.getInstance()
    private var dbReference : DatabaseReference= database.reference.child("User")



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = RegisterFragmentBinding.inflate(layoutInflater)
        setContentView(binding.root)
        bindViews()
    }


    private fun bindViews() {
        with(binding) {
            btnBack.setOnClickListener { replaceActivity() }
            btnRegister.setOnClickListener { createNewAccount() }
        }
    }


    private fun replaceActivity() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }

    private fun createNewAccount() = with(binding) {
        val email: String = edtEmail.text.toString()
        val name: String = edtName.text.toString()
        val age: String = edtAge.text.toString()
        val password: String = edtPassword.text.toString()
        val address: String = edtAddress.text.toString()

        if (email.isEmpty() || name.isEmpty() || age.isEmpty() || password.isEmpty() || address.isEmpty()){
            Toast.makeText(this@RegisterActivity, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show()
        }else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this@RegisterActivity, "Por favor, ingrese un correo electrónico válido", Toast.LENGTH_SHORT).show()
        }else{
            progressBar.visibility=View.VISIBLE

            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this@RegisterActivity){
                        task ->

                    if (task.isSuccessful){
                        val user: FirebaseUser?=auth.currentUser
                        verifyEmail(user)

                        val userBD= user?.uid?.let { dbReference.child(it) }

                        userBD?.child("Name")?.setValue(name)
                        userBD?.child("Edad")?.setValue(age)
                        userBD?.child("Direccion")?.setValue(address)
                        Toast.makeText(this@RegisterActivity, "Registrado", Toast.LENGTH_SHORT).show()
                        replaceActivity()
                    }else{
                        Toast.makeText(this@RegisterActivity, "Error al registrar el usuario", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }

    private fun verifyEmail(user: FirebaseUser?){
        user?.sendEmailVerification()?.addOnCompleteListener(this){ task ->
            if(task.isComplete){
                Toast.makeText(this@RegisterActivity, "Email enviado", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(this@RegisterActivity, "Error al enviar el email", Toast.LENGTH_SHORT).show()
            }
        }
    }


}