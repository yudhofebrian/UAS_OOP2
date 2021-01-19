package com.example.crudkaryawan

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.crudkaryawan.Database.karyawan
import com.example.crudkaryawan.R
import kotlinx.android.synthetic.main.adapter_karyawan.view.*

class karyawanAdapter (private val allkaryawan: ArrayList<karyawan>, private val listener: OnAdapterListener) : RecyclerView.Adapter<karyawanAdapter.makananViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): makananViewHolder {
        return makananViewHolder(
            LayoutInflater.from(parent.context).inflate( R.layout.adapter_karyawan, parent, false)
        )
    }

    override fun getItemCount() = allkaryawan.size

    override fun onBindViewHolder(holder: makananViewHolder, position: Int) {
        val makanan = allkaryawan[position]
        holder.view.text_daftar.text = makanan.menu
        holder.view.text_daftar.setOnClickListener {
            listener.onClick(makanan)
        }
        holder.view.icon_delete.setOnClickListener {
            listener.onDelete(makanan)
        }
    }

    class makananViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    fun setData(list: List<karyawan>) {
        allkaryawan.clear()
        allkaryawan.addAll(list)
        notifyDataSetChanged()
    }

    interface OnAdapterListener {
        fun onClick(karyawan: karyawan)
        fun onDelete(karyawan: karyawan)
    }
}