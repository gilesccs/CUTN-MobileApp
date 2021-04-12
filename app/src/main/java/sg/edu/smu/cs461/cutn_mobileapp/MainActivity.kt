package sg.edu.smu.cs461.cutn_mobileapp

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.tensorflow.lite.support.image.TensorImage
import sg.edu.smu.cs461.cutn_mobileapp.ml.GroceryModel


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


    private fun individualPage() {
        val rewardsBtn = findViewById<ImageButton>(R.id.microphone)
        rewardsBtn?.setOnClickListener{
            val it = Intent(this, IndividualProduct::class.java)
            startActivityForResult(it, 4321)
        }
    }

    fun launchCameraForClassifier(view: View) {
        val pickImageFileIntent = Intent()
        while (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.CAMERA),111)
        }

        pickImageFileIntent.type = "image/*"
        pickImageFileIntent.action = Intent.ACTION_GET_CONTENT
        val pickGalleryImageIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        val captureCameraImageIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        val pickTitle = "Capture from camera or Select from gallery the Profile photo"
        val chooserIntent = Intent.createChooser(pickImageFileIntent, pickTitle)
        chooserIntent.putExtra(
            Intent.EXTRA_INITIAL_INTENTS, arrayOf(
                captureCameraImageIntent,
                pickGalleryImageIntent
            )
        )
        startActivityForResult(chooserIntent, REQ_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, imageReturnedIntent: Intent?) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent)
//        val profileImage = findViewById<ImageView>(R.id.profileImage)
        if (requestCode === REQ_CODE) {
            Log.i("img", "0" + imageReturnedIntent.toString())

            var pic = imageReturnedIntent?.getParcelableExtra<Bitmap>("data")

//            profileImage.layoutParams.height = 600;
//            profileImage.requestLayout();
            if (pic === null) {
                val selectedImage: Uri? = imageReturnedIntent?.data
//                profileImage.setImageURI(selectedImage)
                val bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, selectedImage)
                Log.i("img", "1" + bitmap.toString())
//                val uri = saveImageToInternalStorage(bitmap)
            } else {
                Log.i("img", "2" + pic.toString())
                analyzeWithClassifier(this, pic)

//                profileImage.setImageBitmap(Bitmap.createScaledBitmap(pic, 500, 600, false));
//                storeImage(pic)
            }
        }
    }

    private fun analyzeWithClassifier(ctx: Context, bitmap: Bitmap) {
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
//            items.add( Recognition(output.label, output.score ))
            Log.i("img", "label: "+output.label + " score: "+ output.score)
        }

        // Releases model resources if no longer used.
        groceryModel.close()

        val it = Intent(this, Classifier::class.java)
        it.putExtra("label", probability[0].label)
//        profileImage.setImageBitmap(Bitmap.createScaledBitmap(pic, 500, 600, false));
        it.putExtra("imgBitmap", Bitmap.createScaledBitmap(bitmap, 300, 300, false))
        startActivityForResult(it, 123)

    }
}