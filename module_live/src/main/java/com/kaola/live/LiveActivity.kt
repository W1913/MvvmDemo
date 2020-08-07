package com.kaola.live

import android.os.Bundle
import android.util.SparseArray
import com.alibaba.android.arouter.launcher.ARouter
import com.kaola.live.databinding.ActivityLiveBinding
import com.kaola.live.widget.MediaController
import com.pili.pldroid.player.AVOptions
import com.pro.base.base.activity.BaseVmActivity
import com.pro.base.router.RouterActivityPath
import kotlinx.android.synthetic.main.activity_live.*

/**
 * @author wjh
 * on      : 2020/7/24
 * description　:
 */
class LiveActivity : BaseVmActivity<LiveViewModel, ActivityLiveBinding>() {
    override fun layoutId(): Int = R.layout.activity_live
    override fun getBindingVariable(): Int = BR.viewModel

    override fun initView(savedInstanceState: Bundle?) {
        val mMediaController = MediaController(this@LiveActivity)
        PLVideoView.setMediaController(mMediaController)
        intent.putExtra("mediaCodec", AVOptions.MEDIA_CODEC_AUTO)

    }

    override fun createObserver() {
    }

    override fun showLoading(message: String) {
    }

    override fun dismissLoading() {
    }

    override fun getBindingClickVariable(): SparseArray<Any>? = SparseArray(0)

    inner class ProxyClick {
        /** 全屏操作*/
        fun toZhiBo() {
            ARouter.getInstance().build(RouterActivityPath.Live.PAGER_LIVE).navigation()
        }
    }

}