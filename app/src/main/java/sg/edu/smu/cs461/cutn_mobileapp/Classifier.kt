package sg.edu.smu.cs461.cutn_mobileapp

import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.tensorflow.lite.support.image.TensorImage
import sg.edu.smu.cs461.cutn_mobileapp.ml.GroceryModel
import www.sanju.motiontoast.MotionToast
import java.io.File

class Classifier : AppCompatActivity(), ClassifierAdapter.OnItemClickListener {
    private var labelTest:String? = null
    private var numRecyclerCards = 5

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_classifier)
        supportActionBar?.hide()
        goBackHomePage()
        val product = intent.getStringExtra("product")
        if (product == null) {
            analyzeWithClassifier(this)
        } else {
            setPlaceholderImage()
            populateRecyclerCard(product)
        }
    }
    private fun calculateInSampleSize(options: BitmapFactory.Options, reqWidth: Int, reqHeight: Int): Int {
        // Raw height and width of image
        val (height: Int, width: Int) = options.run { outHeight to outWidth }
        var inSampleSize = 1

        if (height > reqHeight || width > reqWidth) {
            val halfHeight: Int = height / 2
            val halfWidth: Int = width / 2
            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while (halfHeight / inSampleSize >= reqHeight && halfWidth / inSampleSize >= reqWidth) {
                inSampleSize *= 2
            }
        }
        return inSampleSize
    }

    private fun decodeSampledBitmapFromResource(
        path: String,
        reqWidth: Int,
        reqHeight: Int
    ): Bitmap {
        // First decode with inJustDecodeBounds=true to check dimensions
        return BitmapFactory.Options().run {
            inJustDecodeBounds = true
            BitmapFactory.decodeFile(path)

            // Calculate inSampleSize
            inSampleSize = calculateInSampleSize(this, reqWidth, reqHeight)

            // Decode bitmap with inSampleSize set
            inJustDecodeBounds = false
            BitmapFactory.decodeFile(path)
        }
    }

    private fun analyzeWithClassifier(ctx: Context) {
        val imgFile = File(getDir("groceryPhoto", MODE_APPEND).toString() + "/groceryItem.jpg")
        var bitmap: Bitmap? = null
        var userImage = findViewById<ImageView>(R.id.userImage)
        val width = userImage.drawable.intrinsicWidth
        val height = userImage.drawable.intrinsicHeight
        if (imgFile.exists()) {
            userImage.setImageBitmap(
                decodeSampledBitmapFromResource(imgFile.absolutePath, width, height)
            )
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

        // Releases model resources if no longer used.
        groceryModel.close()

        setLabel(probability[0].label)
        this.labelTest = probability[0].label
        populateRecyclerCard(probability[0].label)
    }

    private fun setPlaceholderImage() {
        var userImage = findViewById<ImageView>(R.id.userImage)
        userImage.setImageDrawable(getDrawable(R.drawable.no_image_foreground))
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
        if (list.size == 0) {
            MotionToast.createColorToast(this,
                "Sorry!",
                "No matching products found!!",
                MotionToast.TOAST_WARNING,
                MotionToast.GRAVITY_BOTTOM,
                MotionToast.LONG_DURATION,
                ResourcesCompat.getFont(this,R.font.helvetica_regular))
            return emptyList()
        }
        val list2 = ArrayList<Product>()
        val list3 = ArrayList<ClassifierModel>()
        var idx = 0

        while (list.size < numRecyclerCards) {
            list.add(list[idx])
            idx++
        }

        for (i in 0 until size){
            val item = Product(list.get(i).category,"${list.get(i).productname}",list.get(i).quantity, list.get(i).description,list.get(i).price, list.get(i).country, list.get(i).productid)
            list2 += item
        }

        for (i in 0 until size){
            var j = i
            val drawable = this.resources.getIdentifier("r${list2[i].productid}", "drawable",this.packageName)
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
}