package fr.clesspavy

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.airbnb.lottie.LottieAnimationView
import fr.clesspavy.thisismeapp.R

private lateinit var continues: LottieAnimationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()

        continues = findViewById(R.id.continu)


        continues.setOnClickListener {
            val intent = Intent(this@MainActivity, PrincipalA::class.java)
            startActivity(intent)
        }
    }
}
