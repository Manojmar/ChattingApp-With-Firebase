package com.example.chatingapp

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chatingapp.databinding.ActivityChatBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class Chat : AppCompatActivity() {

    private lateinit var chatBinding: ActivityChatBinding
    private lateinit var messageadapter: messageadapter
    private lateinit var messageList: ArrayList<Message>
    private lateinit var mDbRef:DatabaseReference

    var receiverRoom:String?=null
    var senderRoom:String?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        chatBinding= ActivityChatBinding.inflate(layoutInflater)
        setContentView(chatBinding.root)


        val name =intent.getStringExtra("name")
        val receiverUid =intent.getStringExtra("uid")

        val senderUid =FirebaseAuth.getInstance().currentUser?.uid
        mDbRef = FirebaseDatabase.getInstance().reference

        senderRoom=receiverUid + senderUid
        receiverRoom=senderUid + receiverUid

        supportActionBar?.title=name

        messageList= ArrayList()
        messageadapter= messageadapter(this,messageList)

        chatBinding.chatRecyclerview.layoutManager=LinearLayoutManager(this,)
        chatBinding.chatRecyclerview.adapter=messageadapter

        mDbRef.child("chats").child(senderRoom!!).child("messages")
            .addValueEventListener(object :ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    messageList.clear()
                    for (postSnapShot in snapshot.children){
                        val message = postSnapShot.getValue(Message::class.java)
                        messageList.add(message!!)
                    }
                    messageadapter.notifyDataSetChanged()
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })

        chatBinding.apply {
            sendimage.setOnClickListener {
                val messsage =textmessage.text.toString()
                val messageobject=Message(messsage,senderUid)

                mDbRef.child("chats").child(senderRoom!!).child("messages").push()
                    .setValue(messageobject).addOnSuccessListener {
                        mDbRef.child("chats").child(receiverRoom!!).child("messages").push()
                            .setValue(messageobject)
                    }
                textmessage.setText("")
            }
        }
    }
}