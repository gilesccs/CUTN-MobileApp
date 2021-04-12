package sg.edu.smu.cs461.cutn_mobileapp

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import java.text.DecimalFormat

class PopularItemAdapter (private val productList: List<PopularItem>, private val listener: OnItemClickListener) : RecyclerView.Adapter<PopularItemAdapter.PopularItemViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularItemViewHolder {
        var popularItemView = LayoutInflater.from(parent.context).inflate(R.layout.popular_items_layout, parent, false)
        return PopularItemViewHolder(popularItemView)
    }

    override fun onBindViewHolder(holder: PopularItemViewHolder, position: Int) {
        val currentProduct = productList[position]
        holder.imageViewPopularItem.setImageResource(currentProduct.imageResource)
        holder.productName.text = currentProduct.productname.capitalize()
        holder.description.text = currentProduct.description.capitalize()

        // Format price
        val dec = DecimalFormat("##0.00")
        val totalPriceString = dec.format(currentProduct.price)

        holder.price.text = "Price: $" + totalPriceString
        holder.quantity.text = "Quantity: " + currentProduct.quantity
        holder.cardView.setCardBackgroundColor(Color.rgb(243,241,235))
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    inner class PopularItemViewHolder(popularItemView: View) : RecyclerView.ViewHolder(popularItemView), View.OnClickListener {
        val imageViewPopularItem: ImageView = popularItemView.findViewById(R.id.imageViewPopularItem)
        val productName: TextView = popularItemView.findViewById(R.id.productName)
        val description: TextView = popularItemView.findViewById(R.id.description)
        val price: TextView = popularItemView.findViewById(R.id.price)
        val quantity: TextView = popularItemView.findViewById(R.id.quantity)
        val cardView: CardView = popularItemView.findViewById(R.id.cardView)
        init {
            popularItemView.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            val position: Int = adapterPosition
            if (position != RecyclerView.NO_POSITION){
                listener.onItemClick(position)
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }
}