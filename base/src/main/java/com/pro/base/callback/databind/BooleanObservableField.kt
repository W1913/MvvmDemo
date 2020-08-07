package com.pro.base.callback.databind

import androidx.databinding.ObservableField

/**
 * @author wjh
 * on      : 2020/7/17
 * description　: 自定义的Boolean类型ObservableField 提供了默认值，避免取值的时候还要判空
 */
class BooleanObservableField(value: Boolean = false) : ObservableField<Boolean>(value) {
    override fun get(): Boolean {
        return super.get()!!
    }

}