package com.example.practica_final.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.practica_final.domain.ProductAdapter
import com.example.practica_final.domain.ProductProvider
import com.example.practica_final.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
    }

    private fun initRecyclerView() {
        val recyclerView: RecyclerView = binding.listProducts
        recyclerView.layoutManager = LinearLayoutManager(requireActivity())
        recyclerView.adapter = ProductAdapter(ProductProvider.productList)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}