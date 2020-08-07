package com.pro.base.base.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.pro.base.base.viewmodel.BaseViewModel
import com.pro.base.ext.getVmClazz
import com.pro.base.network.manager.NetState
import com.pro.base.network.manager.NetworkStateManager
import com.umeng.analytics.MobclickAgent
import com.umeng.message.PushAgent
import android.util.SparseArray as SparseArray1
import androidx.core.util.forEach as forEach1

/**
 * @author wjh
 * on      : 2020/7/17
 * description　: ViewModelActivity基类，把ViewModel注入进来了
 */
abstract class BaseVmActivity<VM : BaseViewModel, DB : ViewDataBinding> : AppCompatActivity() {

    lateinit var mViewModel: VM

    lateinit var mDatabind: DB

    abstract fun layoutId(): Int

    abstract fun initView(savedInstanceState: Bundle?)

    abstract fun showLoading(message: String = "请求网络中...")

    abstract fun dismissLoading()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        PushAgent.getInstance(this).onAppStart();
        initDataBind()
        init(savedInstanceState)
    }

    private fun init(savedInstanceState: Bundle?) {
        registerUiChange()
        initView(savedInstanceState)
        createObserver()
        NetworkStateManager.instance.mNetworkStateCallback.observe(this, Observer {
            onNetworkStateChanged(it)
        })
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
     * 创建LiveData数据观察者
     */
    abstract fun createObserver()

    /**
     * 注册 UI 事件
     */
    private fun registerUiChange() {
        //显示弹窗
        mViewModel.loadingChange.showDialog.observe(this, Observer {
            showLoading(
                if (it.isEmpty()) {
                    "请求网络中..."
                } else it
            )
        })
        //关闭弹窗
        mViewModel.loadingChange.dismissDialog.observe(this, Observer {
            dismissLoading()
        })
    }

    private val bindingParams: SparseArray1<Any> = SparseArray1<Any>()

    abstract fun getBindingVariable(): Int
    abstract fun getBindingClickVariable(): SparseArray1<Any>?

    /**
     * 创建DataBinding
     */
    private fun initDataBind() {
        mViewModel = createViewModel()
        mDatabind = DataBindingUtil.setContentView(this, layoutId())
        mDatabind.lifecycleOwner = this
        if (getBindingVariable() > 0) {
            mDatabind.setVariable(getBindingVariable(), mViewModel)
        }
        if (getBindingClickVariable()?.size()!! > 0) {
            val bindingClickVariable = getBindingClickVariable()
            bindingClickVariable!!.forEach1<Any> { i: Int, any: Any ->
                mDatabind.setVariable(i, any)
            }
        }
        mDatabind.executePendingBindings()
    }

    override fun onResume() {
        super.onResume()
        MobclickAgent.onResume(this);
    }

    override fun onPause() {
        super.onPause()
        MobclickAgent.onPause(this);
    }
}