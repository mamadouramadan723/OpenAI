package com.rmd.android.openai.ui.dashboard

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.rmd.android.openai.R
import com.rmd.android.openai.databinding.MenugrvItemBinding
import com.rmd.android.openai.model.Menu
import com.squareup.picasso.Picasso

internal class MenuAdapter(
    private val menuList: List<Menu>,
    private val context: Context
) : BaseAdapter() {

    private var layoutInflater: LayoutInflater? = null

    private lateinit var binding: MenugrvItemBinding

    override fun getCount(): Int {
        return menuList.size
    }

    override fun getItem(position: Int): Any? {
        return null
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var convertVw = convertView


        if (layoutInflater == null) {
            layoutInflater =
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        }

        if (convertVw == null) {
            // on below line we are passing the layout file
            // which we have to inflate for each item of grid view.
            convertVw = layoutInflater!!.inflate(R.layout.menugrv_item, null)
        }

        binding = MenugrvItemBinding.bind(convertVw!!)
        binding.menuNameTv.text = menuList[position].menuName
        Picasso.get()
            .load(menuList[position].menuImg).into(binding.menuImgVw)

        return convertVw
    }
}