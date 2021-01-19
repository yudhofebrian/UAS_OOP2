package com.example.crudkaryawan

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.crudkaryawan.Database.User
import com.example.crudkaryawan.R
import kotlinx.android.synthetic.main.adapter_user.view.*
import kotlinx.android.synthetic.main.adapter_user.view.icon_delete

class UserAdapter (private val AllUser: ArrayList<User>, private val listener: OnAdapterListener) : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        return UserViewHolder(
            LayoutInflater.from(parent.context).inflate( R.layout.adapter_user, parent, false)
        )
    }

    override fun getItemCount() = AllUser.size

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = AllUser[position]
        holder.view.text_username.text = user.username
        holder.view.text_username.setOnClickListener {
            listener.onClick(user)
        }
        holder.view.icon_delete.setOnClickListener {
            listener.onDelete(user)
        }
    }

    class UserViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    fun setData(list: List<User>) {
        AllUser.clear()
        AllUser.addAll(list)
        notifyDataSetChanged()
    }

    interface OnAdapterListener {
        fun onClick(user: User)
        fun onDelete(user: User)
    }

}