package com.example.firebase_test

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.firebase_test.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {
    private var auth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
        val binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.login.setOnClickListener {
            signIn(
                binding.idEditText.text.toString(),
                binding.passwordEditText.text.toString()
            ) //계정 로그인
        }

        binding.signup.setOnClickListener {
            createAccount(
                binding.idEditText.text.toString(),
                binding.passwordEditText.text.toString()
            ) //계정 새로 등록
        }
    }

    //로그아웃하지 않을 시 자동 로그인, 회원가입시 바로 로그인
    public override fun onStart() {
        super.onStart()
        moveMainPage(auth?.currentUser)
    }

    //계정 생성
    private fun createAccount(email: String, password: String) {
        if (email.isNotEmpty() && password.isNotEmpty()) {
            auth?.createUserWithEmailAndPassword(email, password)
                ?.addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(
                            this, "계정 생성 완료.",
                            Toast.LENGTH_SHORT
                        ).show()
                        moveMainPage(auth?.currentUser) //main 페이지로 이동
                    } else {
                        Toast.makeText(
                            this, "계정 생성 실패.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        }
    }

    private fun signIn(email: String, password: String) {
        if (email.isNotEmpty() && password.isNotEmpty()) {
            auth?.signInWithEmailAndPassword(email, password)
                ?.addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(
                            baseContext, "로그인 성공",
                            Toast.LENGTH_SHORT
                        ).show()
                        moveMainPage(auth?.currentUser) //main 페이지로 이동
                    } else {
                        Toast.makeText(
                            baseContext, "로그인 실패",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        }
    }

    //유저정보 넘겨주고 메인 액티비티 호출
    fun moveMainPage(user: FirebaseUser?) {
        if (user != null) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }


}