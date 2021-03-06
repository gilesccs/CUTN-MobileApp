package sg.edu.smu.cs461.cutn_mobileapp

import android.app.Activity
import android.content.Intent
import android.graphics.PorterDuff
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.LinearLayoutManager
import io.paperdb.Paper
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_shopping_cart.*
import sg.edu.smu.cs461.cutn_mobileapp.R
import sg.edu.smu.cs461.cutn_mobileapp.ShoppingCart
import sg.edu.smu.cs461.cutn_mobileapp.ShoppingCartAdapter
import www.sanju.motiontoast.MotionToast
import java.text.DecimalFormat
import java.util.concurrent.TimeUnit
import kotlin.properties.Delegates

class ShoppingCartActivity : AppCompatActivity(), OnCartItemClickListener {
    lateinit var adapter: ShoppingCartAdapter
    var isVoucherApplied = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shopping_cart)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val upArrow = ContextCompat.getDrawable(this, R.drawable.abc_ic_ab_back_material)
        upArrow?.setColorFilter(
            ContextCompat.getColor(this, R.color.colorPrimary),
            PorterDuff.Mode.SRC_ATOP
        )
        supportActionBar?.setHomeAsUpIndicator(upArrow)

        adapter = ShoppingCartAdapter(this, ShoppingCart.getCart(), this)
        adapter.notifyDataSetChanged()

        shopping_cart_recyclerView.adapter = adapter
        shopping_cart_recyclerView.layoutManager = LinearLayoutManager(this)

        var totalPrice = ShoppingCart.getCart()
            .fold(0.toDouble()) { acc, cartItem -> acc + cartItem.quantity.times(cartItem.product.price!!.toDouble()) }

        val dec = DecimalFormat("##0.00")
        val totalPriceString = dec.format(totalPrice)

        total_price.text = "$${totalPriceString}"

        // Voucher check
        voucherCode.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {
                if (s.length == 8) {
                    if (s.substring(2, 3).toLowerCase() != "e") {
                        MotionToast.darkToast(
                            this@ShoppingCartActivity,
                            "Voucher invalid!",
                            "You have entered an invalid voucher!",
                            MotionToast.TOAST_ERROR,
                            MotionToast.GRAVITY_BOTTOM,
                            MotionToast.LONG_DURATION,
                            ResourcesCompat.getFont(
                                this@ShoppingCartActivity,
                                R.font.helvetica_regular
                            )
                        )
                        return
                    } else if (!isVoucherApplied) {
                        MotionToast.darkToast(
                            this@ShoppingCartActivity,
                            "Voucher code applied!",
                            "Your voucher has been applied for a discount",
                            MotionToast.TOAST_SUCCESS,
                            MotionToast.GRAVITY_BOTTOM,
                            MotionToast.LONG_DURATION,
                            ResourcesCompat.getFont(
                                this@ShoppingCartActivity,
                                R.font.helvetica_regular
                            )
                        )
                        isVoucherApplied = true
                        var totalPrice = ShoppingCart.getCart()
                            .fold(0.toDouble()) { acc, cartItem ->
                                acc + cartItem.quantity.times(
                                    cartItem.product.price!!.toDouble()
                                )
                            } * 0.90

                        val dec = DecimalFormat("##0.00")
                        val totalPriceString = dec.format(totalPrice)

                        total_price.text = "$${totalPriceString}"
                    }
                }
            }

            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int
            ) {
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item?.itemId) {
            android.R.id.home -> {
                onBackPressed()
            }
        }

        return super.onOptionsItemSelected(item!!)
    }

    fun checkout(view: View) {
        val voucher = voucherCode.text
        // Voucher is entered but is invalid
        if (voucher.isNotEmpty() && (voucher.length != 8 || voucher.substring(2, 3)
                .toLowerCase() != "e")
        ) {
            MotionToast.darkToast(
                this@ShoppingCartActivity,
                "Voucher invalid!",
                "Please remove or edit your voucher code.",
                MotionToast.TOAST_ERROR,
                MotionToast.GRAVITY_BOTTOM,
                MotionToast.LONG_DURATION,
                ResourcesCompat.getFont(this@ShoppingCartActivity, R.font.helvetica_regular)
            )
            return
        }
        // if shopping cart is empty
        if (ShoppingCart.getCart().size == 0) {
            MotionToast.darkToast(
                this@ShoppingCartActivity,
                "No items in cart!",
                "Please add more items before checking out.",
                MotionToast.TOAST_ERROR,
                MotionToast.GRAVITY_BOTTOM,
                MotionToast.LONG_DURATION,
                ResourcesCompat.getFont(this@ShoppingCartActivity, R.font.helvetica_regular)
            )
        }
        // to apply voucher if one is specified
        if (!isVoucherApplied && voucher.isNotEmpty()) {
            MotionToast.darkToast(
                this@ShoppingCartActivity,
                "Voucher code applied!",
                "Your voucher has been applied for a discount",
                MotionToast.TOAST_SUCCESS,
                MotionToast.GRAVITY_BOTTOM,
                MotionToast.LONG_DURATION,
                ResourcesCompat.getFont(this@ShoppingCartActivity, R.font.helvetica_regular)
            )
            isVoucherApplied = true
            var totalPrice = ShoppingCart.getCart()
                .fold(0.toDouble()) { acc, cartItem ->
                    acc + cartItem.quantity.times(
                        cartItem.product.price!!.toDouble()
                    )
                } * 0.90

            val dec = DecimalFormat("##0.00")
            val totalPriceString = dec.format(totalPrice)
            total_price.text = "$${totalPriceString}"

        }

        MotionToast.darkToast(
            this@ShoppingCartActivity,
            "Items checked out!",
            "You have successfully checked out your cart.",
            MotionToast.TOAST_SUCCESS,
            MotionToast.GRAVITY_BOTTOM,
            MotionToast.LONG_DURATION,
            ResourcesCompat.getFont(this@ShoppingCartActivity, R.font.helvetica_regular)
        )
        TimeUnit.SECONDS.sleep(1L)
        Paper.book().destroy();
        voucherCode.text.clear()
        startActivity(Intent(this, MainActivity::class.java))
    }

    override fun onItemClick(item: CartItem, position: Int) {
//        Toast.makeText(this, "Removed!", Toast.LENGTH_SHORT)
        ShoppingCart.removeItem(item, this)
        adapter.notifyDataSetChanged()
        recreate()
    }
}