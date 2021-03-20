package sg.edu.smu.cs461.cutn_mobileapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private var REQ_CODE = 3213
    private lateinit var gotoRewards: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val productList = generateDummyList(100)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.adapter = ProductAdapter(productList)

        recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        recyclerView.setHasFixedSize(true)
    }



    private fun generateDummyList(size: Int): List<Product>{
        val list = ArrayList<Product>()

        for (i in 0 until size){
//            val item = Product("Item: ${list.get(i).productname}", list.get(i).price)
            val item = Product("Item: $i", 9.99f,"Singapore","apples from Singapore")
            list += item
        }

        return list
    }

    fun goToAllProducts(view: View) {
        val intent = Intent(this, AllProducts::class.java)
        startActivity(intent)
        Log.i("TABLE CALLED","TEST")
    }
}