package com.example.chatingapp

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.chatingapp.databinding.UserLayoutBinding
import com.google.firebase.auth.FirebaseAuth

class UserAdapter(val context: Context,val userList:ArrayList<User>):RecyclerView.Adapter<UserAdapter.ViewHolder>() {
    class ViewHolder(val userLayoutBinding: UserLayoutBinding):RecyclerView.ViewHolder(userLayoutBinding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(UserLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
      val currentUser=userList[position]
        holder.userLayoutBinding.textname.text= currentUser.name
        holder.itemView.setOnClickListener {
            val intent=Intent(context,Chat::class.java)
            intent.putExtra("name",currentUser.name)
            intent.putExtra("uid",currentUser.uid)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
      return userList.size
    }
}