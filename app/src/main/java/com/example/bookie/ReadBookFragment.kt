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

class ReadBookFragment : Fragment() {
    val viewModel: BookViewModel by activityViewModels()

    private lateinit var binding: FragmentReadBookBinding
    /*
     private val readBooks = arrayOf(
        MyBook("호밀밭의 파수꾼", "제롬 데이비드 샐린저", "민음사", 2023),
        MyBook("채식주의자", "한강", "창비", 2022),
        MyBook("코스모스", "칼 세이건", "사이언스북스", 2006),
        MyBook("물고기는 존재하지 않는다", "룰루 밀러", "곰출판", 2021)
    )
     */

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
                binding.recReadBooks.adapter = ReadBooksAdapter(it.toTypedArray())
            }
        }

        // 버튼 이동 설정
        binding?.btnUnread?.setOnClickListener {
            findNavController().navigate(R.id.action_readBookFragment_to_unreadBookFragment)
        }
    }
}