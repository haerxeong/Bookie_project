package com.example.bookie

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.bookie.databinding.ListHomeBookItemBinding

class BookAdapter(private val books: List<MyBook>) : RecyclerView.Adapter<BookAdapter.Holder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = ListHomeBookItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(books[position])
    }

    override fun getItemCount(): Int {
        return books.size
    }

    class Holder(private val binding: ListHomeBookItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(book: MyBook) {
            if (book.bookImageUrl.isNotEmpty()) {
                Glide.with(binding.root.context)
                    .load(book.bookImageUrl)
                    .into(binding.bookImage)
            } else {
                binding.bookImage.setImageResource(R.drawable.book)
            }
            binding.bookTitle.text = book.title
            binding.bookAuthor.text = book.author
            binding.bookRelease.text = book.release.toString()
        }
    }
}