package com.a90ms.nowrtf.ui

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.NetworkInfo
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.*
import androidx.recyclerview.widget.DefaultItemAnimator
import com.a90ms.nowrtf.R
import com.a90ms.nowrtf.databinding.ActivityMainBinding
import com.a90ms.nowrtf.parser.Parser

class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {
    private lateinit var adapter: ArticleAdapter
    private lateinit var parser: Parser

    private val mainViewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupData()
        setupObserver()
    }

    @SuppressLint("ResourceAsColor")
    private fun setupData() {
        parser = Parser.Builder()
            .context(this)
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
                    articles.clear()
                    notifyDataSetChanged()
                }
                isRefreshing = true
                mainViewModel.fetchFeed(parser)
            }
        }

        if(isOnline()) mainViewModel.fetchFeed(parser)
    }

    private fun setupObserver() {
        val owner = this
        mainViewModel.run {
            rssChannel.observe(owner) {channel ->
                adapter = ArticleAdapter(channel.articles)
                binding.recyclerView.adapter = adapter
                adapter.notifyDataSetChanged()
                binding.progressBar.visibility = View.GONE
                binding.swipLayout.isRefreshing = false
            }
        }
    }

    @Suppress("DEPRECATION")
    fun isOnline(): Boolean {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
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
}

