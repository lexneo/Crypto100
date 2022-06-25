package com.lexneoapps.crypto100.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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
        setUpRv()
        setUpObservers()
        setUpListeners()

    }

    private fun setUpObservers() {
        viewModel.isLoading.observe(viewLifecycleOwner) {
            binding.progressBar.visibility = if (viewModel.isLoadingRv) View.VISIBLE else View.GONE
        }
        viewModel.data.observe(viewLifecycleOwner) {
            val theList = it?.map { data ->
                data.name
            }
            Log.d(TAG, "setUpObservers: $theList")
            coinAdapter.submitList(it)
        }
        viewModel.homeStatus.observe(viewLifecycleOwner) {
            when (it) {
                HomeStatus.REFRESHED -> makeToast("List has been refreshed!")
                HomeStatus.END -> makeToast("You have reached the end of the list")
                HomeStatus.ERROR -> makeToast("${viewModel.errorMessage.value}")
            }
        }

    }

    private fun setUpListeners() {
        binding.refresh.setOnRefreshListener {
            viewModel.restartData()
            binding.refresh.isRefreshing = false
        }
    }


    private val scrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            val layoutManager = recyclerView.layoutManager as LinearLayoutManager
            viewModel.firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
            viewModel.visibleItemCount = layoutManager.childCount
             viewModel.totalItemCount = layoutManager.itemCount

            viewModel.checkForPagination()

        }

        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                viewModel.isScrollingRv = true
            }
        }
    }

    private fun setUpRv() {
        coinAdapter = CoinAdapter()
        binding.recyclerView.apply {
            adapter = coinAdapter
            layoutManager = LinearLayoutManager(requireContext())
            addOnScrollListener(this@HomeFragment.scrollListener)
        }


    }

    private fun makeToast(msg: String) {
        Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null

    }
}