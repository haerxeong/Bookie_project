package com.example.bookie.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.bookie.Mission
import com.example.bookie.repository.MissionRepository

class MissionViewModel : ViewModel() {
    private val _missionList = MutableLiveData<List<Mission>>()
    val missionList: LiveData<List<Mission>> get() = _missionList

    private val repository = MissionRepository()

    init {
        val userId = "1"
        loadMissions(userId)
    }

    // 특정 사용자에 대한 모든 미션 목록을 로드
    fun loadMissions(userId: String) {
        repository.getAllMissions(userId, _missionList)
    }

    // 새로운 미션 추가 및 쿠키 증가
    fun addMissionAndIncrementCookies(userId: String, mission: Mission) {
        repository.addMissionAndIncrementCookies(userId, mission)
    }


}
