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
//        val myDBHelper = MyDBHelper(this)
//        val list = myDBHelper.readData()
//        Log.i("test",list.get(0).productname)

        val productList = generateDummyList(5)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.adapter = ProductAdapter(productList)

        recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        recyclerView.setHasFixedSize(true)
    }



    private fun generateDummyList(size: Int): List<Product>{
//        val list = ArrayList<Product>()
//        list.add(Product("fuji Apple",5f,"fuji","fresh imported apples from fuji"))
//        list.add(Product("fuji Orange",3.99f,"fuji","fresh imported orange from fuji"))
//        list.add(Product("fuji melon",50f,"fuji","fresh imported melon from fuji"))
//        list.add(Product("fuji strawberry",15f,"fuji","fresh imported strawberry from fuji"))
//        list.add(Product("Tokyo banana",7.99f,"Tokyo","fresh imported banana from Tokyo"))

        val myDBHelper = MyDBHelper(this)
        val list = myDBHelper.readData()
//        Log.i("test", list[3].description)

        for (i in 0 until size){
//            val item = Product("Item: ${list.get(i).productname}", list.get(i).price)
//            val item = Product("Item: $i", 9.99f,"Singapore","apples from Singapore")
            val item = Product("${list.get(i).productname}", list.get(i).price, list.get(i).country, list.get(i).description)
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