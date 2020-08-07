package com.pro.base.callback.databind

import androidx.databinding.ObservableField

/**
 * @author wjh
 * on      : 2020/7/17
 * description　:自定义的 Byte 类型 ObservableField  提供了默认值，避免取值的时候还要判空
 */
class ByteObservableField(value: Byte = 0) : ObservableField<Byte>(value) {

    override fun get(): Byte {
        return super.get()!!
    }

}