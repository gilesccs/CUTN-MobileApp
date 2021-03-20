package sg.edu.smu.cs461.cutn_mobileapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class ProductAdapter(var context: Context, var products: List<Product> = arrayListOf()) :
    RecyclerView.Adapter<ProductAdapter.ViewHolder>() {
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ProductAdapter.ViewHolder {
        // The layout design used for each list item
        val view = LayoutInflater.from(context).inflate(R.layout.product_row_item, null)
        return ViewHolder(view)

    }
    // This returns the size of the list.
    override fun getItemCount(): Int = products.size

    override fun onBindViewHolder(viewHolder: ProductAdapter.ViewHolder, position: Int) {
        //we simply call the `bindProduct` function here
        viewHolder.bindProduct(products[position])
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        // This displays the product information for each item
        fun bindProduct(product: Product) {

            itemView.product_name.text = product.name
            itemView.product_price.text = "$${product.price.toString()}"
//            Picasso.get().load(product.photos[0].filename).fit().into(itemView.product_image)

        }

    }

}