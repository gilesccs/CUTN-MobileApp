package sg.edu.smu.cs461.cutn_mobileapp

//..app/src/main/java/yourPackage/ShoppingCartAdapter.kt

import android.content.Context
import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.cart_list_item.view.*
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

//            // This displays the cart item information for each item
//            Picasso.get().load(cartItem.product.photos[0].filename).fit().into(itemView.product_image)
            val variableValue = "r${cartItem.product.productid}"

            val context = itemView.context
            val resourceId = context.resources.getIdentifier(variableValue, "drawable",context.packageName)
//            itemView.product_image.setImageResource(ContextCompat.getDrawable(context, variableValue))
//            itemView.product_image.setImageResource(Resources.getIdentifier(variableValue))
            itemView.product_image.setImageResource(resourceId)

//            itemView.product_image.setImageResource(getResources().getIdentifier(variableValue, "drawable", getPackageName()))
            itemView.product_name.text = cartItem.product.productname

            itemView.product_price.text = "$${cartItem.product.price}"

            itemView.product_quantity.text = cartItem.quantity.toString()

        }
    }

}