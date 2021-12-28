package fr.clesspavy

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import fr.clesspavy.thisismeapp.R

class Authentification : AppCompatActivity() {
    private lateinit var identifiant: EditText
    private lateinit var idPassword: EditText
    private lateinit var login: Button
    private lateinit var signup: Button
    private lateinit var auth:  FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authentification)

        supportActionBar?.hide()

        auth = FirebaseAuth.getInstance()

        identifiant = findViewById(R.id.email)
        idPassword = findViewById(R.id.mdp_auth)
        login = findViewById(R.id.auth1)
        signup = findViewById(R.id.auth2)


       signup.setOnClickListener {
           val intent = Intent(this@Authentification, EmailAuth::class.java)
           finish()
           startActivity(intent)
       }
        login.setOnClickListener {
            val email = identifiant.text.toString()
            val password = idPassword.text.toString()

            loginUser(email, password)
        }
    }

    private fun loginUser(email: String, password: String) {
        auth.createUserWithEmailAndPassword( email, password)
            .addOnCompleteListener(this) { task ->
                if(task.isSuccessful) {

                    val intent = Intent(this@Authentification, MainActivity::class.java)
                    finish()
                    startActivity(intent)
                }
                else
                {
                    Toast.makeText(this@Authentification, "Utilisateur inexistant, Veuillez créer un nouveau compte!", Toast.LENGTH_SHORT ).show()
                }
            }
    }
}