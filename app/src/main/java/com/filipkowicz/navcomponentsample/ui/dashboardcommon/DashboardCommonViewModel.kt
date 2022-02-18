package com.filipkowicz.navcomponentsample.ui.dashboardcommon

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

class DashboardCommonViewModel(
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Common data: " + savedStateHandle.get(ARG)
    }
    val text: LiveData<String> = _text

    companion object {
        const val ARG = "common_arg"
    }
}