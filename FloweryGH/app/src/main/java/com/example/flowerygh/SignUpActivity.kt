package com.example.flowerygh

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.flowerygh.databinding.ActivitySignUpBinding
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SignUpActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        val binding = ActivitySignUpBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        //metion
        val textData: String = binding.flowery.text.toString()
        val builder = SpannableStringBuilder(textData)
        val colorSpan = ForegroundColorSpan(Color.parseColor("#84A47D"))
        builder.setSpan(colorSpan, 8, 15, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        binding.flowery.text = builder

        auth = Firebase.auth

        //google 로그인 옵션 구성, google API 클라이언트 생성
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.your_web_client_id))
            .requestEmail()
            .build()
        //내 앱에서 구글의 계정을 가져다 쓸거다
        googleSignInClient = GoogleSignIn.getClient(this,gso)


        //firebase auth 객체
        auth = FirebaseAuth.getInstance()
        if (auth.getCurrentUser() != null) { //로그인 되어있으면 main화면으로
            val intent = Intent(application, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        //다른 앱/액티비티가 실행된 후, 그 실행이 끝난 후 다시 이 액티비티로 돌아왔을 때
        val result = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            it.resultCode
            it.data

            if (it.resultCode == RESULT_OK) { //ok
                //결과 Intent(data 매개변수) 에서 구글로그인 결과 꺼내오기
                val result = Auth.GoogleSignInApi.getSignInResultFromIntent(it.data!!)!!

                //정상적으로 로그인되었다면
                if (result.isSuccess) {
                    //우리의 Firebase 서버에 사용자 이메일정보보내기
                    val account = result.signInAccount
                    firebaseAuthWithGoogle(account)
                }
            }

        }

        binding.googleButton.setOnClickListener(){ //구글로 로그인하기 클릭하면
            //로그인 통합 페이지로 넘김
            val signInIntent = googleSignInClient.signInIntent

            //startActivityForResult(signInIntent, RC_SIGN_IN)
            result.launch(signInIntent)
        }

        binding.moveLogin.setOnClickListener(){
            moveLoginPage()
        }

    }//onCreate끝

    //onStart, 유저가 앱에 이미 구글 로그인을 했는지 확인
    override fun onStart() {
        super.onStart()
        val account = GoogleSignIn.getLastSignedInAccount(this)
        if(account!==null){ // 이미 로그인 되어있을시 바로 메인 액티비티로 이동
            moveMainPage(auth.currentUser)
        }
    }//onStart끝


    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount?) {
        Toast.makeText(
            this, "firebaseAuthWithGoogle 실행",
            Toast.LENGTH_SHORT
        ).show()
        //Google SignInAccount 객체에서 ID 토큰을 가져와서 Firebase Auth로 교환하고 Firebase에 인증
        val credential = GoogleAuthProvider.getCredential(acct?.idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) { //로그인에 성공하면
                    moveMainPage(auth.currentUser)
                } else { //로그인 실패하면
                    Log.w("SignUpActivity", "firebaseAuthWithGoogle 실패", task.exception)
                }
            }
    }// firebaseAuthWithGoogle END

    private fun moveMainPage(user: FirebaseUser?) {
        if (user != null) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }

    private fun moveLoginPage(){
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }


}