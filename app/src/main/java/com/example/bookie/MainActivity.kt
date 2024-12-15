package com.example.bookie

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.bookie.databinding.ActivityMainBinding
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navController = binding.fragMain.getFragment<NavHostFragment>().navController
        val appBarConfiguration = AppBarConfiguration(
            setOf(R.id.homeFragment, R.id.bookFeedPageFragment, R.id.unreadBookFragment, R.id.accountFragment)
        )

        binding.bottomNav.setupWithNavController(navController)


        val database = Firebase.database
        val myRef = database.getReferenceFromUrl("https://bookie-9ae3d-default-rtdb.firebaseio.com/") // 예: "users"

        // 데이터 읽기
        myRef.child("id").get().addOnSuccessListener { snapshot ->
            val value = snapshot.getValue(String::class.java)
            Log.d("FirebaseData", "id: $value") // 콘솔에 출력
        }.addOnFailureListener {
            Log.e("FirebaseData", "Failed to read value.", it)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = binding.fragMain.getFragment<NavHostFragment>().navController
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}