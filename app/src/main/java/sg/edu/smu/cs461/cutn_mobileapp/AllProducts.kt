package sg.edu.smu.cs461.cutn_mobileapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.recyclerview.widget.RecyclerView


class AllProducts : AppCompatActivity() {

    private lateinit var productAdapter: ProductAdapter
    private var products = listOf<Product>()
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var products_recyclerview: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout)
        swipeRefreshLayout.setColorSchemeColors(ContextCompat.getColor(this, R.color.colorPrimary))

        swipeRefreshLayout.isRefreshing = true

        // assign a layout manager to the recycler view
        products_recyclerview.layoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)

        getProducts()

    }


    fun getProducts() {

        swipeRefreshLayout.isRefreshing = false
//        products = response.body()!!
        val myDBHelper = MyDBHelper(this)
        products = myDBHelper.readByCategory("fruits")

        productAdapter = ProductAdapter(this, products)

        products_recyclerview = findViewById(R.id.products_recyclerview)
        products_recyclerview.adapter = productAdapter
        productAdapter.notifyDataSetChanged()


    }
}
