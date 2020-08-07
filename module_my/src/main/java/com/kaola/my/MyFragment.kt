package com.kaola.my

import android.os.Bundle
import android.util.SparseArray
import com.alibaba.android.arouter.facade.annotation.Route
import com.kaola.my.databinding.FragmentMyBinding
import com.pro.base.base.fragment.BaseVmFragment
import com.pro.base.router.RouterFragmentPath

/**
 * @author wjh
 * on      : 2020/7/24
 * description　:
 */
@Route(path = RouterFragmentPath.My.PAGER_MY)
class MyFragment : BaseVmFragment<MyViewModel, FragmentMyBinding>() {
    override fun layoutId(): Int = R.layout.fragment_my

    override fun initView(savedInstanceState: Bundle?) {
    }

    override fun lazyLoadData() {
    }

    override fun createObserver() {
    }

    override fun showLoading(message: String) {
    }

    override fun dismissLoading() {
    }

    override fun getBindingVariable(): Int  = 0

    override fun getBindingClickVariable(): SparseArray<Any>? = SparseArray(0)
}