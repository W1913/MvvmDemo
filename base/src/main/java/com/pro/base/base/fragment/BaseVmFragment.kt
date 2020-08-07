package com.pro.base.base.fragment

import android.content.Context
import android.os.Bundle
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.util.forEach
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.pro.base.base.viewmodel.BaseViewModel
import com.pro.base.ext.getVmClazz
import com.pro.base.network.manager.NetState
import com.pro.base.network.manager.NetworkStateManager

/**
 * Created　: wjh
 * on      : 2020/7/17
 * description　: ViewModelFragment基类，自动把ViewModel注入Fragment
 */
abstract class BaseVmFragment<VM : BaseViewModel, DB : ViewDataBinding> : Fragment() {

    //是否第一次加载
    private var isFirst: Boolean = true

    lateinit var mDatabind: DB

    lateinit var mViewModel: VM

    lateinit var mActivity: AppCompatActivity

    abstract fun getBindingVariable(): Int
    abstract fun getBindingClickVariable(): SparseArray<Any>?

    /**
     * 当前Fragment绑定的视图布局
     */
    abstract fun layoutId(): Int

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mViewModel = createViewModel()
        mDatabind = DataBindingUtil.inflate(inflater, layoutId(), container, false)
        mDatabind.lifecycleOwner = this
        if (getBindingVariable() > 0) {
            mDatabind.setVariable(getBindingVariable(), mViewModel)
        }
        if (getBindingClickVariable()?.size()!! > 0) {
            val bindingClickVariable = getBindingClickVariable()
            bindingClickVariable!!.forEach<Any> { i: Int, any: Any ->
                mDatabind.setVariable(i, any)
            }
        }
        mDatabind.executePendingBindings()

        return mDatabind.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mActivity = context as AppCompatActivity
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isFirst = true

        initView(savedInstanceState)
        createObserver()
        onVisible()
        registorDefUIChange()
        initData()
    }

    /**
     * 网络变化监听 子类重写
     */
    open fun onNetworkStateChanged(netState: NetState) {}

    /**
     * 创建viewModel
     */
    private fun createViewModel(): VM {
        return ViewModelProvider(this).get(getVmClazz(this))
    }

    /**
     * 初始化view
     */
    abstract fun initView(savedInstanceState: Bundle?)

    /**
     * 懒加载
     */
    abstract fun lazyLoadData()

    /**
     * 创建观察者
     */
    abstract fun createObserver()

    override fun onResume() {
        super.onResume()
        onVisible()
    }

    /**
     * 是否需要懒加载
     */
    private fun onVisible() {
        if (lifecycle.currentState == Lifecycle.State.STARTED && isFirst) {
            lazyLoadData()
            //在Fragment中，只有懒加载过了才能开启网络变化监听
            NetworkStateManager.instance.mNetworkStateCallback.observe(
                viewLifecycleOwner,
                Observer {
                    //不是首次订阅时调用方法，防止数据第一次监听错误
                    if (!isFirst) {
                        onNetworkStateChanged(it)
                    }
                })
            isFirst = false
        }
    }

    /**
     * Fragment执行onCreate后触发的方法
     */
    open fun initData() {}


    abstract fun showLoading(message: String = "请求网络中...")

    abstract fun dismissLoading()


    /**
     * 注册 UI 事件
     */
    private fun registorDefUIChange() {
        mViewModel.loadingChange.showDialog.observe(viewLifecycleOwner, Observer {
            showLoading()
        })
        mViewModel.loadingChange.dismissDialog.observe(viewLifecycleOwner, Observer {
            dismissLoading()
        })
    }

}