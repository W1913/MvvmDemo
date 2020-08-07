package com.pro.base.callback.livedata

import androidx.lifecycle.MutableLiveData

/**
 * @author wjh
 * on      : 2020/7/17
 * description　:自定义的Double类型 MutableLiveData 提供了默认值，避免取值的时候还要判空
 */
class StringLiveData : MutableLiveData<String>() {

    override fun getValue(): String {
        return super.getValue() ?: ""
    }

}