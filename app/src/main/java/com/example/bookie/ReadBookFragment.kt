package com.example.bookie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bookie.databinding.FragmentReadBookBinding
import com.example.bookie.viewmodel.BookViewModel

class ReadBookFragment : Fragment(), ReadBooksAdapter.OnSetWriteClickListener {

    val viewModel: BookViewModel by activityViewModels()
    private lateinit var binding: FragmentReadBookBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // View 바인딩 초기화
        binding = FragmentReadBookBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // RecyclerView 설정
        binding.recReadBooks.layoutManager = LinearLayoutManager(requireContext())

        viewModel.readBooks.observe(viewLifecycleOwner) { books ->
            books?.let {
                binding.recReadBooks.adapter = ReadBooksAdapter(it.toTypedArray(), this)
            }
        }

        // UnreadBookFragment로 이동
        binding.btnUnread.setOnClickListener {
            findNavController().navigate(R.id.action_readBookFragment_to_unreadBookFragment)
        }
    }

    // OnSetWriteClickListener 구현 - BookDiaryWriteFragment로 이동
    override fun onSetWriteClick(bookId: String) {
        val bundle = Bundle()
        bundle.putString("bookId", bookId)  // bookId를 Bundle에 저장

        findNavController().navigate(R.id.action_readBookFragment_to_bookDiaryWriteFragment, bundle)
    }
}