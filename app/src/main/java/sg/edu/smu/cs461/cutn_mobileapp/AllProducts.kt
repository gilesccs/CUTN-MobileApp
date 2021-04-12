package sg.edu.smu.cs461.cutn_mobileapp

import android.content.Intent
import android.graphics.PorterDuff
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import io.paperdb.Paper
import kotlinx.android.synthetic.main.activity_all_products.*
import java.util.concurrent.TimeUnit


class AllProducts : AppCompatActivity() {

    private lateinit var productAdapter: AllProductAdapter
    private var products = listOf<Product>()
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var products_recyclerview: RecyclerView
    private var category = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        Paper.init(this)
        setContentView(R.layout.activity_all_products)

//        setSupportActionBar(toolbar)

        val cart_size = findViewById<TextView>(R.id.cart_size)
        cart_size.text = ShoppingCart.getShoppingCartSize().toString()

        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val upArrow = ContextCompat.getDrawable(this, R.drawable.abc_ic_ab_back_material)
        upArrow?.setColorFilter(
            ContextCompat.getColor(this, R.color.colorPrimary),
            PorterDuff.Mode.SRC_ATOP
        )
        supportActionBar?.setHomeAsUpIndicator(upArrow)

        try {
            swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout)
            swipeRefreshLayout.setColorSchemeColors(
                ContextCompat.getColor(
                    this,
                    R.color.colorPrimary
                )
            )
            swipeRefreshLayout.isRefreshing = true
            swipeRefreshLayout.setOnRefreshListener {
//                products = emptyList()
//                getProducts()
                swipeRefreshLayout.isRefreshing = false
                Log.i ("why","refreshing...")
            }

        } catch (e: Exception) {
            Log.i("ERROR", e.toString())
        }

        try {
            products_recyclerview = findViewById(R.id.products_recyclerview)
            // assign a layout manager to the recycler view
            products_recyclerview.layoutManager =
                StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)

            getProducts()
            showCart.setOnClickListener {
                Log.i("cart", "Button pressed!")
                startActivity(Intent(this, ShoppingCartActivity::class.java))
            }

        } catch (e: Exception) {
            Log.i("ERROR", e.toString())
        }


    }

    fun goToShoppingCart(view: View) {
        Log.i("cart", "Button pressed!")
        startActivity(Intent(this, ShoppingCartActivity::class.java))
    }


    fun getProducts() {

//        products = response.body()!!
        val myDBHelper = MyDBHelper(this)

        // GET FROM INTENT HERE
        if (category == "") {
            category = intent.getStringExtra("category")?.toLowerCase().toString()
            val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
            toolbar.title = category.capitalize()
        }
        Log.i("category", category.toString())
        products = category?.let { myDBHelper.readByCategory(it) }!!

        Log.i("products", products.toString())

//        products = myDBHelper.readByCategory("fruits")
//
//        products = listOf(
//            Product("name", "1", "description", 4.0f, "Singapore"),
//            Product("name", "1", "description", 4.0f, "Singapore")
//        )

        Log.i("products", products[0].productname.toString())
        productAdapter = AllProductAdapter(this, products)

        products_recyclerview = findViewById(R.id.products_recyclerview)
        products_recyclerview.adapter = productAdapter
        productAdapter.notifyDataSetChanged()

        swipeRefreshLayout.isRefreshing = false


    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item?.itemId) {
            android.R.id.home -> {
                onBackPressed()
            }
        }

        return super.onOptionsItemSelected(item!!)
    }


}
