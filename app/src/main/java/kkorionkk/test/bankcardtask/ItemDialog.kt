package kkorionkk.test.bankcardtask

import android.annotation.SuppressLint
import android.app.Activity
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.google.gson.Gson

class ItemDialog: Activity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.item_dialog)

        val prefs: SharedPreferences = applicationContext.getSharedPreferences("items", MODE_PRIVATE)
        val state: String? = prefs.getString("State", "{}")
        val gson = Gson()
        val json: String? = prefs.getString(state, "{}")
        val obj: Info = gson.fromJson(json, Info::class.java)

        val header: Button = findViewById(R.id.dialog_header)
        header.text = state
        header.setOnClickListener {
            finish()
        }

        val dialogScheme: TextView = findViewById(R.id.dialog_scheme)
        dialogScheme.text = obj.scheme.toString().uppercase()

        val dialogBrand: TextView = findViewById(R.id.dialog_brand)
        dialogBrand.text = obj.brand

        val dialogNumberLength: TextView = findViewById(R.id.dialog_number_length)
        val dialogNumberLuhn: TextView = findViewById(R.id.dialog_number_luhn)
        dialogNumberLength.text = "Length: " + obj.number?.length.toString()
        dialogNumberLuhn.text = "Luhn: " + obj.number?.luhn.toString()

        val dialogType: TextView = findViewById(R.id.dialog_type)
        dialogType.text = obj.type

        val dialogPrepaid: TextView = findViewById(R.id.dialog_prepaid)
        dialogPrepaid.text = obj.prepaid.toString()

        val dialogCountry: TextView = findViewById(R.id.dialog_country)
        val string: String = obj.country?.emoji + " " + obj.country?.name.toString()
        dialogCountry.text = string

        val dialogBank: TextView = findViewById(R.id.dialog_bank)
        val dialogBankNumber: TextView = findViewById(R.id.dialog_bank_number)
        val dialogBankCity: TextView = findViewById(R.id.dialog_bank_city)
        val dialogBankUrl: TextView = findViewById(R.id.dialog_bank_url)
        dialogBank.text = obj.bank?.name
        dialogBankNumber.text = obj.bank?.phone
        dialogBankCity.text = obj.bank?.city
        dialogBankUrl.text = obj.bank?.url


    }
}