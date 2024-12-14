//MissionViewModel
package com.example.bookie.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.bookie.Mission
import com.example.bookie.repository.MissionRepository

class MissionViewModel : ViewModel() {
    private val _missionList = MutableLiveData<List<Mission>>()
    val missionList: LiveData<List<Mission>> get() = _missionList

    private val _mission = MutableLiveData<Mission>()
    val mission: LiveData<Mission> get() = _mission

    private val repository = MissionRepository()

    init {
        loadMission()
    }

    private fun loadMission() {
        val userId = "1"
        repository.getMission(userId, _mission)
    }

    // 특정 사용자에 대한 미션 목록을 로드하기 위한 메서드
    fun loadMissions(userId: String) {
        repository.getAllMissions(userId, _missionList)
    }

    // **Create**: 새로운 미션 추가
    fun addMission(userId: String, mission: Mission) {
        repository.addMission(userId, mission)
    }

    // **Update**: 미션 수정
    fun updateMission(userId: String, missionId: String, updatedMission: Mission) {
        repository.updateMission(userId, missionId, updatedMission)
    }

    // **Delete**: 미션 삭제
    fun deleteMission(userId: String, missionId: String) {
        repository.deleteMission(userId, missionId)
    }
}