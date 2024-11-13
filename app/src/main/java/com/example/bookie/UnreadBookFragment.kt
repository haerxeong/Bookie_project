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

class UnreadBookFragment : Fragment(), UnreadBooksAdapter.OnSetReadClickListener {
    val viewModel: BookViewModel by activityViewModels()

    private lateinit var binding: FragmentUnreadBookBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // View 바인딩 초기화
        binding = FragmentUnreadBookBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // RecyclerView 설정
        binding.recUnreadBooks.layoutManager = LinearLayoutManager(requireContext())

        viewModel.unreadBooks.observe(viewLifecycleOwner) { books ->
            books?.let {
                binding.recUnreadBooks.adapter = UnreadBooksAdapter(it.toTypedArray(), this)
            }
        }

        // 버튼 이동 설정
        binding.btnRead.setOnClickListener {
            findNavController().navigate(R.id.action_unreadBookFragment_to_readBookFragment)
        }
        binding.btnAdd.setOnClickListener {
            findNavController().navigate(R.id.action_unreadBookFragment_to_addUnreadBookFragment)
        }
    }

    override fun onSetReadClick(unreadbook: MyBook) {
        viewModel.setIsRead(unreadbook.id)  // btnSetRead가 클릭될 때 호출 -> setIsRead 호출
    }
}