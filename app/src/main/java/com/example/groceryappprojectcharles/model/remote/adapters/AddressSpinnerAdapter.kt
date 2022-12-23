package com.example.groceryappprojectcharles.model.remote.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.groceryappprojectcharles.R
import com.example.groceryappprojectcharles.model.local.entity.Address

class AddressSpinnerAdapter(private val context: Context, private val addressList: List<Address>) : BaseAdapter() {
    override fun getCount() = addressList.size

    override fun getItem(p0: Int) = addressList[p0]

    override fun getItemId(p0: Int) = p0.toLong()

    override fun getView(position: Int, convertView: View?, viewGroup: ViewGroup?): View {
        var convView: View? = convertView
        if (convView == null) {
            convView = LayoutInflater.from(context)
                .inflate(R.layout.address_item, viewGroup, false)
        }
        val address = getItem(position)
        convView?.apply {
            val nickname = findViewById<TextView>(R.id.txtNickname)
            val houseNo = findViewById<TextView>(R.id.txtHouseNo)
            val street = findViewById<TextView>(R.id.txtStreet)
            val state = findViewById<TextView>(R.id.txtState)
            val city = findViewById<TextView>(R.id.txtCity)
            val zip = findViewById<TextView>(R.id.txtZip)

            address.apply {
                nickname.text = address.nickname
                houseNo.text = address.houseNo
                street.text = address.street
                state.text = address.state
                city.text = address.city
                zip.text = address.zip.toString()
            }
        }
        return convView!!
    }
}