package com.example.bookie

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
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

        // RecyclerView 설정
        binding?.bookList?.layoutManager = GridLayoutManager(requireContext(), 1, GridLayoutManager.HORIZONTAL, false)

        bookViewModel.unreadBooks.observe(viewLifecycleOwner) { books ->
            books?.let {
                binding?.bookList?.adapter = BookAdapter(it)
            }
        }

        // 목표 설정하는 것도 만들어야 함..!!! 회원가입할 때 받으면 좋을 것 같은데...
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

        missionViewModel.mission.observe(viewLifecycleOwner) { mission ->
            mission?.let {
                binding?.btnCookie?.text = "쿠키 x${it.cookies}"
                binding?.missionTitle?.text = it.missionText
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
