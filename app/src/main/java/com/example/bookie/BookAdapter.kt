package com.example.bookie

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class BookAdapter(private val books: List<MyBook>) : RecyclerView.Adapter<BookAdapter.BookViewHolder>() {

    class BookViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.txt_title)
        val authorTextView: TextView = itemView.findViewById(R.id.txt_author)
        val publisherTextView: TextView = itemView.findViewById(R.id.txt_publisher)
        val yearTextView: TextView = itemView.findViewById(R.id.txt_release)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_unreadbooks, parent, false)
        return BookViewHolder(view)
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        val book = books[position]
        holder.titleTextView.text = book.title
        holder.authorTextView.text = book.author
        holder.publisherTextView.text = book.publisher
        holder.yearTextView.text = book.release.toString()
    }

    override fun getItemCount(): Int {
        return books.size
    }
}