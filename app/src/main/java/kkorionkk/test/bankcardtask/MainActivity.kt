package kkorionkk.test.bankcardtask

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import java.net.HttpURLConnection
import java.net.URL
import kotlin.concurrent.thread
import org.json.JSONTokener
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    private var state: String = ""
    private var historyList = mutableListOf<Info>()

    @SuppressLint("CommitPrefEdits")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val prefs: SharedPreferences = applicationContext.getSharedPreferences("items", MODE_PRIVATE)

        val input : TextView = findViewById(R.id.bin_input_text)
        val binSendButton: Button = findViewById(R.id.bin_send_button)
        val queryHistory: RecyclerView = findViewById(R.id.query_list)

        binSendButton.setOnClickListener {
            val text = input.text.toString()
            state = text
            var response: Info

            thread {
                response = getInfo(text)
                this.runOnUiThread(Runnable {
                    val prefsEditor: SharedPreferences.Editor = prefs.edit()
                    val gson = Gson()

                    historyList.add(response)
                    queryHistory.layoutManager = LinearLayoutManager(applicationContext)
                    queryHistory.adapter = QueryHistoryAdapter(historyList)


                    val json: String = gson.toJson(response)
                    prefsEditor.putString(text, json)
                    prefsEditor.putString("State", text)
                    prefsEditor.apply()


                    val intent = Intent(this, ItemDialog::class.java)

                    startActivity(intent)
                })
            }

        }
    }
    private fun getInfo(number: String): Info {
        val string = "https://lookup.binlist.net/$number"
        val url = URL(string)

        var ext = ""
        val info = Info()
        val number = kkorionkk.test.bankcardtask.Number()
        val country = Country()
        val bank = Bank()

        with(url.openConnection() as HttpURLConnection) {
            requestMethod = "GET"

            inputStream.bufferedReader().use {
                it.lines().forEach(){ line -> ext += line }
            }

            val jsonObject = JSONTokener(ext).nextValue() as JSONObject
            if (jsonObject.has("number")) {
                if (jsonObject.getJSONObject("number").has("length")) {
                    number.length = jsonObject.getJSONObject("number").getInt("length")
                }
                if (jsonObject.getJSONObject("number").has("luhn")) {
                    number.luhn = jsonObject.getJSONObject("number").getBoolean("luhn")
                }
            }
            info.number = number
            if (jsonObject.has("scheme")) {
                info.scheme = jsonObject.getString("scheme")
            }
            if (jsonObject.has("type")) {
                info.type = jsonObject.getString("type")
            }
            if (jsonObject.has("brand")) {
                info.brand = jsonObject.getString("brand")
            }
            if (jsonObject.has("prepaid")) {
                info.prepaid = jsonObject.getBoolean("prepaid")
            }
            if (jsonObject.has("country")) {
                if (jsonObject.getJSONObject("country").has("numeric")) {
                    country.numeric = jsonObject.getJSONObject("country").getString("numeric")
                }
                if (jsonObject.getJSONObject("country").has("alpha2")) {
                    country.alpha2 = jsonObject.getJSONObject("country").getString("alpha2")
                }
                if (jsonObject.getJSONObject("country").has("name")) {
                    country.name = jsonObject.getJSONObject("country").getString("name")
                }
                if (jsonObject.getJSONObject("country").has("emoji")) {
                    country.emoji = jsonObject.getJSONObject("country").getString("emoji")
                }
                if (jsonObject.getJSONObject("country").has("currency")) {
                    country.currency = jsonObject.getJSONObject("country").getString("currency")
                }
                if (jsonObject.getJSONObject("country").has("latitude")) {
                    country.latitude = jsonObject.getJSONObject("country").getInt("latitude")
                }
                if (jsonObject.getJSONObject("country").has("longitude")) {
                    country.longitudeL = jsonObject.getJSONObject("country").getInt("longitude")
                }

            }
            info.country = country
            if (jsonObject.has("bank")) {
                if (jsonObject.getJSONObject("bank").has("name")) {
                    bank.name = jsonObject.getJSONObject("bank").getString("name")
                }
                if (jsonObject.getJSONObject("bank").has("url")) {
                    bank.url = jsonObject.getJSONObject("bank").getString("url")
                }
                if (jsonObject.getJSONObject("bank").has("phone")) {
                    bank.phone = jsonObject.getJSONObject("bank").getString("phone")
                }
                if (jsonObject.getJSONObject("bank").has("city")) {
                    bank.city = jsonObject.getJSONObject("bank").getString("city")
                }
            }
            info.bank = bank
        }
        return info
    }


}