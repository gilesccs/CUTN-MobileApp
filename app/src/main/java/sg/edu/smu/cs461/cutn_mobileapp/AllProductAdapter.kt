package sg.edu.smu.cs461.cutn_mobileapp

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import org.w3c.dom.Text
import www.sanju.motiontoast.MotionToast
import java.text.DecimalFormat

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
            val dec = DecimalFormat("##0.00")
            val totalPriceString = dec.format(product.price)
            this.product_price.text = "$${totalPriceString}"
            this.product_image.setImageResource(R.drawable.r1)
            val variableValue = "r${product.productid}"

            val context = itemView.context
            val resourceId = context.resources.getIdentifier(variableValue, "drawable",context.packageName)
            this.product_image.setImageResource(resourceId)

            
//            itemView.product_image.setImageResource(Resources.getIdentifier(variableValue))
//            itemView.product_image.setImageResource(ContextCompat.getDrawable(context, variableValue))
//            Picasso.get().load(product.photos[0].filename).fit().into(itemView.product_image)

            Observable.create(ObservableOnSubscribe<MutableList<CartItem>> {

                this.addToCart.setOnClickListener { view ->

                    val item = CartItem(product)

                    ShoppingCart.addItem(item)

                    //notify users
                    MotionToast.darkToast(itemView.context as Activity,
                        "Added to cart",
                        "${product.productname} added!",
                        MotionToast.TOAST_SUCCESS,
                        MotionToast.GRAVITY_BOTTOM,
                        MotionToast.SHORT_DURATION,
                        ResourcesCompat.getFont(context,R.font.helvetica_regular))
                    Log.i("item", "Added to cart: ${product.productname}")
                    it.onNext(ShoppingCart.getCart())

                }

                this.removeItem.setOnClickListener { view ->

                    val item = CartItem(product)

                    ShoppingCart.removeItem(item, itemView.context)

                    //notify users
                    MotionToast.darkToast(itemView.context as Activity,
                        "Removed from cart",
                        "${product.productname} removed!",
                        MotionToast.TOAST_SUCCESS,
                        MotionToast.GRAVITY_BOTTOM,
                        MotionToast.SHORT_DURATION,
                        ResourcesCompat.getFont(context,R.font.helvetica_regular))
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