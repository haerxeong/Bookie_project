package com.example.bookie

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bookie.databinding.FragmentBookFeedPageBinding
/*
**inflate**는 XML 레이아웃 파일을 메모리에 로드하여 화면에 표시할 수 있는 View 객체로 만드는 과정.
 즉, XML로 정의된 UI 레이아웃을 코드에서 사용할 수 있도록 변환하는 작업.

 **bind**는 데이터를 View에 연결하는 과정.
 * ViewHolder는 bind 메서드를 통해 특정 위치의 데이터를 View에 설정하는 역할.

 **View**는 안드로이드에서 화면에 표시되는 UI 구성 요소를 나타내는 가장 기본적인 클래스.
 * 모든 UI 요소는 View를 기반으로 하며, 버튼, 텍스트뷰, 이미지뷰 등은 모두 View 클래스를 상속한 클래스들
 */

class BookFeedPageFragment : Fragment() {

    private var _binding: FragmentBookFeedPageBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // RecyclerView 설정
        binding.recFeeds.layoutManager = LinearLayoutManager(requireContext())
        //binding.recFeeds.adapter = FeedsAdapter()

        //binding = FragmentBookFeedPageBinding.inflate(layoutInflater)
        //setContentView(binding.root)
        //binding.recFeeds.layoutManager = LinearLayoutManager(this)
        //binding.recFeeds.adapter = FeedsAdapter()
/*
        // Firebase Database 참조 설정
        database = FirebaseDatabase.getInstance().getReference("feeds")
        feedList = mutableListOf()
        adapter = FeedsAdapter(feedList)



        // Firebase에서 데이터 가져오기
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                feedList.clear()
                for (feedSnapshot in snapshot.children) {
                    val feed = feedSnapshot.getValue(Feed::class.java)
                    feed?.let { feedList.add(it) }
                }
                adapter.notifyDataSetChanged() // 데이터 변경 후 어댑터 갱신
            }

            override fun onCancelled(error: DatabaseError) {
                // 오류 처리
            }
        })

 */
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentBookFeedPageBinding.inflate(inflater, container, false)
        return binding.root
    }
/*
    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            BookFeedPageFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

 */
}