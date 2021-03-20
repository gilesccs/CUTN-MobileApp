package sg.edu.smu.cs461.cutn_mobileapp

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar

class AllProductAdapter(var context: Context, var products: List<Product> = arrayListOf()) :
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
        val product = products[position]
        viewHolder.bindProduct(products[position])
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val product_name: TextView = view.findViewById(R.id.product_name)
        val product_price: TextView = view.findViewById(R.id.product_price)
        val product_image: ImageView = view.findViewById(R.id.product_image)
        val addToCart: ImageButton = view.findViewById(R.id.addToCart)
        val removeItem: ImageButton = view.findViewById(R.id.removeItem)

        // This displays the product information for each item
        fun bindProduct(product: Product) {

//            itemView.product_name.text = product.name
            this.product_name.text = product.productname
//            this.product_price.text = "$${product.price.toString()}"
            this.product_price.text = product.price.toString()
            this.product_image.setImageResource(R.drawable.r1)
//            Picasso.get().load(product.photos[0].filename).fit().into(itemView.product_image)
            this.addToCart.setOnClickListener { view ->

                val item = CartItem(product)

                ShoppingCart.addItem(item)
                //notify users
//                Snackbar.make(
//                    (itemView.context as MainActivity).coordinator,
//                    "${product.name} added to your cart",
//                    Snackbar.LENGTH_LONG
//                ).show()
//                Snac
                Log.i("item","Added to cart: ${product.productname}")

            }

            this.removeItem.setOnClickListener { view ->

                val item = CartItem(product)

                ShoppingCart.removeItem(item, itemView.context)

//                Snackbar.make(
//                    (itemView.context as MainActivity).coordinator,
//                    "${product.name} removed from your cart",
//                    Snackbar.LENGTH_LONG
//                ).show()
                Log.i("item","Removed from cart: ${product.productname}")

            }
        }

    }

}