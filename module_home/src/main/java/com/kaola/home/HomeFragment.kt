package com.kaola.home

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.util.SparseArray
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.alibaba.android.arouter.facade.annotation.Route
import com.blankj.utilcode.util.ColorUtils
import com.blankj.utilcode.util.ToastUtils
import com.kaola.home.databinding.FragmentHomeBinding
import com.pro.base.base.appContext
import com.pro.base.base.fragment.BaseVmFragment
import com.pro.base.ext.parseState
import com.pro.base.router.RouterFragmentPath
import kotlinx.android.synthetic.main.fragment_home.*
import net.lucode.hackware.magicindicator.MagicIndicator
import net.lucode.hackware.magicindicator.buildins.UIUtil
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator

/**
 * @author wjh
 * on      : 2020/7/24
 * description　:
 */
@Route(path = RouterFragmentPath.Home.PAGER_HOME)
class HomeFragment : BaseVmFragment<HomeViewModel, FragmentHomeBinding>() {
    override fun layoutId(): Int = R.layout.fragment_home

    //fragment集合
    var fragments: ArrayList<Fragment> = arrayListOf()

    //标题集合
    var mDataList: ArrayList<String> = arrayListOf()

    override fun getBindingClickVariable(): SparseArray<Any> {
        val sparseArray = SparseArray<Any>()
        sparseArray.append(BR.click, ProxyClick())
        return sparseArray
    }

    //请求数据ViewModel
    private val requestHomeViewModel: HomeViewModel by viewModels()

    override fun initView(savedInstanceState: Bundle?) {
        mDataList.add(0, "直播")
        mDataList.add(1, "预约")
        fragments.add(HomeChildFragment.newInstance(0, true))
        fragments.add(HomeChildFragment.newInstance(1, true))

        //初始化viewpager2
        view_pager.init(this, fragments)
        //初始化 magic_indicator
        magic_indicator.bindViewPager2(view_pager, mDataList)
    }

    override fun lazyLoadData() {
        requestHomeViewModel.getBannerData()
    }

    override fun createObserver() {
        requestHomeViewModel.run {
            //监听轮播图请求的数据变化
            bannerData.observe(viewLifecycleOwner, Observer { resultState ->
                parseState(resultState, { data ->
                    mDatabind.bannerView.run {
                        this.setHolderCreator {
                            HomeBannerViewHolder()
                        }.setOnPageClickListener {
                            ToastUtils.showLong("我被点击了")
                        }.create(data.toList())
                    }
                }, {
                    Log.e("bannerData", it.errorMsg)
                })
            })
        }

    }

    override fun showLoading(message: String) {
    }

    override fun dismissLoading() {
    }

    inner class ProxyClick {
        /** 消息*/
        fun toInfo() {
            ToastUtils.showShort("消息")
        }

        /** 搜索*/
        fun toSearch() {
            ToastUtils.showShort("搜索")
        }
    }


    fun ViewPager2.init(
        fragment: Fragment,
        fragments: ArrayList<Fragment>,
        isUserInputEnabled: Boolean = true
    ): ViewPager2 {
        //是否可滑动
        this.isUserInputEnabled = isUserInputEnabled
        //设置适配器
        adapter = object : FragmentStateAdapter(fragment) {
            override fun createFragment(position: Int) = fragments[position]
            override fun getItemCount() = fragments.size
        }
        return this
    }

    fun MagicIndicator.bindViewPager2(
        viewPager: ViewPager2,
        mDataList: ArrayList<String> = arrayListOf(),
        mStringList: ArrayList<String> = arrayListOf(),
        action: (index: Int) -> Unit = {}
    ) {
        val commonNavigator = CommonNavigator(appContext)
        commonNavigator.adapter = object : CommonNavigatorAdapter() {
            override fun getCount(): Int {
                return if (mDataList.size != 0) {
                    mDataList.size
                } else {
                    mStringList.size
                }
            }

            override fun getTitleView(context: Context, index: Int): IPagerTitleView {
                return ScaleTransitionPagerTitleView(appContext).apply {
                    text = if (mDataList.size != 0) {
                        mDataList[index]
                    } else {
                        mStringList[index]
                    }
                    textSize = 20f
                    normalColor = ColorUtils.getColor(R.color.colorBlack333)
                    selectedColor = ColorUtils.getColor(R.color.black)
                    setOnClickListener {
                        viewPager.currentItem = index
                        action.invoke(index)
                    }
                }
            }

            override fun getIndicator(context: Context): IPagerIndicator {
                return LinePagerIndicator(context).apply {
                    mode = LinePagerIndicator.MODE_EXACTLY
                    //线条的宽高度
                    lineHeight = UIUtil.dip2px(appContext, 3.0).toFloat()
                    lineWidth = UIUtil.dip2px(appContext, 15.0).toFloat()
                    //线条的圆角
                    roundRadius = UIUtil.dip2px(appContext, 6.0).toFloat()
                    startInterpolator = AccelerateInterpolator()
                    endInterpolator = DecelerateInterpolator(2.0f)
                    //线条的颜色
                    setColors(ColorUtils.getColor(R.color.colorPrimary))
                }
            }
        }
        this.navigator = commonNavigator

        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                this@bindViewPager2.onPageSelected(position)
                action.invoke(position)
            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
                this@bindViewPager2.onPageScrolled(position, positionOffset, positionOffsetPixels)
            }

            override fun onPageScrollStateChanged(state: Int) {
                super.onPageScrollStateChanged(state)
                this@bindViewPager2.onPageScrollStateChanged(state)
            }
        })
    }

    override fun getBindingVariable(): Int = BR.viewModel
}