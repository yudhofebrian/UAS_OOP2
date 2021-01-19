package com.example.crudmakanan

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.crudmakanan.Database.AppRoomDB
import com.example.crudmakanan.Database.Constant
import com.example.crudmakanan.Database.makanan
import kotlinx.android.synthetic.main.activity_makanan.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class makananActivity : AppCompatActivity() {

    val db by lazy { AppRoomDB(this) }
    lateinit var makananAdapter: makananAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_makanan)
        setupListener()
        setupRecyclerView()
    }

    override fun onStart() {
        super.onStart()
        loadmakanan()
    }

    fun loadmakanan(){
        CoroutineScope(Dispatchers.IO).launch {
            val allmakanan = db.makananDao().getAllmakanan()
            Log.d("HelmActivity", "dbResponse: $allmakanan")
            withContext(Dispatchers.Main) {
                makananAdapter.setData(allmakanan)
            }
        }
    }

    fun setupListener() {
        btn_createmakanan.setOnClickListener {
           intentEdit(0, Constant.TYPE_CREATE)
        }
    }

    fun setupRecyclerView() {
        makananAdapter = makananAdapter(arrayListOf(), object: makananAdapter.OnAdapterListener {
            override fun onClick(makanan: makanan) {
                intentEdit(makanan.id, Constant.TYPE_READ)
            }

            override fun onDelete(makanan: makanan) {
                deleteDialog(makanan)
            }

        })
        list_makanan.apply {
            layoutManager = LinearLayoutManager(applicationContext)
            adapter = makananAdapter
        }
    }

    fun intentEdit(makananId: Int, intentType: Int ) {
        startActivity(
            Intent(applicationContext, EditmakananActivity::class.java)
                .putExtra("intent_id", makananId)
                .putExtra("intent_type", intentType)
        )
    }

    private fun deleteDialog(makanan: makanan) {
        val alertDialog = AlertDialog.Builder(this)
        alertDialog.apply {
            setTitle("Konfirmasi")
            setMessage("Yakin ingin menghapus data ini?")
            setNegativeButton("Batal") { dialogInterface, i ->
                dialogInterface.dismiss()
            }
            setPositiveButton("Hapus") { dialogInterface, i ->
                dialogInterface.dismiss()
                CoroutineScope(Dispatchers.IO).launch {
                    db.makananDao().deletemakanan(makanan)
                    loadmakanan()
                }
            }
        }
        alertDialog.show()
    }
}