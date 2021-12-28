package fr.clesspavy

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import fr.clesspavy.thisismeapp.R

class EmailAuth : AppCompatActivity() {

    private lateinit var idName: EditText
    private lateinit var idPassword: EditText
    private lateinit var identifiant: EditText
    private lateinit var signup: Button

    private lateinit var auth: FirebaseAuth
    private lateinit var dbRef: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_email_auth)

        supportActionBar?.hide()

        auth = FirebaseAuth.getInstance()

        idName = findViewById(R.id.name_auth)
        identifiant = findViewById(R.id.email)
        idPassword = findViewById(R.id.mdp_auth)
        signup = findViewById(R.id.auth2)

      signup.setOnClickListener {
            val name = idName.text.toString()
            val email = identifiant.text.toString()
            val password =idPassword.text.toString()

            signUp(name, email, password)
        }

    }

    private fun signUp(name: String, email: String, password: String) {
        auth.createUserWithEmailAndPassword( email, password)
            .addOnCompleteListener { task ->
                if(task.isSuccessful) {

                    addUserInDatabase(name, email, auth.currentUser?.uid!!)

                    val intent = Intent(this@EmailAuth, MainActivity::class.java)
                    finish()
                    startActivity(intent)


                }else{
                    Toast.makeText(this@EmailAuth, "Erreur detectée", Toast.LENGTH_SHORT ).show()
                }
            }
    }

    private fun addUserInDatabase(name: String, email: String, uid: String) {
        dbRef = FirebaseDatabase.getInstance().getReference()
        dbRef.child("user").child(uid).setValue(User(name,email,uid))
    }
}