package com.example.flowerygh

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.helper.widget.MotionEffect.TAG
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.example.flowerygh.databinding.ActivityUserInformationBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.ktx.Firebase
import java.util.*

class UserInformationActivity : AppCompatActivity() {
    lateinit var image : ImageView
    private lateinit var auth: FirebaseAuth

    var cal = Calendar.getInstance()


    override fun onCreate(savedInstanceState: Bundle?) {
        val binding = ActivityUserInformationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        super.onCreate(savedInstanceState)

        val user = Firebase.auth.currentUser
        auth = Firebase.auth

        //31개의 씨앗 중 꽃을
        cal.set(Calendar.DAY_OF_MONTH,1)
        var lastDate = cal.getActualMaximum(Calendar.DAY_OF_MONTH)
        binding.textView.text = "${lastDate}개의 씨앗 중 꽃을"


        //프로필 사진 클릭했을 때
        binding.imageView.setOnClickListener{
            when{
                ContextCompat.checkSelfPermission(
                    this,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE
                )== PackageManager.PERMISSION_GRANTED ->{
                    // 권한이 잘 부여 되었을 때 갤러리에서 사진을 선택하는 기능
                    navigatesPhotos()
                }
                shouldShowRequestPermissionRationale(android.Manifest.permission.READ_EXTERNAL_STORAGE)->{
                    // 교육용 팝 확인 후 권한 팝업 띄우는 기능

                    showContextPopupPermission()
                }
                else ->{
                    requestPermissions(arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),1000)
                }
            }
        }
        //로그인한 이메일 가져오기


        val activityResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            it.resultCode
            it.data

            if(it.resultCode == RESULT_OK){
                val path = it.data?.getStringExtra("image")
                println("##DDD${path}")
                if(it.data!=null){
                    Glide.with(applicationContext).load(path).into(image)
                }
            }
        }


        //사용자 이메일
        binding.userEmailText.text = auth.currentUser?.email

        //사용자 이름
        binding.userName.setText(user?.displayName)
        //사용자 상태메세지


        //업로드 버튼 클릭했을 때
        binding.uploadButton.setOnClickListener{
            val profileUpdates = userProfileChangeRequest {
                displayName = binding.userName.text.toString()
                photoUri = Uri.parse(binding.imageView.toString())
            }
            user!!.updateProfile(profileUpdates)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d(TAG, "User profile updated.")
                    }
                }
            moveMainPage()
        }


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 2000) {
            var path = data?.getStringExtra("image")
            println("##DDD${path}")
            if(data!=null){
                Glide.with(applicationContext).load(path).into(image)
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode){
            1000->{
                if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    // 권한이 부여되었으므로

                    navigatesPhotos()
                }else{
                    Toast.makeText(applicationContext,"권한이 거부되었습니다!", Toast.LENGTH_SHORT).show()
                }
            }
            else->{

            }
        }
    }
    private fun navigatesPhotos(){
        startActivity(Intent(this,imagePickerActivity::class.java))

    }



    private fun showContextPopupPermission(){
        AlertDialog.Builder(this).setTitle("권한이 필요합니다")
            .setMessage("사진을 불러오기 위해 권한이 필요합니다")
            .setPositiveButton("동의하기") { _, _ ->
                requestPermissions(arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),1000)
            }
            .setNegativeButton("취소하기") { _, _ ->}
            .create()
            .show()

    }


    private fun moveMainPage(){
        startActivity(Intent(this,MainActivity::class.java))
        finish()
    }


}