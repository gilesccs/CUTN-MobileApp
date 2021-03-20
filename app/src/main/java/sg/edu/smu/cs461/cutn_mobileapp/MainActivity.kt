package sg.edu.smu.cs461.cutn_mobileapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val productList = generateDummyList(100)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.adapter = ProductAdapter(productList)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)
    }

    private fun generateDummyList(size: Int): List<Product>{
        val list = ArrayList<Product>()

        for (i in 0 until size){
//            val item = Product("Item: ${list.get(i).productname}", list.get(i).price)
            val item = Product("Item: $i", 9.99f)
            list += item
        }

        return list
    }
}