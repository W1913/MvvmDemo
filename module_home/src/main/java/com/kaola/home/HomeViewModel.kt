package com.kaola.home

import androidx.lifecycle.MutableLiveData
import com.pro.base.base.viewmodel.BaseViewModel
import com.pro.base.bean.BannerBean
import com.pro.base.ext.request
import com.pro.base.network.apiService
import com.pro.base.state.ResultState


class HomeViewModel : BaseViewModel(){
    //首页轮播图数据
    var bannerData: MutableLiveData<ResultState<ArrayList<BannerBean>>> = MutableLiveData()


    /**
     * 获取轮播图数据
     */
    fun getBannerData() {
        request({ apiService.getBanner() }, bannerData)
    }
}