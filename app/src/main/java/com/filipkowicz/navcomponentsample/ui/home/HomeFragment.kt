package com.filipkowicz.navcomponentsample.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.filipkowicz.navcomponentsample.R
import com.filipkowicz.navcomponentsample.databinding.FragmentHomeBinding
import com.filipkowicz.navcomponentsample.ui.dashboard.DashboardViewModel
import com.filipkowicz.navcomponentsample.ui.dashboardcommon.DashboardCommonViewModel

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
                ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textHome
        homeViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        binding.detailsButton.setOnClickListener {
//            findNavController().navigate(R.id.action_navigation_home_to_nested_graph, bundleOf(
//                DashboardCommonViewModel.ARG to "arg",
//                DashboardViewModel.ARG to "external arg"
//            ))

            findNavController().handleDeepLink( findNavController().createDeepLink()
                .setGraph(R.navigation.mobile_navigation)
                .addDestination(R.id.navigation_home)
                .addDestination(R.id.nested_graph, bundleOf(DashboardCommonViewModel.ARG to "xxx", DashboardViewModel.ARG to "ooo"))
                .addDestination(R.id.navigation_dashboard_details, bundleOf(DashboardCommonViewModel.ARG to "yyy", DashboardViewModel.ARG to "eee"))
                .createTaskStackBuilder().getIntent(0))

        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}