package sg.edu.smu.cs461.cutn_mobileapp

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import org.w3c.dom.Text

class AllProductAdapter(var context: Context, var products: List<Product> = arrayListOf()) :
    RecyclerView.Adapter<AllProductAdapter.ViewHolder>() {
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): AllProductAdapter.ViewHolder {
        // The layout design used for each list item
        val view = LayoutInflater.from(context).inflate(R.layout.product_row_item, null)
        return ViewHolder(view)

    }

    // This returns the size of the list.
    override fun getItemCount(): Int = products.size

    override fun onBindViewHolder(viewHolder: AllProductAdapter.ViewHolder, position: Int) {
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
        @SuppressLint("CheckResult")
        fun bindProduct(product: Product) {

//            itemView.product_name.text = product.name
            this.product_name.text = product.productname
//            this.product_price.text = "$${product.price.toString()}"
            this.product_price.text = product.price.toString()
            this.product_image.setImageResource(R.drawable.r1)
//            Picasso.get().load(product.photos[0].filename).fit().into(itemView.product_image)

            Observable.create(ObservableOnSubscribe<MutableList<CartItem>> {

                this.addToCart.setOnClickListener { view ->

                    val item = CartItem(product)

                    ShoppingCart.addItem(item)
                    //notify users
//                Snackbar.make(
//                    (itemView.context as MainActivity).coordinator,
//                    "${product.name} added to your cart",
//                    Snackbar.LENGTH_LONG
//                ).show()
//                Toast.makeText(
//                    this.context,
//                    product.productname + " added to your cart",
//                    Toast.LENGTH_SHORT
//                ).show()
//                Snac
                    Log.i("item", "Added to cart: ${product.productname}")
                    it.onNext(ShoppingCart.getCart())

                }

                this.removeItem.setOnClickListener { view ->

                    val item = CartItem(product)

                    ShoppingCart.removeItem(item, itemView.context)

//                Snackbar.make(
//                    (itemView.context as MainActivity).coordinator,
//                    "${product.name} removed from your cart",
//                    Snackbar.LENGTH_LONG
//                ).show()
                    Log.i("item", "Removed from cart: ${product.productname}")
                    it.onNext(ShoppingCart.getCart())
                }
            }).subscribe { cart ->
                var quantity = 0

                cart.forEach { cartItem ->
                    quantity += cartItem.quantity
                }

                (itemView.context as AllProducts).findViewById<TextView>(R.id.cart_size).text = quantity.toString()

//                Toast.makeText(this.context, "Cart size $quantity", Toast.LENGTH_SHORT).show()
            }
        }

    }

}