package sg.edu.smu.cs461.cutn_mobileapp

//..app/src/main/java/yourPackage/ShoppingCartAdapter.kt

import android.content.Context
import android.content.res.Resources
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import kotlinx.android.synthetic.main.cart_list_item.view.*
import java.text.DecimalFormat
import kotlin.properties.Delegates

class ShoppingCartAdapter(var context: Context, var cartItems: List<CartItem>) :

    RecyclerView.Adapter<ShoppingCartAdapter.ViewHolder>() {
    var resourceId by Delegates.notNull<Int>()
    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ShoppingCartAdapter.ViewHolder {

        // The layout design used for each list item
        val layout = LayoutInflater.from(context).inflate(R.layout.cart_list_item, parent, false)


        return ViewHolder(layout)
    }

    // This returns the size of the list.
    override fun getItemCount(): Int = cartItems.size

    override fun onBindViewHolder(viewHolder: ShoppingCartAdapter.ViewHolder, position: Int) {

        //we simply call the `bindItem` function here
        viewHolder.bindItem(cartItems[position])
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bindItem(cartItem: CartItem) {

            Observable.create(ObservableOnSubscribe<MutableList<CartItem>> {
                // This displays the cart item information for each item
//            Picasso.get().load(cartItem.product.photos[0].filename).fit().into(itemView.product_image)
                val variableValue = "r${cartItem.product.productid}"

                val context = itemView.context
                val resourceId =
                    context.resources.getIdentifier(variableValue, "drawable", context.packageName)
//            itemView.product_image.setImageResource(ContextCompat.getDrawable(context, variableValue))
//            itemView.product_image.setImageResource(Resources.getIdentifier(variableValue))
                itemView.product_image.setImageResource(resourceId)

//            itemView.product_image.setImageResource(getResources().getIdentifier(variableValue, "drawable", getPackageName()))
                itemView.product_name.text = cartItem.product.productname

                val dec = DecimalFormat("##0.00")
                val totalPriceString = dec.format(cartItem.product.price)
                itemView.product_price.text = "$${totalPriceString}"

                itemView.product_quantity.text = cartItem.quantity.toString()

                itemView.removeItemBtn.setOnClickListener { view ->

                    ShoppingCart.removeItem(cartItem, itemView.context)
                    Log.i("item", "Removed from cart")
                    it.onNext(ShoppingCart.getCart())


//                Snackbar.make(
//                    (itemView.context as MainActivity).coordinator,
//                    "${product.name} removed from your cart",
//                    Snackbar.LENGTH_LONG
//                ).show()
//                Log.i("item", "Removed from cart: ${product.productname}")
//                it.onNext(ShoppingCart.getCart())
                }
            }).subscribe { cart ->
                var quantity = 0

                cart.forEach { cartItem ->
                    quantity += cartItem.quantity
                }
            }
        }
    }

}