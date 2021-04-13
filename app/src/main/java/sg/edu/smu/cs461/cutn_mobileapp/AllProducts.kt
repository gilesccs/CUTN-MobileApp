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
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import io.paperdb.Paper
import kotlinx.android.synthetic.main.activity_all_products.*
import www.sanju.motiontoast.MotionToast
import java.util.concurrent.TimeUnit


class AllProducts : AppCompatActivity() {

    private lateinit var productAdapter: AllProductAdapter
    private var products = listOf<Product>()
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var products_recyclerview: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_products)
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
            swipeRefreshLayout.setOnRefreshListener {
                swipeRefreshLayout.isRefreshing = false
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
                startActivity(Intent(this, ShoppingCartActivity::class.java))
            }
        } catch (e: Exception) {
            Log.i("ERROR", e.toString())
        }
    }

    fun goToShoppingCart(view: View) {
        startActivity(Intent(this, ShoppingCartActivity::class.java))
    }

    fun getProducts() {
        val myDBHelper = MyDBHelper(this)
        val category = intent.getStringExtra("category")
        val keyword = intent.getStringExtra("product")
        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)

        if (category != null) {
            products = category.toString().toLowerCase().let { myDBHelper.readByCategory(it) }!!
            toolbar.title = category.toString().capitalize()
        }
        else if (keyword != null) {
            products = myDBHelper.readByMachineLearning(keyword.toString())
            toolbar.title = "${products.size} search result(s) for \"" + keyword.toString().capitalize() + "\""
            if (products.size == 0) {
                MotionToast.createColorToast(
                    this,
                    "Sorry!",
                    "No matching products found!",
                    MotionToast.TOAST_WARNING,
                    MotionToast.GRAVITY_BOTTOM,
                    MotionToast.LONG_DURATION,
                    ResourcesCompat.getFont(this, R.font.helvetica_regular)
                )
            }
        }
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
