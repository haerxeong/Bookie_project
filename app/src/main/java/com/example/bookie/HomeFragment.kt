package com.example.bookie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.navigation.fragment.findNavController
import com.example.bookie.adapter.MissionAdapter
import com.example.bookie.databinding.FragmentHomeBinding
import com.example.bookie.viewmodel.BookViewModel
import com.example.bookie.viewmodel.MissionViewModel
import com.example.bookie.viewmodel.UserViewModel

class HomeFragment : Fragment() {
    private val bookViewModel: BookViewModel by activityViewModels()
    private val userViewModel: UserViewModel by activityViewModels()
    private val missionViewModel: MissionViewModel by activityViewModels()
    private var binding: FragmentHomeBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 책 RecyclerView 설정
        binding?.bookList?.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        bookViewModel.unreadBooks.observe(viewLifecycleOwner) { books ->
            books?.let {
                binding?.bookList?.adapter = BookAdapter(it)
            }
        }

        // 유저 정보 업데이트
        userViewModel.user.observe(viewLifecycleOwner) { user ->
            user?.let {
                updateUserInfo(it.username, it.goal)
            }
        }

        // 읽은 책 목록 관찰
        bookViewModel.readBooks.observe(viewLifecycleOwner) { readBooks ->
            userViewModel.user.value?.let { user ->
                updateGoalProgress(readBooks.size, user.goal)
            }
        }

        // 미션 RecyclerView 설정
        binding?.missionList?.layoutManager = LinearLayoutManager(requireContext())
        missionViewModel.missionList.observe(viewLifecycleOwner) { missions ->
            missions?.let {
                binding?.missionList?.adapter = MissionAdapter(it)
            }
        }

        // 미션 시작 버튼 클릭 리스너
        binding?.btnStartMission?.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_missionWritingFragment)
        }
    }

    private fun updateUserInfo(username: String, goal: Int) {
        binding?.greetingText?.text = "$username 님, \n 오늘 읽은 책이 내일의 나를 만듭니다."
        val readBooksCount = bookViewModel.readBooks.value?.size ?: 0
        binding?.goalTxt?.text = "Goal: $readBooksCount/$goal"
        updateGoalProgress(readBooksCount, goal)
    }

    private fun updateGoalProgress(readBooksCount: Int, goal: Int) {
        if (goal > 0) {
            binding?.goalRing?.progress = (readBooksCount * 100 / goal)
        } else {
            binding?.goalRing?.progress = 0
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}
