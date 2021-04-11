package com.a90ms.nowrtf.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.a90ms.nowrtf.R
import com.a90ms.nowrtf.databinding.RowContentsBinding
import com.a90ms.nowrtf.databinding.RowDateBinding
import com.a90ms.nowrtf.parser.data.Article
import com.a90ms.nowrtf.util.DateConverter
import com.squareup.picasso.Picasso

class ArticleAdapter(val articles: MutableList<Article>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        val layoutInflater = LayoutInflater.from(parent.context)

        return when (viewType) {
            TYPE_DATE -> {
                val binding = RowDateBinding.inflate(layoutInflater, parent, false)
                DateViewHolder(binding)
            }
            else -> {
                val binding = RowContentsBinding.inflate(layoutInflater, parent, false)
                ContentsViewHolder(binding)
            }
        }
    }

    class DateViewHolder(private val binding: RowDateBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Article) {
            binding.run {
                date.text = item.pubDate
            }
        }
    }

    class ContentsViewHolder(private val binding: RowContentsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(item: Article, isVisible: Boolean) {
            binding.run {

                txtDate.text = DateConverter.getDateTitle(item.pubDate)
                txtDate.isVisible = isVisible

                title.text = item.title
                traffic.text = item.traffic

                pubDate.text = DateConverter.getDate(item.pubDate)
                var newsData = ""
                for (i in 0 until item.news.size) {
                    newsData = if (i == item.news.size - 1) {
                        newsData + item.news[i]
                    } else {
                        newsData + item.news[i]
                    }
                }
                categories.text = newsData
                Picasso.get()
                    .load(item.mainImage)
                    .placeholder(R.drawable.ic_launcher_background)
                    .into(image)

                imageSource.text = "by [${item.mainImageSource}]"
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position > 0) {
            if (articles[position].pubDate == articles[position - 1].pubDate) {
                TYPE_DATE
            } else {
                TYPE_CONTENTS
            }
        } else {
            TYPE_CONTENTS
        }
    }

    override fun getItemCount() = articles.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        when (holder) {
            is ContentsViewHolder -> {

                val isVisible = if (position > 0) {
                    DateConverter.getDateTitle(articles[position].pubDate) != DateConverter.getDateTitle(articles[position-1].pubDate)
                } else {
                    true
                }

                holder.bind(articles[position], isVisible)
            }
            is DateViewHolder -> holder.bind(articles[position])
        }
    }

    companion object {
        private const val TYPE_CONTENTS = 0
        private const val TYPE_DATE = 1
    }
}