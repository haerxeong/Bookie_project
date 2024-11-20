package com.example.bookie

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.bookie.databinding.ListReadbooksBinding

class ReadBooksAdapter(
    val readbooks: Array<MyBook>,
    private val listener: OnSetWriteClickListener
) : RecyclerView.Adapter<ReadBooksAdapter.Holder>() {

    // 글쓰기 버튼 클릭 이벤트를 위한 인터페이스
    interface OnSetWriteClickListener {
        fun onSetWriteClick()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = ListReadbooksBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding, listener)
    }

    // 렌더링해주는 역할
    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(readbooks[position])
    }

    // view의 아이템 수
    override fun getItemCount() = readbooks.size

    class Holder(
        private val binding: ListReadbooksBinding,
        private val listener: OnSetWriteClickListener
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(readbook: MyBook) {
            binding.imageView.setImageResource(R.drawable.book)
            binding.txtTitle.text = readbook.title
            binding.txtAuthor.text = readbook.author
            binding.txtPublisher.text = readbook.publisher
            binding.txtRelease.text = readbook.release.toString()

            binding.root.setOnClickListener {
                Toast.makeText(binding.root.context, "제목: ${readbook.title} 작가: ${readbook.author}", Toast.LENGTH_SHORT).show()
            }

            // 글쓰기 버튼 클릭 시 인터페이스 메서드 호출
            binding.btnWrite.setOnClickListener {
                listener.onSetWriteClick()
            }
        }
    }
}
