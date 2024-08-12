package com.nadia.testsuitmedia

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class UserAdapter(private val users: MutableList<User>, private val itemClickListener: (User) -> Unit) : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    class UserViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val userName: TextView = view.findViewById(R.id.txtFull)
        val userEmail: TextView = view.findViewById(R.id.txtEmail)
        val userAvatar: ImageView = view.findViewById(R.id.imgUser)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = users[position]
        holder.userName.text = "${user.first_name} ${user.last_name}"
        holder.userEmail.text = user.email
        Glide.with(holder.userAvatar.context).load(user.avatar).into(holder.userAvatar)
        holder.itemView.setOnClickListener { itemClickListener(user) }
    }

    override fun getItemCount(): Int = users.size

    fun addUsers(newUsers: List<User>) {
        users.addAll(newUsers)
        notifyDataSetChanged()
    }

    fun clear() {
        users.clear()
        notifyDataSetChanged()
    }
}