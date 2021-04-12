package sg.edu.smu.cs461.cutn_mobileapp

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.util.Log
import android.view.View
import android.widget.*
import android.widget.ImageButton
import android.widget.ImageView
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.tensorflow.lite.support.image.TensorImage
import sg.edu.smu.cs461.cutn_mobileapp.ml.GroceryModel
import android.widget.Toast
import io.paperdb.Paper
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity(), PopularItemAdapter.OnItemClickListener, CategoryAdapter.OnItemClickListener {
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
//        individualPage()

        val productList = generateDummyListForPopularItem(5)
        val recyclerViewPopularItem = findViewById<RecyclerView>(R.id.recyclerViewPopularItem)
        recyclerViewPopularItem.adapter = PopularItemAdapter(productList, this)
        recyclerViewPopularItem.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        recyclerViewPopularItem.setHasFixedSize(true)

        val categoryList = generateDummyListForCategory(4)
        val recyclerViewCategory = findViewById<RecyclerView>(R.id.recyclerViewCategory)
        recyclerViewCategory.adapter = CategoryAdapter(categoryList, this)
        recyclerViewCategory.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        recyclerViewCategory.setHasFixedSize(true)

        // For shopping cart
        Paper.init(this)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.i("test", requestCode.toString())
        if (requestCode == SPEECH_CODE) {
            Log.i("test", "yup")
            val result = data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
//            val searchBtn = findViewById<EditText>(R.id.searchForProduct)
            Log.i("test", "result is " + result?.get(0).toString())
//            searchBtn.setText(result?.get(0).toString())
            Toast.makeText(this, "${result} clicked", Toast.LENGTH_SHORT).show()
        } else {
            var pic = data?.getParcelableExtra<Bitmap>("data")
            if (pic === null) {
                val selectedImage: Uri? = data?.data
                val bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, selectedImage)
            } else {
                storeImage(pic)
            }
        }
    }

    override fun onItemClick(position: Int) {
        val productList = generateDummyListForPopularItem(5)
        val adapter = PopularItemAdapter(productList, this)
        val clickedItem: PopularItem = productList[position]

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

    override fun onItemClick2(position: Int) {
        val categoryList = generateDummyListForCategory(4)
        val adapter = CategoryAdapter(categoryList, this)
        val clickedItem: Category = categoryList[position]

        val it = Intent(this, AllProducts::class.java)
        it.putExtra("category", clickedItem.category)
        startActivity(it)

//        Toast.makeText(this, "${clickedItem.category} clicked", Toast.LENGTH_SHORT).show()
//        clickedItem.category = "Clicked"
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
            val drawable = when (i%5){
                0 -> R.drawable.r1
                1 -> R.drawable.r2
                2 -> R.drawable.r3
                3 -> R.drawable.r4
                else -> R.drawable.r5
            }

            val item = PopularItem(drawable,list.get(j).productid,list.get(j).productname,list.get(j).description,list.get(j).price,list.get(j).quantity,list.get(j).country)
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

    fun goToAllProducts(view: View) {
        val intent = Intent(this, AllProducts::class.java)
        startActivity(intent)
        Log.i("TABLE CALLED","TEST")
    }
    
//    private fun individualPage() {
//        val rewardsBtn = findViewById<ImageButton>(R.id.photo)
//        rewardsBtn?.setOnClickListener{
//            val it = Intent(this, IndividualProduct::class.java)
//            startActivityForResult(it, 4321)
//        }
//    }

    fun launchCameraForClassifier(view: View) {
        val pickImageFileIntent = Intent()
        while (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.CAMERA),111)
        }
        pickImageFileIntent.type = "image/*"
        pickImageFileIntent.action = Intent.ACTION_GET_CONTENT
//        val pickGalleryImageIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        val captureCameraImageIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        val pickTitle = "Capture from camera or Select from gallery the Profile photo"
        val chooserIntent = Intent.createChooser(pickImageFileIntent, pickTitle)
        chooserIntent.putExtra(
            Intent.EXTRA_INITIAL_INTENTS, arrayOf(
                captureCameraImageIntent,
//                pickGalleryImageIntent
            )
        )
        startActivityForResult(chooserIntent, REQ_CODE)
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

    fun goToShoppingCart(view: View) {
        val intent = Intent(this, ShoppingCartActivity::class.java)
        startActivity(intent)
    }

    /** Create a File for saving an image or video  */
    private fun getOutputMediaFile(): File? {
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.
//        Log.i("dirPic", filesDir
//                .toString() + "/Android/data/"
//                + applicationContext.packageName
//                + "/Files")
        val mediaStorageDir = File(getDir("groceryPhoto", MODE_APPEND)
            .toString())
        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                return null
            }
        }
        // Create a media file name
        val mediaFile: File
        val mImageName = "groceryItem.jpg"
        mediaFile = File(mediaStorageDir.path + File.separator + mImageName)
        return mediaFile
    }

    private fun storeImage(image: Bitmap) {
        val pictureFile = getOutputMediaFile()
        if (pictureFile == null) {
            Log.d("TAG",
                "Error creating media file, check storage permissions: ") // e.getMessage());
            return
        }
        try {
            val fos = FileOutputStream(pictureFile)
            image.compress(Bitmap.CompressFormat.PNG, 90, fos)
            fos.close()
            startClassifier()
        } catch (e: FileNotFoundException) {
            Log.d("TAG", "File not found: ")
        } catch (e: IOException) {
            Log.d("TAG", "Error accessing file: ")
        }
    }

    private fun startClassifier() {
        val it = Intent(this, Classifier::class.java)
        startActivity(it)
    }


}