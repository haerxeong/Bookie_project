package com.example.bookie

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.bookie.databinding.FragmentAddUnreadBookBinding
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import com.example.bookie.viewmodel.BookViewModel

class AddUnreadBookFragment : Fragment() {

    private lateinit var binding: FragmentAddUnreadBookBinding
    private val bookViewModel: BookViewModel by activityViewModels() // ViewModel을 공유

    private lateinit var etBookName: EditText
    private lateinit var etWriter: EditText
    private lateinit var etPublisher: EditText
    private lateinit var etYear: EditText
    private lateinit var btnUpload: Button
    private lateinit var imageButtonAddUnread: ImageButton

    // 이미지 선택기 ActivityResultLauncher 선언
    private val imagePickerLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            imageButtonAddUnread.setImageURI(it) // 선택된 이미지 표시
            bookViewModel.setImageUri(it) // 이미지 URI ViewModel에 저장
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddUnreadBookBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // EditText 및 버튼 초기화
        etBookName = binding.etBookName
        etWriter = binding.etWriter
        etPublisher = binding.etPublisher
        etYear = binding.etYear
        btnUpload = binding.btnUplaod
        imageButtonAddUnread = binding.imagebtnAddUnread

        // 업로드 버튼 초기 상태 비활성화
        btnUpload.isEnabled = false

        // 모든 입력 필드를 모니터링하여 조건에 맞게 버튼 활성화
        val textWatcher = object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                btnUpload.isEnabled = etBookName.text.isNotEmpty() &&
                        etWriter.text.isNotEmpty() &&
                        etPublisher.text.isNotEmpty() &&
                        etYear.text.isNotEmpty()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        }

        // 모든 EditText에 TextWatcher 설정
        listOf(etBookName, etWriter, etPublisher, etYear).forEach {
            it.addTextChangedListener(textWatcher)
        }

        // 업로드 버튼 클릭 리스너 설정
        btnUpload.setOnClickListener {
            uploadBookDetails()
            findNavController().navigate(R.id.action_addUnreadBookFragment_to_unreadBookFragment)
        }

        // 이미지 버튼 클릭 리스너 설정
        imageButtonAddUnread.setOnClickListener {
            openImagePicker()
        }
    }

    private fun uploadBookDetails() {
        val bookName = etBookName.text.toString()
        val writer = etWriter.text.toString()
        val publisher = etPublisher.text.toString()
        val year = etYear.text.toString()

        if (bookName.isEmpty() || writer.isEmpty() || publisher.isEmpty() || year.isEmpty()) {
            Toast.makeText(requireContext(), "모든 필드를 작성해 주세요.", Toast.LENGTH_SHORT).show()
            return
        }

        val newBook = MyBook(
            id = (bookViewModel.booklist.value?.size ?: 0) + 1,
            title = bookName,
            author = writer,
            publisher = publisher,
            release = year.toInt(),
            isRead = false,
            reviewText = "" // 리뷰 필드는 기본값
            //reviewText 의 데이터는 현정님의 독서일기글쓰기 화면에서 가져오도록 수정해!!!
            //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        )

        bookViewModel.addBook(newBook)
        Toast.makeText(requireContext(), "책 정보가 업로드되었습니다.", Toast.LENGTH_SHORT).show()

        // 필드 초기화
        clearFields()
    }

    private fun clearFields() {
        etBookName.text.clear()
        etWriter.text.clear()
        etPublisher.text.clear()
        etYear.text.clear()
    }

    private fun openImagePicker() {
        imagePickerLauncher.launch("image/*")
    }
}

