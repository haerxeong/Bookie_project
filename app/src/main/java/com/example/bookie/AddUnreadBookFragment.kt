package com.example.bookie

import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.bookie.databinding.FragmentAddUnreadBookBinding
import com.example.bookie.viewmodel.BookViewModel
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import com.google.firebase.ktx.Firebase

class AddUnreadBookFragment : Fragment() {

    private lateinit var binding: FragmentAddUnreadBookBinding
    private val bookViewModel: BookViewModel by activityViewModels()
    private lateinit var storage: FirebaseStorage

    private lateinit var etBookName: EditText
    private lateinit var etWriter: EditText
    private lateinit var etPublisher: EditText
    private lateinit var etYear: EditText
    private lateinit var btnUpload: Button
    private lateinit var imageButtonAddUnread: ImageButton

    // 이미지 선택기 ActivityResultLauncher 선언
    private val imagePickerLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        if (uri != null) {
            Log.d("ImagePicker", "이미지 URI: $uri")
            imageButtonAddUnread.setImageURI(uri)
            bookViewModel.setImageUri(uri)
            uploadImageToFirebase(uri)
        } else {
            Log.d("ImagePicker", "이미지 선택 취소됨")
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

        // Firebase Storage 초기화
        storage = Firebase.storage

        // UI 요소 초기화
        etBookName = binding.etBookName
        etWriter = binding.etWriter
        etPublisher = binding.etPublisher
        etYear = binding.etYear
        btnUpload = binding.btnUplaod
        imageButtonAddUnread = binding.imagebtnAddUnread

        btnUpload.isEnabled = false

        // 입력 필드 상태를 확인하여 버튼 활성화
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
        listOf(etBookName, etWriter, etPublisher, etYear).forEach {
            it.addTextChangedListener(textWatcher)
        }

        btnUpload.setOnClickListener {
            uploadBookDetails()
            findNavController().navigate(R.id.action_addUnreadBookFragment_to_unreadBookFragment)
        }

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
            id = "1",
            title = bookName,
            author = writer,
            publisher = publisher,
            release = year.toInt(),
            isRead = false
        )

        bookViewModel.addBook(newBook)
        Toast.makeText(requireContext(), "책 정보가 업로드되었습니다.", Toast.LENGTH_SHORT).show()
        clearFields()
    }

    private fun clearFields() {
        etBookName.text.clear()
        etWriter.text.clear()
        etPublisher.text.clear()
        etYear.text.clear()
    }

    private fun openImagePicker() {
        Log.d("ImagePicker", "이미지 선택기 열기")
        imagePickerLauncher.launch("image/*")
    }

    private fun uploadImageToFirebase(uri: Uri) {
        val storageRef = storage.reference
        val imagesRef = storageRef.child("images/${uri.lastPathSegment}")
        val uploadTask = imagesRef.putFile(uri)

        uploadTask.addOnSuccessListener {
            Log.d("FirebaseStorage", "이미지 업로드 성공")
            Toast.makeText(requireContext(), "이미지 업로드 성공", Toast.LENGTH_SHORT).show()
        }.addOnFailureListener {
            Log.e("FirebaseStorage", "이미지 업로드 실패", it)
            Toast.makeText(requireContext(), "이미지 업로드 실패", Toast.LENGTH_SHORT).show()
        }
    }
    // realtime에도 저장하고 uri 사용해서 콜백 함수로 이미지 바인딩
    // 완성도는 별로 중요X. 구현했는지, 리팩토링이 중요. 코드 깔끔하고 가독성 좋아야 함
    // 코드 이해했는지/깔끔한지. 리팩토링 중요!!!!!!!!
}