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
        recyclerViewPopularItem.adapter = PopularItemAdapter(productList)
        recyclerViewPopularItem.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        recyclerViewPopularItem.setHasFixedSize(true)

        val categoryList = generateDummyListForCategory(4)
        val recyclerViewCategory = findViewById<RecyclerView>(R.id.recyclerViewCategory)
        recyclerViewCategory.adapter = CategoryAdapter(categoryList)
        recyclerViewCategory.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        recyclerViewCategory.setHasFixedSize(true)
    }

    private fun generateDummyListForPopularItem(size: Int): List<PopularItem>{
        val myDBHelper = MyDBHelper(this)
        val list = myDBHelper.readData()

        for (i in 0 until size){
            val item = Product("${list.get(i).productname}", list.get(i).price, list.get(i).quantity, list.get(i).description)
            list += item
        }

        val list2 = ArrayList<PopularItem>()
        for (i in 0 until size){
            var j = i
            val drawable = when (i%4){
                0 -> R.drawable.r1
                1 -> R.drawable.r2
                2 -> R.drawable.r3
                else -> R.drawable.r4
            }

            val item = PopularItem(drawable,list.get(j).productname,list.get(j).description,list.get(j).price,list.get(j).quantity)
            list2 += item
        }
        return list2
    }

    private fun generateDummyListForCategory(size: Int): List<Category>{
        val list = ArrayList<Category>()
        val categoryList = listOf("Fruits","Vegetables","Packages","Snack")

        for (i in 0 until size){
            var j = i
            val drawable = when (i%4){
                0 -> R.drawable.fruits2
                1 -> R.drawable.vegetable
                2 -> R.drawable.milk
                else -> R.drawable.snack
            }

            if (j > 3){
                j = 0
            }

            val item = Category(drawable,categoryList.get(j))
            list += item
        }
        return list
    }

    fun goToAllProducts(view: View) {
        val intent = Intent(this, AllProducts::class.java)
        startActivity(intent)
        Log.i("TABLE CALLED","TEST")
    }
    

    private fun individualPage() {
        val rewardsBtn = findViewById<ImageButton>(R.id.microphone)
        rewardsBtn?.setOnClickListener{
            val it = Intent(this, IndividualProduct::class.java)
            startActivityForResult(it, 4321)
        }
    }
}