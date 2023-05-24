package com.example.practica_final.ui.login

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import com.example.practica_final.R
import com.example.practica_final.SecondActivity
import com.example.practica_final.databinding.LoginFragmentBinding
import com.google.firebase.auth.FirebaseAuth

class LoginFragment : Fragment() {

    private var _binding: LoginFragmentBinding? = null
    private val binding get() = _binding!!

    private var auth: FirebaseAuth = FirebaseAuth.getInstance()
    
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = LoginFragmentBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindViews()
    }

    private fun bindViews() = with(binding) {
        btnLogin.setOnClickListener {
            loginUser()
        }
        btnRegister.setOnClickListener {
            replaceFragment()
        }
    }

    private fun loginUser() = with(binding) {

        val email: String = edtEmail.text.toString()
        val password: String = edtPassword.text.toString()

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(
                requireContext(),
                "Por favor, complete todos los campos",
                Toast.LENGTH_SHORT
            ).show()
        } else if (!Patterns.EMAIL_ADDRESS.matcher(edtEmail.text.toString()).matches()) {
            Toast.makeText(
                requireContext(),
                "Por favor, ingrese un correo electrónico válido",
                Toast.LENGTH_SHORT
            ).show()
        } else {
            progressBar.visibility = View.VISIBLE

            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(requireActivity()) { task ->
                    if (task.isSuccessful) {
                        action()
                    } else {
                        Toast.makeText(
                            requireContext(),
                            "Error en la autenticación",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        }
    }

    private fun action(){
        startActivity(Intent(requireActivity(),SecondActivity::class.java))
    }

    private fun replaceFragment(){

        val fragmentManager : FragmentManager = requireActivity().supportFragmentManager
        fragmentManager.beginTransaction()
            .replace(R.id.frameContainer, RegisterFragment())
            .addToBackStack(null)
            .commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}