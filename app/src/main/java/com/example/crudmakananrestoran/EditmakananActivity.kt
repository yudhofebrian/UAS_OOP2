package com.example.crudmakanan


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.crudmakanan.Database.AppRoomDB
import com.example.crudmakanan.Database.Constant
import com.example.crudmakanan.Database.makanan
import com.example.crudmakanan.R
import kotlinx.android.synthetic.main.activity_edit_makanan.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EditmakananActivity : AppCompatActivity() {

    val db by lazy { AppRoomDB(this) }
    private var makananId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_makanan)
        setupListener()
        setupView()
    }

    fun setupListener(){
        btn_savemakanan.setOnClickListener{
            CoroutineScope(Dispatchers.IO).launch {
                db.makananDao().addmakanan(
                        makanan(0, txt_menu.text.toString(), Integer.parseInt(txt_jumlah.text.toString()), Integer.parseInt(txt_harga.text.toString()) )
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
                btn_savemakanan.visibility = View.GONE
                getMakanan()
            }
        }
    }

    fun getMakanan() {
        makananId = intent.getIntExtra("intent_id", 0)
        CoroutineScope(Dispatchers.IO).launch {
            val makanans =  db.makananDao().getmakanan( makananId )[0]
            txt_menu.setText( makanans.menu )
            txt_harga.setText( makanans.harga.toString() )
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}
