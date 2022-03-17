package com.filipkowicz.navcomponentsample.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavOptions
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.filipkowicz.navcomponentsample.R
import com.filipkowicz.navcomponentsample.databinding.FragmentDashboardBinding
import com.filipkowicz.navcomponentsample.databinding.FragmentDupaBinding
import com.filipkowicz.navcomponentsample.ui.dashboard.DashboardFragmentDirections
import com.filipkowicz.navcomponentsample.ui.dashboard.DashboardViewModel
import com.filipkowicz.navcomponentsample.ui.dashboardcommon.DashboardCommonViewModel

class DupaFragment : Fragment() {

    private var _binding: FragmentDupaBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {



        _binding = FragmentDupaBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.detailsButton.setOnClickListener {
            findNavController().navigate(
                R.id.navigation_home,
                null,
                NavOptions
                    .Builder()
                    .setPopUpTo(R.id.mobile_navigation, false)
                    .build()
            )
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}