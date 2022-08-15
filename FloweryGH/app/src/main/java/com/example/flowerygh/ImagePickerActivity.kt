package com.example.flowerygh

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class imagePickerActivity : AppCompatActivity() {
    var list = ArrayList<String>();
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_picker)

        getGalleryPhotos(applicationContext)
        var adapter = ImagePickerAdapter(applicationContext,list)
        var rcv = findViewById<RecyclerView>(R.id.rcv)
        rcv.layoutManager = GridLayoutManager(applicationContext,3)
        rcv.adapter = adapter

        var button = findViewById<Button>(R.id.submit)
        button.setOnClickListener {
            var intent = Intent(this,UserInformationActivity::class.java)
            var bundle = Bundle()
//            bundle.putString("image",adapter.list.get(adapter.checkBox))
//            startActivity(intent,bundle)
            intent.putExtra("image", adapter.list.get(adapter.checkBox));
            setResult(Activity.RESULT_OK, intent);
            finish()
        }

    }
    fun getGalleryPhotos( context : Context) : ArrayList<String>{
        var pictures = ArrayList<String>()

        var uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI

        var colums =
            arrayOf(MediaStore.Images.Media.DATA, MediaStore.Images.Media._ID)
        val orderBy = MediaStore.Images.Media._ID

        var cursor = context.contentResolver.query(uri,colums,null,null,orderBy)

        if(cursor !=null && cursor.count>0){
            while(cursor.moveToNext()){


                var indexPath = cursor.getColumnIndex(MediaStore.MediaColumns.DATA)
                list.add(cursor.getString(indexPath))

            }
        }else{
            Log.e("getGalleryPhotos","error")

        }
        pictures.reverse()
        return pictures
    }
}