package com.example.chatingapp

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.chatingapp.databinding.ReceiveBinding
import com.example.chatingapp.databinding.SendBinding
import com.example.chatingapp.databinding.UserLayoutBinding
import com.google.android.gms.dynamic.IFragmentWrapper
import com.google.firebase.auth.FirebaseAuth

class messageadapter(val context: Context,val message:ArrayList<Message>):RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    val ITEM_RECEIVE =1
    val ITEM_SENT =2

    class SentViewHolder(val sendBinding: SendBinding):RecyclerView.ViewHolder(sendBinding.root) {
   val send=sendBinding.sent
    }

    class ReceiveViewHolder(val receiveBinding: ReceiveBinding):RecyclerView.ViewHolder(receiveBinding.root) {
val receive=receiveBinding.receive
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == 1){
            ReceiveViewHolder(ReceiveBinding.inflate(LayoutInflater.from(parent.context),parent,false))
        }else{
            SentViewHolder(SendBinding.inflate(LayoutInflater.from(parent.context),parent,false))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currentMessage = message[position]
            if (holder.javaClass==SentViewHolder::class.java){
                val viewHolder=holder as SentViewHolder
                holder.send.text=currentMessage.message
            }else{
                val viewHolder=holder as ReceiveViewHolder
                holder.receive.text=currentMessage.message
            }
    }

    override fun getItemViewType(position: Int): Int {
        val currentMessage = message[position]
        return if (FirebaseAuth.getInstance().currentUser?.uid.equals(currentMessage.senderId)){
            ITEM_SENT
        }else{
            ITEM_RECEIVE
        }
    }

    override fun getItemCount(): Int {
        return message.size
    }


}