package com.example.bookie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bookie.databinding.FragmentUnreadBookBinding
import com.example.bookie.viewmodel.BookViewModel
import androidx.appcompat.widget.SearchView

class UnreadBookFragment : Fragment(), UnreadBooksAdapter.OnSetReadClickListener {
    val viewModel: BookViewModel by activityViewModels()

    private lateinit var binding: FragmentUnreadBookBinding
    private lateinit var adapter: UnreadBooksAdapter
    private var allUnreadBooks: List<MyBook> = emptyList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUnreadBookBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // RecyclerView 설정
        binding.recUnreadBooks.layoutManager = LinearLayoutManager(requireContext())
        adapter = UnreadBooksAdapter(emptyList(), this)
        binding.recUnreadBooks.adapter = adapter

        // unreadBooks LiveData 관찰
        viewModel.unreadBooks.observe(viewLifecycleOwner) { books ->
            allUnreadBooks = books
            filterBooks(binding.searchBook.query.toString())
        }

        // SearchView 설정
        binding.searchBook.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterBooks(newText ?: "")
                return true
            }
        })

        // 버튼 이동 설정
        binding.btnRead.setOnClickListener {
            findNavController().navigate(R.id.action_unreadBookFragment_to_readBookFragment)
        }

        binding.btnAdd.setOnClickListener {
            findNavController().navigate(R.id.action_unreadBookFragment_to_addUnreadBookFragment)
        }
    }

    private fun filterBooks(query: String) {
        val filteredBooks = allUnreadBooks.filter { it.title.contains(query, ignoreCase = true) }
        adapter.updateBooks(filteredBooks)
    }

    // setIsRead 호출 시 isRead 값을 true로
    override fun onSetReadClick(unreadbook: MyBook) {
        viewModel.setIsRead("1", unreadbook.id, true)  // 책 상태 업데이트
    }
}