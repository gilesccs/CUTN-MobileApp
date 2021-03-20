package sg.edu.smu.cs461.cutn_mobileapp

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.Fragment


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
        viewRewards(this)
    }

    private fun viewRewards(view: fragment_homepage) {
        val rewardsBtn = activity?.findViewById<ImageButton>(R.id.rewards)
        rewardsBtn?.setOnClickListener{
            val it = Intent(activity, Rewards::class.java)
            startActivityForResult(it, 4321)
        }
    }

    override fun onClick(v: View?) {

    }


}