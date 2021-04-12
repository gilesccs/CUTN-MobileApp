package sg.edu.smu.cs461.cutn_mobileapp

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

class ProductAdapter(private val productList: List<Product>) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        var productView = LayoutInflater.from(parent.context).inflate(R.layout.popular_items_layout, parent, false)
        return ProductViewHolder(productView)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val currentProduct = productList[position]
        holder.productName.text = "Item: " + currentProduct.productname
        holder.description.text = "Description: " + currentProduct.description
        holder.price.text = "Price: $" + currentProduct.price.toString()
        holder.quantity.text = "Quantity: " + currentProduct.quantity



    }

    override fun getItemCount(): Int {
        return productList.size
    }

    class ProductViewHolder(productView: View) : RecyclerView.ViewHolder(productView) {
//        val imageView: ImageView = productView.findViewById(R.id.imageView)
        val productName: TextView = productView.findViewById(R.id.productName)
        val description: TextView = productView.findViewById(R.id.description)
        val price: TextView = productView.findViewById(R.id.price)
        val quantity: TextView = productView.findViewById(R.id.quantity)

    }
}