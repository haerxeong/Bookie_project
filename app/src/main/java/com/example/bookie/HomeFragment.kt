package com.example.bookie

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.navigation.fragment.findNavController
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

        // 책 목록 RecyclerView 설정
        binding?.bookList?.layoutManager = GridLayoutManager(requireContext(), 1, GridLayoutManager.HORIZONTAL, false)
        bookViewModel.unreadBooks.observe(viewLifecycleOwner) { books ->
            books?.let {
                binding?.bookList?.adapter = BookAdapter(it)
            }
        }

        // 미션 목록 RecyclerView 설정
        binding?.missionList?.layoutManager = LinearLayoutManager(requireContext())
        missionViewModel.missionList.observe(viewLifecycleOwner) { missions ->
            missions?.let {
                binding?.missionList?.adapter = MissionAdapter(it)
            }
        }

        // 미션 데이터 가져오기 (ID 직접 설정)
        val userId = "defaultUserId" // 여기에 기본 사용자 ID를 직접 입력
        missionViewModel.loadMissions(userId)

        // 사용자 목표 및 인사말 설정
        userViewModel.user.observe(viewLifecycleOwner) { user ->
            user?.let {
                binding?.greetingText?.text = "${it.username}님, \n오늘 읽은 책이 내일의 나를 만듭니다."
                binding?.goalTxt?.text = "Goal: ${bookViewModel.readBooks.value?.size ?: 0}/${it.goal}"

                if (it.goal > 0) {
                    binding?.goalRing?.progress = ((bookViewModel.readBooks.value?.size ?: 0) * 100 / it.goal)
                } else {
                    binding?.goalRing?.progress = 0
                }
            }
        }

        // 읽은 책 데이터 관찰
        bookViewModel.readBooks.observe(viewLifecycleOwner) { readBooks ->
            userViewModel.user.value?.let { user ->
                binding?.goalTxt?.text = "Goal: ${readBooks.size}/${user.goal}"

                if (user.goal > 0) {
                    binding?.goalRing?.progress = (readBooks.size * 100 / user.goal)
                } else {
                    binding?.goalRing?.progress = 0
                }
            }
        }

        // 현재 미션 관찰
        missionViewModel.mission.observe(viewLifecycleOwner) { mission ->
            mission?.let {
                binding?.btnCookie?.text = "쿠키 x${it.cookies}"
                binding?.missionTitle?.text = it.missionText
            }
        }

        // 미션 시작 버튼 클릭 리스너
        binding?.btnStartMission?.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_missionWritingFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}