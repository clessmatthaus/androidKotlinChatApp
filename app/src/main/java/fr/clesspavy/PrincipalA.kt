package fr.clesspavy

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import fr.clesspavy.thisismeapp.R
import java.util.*

class PrincipalA : AppCompatActivity() {

    private lateinit var  userRecyclerView: RecyclerView
    private lateinit var usersList: ArrayList<User>
    private lateinit var adapter: UserAdapter
    private lateinit var auth: FirebaseAuth
    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_principal)

        auth = FirebaseAuth.getInstance()
        dbRef = FirebaseDatabase.getInstance().getReference()

        usersList = ArrayList()
        adapter = UserAdapter(this,usersList)

        userRecyclerView = findViewById(R.id.userRecyclerView)

        userRecyclerView.layoutManager = LinearLayoutManager(this)
        userRecyclerView.adapter = adapter

        dbRef.child("user").addValueEventListener(object: ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {

                usersList.clear()

               for(postSnapshot in snapshot.children){
                   val currentUser = postSnapshot.getValue(User::class.java)

                   if(auth.currentUser?.uid != currentUser?.uid){
                      usersList.add(currentUser!!)
                   }

               }
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.nav_deconnexion){

            val intent = Intent(this@PrincipalA, Authentification::class.java)
            finish()
            startActivity(intent)

            auth.signOut()
            finish()
            return true
        }
        return true
    }
}