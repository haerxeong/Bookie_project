package com.example.bookie

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.bookie.databinding.FragmentAddUnreadBookBinding
import com.example.bookie.databinding.FragmentUnreadBookBinding


class AddUnreadBookFragment : Fragment() {
    private lateinit var binding: FragmentAddUnreadBookBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /*
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
         */
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAddUnreadBookBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 버튼 이동 설정
        binding.btnUpload.setOnClickListener {
            findNavController().navigate(R.id.action_addUnreadBookFragment_to_unreadBookFragment)
        }
    }
/*
    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AddUnreadBookFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

 */
}