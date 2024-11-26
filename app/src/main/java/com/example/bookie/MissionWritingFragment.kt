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
import com.example.bookie.databinding.FragmentMissionWritingBinding
import com.example.bookie.viewmodel.MissionViewModel

class MissionWritingFragment : Fragment() {

    private var _binding: FragmentMissionWritingBinding? = null
    private val binding get() = _binding!!
    private val missionViewModel: MissionViewModel by activityViewModels()

    // 예시로 userId를 하드코딩했으나, 실제로는 로그인한 사용자 ID를 받아와야 함
    private val userId = "1"  // 이 부분을 실제 로그인된 사용자 ID로 변경해야 함

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMissionWritingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val missionInput = binding.mission1EditText
        val publishButton = binding.publishButton

        // 발행 버튼 비활성화 초기화
        publishButton.isEnabled = false

        // 입력 필드가 비어있는지 체크하여 버튼 상태 변경
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

            // 새로운 미션 객체 생성
            val newMission = Mission(missionText)

            // 미션 추가 (userId와 함께)
            missionViewModel.addMission(userId, newMission)

            // 입력 필드 비우기
            missionInput.text.clear()

            // Toast로 알림
            Toast.makeText(requireContext(), "미션이 추가되었습니다!", Toast.LENGTH_SHORT).show()
        }
    }

    // 버튼 활성화 상태 업데이트
    private fun updatePublishButtonState() {
        val missionText = binding.mission1EditText.text.toString()
        binding.publishButton.isEnabled = missionText.isNotEmpty()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
