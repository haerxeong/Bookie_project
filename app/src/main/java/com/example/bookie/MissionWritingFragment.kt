import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.activity.result.contract.ActivityResultContracts

class MissionWritingFragment : Fragment() {

    private lateinit var commentEditText: EditText
    private lateinit var galleryIcon: ImageView
    private var comment: String? = null
    private var selectedImageUri: Uri? = null

    // 사진 선택 결과 처리
    private val selectImageLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK && result.data != null) {
            selectedImageUri = result.data?.data
            Toast.makeText(requireContext(), "사진이 선택되었습니다.", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(requireContext(), "사진 선택을 취소했습니다.", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_mission_writing, container, false)

        // EditText와 ImageView 초기화
        commentEditText = view.findViewById(R.id.mission1EditText)
        galleryIcon = view.findViewById(R.id.add_photo_icon)

        // 갤러리 아이콘 클릭 리스너 설정
        galleryIcon.setOnClickListener {
            openGallery()
        }

        // 발행 버튼 클릭 리스너 설정
        val publishButton: Button = view.findViewById(R.id.publishButton)
        publishButton.setOnClickListener {
            comment = commentEditText.text.toString()
            if (comment.isNullOrBlank()) {
                Toast.makeText(requireContext(), "구절을 입력하세요.", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "입력한 구절이 저장되었습니다: $comment", Toast.LENGTH_SHORT).show()
            }
        }

        return view
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        selectImageLauncher.launch(intent)
    }
}
