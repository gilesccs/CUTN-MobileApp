package sg.edu.smu.cs461.cutn_mobileapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ClassifierAdapter (private val classifierList: List<ClassifierModel>) : RecyclerView.Adapter<ClassifierAdapter.ClassifierViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClassifierViewHolder {
        var classifierView = LayoutInflater.from(parent.context).inflate(R.layout.classifier_layout, parent, false)
        return ClassifierViewHolder(classifierView)
    }

    override fun onBindViewHolder(holder: ClassifierViewHolder, position: Int) {
        val currentProduct = classifierList[position]
        holder.imageViewPopularItem.setImageResource(currentProduct.imageResource)
        holder.productName.text = "Item: " + currentProduct.productname
        holder.description.text = "Description: " + currentProduct.description
        holder.price.text = "Price: $" + currentProduct.price.toString()
        holder.quantity.text = "Quantity: " + currentProduct.quantity
    }

    override fun getItemCount(): Int {
        return classifierList.size
    }

    class ClassifierViewHolder(classifierView: View) : RecyclerView.ViewHolder(classifierView) {
        val imageViewPopularItem: ImageView = classifierView.findViewById(R.id.imageViewPopularItem)
        val productName: TextView = classifierView.findViewById(R.id.productName)
        val description: TextView = classifierView.findViewById(R.id.description)
        val price: TextView = classifierView.findViewById(R.id.price)
        val quantity: TextView = classifierView.findViewById(R.id.quantity)
    }
}