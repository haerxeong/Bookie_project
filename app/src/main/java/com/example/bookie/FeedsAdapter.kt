package com.example.bookie

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.bookie.databinding.ListFeedsBinding

class FeedsAdapter(val feeds: Array<Feeds>): RecyclerView.Adapter<FeedsAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = ListFeedsBinding.inflate(LayoutInflater.from(parent.context))
        return Holder(binding)
    }

    override fun getItemCount() = feeds.size


    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(feeds[position])
    }


    class Holder(private val binding: ListFeedsBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(feed: Feeds) {
            binding.imageID.setImageResource(R.drawable.ic_launcher_foreground)
            binding.txtName.text = feed.name
            binding.txtBookName.text = feed.bookName
            binding.txtBookReviw.text = feed.bookReview
            binding.imageBook.setImageResource(R.drawable.book)
        }
    }
}