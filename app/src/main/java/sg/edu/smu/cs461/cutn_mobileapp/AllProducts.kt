package sg.edu.smu.cs461.cutn_mobileapp

import android.os.Bundle
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.material.appbar.CollapsingToolbarLayout


class AllProducts : AppCompatActivity() {

    private lateinit var productAdapter: ProductAdapter
    private var products = listOf<Product>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        val swipeRefreshLayout = findViewById<androidx.swiperefreshlayout>(R.id.swipeRefreshLayout)
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
        products = myDBHelper.readByCategory()

        productAdapter = ProductAdapter(this, products)

        products_recyclerview.adapter = productAdapter
        productAdapter.notifyDataSetChanged()


    }
}
}