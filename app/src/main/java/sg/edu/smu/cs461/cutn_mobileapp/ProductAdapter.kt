package sg.edu.smu.cs461.cutn_mobileapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ProductAdapter(private val productList: List<Product>) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        var productView = LayoutInflater.from(parent.context).inflate(R.layout.popular_items_layout, parent, false)

        return ProductViewHolder(productView)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val currentProduct = productList[position]

        holder.textView1.text = currentProduct.productname
        holder.textView2.text = "$" + currentProduct.price.toString()
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    class ProductViewHolder(productView: View) : RecyclerView.ViewHolder(productView) {
        val imageView: ImageView = productView.findViewById(R.id.imageView)
        val textView1: TextView = productView.findViewById(R.id.productName)
        val textView2: TextView = productView.findViewById(R.id.price)
    }
}