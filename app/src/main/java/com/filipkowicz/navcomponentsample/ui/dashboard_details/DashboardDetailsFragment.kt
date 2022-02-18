package com.filipkowicz.navcomponentsample.ui.dashboard_details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.navGraphViewModels
import com.filipkowicz.navcomponentsample.R
import com.filipkowicz.navcomponentsample.databinding.FragmentDashboardDetailsBinding
import com.filipkowicz.navcomponentsample.ui.dashboard.DashboardDetailsViewModel
import com.filipkowicz.navcomponentsample.ui.dashboardcommon.DashboardCommonViewModel

class DashboardDetailsFragment : Fragment() {

    private var _binding: FragmentDashboardDetailsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val commonViewModel: DashboardCommonViewModel by navGraphViewModels(R.id.nested_graph)

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        val dashboardViewModel =
            ViewModelProvider(this)[DashboardDetailsViewModel::class.java]

        _binding = FragmentDashboardDetailsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textDashboard
        dashboardViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }

        commonViewModel.text.observe(viewLifecycleOwner) {
            binding.textCommonDashboard.text = it
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}