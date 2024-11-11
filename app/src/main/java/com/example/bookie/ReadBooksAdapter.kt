package com.example.bookie

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.bookie.databinding.ListReadbooksBinding

class ReadBooksAdapter(val readbooks: Array<MyBook>) : RecyclerView.Adapter<ReadBooksAdapter.Holder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = ListReadbooksBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    // 렌더링해주는 역할
    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(readbooks[position])
    }

    // view의 아이템 수
    override fun getItemCount() = readbooks.size

    class Holder(private val binding: ListReadbooksBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(readbook: MyBook) {
            binding.imageView.setImageResource(R.drawable.book)
            binding.txtTitle.text = readbook.title
            binding.txtAuthor.text = readbook.author
            binding.txtPublisher.text = readbook.publisher
            binding.txtRelease.text = readbook.release.toString()

            binding.root.setOnClickListener {
                Toast.makeText(binding.root.context, "제목: ${readbook.title} 작가: ${readbook.author}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}