package sg.edu.smu.cs461.cutn_mobileapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class IndividualProduct : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_individual_product)
        goBackHomePage()
    }

    private fun goBackHomePage(){
        val btn = findViewById<Button>(R.id.goBack)
        btn.setOnClickListener{
            val it = Intent(this, MainActivity::class.java)
            startActivity(it)
        }
    }
}