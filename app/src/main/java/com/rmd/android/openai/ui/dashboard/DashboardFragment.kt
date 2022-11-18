package com.rmd.android.openai.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.rmd.android.openai.R
import com.rmd.android.openai.databinding.FragmentDashboardBinding
import com.rmd.android.openai.model.Menu

class DashboardFragment : Fragment() {

    private lateinit var binding: FragmentDashboardBinding
    private lateinit var menuAdapter: MenuAdapter

    private var menuList = ArrayList<Menu>()
    var url =
        "https://png.pngtree.com/png-clipart/20190520/original/pngtree-3d-earth-render-01-png-image_3536341.jpg"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_dashboard, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //initializations
        binding = FragmentDashboardBinding.bind(view)

        menuList.add(Menu("Advanced tweet classifier", url))
        menuList.add(Menu("English to other languages", url))
        menuList.add(Menu("Classification", url))
        menuList.add(Menu("SQL translate", url))
        menuList.add(Menu("Friend chat", url))
        menuList.add(Menu("Interview questions", url))
        menuList.add(Menu("DALL.E Image Generation", url))


        menuAdapter = MenuAdapter(menuList, requireContext())
        binding.menuGv.adapter = menuAdapter

        binding.menuGv.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            when (position) {
                0 -> NavHostFragment.findNavController(this)
                    .navigate(R.id.action_navigation_dashboard_to_navigation_menu_atc)
                6 -> NavHostFragment.findNavController(this)
                    .navigate(R.id.action_navigation_dashboard_to_navigation_menu_dalle_ig)
            }


        }


    }
}