package com.example.chatingapp

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.chatingapp.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class MainActivity : AppCompatActivity() {

    private lateinit var mainBinding: ActivityMainBinding
    private lateinit var userList:ArrayList<User>
    private lateinit var adapter:UserAdapter
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mDbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)

        mAuth = FirebaseAuth.getInstance()
        mDbRef= FirebaseDatabase.getInstance().reference

        userList = ArrayList()
        adapter = UserAdapter(this, userList)
        mainBinding.apply {
            userRecyclerView.adapter = adapter
            userRecyclerView.layoutManager = LinearLayoutManager(this@MainActivity)
        }

        mDbRef.child("user").addValueEventListener(object : ValueEventListener {
            @SuppressLint("NotifyDataSetChanged")
            override fun onDataChange(snapshot: DataSnapshot) {
              userList.clear()
                for (postSnapShot in snapshot.children){
                    val currentUser = postSnapShot.getValue(User::class.java)
                    if (mAuth.currentUser?.uid!=currentUser?.uid){
                        userList.add(currentUser!!)
                    }
                }
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId==R.id.logout){
            mAuth.signOut()
            startActivity(Intent(this,Login::class.java))
            finish()
            return true
        }
        return true
    }
}