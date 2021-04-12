package sg.edu.smu.cs461.cutn_mobileapp

//..app/src/main/java/yourPackage/ShoppingCartAdapter.kt

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import kotlinx.android.synthetic.main.cart_list_item.view.*
import kotlinx.android.synthetic.main.cart_list_item.view.product_image
import kotlinx.android.synthetic.main.cart_list_item.view.product_name
import kotlinx.android.synthetic.main.cart_list_item.view.product_price
import kotlinx.android.synthetic.main.product_row_item.view.*
import java.text.DecimalFormat
import kotlin.properties.Delegates

class ShoppingCartAdapter(var context: Context, var cartItems: List<CartItem>, var clickListener: OnCartItemClickListener) :

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
//        notifyItemChanged(position)
        viewHolder.initialize(cartItems[position],clickListener)

//        viewHolder.itemView.removeItemBtn.setOnClickListener{
//            ShoppingCart.removeItem(cartItems[position], context)
//            Log.i("removed", cartItems[position].toString())
//            notifyDataSetChanged()
//        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        @SuppressLint("CheckResult")
        fun bindItem(cartItem: CartItem) {

            // This displays the cart item information for each item
            val variableValue = "r${cartItem.product.productid}"

            val context = itemView.context
            val resourceId =
                context.resources.getIdentifier(variableValue, "drawable", context.packageName)
            itemView.product_image.setImageResource(resourceId)

            itemView.product_name.text = cartItem.product.productname

            val dec = DecimalFormat("##0.00")
            val totalPriceString = dec.format(cartItem.product.price)
            itemView.product_price.text = "$${totalPriceString}"

            itemView.product_quantity.text = cartItem.quantity.toString()

//            Observable.create(ObservableOnSubscribe<MutableList<CartItem>> {
//
//                itemView.removeItemBtn.setOnClickListener { view ->
//
//                    ShoppingCart.removeItem(cartItem, itemView.context)
//                    Log.i("item", "Removed from cart")
//                    it.onNext(ShoppingCart.getCart())
//                }
//            }).subscribe{cart ->
//                var quantity = 0
//
//                cart.forEach { cartItem ->
//                    quantity += cartItem.quantity
//                }
////                (itemView.context as AllProducts).findViewById<TextView>(R.id.cart_size).text = quantity.toString()
//            }
        }

        fun initialize(item: CartItem, action: OnCartItemClickListener){
            itemView.removeItemBtn.setOnClickListener{
                action.onItemClick(item, adapterPosition)
            }
        }
    }

}

interface OnCartItemClickListener{
    fun onItemClick(item: CartItem, position: Int)
}