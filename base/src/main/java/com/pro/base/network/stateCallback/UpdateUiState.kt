package com.pro.base.network.stateCallback

/**
 * @author wjh
 * on      : 2020/7/29
 * description　:操作数据的状态类
 */
data class UpdateUiState<T>(
    //请求是否成功
    var isSuccess: Boolean = true,
    //操作的对象
    var data: T? = null,
    //请求失败的错误信息
    var errorMsg: String = ""
)