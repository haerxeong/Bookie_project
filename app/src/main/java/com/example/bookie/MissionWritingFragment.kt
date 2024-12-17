package com.example.bookie

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.bookie.databinding.FragmentMissionWritingBinding
import com.example.bookie.viewmodel.MissionViewModel

class MissionWritingFragment : Fragment() {

    private var _binding: FragmentMissionWritingBinding? = null
    private val missionViewModel: MissionViewModel by activityViewModels()

    private val userId = "1"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMissionWritingBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding?.let { binding ->
            val missionInput = binding.mission1EditText
            val publishButton = binding.publishButton

            // 발행 버튼 초기 상태 비활성화
            publishButton.isEnabled = false

            // 입력 필드 상태 감지
            missionInput.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    updatePublishButtonState()
                }

                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            })

            // 발행 버튼 클릭 리스너
            publishButton.setOnClickListener {
                val missionText = missionInput.text.toString()
                if (missionText.isEmpty()) {
                    Toast.makeText(requireContext(), "미션 내용을 입력해주세요.", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                // 새로운 미션 생성
                val newMission = Mission(missionText)

                // 미션 추가 및 쿠키 증가
                missionViewModel.addMissionAndIncrementCookies(userId, newMission)

                // 입력 필드 초기화
                missionInput.text.clear()

                // Toast 알림
                Toast.makeText(requireContext(), "미션이 추가되고 쿠키가 증가했습니다!", Toast.LENGTH_SHORT).show()

                // RewardCookieFragment로 이동
                findNavController().navigate(R.id.action_missionWritingFragment_to_rewardCookieFragment)
            }
        }
    }

    // 버튼 활성화 상태 업데이트
    private fun updatePublishButtonState() {
        _binding?.let { binding ->
            val missionText = binding.mission1EditText.text.toString()
            binding.publishButton.isEnabled = missionText.isNotEmpty()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
