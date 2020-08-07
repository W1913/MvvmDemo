package com.kaola.home

import android.os.Bundle
import android.util.SparseArray
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.kaola.home.databinding.FragmentHomeChildBinding
import com.pro.base.base.fragment.BaseVmFragment
import kotlinx.android.synthetic.main.fragment_home_child.*
import kotlinx.coroutines.*


/**
 * @author wjh
 * on      : 2020/7/29
 * description　:
 */
class HomeChildFragment : BaseVmFragment<HomeChildViewModel, FragmentHomeChildBinding>() {
    companion object {
        fun newInstance(cid: Int, isNew: Boolean): HomeChildFragment {
            val args = Bundle()
            args.putInt("cid", cid)
            val fragment = HomeChildFragment()
            fragment.arguments = args
            return fragment
        }
    }

    private val adapter: HomeChildAdapter by lazy { HomeChildAdapter(mutableListOf()) }

    override fun layoutId(): Int = R.layout.fragment_home_child

    override fun initView(savedInstanceState: Bundle?) {
        var list = mutableListOf<String>()

        rv.layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false);
        rv.adapter = adapter
        val scope = CoroutineScope(Dispatchers.Main)
        scope.launch(Dispatchers.Main) {

            withContext(Dispatchers.IO) {
                //这里模拟网络请求
                delay(2000)
                list.add("1111")
                list.add("22")
                list.add("33")
                list.add("4")
                list.add("5")
                list.add("6")
                list.add("7")
                list.add("9")
                list.add("13")
                list.add("12")
                list.add("ed")
                list.add("rf")
                mViewModel.listData.postValue(list)
            }
        }
    }

    override fun lazyLoadData() {
        mViewModel.listData.observe(viewLifecycleOwner, Observer {
            adapter.addData(it)
        })
    }

    override fun createObserver() {
    }

    override fun showLoading(message: String) {
    }

    override fun dismissLoading() {
    }

    override fun getBindingVariable(): Int = BR.viewModel

    override fun getBindingClickVariable(): SparseArray<Any>? = SparseArray(0)
}