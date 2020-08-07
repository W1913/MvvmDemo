package com.pro.base.callback.livedata

import androidx.lifecycle.MutableLiveData


/**
 * @author wjh
 * on      : 2020/7/17
 * description　:自定义的Boolean类型 MutableLiveData 提供了默认值，避免取值的时候还要判空
 */
class BooleanLiveData : MutableLiveData<Boolean>() {

    override fun getValue(): Boolean {
        return super.getValue() ?: false
    }
}

