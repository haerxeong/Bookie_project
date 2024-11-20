package com.example.bookie

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.navigation.fragment.findNavController
//import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bookie.databinding.FragmentHomeBinding
import com.example.bookie.viewmodel.BookViewModel

class HomeFragment : Fragment() {
    val viewModel: BookViewModel by activityViewModels()
    var binding: FragmentHomeBinding? = null // private으로 해야하나?
    /*
    private val books: Array<MyBook> = arrayOf(
        MyBook("불편한 편의점", "김호연", "나무옆의자", 2021),
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
        binding = FragmentHomeBinding.inflate(inflater)

        // Inflate the layout for this fragment
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // RecyclerView 설정
        binding?.bookList?.layoutManager = GridLayoutManager(requireContext(), 1, GridLayoutManager.HORIZONTAL, false)

        viewModel.unreadBooks.observe(viewLifecycleOwner) { books ->
            books?.let {
                binding?.bookList?.adapter = BookAdapter(it)
            }
        }
        binding?.btnStartMission?.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_missionWritingFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}