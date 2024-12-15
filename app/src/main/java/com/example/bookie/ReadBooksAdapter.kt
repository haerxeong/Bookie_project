package com.example.bookie

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.bookie.databinding.ListReadbooksBinding

class ReadBooksAdapter(
    val readbooks: Array<MyBook>,
    private val listener: OnSetWriteClickListener
) : RecyclerView.Adapter<ReadBooksAdapter.Holder>() {

    interface OnSetWriteClickListener {
        fun onSetWriteClick(bookId: String)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = ListReadbooksBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding, listener)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(readbooks[position])
    }

    override fun getItemCount() = readbooks.size

    class Holder(
        private val binding: ListReadbooksBinding,
        private val listener: OnSetWriteClickListener
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(readbook: MyBook) {
            if (readbook.bookImageUrl.isNotEmpty()) {
                Glide.with(binding.root.context)
                    .load(readbook.bookImageUrl)
                    .into(binding.imageView)
            } else {
                binding.imageView.setImageResource(R.drawable.book)
            }
            binding.txtTitle.text = readbook.title
            binding.txtAuthor.text = readbook.author
            binding.txtPublisher.text = readbook.publisher
            binding.txtRelease.text = readbook.release.toString()

            binding.root.setOnClickListener {
                Toast.makeText(binding.root.context, "제목: ${readbook.title} 작가: ${readbook.author}", Toast.LENGTH_SHORT).show()
            }

            binding.btnWrite.setOnClickListener {
                Log.d("ReadBooksAdapter", "Book clicked, Book ID: ${readbook.id}")
                listener.onSetWriteClick(readbook.id)
            }
        }
    }
}