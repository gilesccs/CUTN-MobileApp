package sg.edu.smu.cs461.cutn_mobileapp

import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class Classifier : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_classifier)
        Log.i("img", "classifier just started!")

        var label = intent.getStringExtra("label").toString()
        val i = Log.i("img", "in classifier: " + label)
//        var imgBitmap = intent.getParcelableExtra("imgBitmap")
        var imgBitmap = intent.getParcelableExtra<Bitmap>("imgBitmap")
        var userImage = findViewById<ImageView>(R.id.userImage)
        userImage.setImageBitmap(imgBitmap)

        setLabel(label)

        val classifierList = generateDummyListForClassifier(4, "apple")
        val recyclerViewClassifier = findViewById<RecyclerView>(R.id.recyclerViewClassifier)
        recyclerViewClassifier.adapter = ClassifierAdapter(classifierList)
        recyclerViewClassifier.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        recyclerViewClassifier.setHasFixedSize(true)
    }

    private fun generateDummyListForClassifier(size: Int, itemIdentified: String): List<ClassifierModel>{
        val myDBHelper = MyDBHelper(this)
        val list = myDBHelper.readByMachineLearning(itemIdentified)
        val list2 = ArrayList<Product>()
        val list3 = ArrayList<ClassifierModel>()

        for (i in 0 until size){
            val item = Product(list.get(i).category,"${list.get(i).productname}",list.get(i).quantity, list.get(i).description,list.get(i).price, list.get(i).country)
            list2 += item
        }

        for (i in 0 until size){
            var j = i
            val drawable = when (i%4){
                0 -> R.drawable.fr
                1 -> R.drawable.vg
                2 -> R.drawable.milk
                else -> R.drawable.snack
            }

            if (j > 3){
                j = 0
            }

            val item = ClassifierModel(drawable,list2.get(j).productid,list2.get(j).productname,list2.get(j).description,list2.get(j).price,list2.get(j).quantity,list2.get(j).country)
            list3 += item
        }
        return list3
    }

    private fun setLabel(label: String) {
        val classifierLabel = findViewById<TextView>(R.id.classifierLabel)
        classifierLabel.setText("Our SmartGrocery Assistant has detected $label. Check out what we have in stock below!")
    }

//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        var label = data?.getStringExtra("label").toString()
//        Log.i("img", "in classifier: "+label)
//
////        when (requestCode) {
////            1234 -> {
////                Toast.makeText(this, "Your rating for Shaun: $rating", Toast.LENGTH_SHORT).show()
////            }
////            1236 -> {
////                Toast.makeText(this, "Your rating for Gladwin: $rating", Toast.LENGTH_SHORT).show()
////            }
////            1237 -> {
////                Toast.makeText(this, "Your rating for Alwyn: $rating", Toast.LENGTH_SHORT).show()
////            }
////            1238 -> {
////                Toast.makeText(this, "Your rating for Giles: $rating", Toast.LENGTH_SHORT).show()
////            }
////        }
//
//    }
}