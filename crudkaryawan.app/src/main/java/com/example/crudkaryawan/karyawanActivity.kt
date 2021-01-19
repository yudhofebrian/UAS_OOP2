package com.example.crudkaryawan

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.crudkaryawan.Database.AppRoomDB
import com.example.crudkaryawan.Database.Constant
import com.example.crudkaryawan.Database.karyawan
import com.example.crudkaryawan.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class karyawanActivity : AppCompatActivity() {

    val db by lazy { AppRoomDB(this) }
    lateinit var karyawanAdapter: karyawanAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_karyawan)
        setupListener()
        setupRecyclerView()
    }

    override fun onStart() {
        super.onStart()
        loadkaryawan()
    }

    fun loadkaryawan(){
        CoroutineScope(Dispatchers.IO).launch {
            val allkaryawan = db.karyawanDao().getAllkaryawan()
            Log.d("HelmActivity", "dbResponse: $allkaryawan")
            withContext(Dispatchers.Main) {
                karyawanAdapter.setData(allkaryawan)
            }
        }
    }

    fun setupListener() {
        btn_createkaryawan.setOnClickListener {
           intentEdit(0, Constant.TYPE_CREATE)
        }
    }

    fun setupRecyclerView() {
        karyawanAdapter = karyawanAdapter(arrayListOf(), object: karyawanAdapter.OnAdapterListener {
            override fun onClick(karyawan: karyawan) {
                intentEdit(karyawan.id, Constant.TYPE_READ)
            }

            override fun onDelete(karyawan: karyawan) {
                deleteDialog(karyawan)
            }

        })
        list_karyawan.apply {
            layoutManager = LinearLayoutManager(applicationContext)
            adapter = karyawanAdapter
        }
    }

    fun intentEdit(makananId: Int, intentType: Int ) {
        startActivity(
            Intent(applicationContext, EditkaryawanActivity::class.java)
                .putExtra("intent_id", makananId)
                .putExtra("intent_type", intentType)
        )
    }

    private fun deleteDialog(karyawan: karyawan) {
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
                    db.karyawanDao().deletekaryawan(karyawan)
                    loadkaryawan()
                }
            }
        }
        alertDialog.show()
    }
}