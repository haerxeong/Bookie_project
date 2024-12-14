package com.example.bookie.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.bookie.Mission
import com.google.firebase.database.*

class MissionRepository {
    private val database = FirebaseDatabase.getInstance()
    private val userRef = database.getReference("users")

    // **Create**: 새로운 미션 추가
    fun addMission(userId: String, mission: Mission) {
        val missionRef = userRef.child(userId).child("mission").push()
        missionRef.setValue(mission).addOnCompleteListener {
            if (it.isSuccessful) {
                Log.d("MissionRepository", "Mission added successfully for user: $userId")
            } else {
                Log.e("MissionRepository", "Error adding mission", it.exception)
            }
        }
    }

    // **Read**: 모든 미션 가져오기 (특정 사용자)
    fun getAllMissions(userId: String, missionsLiveData: MutableLiveData<List<Mission>>) {
        userRef.child(userId).child("mission").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val missionList = mutableListOf<Mission>()
                for (data in snapshot.children) {
                    val mission = data.getValue(Mission::class.java)
                    mission?.let { missionList.add(it) }
                }
                missionsLiveData.postValue(missionList)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("MissionRepository", "Failed to load missions for user: $userId", error.toException())
            }
        })
    }

    // **Read**: 하나의 미션 가져오기
    fun getMission(userId: String, missionLiveData: MutableLiveData<Mission>) {
        userRef.child(userId).child("mission").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val mission = snapshot.children.firstOrNull()?.getValue(Mission::class.java)
                missionLiveData.value = mission
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("MissionRepository", "Failed to load mission for user: $userId", error.toException())
            }
        })
    }

    // **Create + Increment**: 미션 추가와 쿠키 증가
    fun addMissionAndIncrementCookies(userId: String, mission: Mission) {
        val missionRef = userRef.child(userId).child("mission").push()
        missionRef.setValue(mission).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                // 쿠키 +1
                userRef.child(userId).child("account").child("cookies").addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val currentCookies = snapshot.getValue(Int::class.java) ?: 0
                        userRef.child(userId).child("account").child("cookies").setValue(currentCookies + 1)
                    }

                    override fun onCancelled(error: DatabaseError) {
                        Log.e("MissionRepository", "Failed to increment cookies for user: $userId", error.toException())
                    }
                })
            } else {
                Log.e("MissionRepository", "Error adding mission and incrementing cookies", task.exception)
            }
        }
    }

    // **Update**: 특정 미션 수정
    fun updateMission(userId: String, missionId: String, updatedMission: Mission) {
        userRef.child(userId).child("mission").child(missionId).setValue(updatedMission).addOnCompleteListener {
            if (it.isSuccessful) {
                Log.d("MissionRepository", "Mission updated successfully for user: $userId, missionId: $missionId")
            } else {
                Log.e("MissionRepository", "Error updating mission for user: $userId, missionId: $missionId", it.exception)
            }
        }
    }

    // **Delete**: 특정 미션 삭제
    fun deleteMission(userId: String, missionId: String) {
        userRef.child(userId).child("mission").child(missionId).removeValue().addOnCompleteListener {
            if (it.isSuccessful) {
                Log.d("MissionRepository", "Mission deleted successfully for user: $userId, missionId: $missionId")
            } else {
                Log.e("MissionRepository", "Error deleting mission for user: $userId, missionId: $missionId", it.exception)
            }
        }
    }
}
