package com.lexneoapps.crypto100.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.lexneoapps.crypto100.R
import com.lexneoapps.crypto100.databinding.FragmentHomeBinding
import com.lexneoapps.crypto100.ui.adapters.CoinAdapter
import dagger.hilt.android.AndroidEntryPoint

const val TAG = "HomeFragment"
@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeViewModel by viewModels()
    private lateinit var coinAdapter: CoinAdapter


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpAdapter()
        setUpObservers()
        setUpListeners()

    }

    private fun setUpObservers() {
        viewModel.coins.observe(viewLifecycleOwner) {
            Log.d(TAG, "setUpObservers: $it")
            coinAdapter.submitData(viewLifecycleOwner.lifecycle, it)
        }
    }

    private fun setUpListeners(){
        binding.refresh.setOnRefreshListener {
            coinAdapter.refresh()
            binding.refresh.isRefreshing = false
        }
    }

    private fun setUpAdapter() {
        coinAdapter = CoinAdapter()
        binding.recyclerView.adapter = coinAdapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null

    }
}