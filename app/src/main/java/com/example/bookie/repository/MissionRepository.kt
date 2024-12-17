package com.example.bookie.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.bookie.Mission
import com.google.firebase.database.*

class MissionRepository {
    private val database = FirebaseDatabase.getInstance()
    private val userRef = database.getReference("users")


    // 미션 가져오기
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
                Log.e(
                    "MissionRepository",
                    "Failed to load missions for user: $userId",
                    error.toException()
                )
            }
        })
    }

    // 미션 추가와 쿠키 증가
    fun addMissionAndIncrementCookies(userId: String, mission: Mission) {
        val missionRef = userRef.child(userId).child("mission").push()
        missionRef.setValue(mission).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                // 쿠키 +1
                userRef.child(userId).child("account").child("cookies")
                    .addListenerForSingleValueEvent(object : ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            val currentCookies = snapshot.getValue(Int::class.java) ?: 0
                            userRef.child(userId).child("account").child("cookies")
                                .setValue(currentCookies + 1)
                        }

                        override fun onCancelled(error: DatabaseError) {
                            Log.e(
                                "MissionRepository",
                                "Failed to increment cookies for user: $userId",
                                error.toException()
                            )
                        }
                    })
            } else {
                Log.e(
                    "MissionRepository",
                    "Error adding mission and incrementing cookies",
                    task.exception
                )
            }
        }
    }
}
