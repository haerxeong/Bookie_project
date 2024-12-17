package com.example.bookie

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.bookie.databinding.FragmentBookDiaryWriteBinding
import com.example.bookie.viewmodel.BookDiaryViewModel
import com.example.bookie.viewmodel.BookViewModel

class BookDiaryWriteFragment : Fragment() {

    private var _binding: FragmentBookDiaryWriteBinding? = null
    private val diaryViewModel: BookDiaryViewModel by activityViewModels()
    private val bookViewModel: BookViewModel by activityViewModels()

    private var selectedBookId: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBookDiaryWriteBinding.inflate(inflater, container, false)

        // Bundle에서 전달된 bookId를 받음
        selectedBookId = arguments?.getString("bookId")

        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding?.let { binding ->
            val readingBookNameInput = binding.readingBookNameInput
            val readingDateInput = binding.readingDateInput
            val diaryContentInput = binding.diaryContentInput
            val publishButton = binding.publishButton

            publishButton.isEnabled = false

            readingDateInput.addTextChangedListener { updatePublishButtonState() }
            readingBookNameInput.addTextChangedListener { updatePublishButtonState() }
            diaryContentInput.addTextChangedListener { updatePublishButtonState() }

            publishButton.setOnClickListener {
                selectedBookId?.let { bookId ->
                    uploadDiaryDetails(
                        bookId,
                        readingDateInput.text.toString(),
                        readingBookNameInput.text.toString(),
                        diaryContentInput.text.toString()
                    )
                } ?: run {
                    Toast.makeText(requireContext(), "책을 선택해주세요.", Toast.LENGTH_SHORT).show()
                }
            }

            selectedBookId?.let { bookId ->
                bookViewModel.booklist.value?.find { it.id == bookId }?.let { book ->
                    if (book.bookImageUrl.isNotEmpty()) {
                        Glide.with(this)
                            .load(book.bookImageUrl)
                            .into(binding.bookImage)
                    } else {
                        binding.bookImage.setImageResource(R.drawable.book)
                    }
                    binding.bookTitle.text = book.title
                    binding.readingBookNameInput.setText(book.title)
                    binding.bookInfo.text = "${book.author} | ${book.publisher} | ${book.release}"
                }
            }
        }
    }

    private fun uploadDiaryDetails(
        bookId: String,
        readingDateText: String,
        readingBookNameText: String,
        diaryContent: String
    ) {
        _binding?.let { binding ->
            if (readingDateText.isEmpty() || readingBookNameText.isEmpty() || diaryContent.isEmpty()) {
                Toast.makeText(requireContext(), "날짜와 책 이름, 내용을 모두 입력해주세요", Toast.LENGTH_SHORT).show()
                return
            }

            val newDiary = BookDiary(
                readDate = readingDateText,
                bookName = readingBookNameText,
                reviewText = diaryContent
            )

            diaryViewModel.addDiary(bookId, newDiary)
            clearFields()
            Toast.makeText(requireContext(), "발행이 완료되었습니다", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_bookDiaryWriteFragment_to_readBookFragment)
        }
    }

    private fun updatePublishButtonState() {
        _binding?.let { binding ->
            val isDateValid = binding.readingDateInput.text.isNotEmpty()
            val isBookNameValid = binding.readingBookNameInput.text.isNotEmpty()
            val isContentValid = binding.diaryContentInput.text.isNotEmpty()
            binding.publishButton.isEnabled = isDateValid && isContentValid && isBookNameValid
        }
    }

    private fun clearFields() {
        _binding?.let { binding ->
            binding.readingDateInput.text.clear()
            binding.readingBookNameInput.text.clear()
            binding.diaryContentInput.text.clear()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
