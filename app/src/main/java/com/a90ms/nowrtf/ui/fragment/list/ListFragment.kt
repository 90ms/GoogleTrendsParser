package com.a90ms.nowrtf.ui.fragment.list

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.NetworkInfo
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.recyclerview.widget.DefaultItemAnimator
import com.a90ms.nowrtf.R
import com.a90ms.nowrtf.databinding.FragmentListBinding
import com.a90ms.nowrtf.parser.Parser
import com.a90ms.nowrtf.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListFragment: BaseFragment<FragmentListBinding>(R.layout.fragment_list) {

    private lateinit var adapter: SearchListAdapter
    private lateinit var parser: Parser

    private val mainViewModel by viewModels<ListViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupData()
        setupObserver()
    }

    @SuppressLint("ResourceAsColor")
    private fun setupData() {
        parser = Parser.Builder()
            .context(requireContext())
            .cacheExpirationMillis(24L * 60L * 60L * 100L) //하루
            .build()

        binding.recyclerView.run {
            itemAnimator = DefaultItemAnimator()
            setHasFixedSize(true)
        }

        binding.swipLayout.run {
            setColorSchemeColors(R.color.teal_700, R.color.purple_200)
            canChildScrollUp()
            setOnRefreshListener {
                adapter.run {
                    trendsItems.clear()
                    notifyDataSetChanged()
                }
                isRefreshing = true
                mainViewModel.fetchFeed(parser)
            }
        }

        if(isNetwork()) mainViewModel.fetchFeed(parser)
    }

    private fun setupObserver() {
        mainViewModel.run {
            rssChannel.observe(requireActivity()) {channel ->
                adapter =
                    SearchListAdapter(channel.trendsItems)
                binding.recyclerView.adapter = adapter
                adapter.notifyDataSetChanged()
                binding.progressBar.visibility = View.GONE
                binding.swipLayout.isRefreshing = false
            }
        }
    }

    @Suppress("DEPRECATION")
    fun isNetwork(): Boolean {
        val connectivityManager = requireActivity().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val network = connectivityManager.activeNetwork ?: return false
            val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false
            return when {
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                else -> false
            }
        } else {
            val activeNetworkInfo: NetworkInfo? = connectivityManager.activeNetworkInfo
            return activeNetworkInfo != null && activeNetworkInfo.isConnected
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
}