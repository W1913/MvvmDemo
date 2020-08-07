package com.kaola.home

import androidx.lifecycle.MutableLiveData
import com.pro.base.base.viewmodel.BaseViewModel

class HomeChildViewModel : BaseViewModel(){
    var listData: MutableLiveData<MutableList<String>> = MutableLiveData()
}