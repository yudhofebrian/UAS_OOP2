package com.example.crudmakanan

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.crudmakanan.Database.makanan
import kotlinx.android.synthetic.main.adapter_makanan.view.*

class makananAdapter (private val allmakanan: ArrayList<makanan>, private val listener: OnAdapterListener) : RecyclerView.Adapter<makananAdapter.makananViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): makananViewHolder {
        return makananViewHolder(
            LayoutInflater.from(parent.context).inflate( R.layout.adapter_makanan, parent, false)
        )
    }

    override fun getItemCount() = allmakanan.size

    override fun onBindViewHolder(holder: makananViewHolder, position: Int) {
        val makanan = allmakanan[position]
        holder.view.text_merk.text = makanan.menu
        holder.view.text_merk.setOnClickListener {
            listener.onClick(makanan)
        }
        holder.view.icon_delete.setOnClickListener {
            listener.onDelete(makanan)
        }
    }

    class makananViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    fun setData(list: List<makanan>) {
        allmakanan.clear()
        allmakanan.addAll(list)
        notifyDataSetChanged()
    }

    interface OnAdapterListener {
        fun onClick(makanan: makanan)
        fun onDelete(makanan: makanan)
    }
}