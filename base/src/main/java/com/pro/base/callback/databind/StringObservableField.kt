package com.pro.base.callback.databind

import androidx.databinding.ObservableField

/**
 * @author wjh
 * on      : 2020/7/17
 * description　:自定义的String类型 ObservableField  提供了默认值，避免取值的时候还要判空
 */
class StringObservableField(value: String = "") : ObservableField<String>(value) {

    override fun get(): String {
        return super.get()!!
    }

}