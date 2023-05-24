package com.example.practica_final.ui.productdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.practica_final.databinding.FragmentDetailBinding
import com.example.practica_final.domain.Product
import com.example.practica_final.domain.ProductAdapter
import com.example.practica_final.domain.ProductProvider
import com.squareup.picasso.Picasso

class ProductDetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    private var productModel: Product? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindViews()
    }

    private fun bindViews() = with(binding) {
        arguments?.let {
            productModel = it.getSerializable("productModel") as Product?
            tvTitle.text = productModel?.title
            tvDescription.text = productModel?.description
            tvRating.text = productModel?.rating.toString()
            Picasso.get().load(productModel?.thumbnail).into(expandedImage)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}