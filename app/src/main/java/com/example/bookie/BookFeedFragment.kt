package com.example.bookie

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bookie.databinding.FragmentBookFeedBinding

class BookFeedFragment : Fragment() {

    private var _binding: FragmentBookFeedBinding? = null
    private val binding get() = _binding!!

    val feeds = arrayOf(
        Feeds(R.drawable.ic_launcher_foreground, "제로", "채식주의자",
            "진정한 나는 무엇인지, 사회에서의 나의 위치는 어떤 것인지 고민하게 만드는 소설", R.drawable.book),
        Feeds(R.drawable.ic_launcher_foreground, "주연", "구토",
            "어려운 소설.. 제목처럼 읽다가 구토가 나올 수도..", R.drawable.book),
        Feeds(R.drawable.ic_launcher_foreground, "차은우", "아내를 모자로 착각한 남자",
            "다양한 아픔을 간직한 인물들이 등장한다. 그 중 가장 기억에 남았던 인물이 있는데...", R.drawable.book)
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBookFeedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // RecyclerView 설정
        binding.recFeeds.layoutManager = LinearLayoutManager(requireContext())
        binding.recFeeds.adapter = FeedsAdapter(feeds)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

/*
    companion object {

        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            BookFeedFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

 */
