package com.example.crudkaryawan


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.crudkaryawan.Database.AppRoomDB
import com.example.crudkaryawan.Database.Constant
import com.example.crudkaryawan.Database.karyawan
import com.example.crudkaryawan.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EditkaryawanActivity : AppCompatActivity() {

    val db by lazy { AppRoomDB(this) }
    private var karyawanId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_karyawan)
        setupListener()
        setupView()
    }

    fun setupListener(){
        btn_savekaryawan.setOnClickListener{
            CoroutineScope(Dispatchers.IO).launch {
                db.karyawanDao().addkaryawan(
                        karyawan(0, txt_menu.text.toString(),txt_name.text.toString(), Integer.parseInt(txt_nip.text.toString()) )
                )
                finish()
            }
        }
    }

    fun setupView() {
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        val intentType = intent.getIntExtra("intent_type", 0)
        when (intentType) {
            Constant.TYPE_CREATE -> {

            }
            Constant.TYPE_READ -> {
                btn_savekaryawan.visibility = View.GONE
                getkaryawan()
            }
        }
    }

    fun getkaryawan() {
        karyawanId = intent.getIntExtra("intent_id", 0)
        CoroutineScope(Dispatchers.IO).launch {
            val karyawans =  db.karyawanDao().getmakanan( karyawanId )[0]
            txt_menu.setText( karyawans.menu )
            txt_nip.setText( karyawans.nik.toString() )
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}
