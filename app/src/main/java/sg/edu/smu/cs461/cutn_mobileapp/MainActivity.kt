package sg.edu.smu.cs461.cutn_mobileapp

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.util.Log
import android.view.View
import android.widget.*
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity(), PopularItemAdapter.OnItemClickListener {
    private var REQ_CODE = 3213
    private lateinit var gotoRewards: ImageView
    private var SPEECH_CODE = 1999

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val micBtn = findViewById<ImageButton>(R.id.microphone)
        micBtn?.setOnClickListener{
            voiceInput()
        }
        individualPage()

        val productList = generateDummyListForPopularItem(5)
        val recyclerViewPopularItem = findViewById<RecyclerView>(R.id.recyclerViewPopularItem)
        recyclerViewPopularItem.adapter = PopularItemAdapter(productList, this)
        recyclerViewPopularItem.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        recyclerViewPopularItem.setHasFixedSize(true)

        val categoryList = generateDummyListForCategory(4)
        val recyclerViewCategory = findViewById<RecyclerView>(R.id.recyclerViewCategory)
        recyclerViewCategory.adapter = CategoryAdapter(categoryList)
        recyclerViewCategory.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        recyclerViewCategory.setHasFixedSize(true)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.i("test", requestCode.toString())
        if (requestCode == SPEECH_CODE) {
            Log.i("test", "yup")
            val result = data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
            val searchBtn = findViewById<EditText>(R.id.searchForProduct)
            Log.i("test", "result is " + result?.get(0).toString())
            searchBtn.setText(result?.get(0).toString())
        }
    }

    override fun onItemClick(position: Int) {
        val productList = generateDummyListForPopularItem(5)
        val adapter = PopularItemAdapter(productList, this)
        val clickedItem: PopularItem = productList[position]

        val it = Intent(this, IndividualProduct::class.java)
        it.putExtra("Product Name", clickedItem.productname)
        it.putExtra("Description", clickedItem.description)
        it.putExtra("Price", clickedItem.price)
        it.putExtra("Quantity", clickedItem.quantity)
        it.putExtra("Country", clickedItem.country)
        startActivityForResult(it, 4321)

        Toast.makeText(this, "${clickedItem.productname} clicked", Toast.LENGTH_SHORT).show()
        clickedItem.productname = "Clicked"
        adapter.notifyItemChanged(position)
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

            val item = PopularItem(drawable,list.get(j).productname,list.get(j).description,list.get(j).price,list.get(j).quantity,list.get(j).country)
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
                0 -> R.drawable.fr
                1 -> R.drawable.vg2
                2 -> R.drawable.milk
                else -> R.drawable.snacks
            }

            if (j > 3){
                j = 0
            }

            val item = Category(drawable,categoryList.get(j))
            list += item
        }
        return list
    }


    private fun individualPage() {
        val rewardsBtn = findViewById<ImageButton>(R.id.photo)
        rewardsBtn?.setOnClickListener{
            val it = Intent(this, IndividualProduct::class.java)
            startActivityForResult(it, 4321)
        }
    }

    private fun voiceInput() {
        if(!SpeechRecognizer.isRecognitionAvailable(this)){
            Toast.makeText(this, "Speech Recognition is not available on this device!", Toast.LENGTH_SHORT).show()
        }else{
            val i = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
            i.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
            i.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
            i.putExtra(RecognizerIntent.EXTRA_PROMPT,"Name a grocery!")
            Log.i("test","reached")
            startActivityForResult(i,SPEECH_CODE)
        }
    }


}