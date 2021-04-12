package sg.edu.smu.cs461.cutn_mobileapp

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.tensorflow.lite.support.image.TensorImage
import sg.edu.smu.cs461.cutn_mobileapp.ml.GroceryModel
import java.io.File

class Classifier : AppCompatActivity(), ClassifierAdapter.OnItemClickListener {
    private var labelTest:String? = null
    private var numRecyclerCards = 5

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_classifier)
        Log.i("img", "classifier just started!")
        goBackHomePage()
        analyzeWithClassifier(this)
    }

    private fun analyzeWithClassifier(ctx: Context) {
        val imgFile = File(getDir("groceryPhoto", MODE_APPEND).toString() + "/groceryItem.jpg")
        var bitmap: Bitmap? = null
        if (imgFile.exists()) {
            bitmap = BitmapFactory.decodeFile(imgFile.absolutePath)
        }

        val groceryModel = GroceryModel.newInstance(ctx)

        // Creates inputs for reference.
        val image = TensorImage.fromBitmap(bitmap)

        // Runs model inference and gets result.
        val outputs = groceryModel.process(image)
        val probability = outputs
            .probabilityAsCategoryList
            .apply { sortByDescending { it.score } }
            .take(1)

        for (output in probability) {
            Log.i("img", "label: "+output.label + " score: "+ output.score)
        }

        // Releases model resources if no longer used.
        groceryModel.close()
        var userImage = findViewById<ImageView>(R.id.userImage)
        userImage.setImageBitmap(bitmap)
        setLabel(probability[0].label)
        Log.i("label", "what am i: "+probability[0].label.toString())
        this.labelTest = probability[0].label
        Log.i("testing",  this.labelTest!!)
        populateRecyclerCard(probability[0].label)
    }

    private fun populateRecyclerCard(label: String) {
        val classifierList = generateDummyListForClassifier(numRecyclerCards, label)
        val recyclerViewClassifier = findViewById<RecyclerView>(R.id.recyclerViewClassifier)
        recyclerViewClassifier.adapter = ClassifierAdapter(classifierList, this)
        recyclerViewClassifier.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        recyclerViewClassifier.setHasFixedSize(true)
    }

    private fun goBackHomePage(){
        val btn = findViewById<Button>(R.id.goBack)
        btn.setOnClickListener{
            val it = Intent(this, MainActivity::class.java)
            startActivity(it)
        }
    }

    override fun onItemClick(position: Int) {
        val classifierList = generateDummyListForClassifier(numRecyclerCards, this.labelTest!!)
        val adapter = ClassifierAdapter(classifierList, this)
        val clickedItem: ClassifierModel = classifierList[position]

        val it = Intent(this, IndividualProduct::class.java)
        it.putExtra("ProductId", clickedItem.productid)
        it.putExtra("ProductName", clickedItem.productname)
        it.putExtra("Description", clickedItem.description)
        it.putExtra("Price", clickedItem.price)
        it.putExtra("Quantity", clickedItem.quantity)
        it.putExtra("Country", clickedItem.country)
        startActivity(it)

        adapter.notifyItemChanged(position)
    }

    private fun generateDummyListForClassifier(size: Int, itemIdentified: String): List<ClassifierModel>{
        val myDBHelper = MyDBHelper(this)
        val list = myDBHelper.readByMachineLearning(itemIdentified)
        val list2 = ArrayList<Product>()
        val list3 = ArrayList<ClassifierModel>()

        var idx = 0
        while (list.size < numRecyclerCards) {
            list.add(list[idx])
            idx++
        }
        Log.i("xo", list.toString())

        for (i in 0 until size){
            val item = Product(list.get(i).category,"${list.get(i).productname}",list.get(i).quantity, list.get(i).description,list.get(i).price, list.get(i).country, list.get(i).productid)
            list2 += item
        }

        for (i in 0 until size){
            var j = i
            val drawable = this.resources.getIdentifier("r${list2[i].productid}", "drawable",this.packageName)
//            val drawable = when (i%4){
//                0 -> R.drawable.fr
//                1 -> R.drawable.vg
//                2 -> R.drawable.milk
//                else -> R.drawable.snack
//            }

//            if (j > 3){
//                j = 0
//            }

            val item = ClassifierModel(drawable,list2.get(j).productid,list2.get(j).productname,list2.get(j).description,list2.get(j).price,list2.get(j).quantity,list2.get(j).country)
            list3 += item
        }
        return list3
    }

    private fun setLabel(label: String) {
        val classifierLabel = findViewById<TextView>(R.id.classifierLabel)
        classifierLabel.setText("Our SmartGrocery Assistant has detected $label. Check out what we have in stock below!")
    }

    fun goToShoppingCart(view: View) {
        val intent = Intent(this, ShoppingCartActivity::class.java)
        startActivity(intent)
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