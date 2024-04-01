package org.d3if0003.assesment1

import android.content.Intent
import android.os.Bundle
import android.os.Message
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.text.NumberFormat
import androidx.appcompat.app.AppCompatActivity
import org.d3if0003.assesment1.navigation.InformationActivity


class MainActivity2 : AppCompatActivity() {
    var quantity = 0
    var price = 0
    var name = ""
    var haswhippedcream = false
    var haschocolate = false

     override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnShare = findViewById<Button>(R.id.btn_share)
        btnShare.setOnClickListener {
            btnShare.setOnClickListener {
                val pricemessage = createOrderSummary(price, name, haswhippedcream, haschocolate)
                shareOrderSummary(pricemessage)
            }

        }
        imageView = findViewById(R.id.image_view)
        imageView.visibility = View.GONE

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_info -> {
                startActivity(Intent(this, InformationActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


    fun goToInformationPage(view: View?) {
        val intent = Intent(this, InformationActivity::class.java)
        startActivity(intent)
    }


    fun shareOrderSummary(pricemessage: String) {
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.type = "text/plain"
        shareIntent.putExtra(Intent.EXTRA_TEXT, pricemessage)
        startActivity(Intent.createChooser(shareIntent, "Share Order Summary"))
    }


    fun increment(view: View?) {
        if (quantity == 100) {
            Toast.makeText(this, "pesanan maximal 100", Toast.LENGTH_SHORT).show()
            return
        }
        quantity = quantity + 1
        display(quantity)
    }

    fun decrement(view: View?){
        if(quantity == 1){
            Toast.makeText(this, "pesanan minimal 1", Toast.LENGTH_SHORT).show()
            return
        }
        quantity = quantity - 1
        display(quantity)
    }

    fun Submitorder(view: View?){
        val nameEditText = findViewById(R.id.edt_name) as EditText
        name = nameEditText.getText().toString()
        val quantityTextView = findViewById(R.id.quantity_textview) as TextView
        val quantityStr = quantityTextView.text.toString().trim()

        if (name.isEmpty() || quantityStr.isEmpty()) {
            Toast.makeText(this, "Ada data yang belum di isi", Toast.LENGTH_SHORT).show()
            return
        }
        quantity = quantityStr.toInt()

        Log.v("MainActivity", "Nama: $name")
        val whippedcreamChekBox = findViewById(R.id.WhippedCream_checkbox) as CheckBox
        haswhippedcream = whippedcreamChekBox.isChecked
        Log.v("MainActivity", "has whippedcream: $haswhippedcream")
        val chocolateChekBox = findViewById(R.id.Choclate_checkbox) as CheckBox
        haschocolate = chocolateChekBox.isChecked
        Log.v("MainActivity", "has whippedcream: $haschocolate")
        price = calculateprice(haswhippedcream, haschocolate)
        val pricemessage = createOrderSummary(price, name, haswhippedcream, haschocolate)
        displayMessage(pricemessage)
        imageView.setImageResource(R.drawable.kopi)
        imageView.visibility = View.VISIBLE

        val btnShare = findViewById<Button>(R.id.btn_share)
        if (pricemessage.isNotEmpty()){
            btnShare.visibility = View.VISIBLE
        } else {
            btnShare.visibility = View.GONE
        }
    }



    private fun calculateprice(
        addwhipedcream : Boolean,
        addchocolate : Boolean
    ): Int {
        var harga = 5000
        if (addwhipedcream){
            harga = harga + 1000
        }
        if (addchocolate){
            harga = harga + 2000
        }
        return quantity * harga
    }

    private fun createOrderSummary(
        price: Int,
        name: String,
        addchocolate: Boolean,
        addwhipedcream: Boolean
    ): String {
        var pricemessage = "Nama = $name"
        pricemessage += "\n Tambahkan Coklat = $addwhipedcream"
        pricemessage += "\n Tambahkan krim = $addchocolate"
        pricemessage += "\n Jumlah Pemesanan = $quantity"
        pricemessage += "\n Total = Rp $price"
        pricemessage += "\n Terimakasih"
        return pricemessage
    }

    private lateinit var imageView: ImageView

    private fun displayMessage(message: String){
        val priceTextView = findViewById(R.id.price_textview) as TextView
        priceTextView.text= message
    }

    private fun display(number: Int) {
        val quantityTextView = findViewById(R.id.quantity_textview) as TextView
        quantityTextView.text ="" +number
    }

    private fun displayPrice(number: Int){
        val priceTextView = findViewById<View>(R.id.price_textview) as TextView
        priceTextView.text = NumberFormat.getCurrencyInstance().format(number.toLong())
    }


}
