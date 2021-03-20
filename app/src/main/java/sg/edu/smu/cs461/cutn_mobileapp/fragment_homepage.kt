package sg.edu.smu.cs461.cutn_mobileapp

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class fragment_homepage : Fragment(), View.OnClickListener {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_homepage, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // code to be written here
    }

    override fun onClick(v: View?) {
        // code to be written here
        val button = v as ImageButton
        val tag = button.tag.toString()

//        val myIntent = Intent(activity, DetailsActivity::class.java)
//        myIntent.putExtra("pokemon_name", tag)
//        startActivity(myIntent)
    }
}