package sg.edu.smu.cs461.cutn_mobileapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class PopularItemAdapter (private val productList: List<PopularItem>, private val listener: OnItemClickListener) : RecyclerView.Adapter<PopularItemAdapter.PopularItemViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularItemViewHolder {
        var popularItemView = LayoutInflater.from(parent.context).inflate(R.layout.popular_items_layout, parent, false)
        return PopularItemViewHolder(popularItemView)
    }

    override fun onBindViewHolder(holder: PopularItemViewHolder, position: Int) {
        val currentProduct = productList[position]
        holder.imageViewPopularItem.setImageResource(currentProduct.imageResource)
        holder.productName.text = "Item: " + currentProduct.productname
        holder.description.text = "Description: " + currentProduct.description
        holder.price.text = "Price: $" + currentProduct.price.toString()
        holder.quantity.text = "Quantity: " + currentProduct.quantity
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