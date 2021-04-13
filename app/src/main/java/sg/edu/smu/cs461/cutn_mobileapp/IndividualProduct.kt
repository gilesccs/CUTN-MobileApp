package sg.edu.smu.cs461.cutn_mobileapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton
import sg.edu.smu.cs461.cutn_mobileapp.ShoppingCart.Companion.addItem
import www.sanju.motiontoast.MotionToast
import java.text.DecimalFormat


class IndividualProduct : AppCompatActivity() {
    var qty = "0"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_individual_product)
        var productId = intent.getIntExtra("ProductId",1)
        var productName = intent.getStringExtra("ProductName") as String
        var desc = intent.getStringExtra("Description") as String
        var price = intent.getFloatExtra("Price",0F)
        var qty = intent.getStringExtra("Quantity") as String
        var cty = intent.getStringExtra("Country") as String
        var namel = findViewById<TextView>(R.id.productNameLabel)
        namel.text = productName

        var locBtn = findViewById<TextView>(R.id.locationLabel)
        locBtn.text = cty

        var imageLabel = findViewById<ImageView>(R.id.productImageLabel)
        val resourceId = this.resources.getIdentifier("r$productId", "drawable",this.packageName)
        imageLabel.setImageResource(resourceId)

        var pricel = findViewById<TextView>(R.id.productPriceLabel)
        val dec = DecimalFormat("##0.00")
        val totalPriceString = dec.format(price)

        pricel.text = "$${totalPriceString}"

        var packl = findViewById<TextView>(R.id.productPackSize)
        packl.text = qty

        var descl = findViewById<TextView>(R.id.productDescLabel)
        descl.text = desc

        val myDBHelper = MyDBHelper(this)
        val list = myDBHelper.readData()
        var currIndex = 0
        var newList = ArrayList<Product>();
        var firstNum = 0
        while(currIndex < 2){
            var rnds = (0..28).random()
            while(rnds == firstNum) {
                rnds = (0..28).random()
            }
            firstNum = rnds
            var currProduct = list[rnds]
            if(currProduct.productname != productName){
                currIndex += 1
                newList.add(currProduct)
            }
        }

        var firstProduct = findViewById<ImageButton>(R.id.recommendationImageOne)
        var firstID = newList[0].productid
        var firstResource = this.resources.getIdentifier("r$firstID", "drawable",this.packageName)
        firstProduct.setImageResource(firstResource)
        var firstName = findViewById<TextView>(R.id.text1)
        firstName.text = newList[0].productname

        firstProduct?.setOnClickListener{
            val it = Intent(this, IndividualProduct::class.java)
            it.putExtra("ProductId", newList[0].productid)
            it.putExtra("ProductName", newList[0].productname)
            it.putExtra("Description", newList[0].description)
            it.putExtra("Price", newList[0].price)
            it.putExtra("Quantity", newList[0].quantity)
            it.putExtra("Country", newList[0].country)
            startActivity(it)
        }

        var secondProduct = findViewById<ImageButton>(R.id.recommendationImageTwo)
        var secondID = newList[1].productid
        var secondResource = this.resources.getIdentifier("r$secondID", "drawable",this.packageName)
        secondProduct.setImageResource(secondResource)
        var secondName = findViewById<TextView>(R.id.text2)
        secondName.text = newList[1].productname
        secondProduct?.setOnClickListener{
            val it = Intent(this, IndividualProduct::class.java)
            it.putExtra("ProductId", newList[1].productid)
            it.putExtra("ProductName", newList[1].productname)
            it.putExtra("Description", newList[1].description)
            it.putExtra("Price", newList[1].price)
            it.putExtra("Quantity", newList[1].quantity)
            it.putExtra("Country", newList[1].country)
            startActivity(it)
        }
        goBackHomePage()
        addQtyListener()
        addToCart("Test", productName, qty, desc, price, cty, productId)
    }

    private fun goBackHomePage(){
        val btn = findViewById<Button>(R.id.goBack)
        btn.setOnClickListener{
            finish()
        }
    }

    private fun addQtyListener(){
        val abutton = findViewById<View>(R.id.quantityBtn) as ElegantNumberButton
        abutton.setOnClickListener(ElegantNumberButton.OnClickListener { qty = abutton.number })
    }

    private fun addToCart(category: String, productname: String, quantity: String, description: String, price: Float, country: String, productid: Int){
        val submit = findViewById<Button>(R.id.submitBtn)
        submit.setOnClickListener{
            val value = qty.toInt()
            if(value == 0){
//                Toast.makeText(this, "Please specify at least 1 quantity!", Toast.LENGTH_SHORT).show()
                MotionToast.createColorToast(
                    this,
                    "Error!",
                    "Please specify at least 1 item!",
                    MotionToast.TOAST_ERROR,
                    MotionToast.GRAVITY_BOTTOM,
                    MotionToast.LONG_DURATION,
                    ResourcesCompat.getFont(this, R.font.helvetica_regular)
                )
            }else{
                var currentProduct = Product(category,productname,quantity,description,price,country,productid)
                addItem(CartItem(currentProduct,(value-1)))
                MotionToast.createColorToast(this,
                    "",
                    "$value item(s) have been added to cart!",
                    MotionToast.TOAST_INFO,
                    MotionToast.GRAVITY_BOTTOM,
                    MotionToast.SHORT_DURATION,
                    ResourcesCompat.getFont(this,R.font.helvetica_regular))
            }
        }
    }

    fun goToShoppingCart(view: View) {
        val intent = Intent(this, ShoppingCartActivity::class.java)
        startActivity(intent)
    }
}