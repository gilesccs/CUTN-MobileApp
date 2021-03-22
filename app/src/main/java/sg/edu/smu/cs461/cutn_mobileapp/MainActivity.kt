package sg.edu.smu.cs461.cutn_mobileapp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private var REQ_CODE = 3213
    private lateinit var gotoRewards: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        individualPage()

        val productList = generateDummyListForPopularItem(5)
        val recyclerViewPopularItem = findViewById<RecyclerView>(R.id.recyclerViewPopularItem)
        recyclerViewPopularItem.adapter = ProductAdapter(productList)
        recyclerViewPopularItem.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        recyclerViewPopularItem.setHasFixedSize(true)

        val categoryList = generateDummyListForCategory(4)
        val recyclerViewCategory = findViewById<RecyclerView>(R.id.recyclerViewCategory)
        recyclerViewCategory.adapter = CategoryAdapter(categoryList)
        recyclerViewCategory.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        recyclerViewCategory.setHasFixedSize(true)
    }

    private fun generateDummyListForPopularItem(size: Int): List<Product>{
        val myDBHelper = MyDBHelper(this)
        val list = myDBHelper.readData()

        for (i in 0 until size){
            val item = Product("${list.get(i).productname}", list.get(i).price, list.get(i).quantity, list.get(i).description)
            list += item
        }
        return list
    }

    private fun generateDummyListForCategory(size: Int): List<Product>{
        val list = ArrayList<Product>()
        val categoryList = listOf("Fruits","Vegetables","Packages")

        for (i in 0 until size){
            var j = i
            val drawable = when (i%3){
                0 -> R.drawable.fruits2
                1 -> R.drawable.vegetable
                else -> R.drawable.milk
            }

            if (j > 2){
                j = 0
            }

            val item = Product(categoryList.get(j))
            list += item
        }
        return list
    }


    private fun individualPage() {
        val rewardsBtn = findViewById<ImageButton>(R.id.microphone)
        rewardsBtn?.setOnClickListener{
            val it = Intent(this, IndividualProduct::class.java)
            startActivityForResult(it, 4321)
        }
    }
}