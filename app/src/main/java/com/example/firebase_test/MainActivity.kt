package com.example.firebase_test

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.firebase_test.databinding.ActivityMainBinding
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    private var auth: FirebaseAuth? = null
    private val viewModel: MyViewModel by viewModels()
    private lateinit var googleSignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //로그아웃
        binding.logout.setOnClickListener {
            //로그인 화면으로
            moveLoginPage()
            //auth?.signOut()
            // Google sign out
            FirebaseAuth.getInstance().signOut()

        }

        binding.floatingActionButton.setOnClickListener {
            //dialog 띄어서 아이템 추가
            ItemDialog().show(supportFragmentManager, "ItemDialog")
        }

        val adapter = MyAdapter(viewModel)

        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.setHasFixedSize(true)//아이템마다 각 높이를 일정하게

        viewModel.itemsLiveData.observe(this) { //변경한걸 인지하고 새로고침
            //adapter.notifyDataSetChanged()
            when (viewModel.itemNotifiedType) {
                ItemNotify.ADD -> adapter.notifyItemInserted(viewModel.itemNotified)
                ItemNotify.UPDATE -> adapter.notifyItemChanged(viewModel.itemNotified)
                ItemNotify.DELETE -> adapter.notifyItemRemoved(viewModel.itemNotified)
            }
        }

        registerForContextMenu(binding.recyclerView) //컨텍스트 메뉴 등록하는법
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        menuInflater.inflate(R.menu.ctx_menu, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.edit -> ItemDialog(viewModel.longClickItem).show(
                supportFragmentManager,
                "ItemDialog"
            )
            R.id.delete -> {
                viewModel.deleteItem(viewModel.longClickItem)
            }
            else -> return super.onContextItemSelected(item)
        }
        return true

    }

    //로그인 액티비티 호출
    private fun moveLoginPage() {
        startActivity(Intent(this, LoginActivity::class.java))
        finish()

    }


}