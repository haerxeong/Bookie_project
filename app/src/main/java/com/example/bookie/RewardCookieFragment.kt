package com.example.bookie

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

class RewardCookieFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_reward_cookie, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 4초 후에 HomeFragment로 이동
        Handler(Looper.getMainLooper()).postDelayed({
            findNavController().navigate(R.id.action_rewardCookieFragment_to_homeFragment)
        }, 4000) // 4000 milliseconds = 4 seconds
    }
}
