package com.pro.base.network.manager

import com.pro.base.callback.livedata.UnPeekLiveData

/**
 * @author wjh
 * 时间　: 2020/5/2
 * description　: 网络变化管理者
 */
class NetworkStateManager private constructor() {

    val mNetworkStateCallback = UnPeekLiveData<NetState>()

    companion object {
        val instance: NetworkStateManager by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            NetworkStateManager()
        }
    }
}