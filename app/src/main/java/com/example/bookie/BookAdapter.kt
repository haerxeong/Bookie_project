package com.example.bookie

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.bookie.databinding.ListHomeBookItemBinding
import com.example.bookie.databinding.ListUnreadbooksBinding

class BookAdapter(private val books: List<MyBook>) : RecyclerView.Adapter<BookAdapter.Holder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = ListHomeBookItemBinding.inflate(LayoutInflater.from(parent.context))
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(books[position])
    }

    class Holder(private val binding: ListHomeBookItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(books: MyBook) {
            binding.bookImage.setImageResource(R.drawable.book)
            binding.bookTitle.text = books.title
            binding.bookAuthor.text = books.author
            binding.bookRelease.text = books.release.toString()
        }
    }

    override fun getItemCount(): Int {
        return books.size
    }
}