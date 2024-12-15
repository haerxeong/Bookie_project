package com.example.bookie

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.bookie.databinding.ListUnreadbooksBinding

class UnreadBooksAdapter(private var unreadbooks: List<MyBook>, private val listener: OnSetReadClickListener): RecyclerView.Adapter<UnreadBooksAdapter.Holder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = ListUnreadbooksBinding.inflate(LayoutInflater.from(parent.context))
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(unreadbooks[position])
    }

    override fun getItemCount() = unreadbooks.size

    inner class Holder(private val binding: ListUnreadbooksBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(unreadbook: MyBook) {
            if (unreadbook.bookImageUrl.isNotEmpty()) {
                Glide.with(binding.root.context)
                    .load(unreadbook.bookImageUrl)
                    .into(binding.imageView)
            } else {
                binding.imageView.setImageResource(R.drawable.book)
            }
            binding.txtTitle.text = unreadbook.title
            binding.txtAuthor.text = unreadbook.author
            binding.txtPublisher.text = unreadbook.publisher
            binding.txtRelease.text = unreadbook.release.toString()

            binding.root.setOnClickListener {
                Toast.makeText(binding.root.context, "제목: ${unreadbook.title} 작가: ${unreadbook.author}", Toast.LENGTH_SHORT).show()
            }

            binding.btnSetRead.setOnClickListener {
                listener.onSetReadClick(unreadbook)
            }
        }
    }

    fun updateBooks(newBooks: List<MyBook>) {
        unreadbooks = newBooks
        notifyDataSetChanged()
    }

    interface OnSetReadClickListener {
        fun onSetReadClick(unreadBook: MyBook)
    }
}