package com.example.bookie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.bookie.databinding.FragmentMissionWritingBinding

class MissionWritingFragment : Fragment() {

    private var binding: FragmentMissionWritingBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMissionWritingBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 발행 버튼 클릭 시 글귀가 없으면 토스트 메시지, 있으면 RewardCookieFragment로 이동
        binding?.publishButton?.setOnClickListener {
            val mission1Text = binding?.mission1EditText?.text.toString()
            if (mission1Text.isBlank()) {
                Toast.makeText(requireContext(), "글귀를 입력하세요.", Toast.LENGTH_SHORT).show()
            } else {
                findNavController().navigate(R.id.action_missionWritingFragment_to_rewardCookieFragment)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}
