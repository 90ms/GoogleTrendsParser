package com.a90ms.nowrtf.ui.fragment.list

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.a90ms.nowrtf.R
import com.a90ms.nowrtf.databinding.RowContentsBinding
import com.a90ms.nowrtf.parser.data.TrendsItem
import com.a90ms.nowrtf.util.CommonDialog
import com.a90ms.nowrtf.util.DateConverter
import com.squareup.picasso.Picasso


class SearchListAdapter(val trendsItems: MutableList<TrendsItem>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = RowContentsBinding.inflate(layoutInflater, parent, false)
        return ContentsViewHolder(
            binding
        )
    }

    class ContentsViewHolder(private val binding: RowContentsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(item: TrendsItem, isVisible: Boolean) {
            binding.run {

                rank.text = item.rank.toString()

                txtDate.text = DateConverter.changeDateFormat(item.pubDate)
                txtDate.isVisible = isVisible

                title.text = item.title
                traffic.text = item.traffic

                pubDate.text = DateConverter.changeTimeFormat(item.pubDate)

                Picasso.get()
                    .load(item.mainImage)
                    .placeholder(R.drawable.ic_launcher_background)
                    .into(image)

                imageSource.text = "${item.mainImageSource}"

                rootView.setOnClickListener { view ->
                    CommonDialog(
                        String.format(view.context.resources.getString(R.string.search_title, item.title)),
                        R.string.search_ok,
                        R.string.search_dismiss
                    ).commonDialog(view.context) {
                        val uri: Uri = Uri.parse("https://search.naver.com/search.naver?where=nexearch&sm=top_hty&fbm=1&ie=utf8&query=${item.title}")
//                        val uri: Uri = Uri.parse("https://www.google.com/search?q=${item.title}")
                        val intent = Intent(Intent.ACTION_VIEW, uri)
                        startActivity(view.context, intent, null)
                    }.show()
                }
            }
        }
    }

    override fun getItemCount() = trendsItems.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        when (holder) {
            is ContentsViewHolder -> {

                val isVisible = if (position > 0) {
                    DateConverter.changeDateFormat(trendsItems[position].pubDate) != DateConverter.changeDateFormat(trendsItems[position - 1].pubDate)
                } else {
                    true
                }
                holder.bind(trendsItems[position], isVisible)
            }
        }
    }

}