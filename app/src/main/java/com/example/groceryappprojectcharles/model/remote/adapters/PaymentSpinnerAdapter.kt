package com.example.groceryappprojectcharles.model.remote.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.groceryappprojectcharles.R
import com.example.groceryappprojectcharles.model.local.entity.Payment

class PaymentSpinnerAdapter(private val context: Context, private val paymentList: List<Payment>) : BaseAdapter() {
    override fun getCount() = paymentList.size

    override fun getItem(p0: Int) = paymentList[p0]

    override fun getItemId(p0: Int) = p0.toLong()

    @SuppressLint("SetTextI18n")
    override fun getView(position: Int, convertView: View?, viewGroup: ViewGroup?): View {
        var convView: View? = convertView
        if (convView == null) {
            convView = LayoutInflater.from(context)
                .inflate(R.layout.payment_item, viewGroup, false)
        }
        val payment = getItem(position)
        convView?.apply {
            val nickname = findViewById<TextView>(R.id.txtNickname)
            val cardNumber = findViewById<TextView>(R.id.txtCardNumber)
            val expDate = findViewById<TextView>(R.id.txtExpDate)

            payment.apply {
                nickname.text = payment.nickname
                cardNumber.text = "Card Ending in: *${ payment.cardNumber.substring(payment.cardNumber.length-5, payment.cardNumber.length-1) }"
                expDate.text = payment.expDate
            }
        }
        return convView!!
    }
}