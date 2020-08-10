package com.pro.zdp

import android.os.Bundle
import android.util.SparseArray
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.alibaba.android.arouter.launcher.ARouter
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx
import com.kaola.common.autoService.LiveAtuoService
import com.pro.base.base.activity.BaseVmActivity
import com.pro.base.router.RouterActivityPath
import com.pro.base.router.RouterFragmentPath
import com.pro.zdp.databinding.ActivityMainBinding
import com.pro.zdp.vm.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import me.hgj.jetpackmvvm.demo.app.util.SettingUtil
import java.util.*


/**
 * @author wjh
 * on      : 2020/7/17
 * description　: MainActivity
 */
class MainActivity : BaseVmActivity<MainViewModel, ActivityMainBinding>() {

    override fun layoutId(): Int = R.layout.activity_main

    override fun getBindingVariable(): Int = BR.viewModel

    override fun initView(savedInstanceState: Bundle?) {
        mViewModel.todoTitle.set("3333")
        mainViewpager.initMain(this)

        bnve.init {
            when (it) {
                R.id.menu_main -> mainViewpager.setCurrentItem(0, false)
                R.id.menu_project -> mainViewpager.setCurrentItem(1, false)
                R.id.menu_system -> ServiceLoader.load(LiveAtuoService::class.java).iterator()
                    .next()
                    .StartLiveActivity(this)
                R.id.menu_public -> mainViewpager.setCurrentItem(2, false)
                R.id.menu_me -> mainViewpager.setCurrentItem(3, false)
            }
        }

        // center item click listener

        val scope = CoroutineScope(Dispatchers.Main)
        scope.launch(Dispatchers.Main) {

            withContext(Dispatchers.IO) {
                delay(2000)
                //todo 获取服务器的资源在进行对应的放回值
                mViewModel.todoTitle.set("这里是协程")
            }
            //在这里更新界面ui
            delay(2000)
            mViewModel.todoTitle.set("这里是协程2")
            delay(2000)
            mViewModel.todoTitle.set("这里是协程3")
        }
    }

    override fun showLoading(message: String) {

    }

    override fun dismissLoading() {
    }

    override fun createObserver() {
    }

    inner class ProxyClick {
        /** 选择时间*/
        fun toZhiBo() {
            ARouter.getInstance().build(RouterActivityPath.Live.PAGER_LIVE).navigation()
        }
    }

    override fun getBindingClickVariable(): SparseArray<Any> {
        val sparseArray = SparseArray<Any>()
        sparseArray.append(BR.click, ProxyClick())
        return sparseArray
    }

    fun BottomNavigationViewEx.init(navigationItemSelectedAction: (Int) -> Unit): BottomNavigationViewEx {
        enableAnimation(true)
        enableShiftingMode(false)
        enableItemShiftingMode(true)
        itemIconTintList = SettingUtil.getColorStateList(SettingUtil.getColor(this@MainActivity))
        itemTextColor = SettingUtil.getColorStateList(this@MainActivity)
        setTextSize(12F)
        setOnNavigationItemSelectedListener {
            navigationItemSelectedAction.invoke(it.itemId)
            true
        }
        return this
    }

    fun ViewPager2.initMain(activity: FragmentActivity): ViewPager2 {
        //是否可滑动
        this.isUserInputEnabled = false
        this.offscreenPageLimit = 5
        //设置适配器
        adapter = object : FragmentStateAdapter(activity) {
            override fun createFragment(position: Int): Fragment {
                when (position) {
                    0 -> {
                        return (ARouter.getInstance().build(
                            RouterFragmentPath.Home.PAGER_HOME
                        ).navigation() as Fragment)
                    }
                    1 -> {
                        return (ARouter.getInstance().build(
                            RouterFragmentPath.Sort.PAGER_SORT
                        ).navigation() as Fragment)
                    }
                    2 -> {
                        return (ARouter.getInstance().build(
                            RouterFragmentPath.Remind.PAGER_REMIND
                        ).navigation() as Fragment)
                    }
                    3 -> {
                        return (ARouter.getInstance().build(
                            RouterFragmentPath.My.PAGER_MY
                        ).navigation() as Fragment)
                    }
                    else -> {
                        return (ARouter.getInstance().build(
                            RouterFragmentPath.Home.PAGER_HOME
                        ).navigation() as Fragment)
                    }
                }
            }

            override fun getItemCount() = 5
        }
        return this
    }
}