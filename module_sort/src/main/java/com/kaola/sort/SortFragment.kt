package com.kaola.sort

import android.os.Bundle
import android.util.SparseArray
import com.alibaba.android.arouter.facade.annotation.Route
import com.kaola.sort.databinding.FragmentSortBinding
import com.pro.base.base.fragment.BaseVmFragment
import com.pro.base.router.RouterFragmentPath

/**
 * @author wjh
 * on      : 2020/7/24
 * description　:
 */
@Route(path = RouterFragmentPath.Sort.PAGER_SORT)
class SortFragment : BaseVmFragment<SortViewModel, FragmentSortBinding>() {
    override fun layoutId(): Int = R.layout.fragment_sort

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