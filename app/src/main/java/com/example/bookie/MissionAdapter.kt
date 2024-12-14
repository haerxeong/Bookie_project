package com.example.bookie

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.bookie.databinding.FragmentMissionAdapterBinding

class MissionAdapter(private val missions: List<Mission>) : RecyclerView.Adapter<MissionAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        // fragment_mission_adapter.xml을 바인딩
        val binding = FragmentMissionAdapterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        // 미션 데이터를 ViewHolder에 전달
        holder.bind(missions[position])
    }

    override fun getItemCount(): Int = missions.size

    class Holder(private val binding: FragmentMissionAdapterBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(mission: Mission) {
            // fragment_mission_adapter.xml의 UI 요소에 미션 데이터 연결
            binding.missionTitle.text = mission.missionText
            binding.missionDetail.text = "쿠키: ${mission.cookies}"
        }
    }
}
