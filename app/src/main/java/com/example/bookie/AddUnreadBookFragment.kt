package com.example.bookie

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.example.bookie.databinding.FragmentAddUnreadBookBinding

//fragment는 parameter를 가지면 안됨.
class AddUnreadBookFragment : Fragment() {
    private var _binding: FragmentAddUnreadBookBinding? = null
    private val binding get() = _binding!!

    private lateinit var etBookName: EditText
    private lateinit var etWriter: EditText
    private lateinit var etPublisher: EditText
    private lateinit var etYear: EditText
    private lateinit var btnUpload: Button
    private lateinit var btnDelete: Button
    private lateinit var imageButtonAddUnread: ImageButton

    companion object {
        @JvmStatic
        fun newInstance(bookName: String? = null, writer: String? = null,
                        publisher: String? = null, year: String? = null) =
            AddUnreadBookFragment().apply {
                arguments = Bundle().apply {
                    putString("ARG_BOOK_NAME", bookName)
                    putString("ARG_WRITER", writer)
                    putString("ARG_PUBLISHER", publisher)
                    putString("ARG_YEAR", year)
                }
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 인수 값을 Fragment에 전달
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddUnreadBookBinding.inflate(inflater, container, false)

        // EditText 초기화
        etBookName = binding.etBookName
        etWriter = binding.etWriter
        etPublisher = binding.etPublisher
        etYear = binding.etYear
        btnUpload = binding.btnUplaod
        btnDelete = binding.btnDelete

        imageButtonAddUnread = binding.imagebtnAddUnread

        // 인수로 전달된 데이터 설정
        arguments?.let {
            etBookName.setText(it.getString("ARG_BOOK_NAME"))
            etWriter.setText(it.getString("ARG_WRITER"))
            etPublisher.setText(it.getString("ARG_PUBLISHER"))
            etYear.setText(it.getString("ARG_YEAR"))
        }

        // 버튼 클릭 리스너 설정
        btnUpload.setOnClickListener {
            uploadBookDetails()
        }



        imageButtonAddUnread.setOnClickListener {
            // 이미지 선택기 열기
            openImagePicker()
        }

        return binding.root

    }

    private fun uploadBookDetails() {
        // 책 정보를 처리하는 로직 구현
        val bookName = etBookName.text.toString()
        val writer = etWriter.text.toString()
        val publisher = etPublisher.text.toString()
        val year = etYear.text.toString()

        if (bookName.isEmpty() || writer.isEmpty() || publisher.isEmpty() || year.isEmpty()) {
            Toast.makeText(requireContext(), "모든 필드를 작성해 주세요.", Toast.LENGTH_SHORT).show()
            return
        }

        // TODO: 데이터베이스에 저장하거나 다른 작업 수행
        Toast.makeText(requireContext(), "책 정보가 업로드되었습니다.", Toast.LENGTH_SHORT).show()
        // 입력 필드 초기화
        clearFields()
    }

    private fun clearFields() {
        etBookName.text.clear()
        etWriter.text.clear()
        etPublisher.text.clear()
        etYear.text.clear()
    }

    // ActivityResultLauncher 선언 (이미지 선택기)
    private val imagePickerLauncher = registerForActivityResult(ActivityResultContracts.GetContent())
    { uri: Uri? ->
        uri?.let {
            imageButtonAddUnread.setImageURI(it) // 선택된 이미지 표시
        }
    }

    private fun openImagePicker() {
        // 이미지 선택기 열기 코드 구현!!!!!!!!!!!!!!!!!!!!!!!!!!
        // Intent 사용하여 이미지 선택기 여는 코드 추가
        imagePickerLauncher.launch("image/*")
    }

    //메모리 누수나 예외 상황 발생 예방을 위한 코드
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // 바인딩 객체 해제
    }
}