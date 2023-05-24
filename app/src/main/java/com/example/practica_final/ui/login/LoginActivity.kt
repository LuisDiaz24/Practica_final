package com.example.practica_final.ui.login

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import com.example.practica_final.SecondActivity
import com.example.practica_final.databinding.LoginFragmentBinding
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: LoginFragmentBinding
    private var auth: FirebaseAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = LoginFragmentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        bindViews()
    }

    private fun bindViews() {
        with(binding) {
            btnLogin.setOnClickListener {
                loginUser()
            }
            btnRegister.setOnClickListener {
                replaceActivity()
            }
        }
    }

    private fun loginUser() {
        with(binding) {
            val email: String = edtEmail.text.toString()
            val password: String = edtPassword.text.toString()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(
                    this@LoginActivity,
                    "Por favor, complete todos los campos",
                    Toast.LENGTH_SHORT
                ).show()
            } else if (!Patterns.EMAIL_ADDRESS.matcher(edtEmail.text.toString()).matches()) {
                Toast.makeText(
                    this@LoginActivity,
                    "Por favor, ingrese un correo electrónico válido",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                progressBar.visibility = View.VISIBLE

                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this@LoginActivity) { task ->
                        if (task.isSuccessful) {
                            action()
                        } else {
                            Toast.makeText(
                                this@LoginActivity,
                                "Error en la autenticación",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
            }
        }
    }

    private fun action() {
        startActivity(Intent(this, SecondActivity::class.java))
    }

    private fun replaceActivity() {
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
    }
}