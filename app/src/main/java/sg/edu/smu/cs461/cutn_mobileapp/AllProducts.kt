package sg.edu.smu.cs461.cutn_mobileapp

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import io.paperdb.Paper


class AllProducts : AppCompatActivity() {

    private lateinit var productAdapter: ProductAdapter
    private var products = listOf<Product>()
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var products_recyclerview: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Paper.init(this)
        setContentView(R.layout.activity_all_products)

        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        val cart_size = findViewById<TextView>(R.id.cart_size)
        cart_size.text = ShoppingCart.getShoppingCartSize().toString()

        try {
            swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout)
            if (swipeRefreshLayout != null) {
                swipeRefreshLayout.setColorSchemeColors(
                    ContextCompat.getColor(
                        this,
                        R.color.colorPrimary
                    )
                )
                swipeRefreshLayout.isRefreshing = true

            }
        } catch (e: Exception) {
            Log.i("ERROR", e.toString())
        }

        try {
            products_recyclerview = findViewById(R.id.products_recyclerview)
            if (products_recyclerview != null) {
                // assign a layout manager to the recycler view
                products_recyclerview.layoutManager =
                    StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)

                getProducts()
            }

        } catch (e: Exception) {
            Log.i("ERROR",e.toString())
        }


    }


    fun getProducts() {

        swipeRefreshLayout.isRefreshing = false
//        products = response.body()!!
//        val myDBHelper = MyDBHelper(this)
//        products = myDBHelper.readByCategory("fruits")
//
        products = listOf(
            Product("name", "1", "description", 4.0f, "Singapore"),
            Product("name", "1", "description", 4.0f, "Singapore")
        )

        Log.i("products", products[0].productname.toString())
        productAdapter = ProductAdapter(this, products)

        products_recyclerview = findViewById(R.id.products_recyclerview)
        products_recyclerview.adapter = productAdapter
        productAdapter.notifyDataSetChanged()


    }
}
