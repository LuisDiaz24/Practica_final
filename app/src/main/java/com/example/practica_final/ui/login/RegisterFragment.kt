package com.example.practica_final.ui.login

import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import com.example.practica_final.R
import com.example.practica_final.databinding.RegisterFragmentBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class RegisterFragment : Fragment() {

    private var _binding: RegisterFragmentBinding? = null
    private val binding get() = _binding!!

    private var database : FirebaseDatabase= FirebaseDatabase.getInstance()
    private var auth : FirebaseAuth = FirebaseAuth.getInstance()
    private var dbReference : DatabaseReference= database.reference.child("User")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = RegisterFragmentBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindViews()
    }

    private fun bindViews() = with(binding) {
        btnBack.setOnClickListener { replaceFragment()}
        btnRegister.setOnClickListener {
            createNewAccount()
        }
    }

    private fun replaceFragment(){
        val fragmentManager : FragmentManager = requireActivity().supportFragmentManager
        fragmentManager.beginTransaction()
            .replace(R.id.frameContainer, LoginFragment())
            .addToBackStack(null)
            .commit()
    }

    private fun createNewAccount() = with(binding) {
        val email: String = edtEmail.text.toString()
        val name: String = edtName.text.toString()
        val age: String = edtAge.text.toString()
        val password: String = edtPassword.text.toString()
        val address: String = edtAddress.text.toString()

        if (email.isEmpty() || name.isEmpty() || age.isEmpty() || password.isEmpty() || address.isEmpty()){
            Toast.makeText(requireContext(), "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show()
        }else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(requireContext(), "Por favor, ingrese un correo electrónico válido", Toast.LENGTH_SHORT).show()
        }else{
            progressBar.visibility=View.VISIBLE

            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(requireActivity()){
                        task ->

                    if (task.isSuccessful){
                        val user: FirebaseUser?=auth.currentUser
                        verifyEmail(user)

                        val userBD= user?.uid?.let { dbReference.child(it) }

                        userBD?.child("Name")?.setValue(name)
                        userBD?.child("Edad")?.setValue(age)
                        userBD?.child("Direccion")?.setValue(address)
                        Toast.makeText(activity, "Registrado", Toast.LENGTH_SHORT).show()
                        replaceFragment()
                    }else{
                        Toast.makeText(activity, "Error al registrar el usuario", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }

    private fun verifyEmail(user: FirebaseUser?){
        user?.sendEmailVerification()?.addOnCompleteListener(requireActivity()){ task ->
            if(task.isComplete){
                Toast.makeText(activity, "Email enviado", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(activity, "Error al enviar el email", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}