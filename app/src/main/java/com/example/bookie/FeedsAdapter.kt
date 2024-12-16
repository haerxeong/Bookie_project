package com.example.bookie

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.bookie.databinding.ListFeedsBinding

class FeedsAdapter(val bookFeedList: List<BookDiary>)
    : RecyclerView.Adapter<FeedsAdapter.Holder>(){
    //viewHolder는 RecyclerView의 각 아이템 뷰를 저장하는 역할

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        //binding 만들기
        val binding = ListFeedsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        //viewHolder 만들고 return
        return Holder(binding)
    }

    override fun getItemCount() =  bookFeedList.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        //holder에 position에 맞는 bind함수 만들어서 호출하기
        holder.bind(bookFeedList[position])
    }


    class Holder(private val binding: ListFeedsBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(bookFeed: BookDiary) {
            binding.imageView.setImageResource(R.drawable.book)
            binding.txtName.text = bookFeed.readDate
            binding.imageButton.setImageResource(R.drawable.book)
            binding.txtBookName.text = bookFeed.bookName
            binding.textView10.text = bookFeed.reviewText
        }

    }
}