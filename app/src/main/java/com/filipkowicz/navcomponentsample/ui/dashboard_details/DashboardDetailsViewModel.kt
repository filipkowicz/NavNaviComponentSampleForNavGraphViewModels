package com.filipkowicz.navcomponentsample.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

class DashboardDetailsViewModel(
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is dashboard details Fragment" + savedStateHandle.get(ARG)
    }
    val text: LiveData<String> = _text

    companion object {
        const val ARG = "dashboard_arg"
    }
}