package com.example.bookie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.bookie.databinding.FragmentBookDiaryWriteBinding

class BookDiaryWriteFragment : Fragment() {

    private var binding: FragmentBookDiaryWriteBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBookDiaryWriteBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 발행 버튼 클릭 리스너
        binding?.publishButton?.setOnClickListener {
            // 발행 완료 토스트 메시지
            Toast.makeText(requireContext(), "발행이 완료되었습니다", Toast.LENGTH_SHORT).show()

            // readBookFragment로 이동
            findNavController().navigate(R.id.action_bookDiaryWriteFragment_to_readBookFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}
