package sg.edu.smu.cs461.cutn_mobileapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun goToAllProducts(view: View) {
        val intent = Intent(this, AllProducts::class.java)
        startActivity(intent)
        Log.i("TABLE CALLED","TEST")
    }
}