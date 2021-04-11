package sg.edu.smu.cs461.cutn_mobileapp

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

class CategoryAdapter (private val productList: List<Category>) : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        var categoryView = LayoutInflater.from(parent.context).inflate(R.layout.category_layout, parent, false)
        return CategoryViewHolder(categoryView)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val currentProduct = productList[position]
        holder.categoryName.text = currentProduct.category
        holder.imageViewCategory.setImageResource(currentProduct.imageResource)
        if (position == 0){
            holder.cardView.setCardBackgroundColor(Color.rgb(240,143,143))
        } else if (position == 1){
            holder.cardView.setCardBackgroundColor(Color.rgb(153,255,102))
        } else if (position == 2){
            holder.cardView.setCardBackgroundColor(Color.rgb(245,245,245))
        } else {
            holder.cardView.setCardBackgroundColor(Color.rgb(255,255,102))
        }
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    class CategoryViewHolder(categoryView: View) : RecyclerView.ViewHolder(categoryView) {
        val imageViewCategory: ImageView = categoryView.findViewById(R.id.imageViewCategory)
        val categoryName: TextView = categoryView.findViewById(R.id.categoryName)
        val cardView: CardView = categoryView.findViewById(R.id.cardView)
    }
}