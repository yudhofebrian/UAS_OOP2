package com.example.crudkaryawan

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.crudkaryawan.Database.AppRoomDB
import com.example.crudkaryawan.Database.Constant
import com.example.crudkaryawan.Database.User
import com.example.crudkaryawan.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UserActivity : AppCompatActivity() {

    val db by lazy { AppRoomDB(this) }
    lateinit var userAdapter: UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)
        setupListener()
        setupRecyclerView()
    }

    override fun onStart() {
        super.onStart()
        loadUser()
    }

    fun loadUser() {
        CoroutineScope(Dispatchers.IO).launch {
            val allUser = db.userDao().getAllUser()
            Log.d("AdduserActivity", "dbResponse: $allUser")
            withContext(Dispatchers.Main) {
                userAdapter.setData(allUser)
            }
        }
    }

    fun setupListener() {
        btn_createUser.setOnClickListener {
            intentEdit(0, Constant.TYPE_CREATE)
        }
    }

    fun setupRecyclerView() {
        userAdapter = UserAdapter(arrayListOf(), object: UserAdapter.OnAdapterListener {
            override fun onClick(user: User) {
                intentEdit(user.id, Constant.TYPE_READ)
            }
            override fun onDelete(user: User) {
                deleteDialog(user)
            }

        })
        list_user.apply {
            layoutManager = LinearLayoutManager(applicationContext)
            adapter = userAdapter
        }
    }

    fun intentEdit(userId: Int, intentType: Int ) {
        startActivity(
            Intent(applicationContext, EditUserActivity::class.java)
                .putExtra("intent_id", userId)
                .putExtra("intent_type", intentType)
        )
    }

    private fun deleteDialog(user: User) {
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
                    db.userDao().deleteUser(user)
                    loadUser()
                }
            }
        }
        alertDialog.show()
    }
}