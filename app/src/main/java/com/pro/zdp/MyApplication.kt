package com.pro.zdp

import android.util.Log
import anet.channel.util.Utils.context
import com.alibaba.android.arouter.launcher.ARouter
import com.blankj.utilcode.util.ToastUtils
import com.pro.base.base.BaseApp
import com.pro.base.util.Constant
import com.pro.base.util.LogUtils
import com.tencent.mm.opensdk.openapi.WXAPIFactory
import com.umeng.commonsdk.UMConfigure
import com.umeng.message.IUmengRegisterCallback
import com.umeng.message.PushAgent
import io.rong.imlib.RongIMClient
import io.rong.imlib.RongIMClient.OnReceiveMessageWrapperListener
import io.rong.imlib.model.Message


/**
 * @author wjh
 * on      : 2020/7/17
 * description　: MyApplication
 */
class MyApplication : BaseApp() {

    companion object {
        lateinit var instance: MyApplication
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        initARouter()
        initUmeng()
        initPay()
        initIM()

    }

    private fun initUmeng() {
        UMConfigure.init(
            this,
            Constant.UMENGKEY,
            "Umeng",
            UMConfigure.DEVICE_TYPE_PHONE,
            Constant.UMENG_MESSAGE_SECRET
        )

        if (BuildConfig.DEBUG) UMConfigure.setLogEnabled(true)

        //获取消息推送代理示例
        val mPushAgent = PushAgent.getInstance(this)
        //注册推送服务，每次调用register方法都会回调该接口
        mPushAgent.register(object : IUmengRegisterCallback {
            override fun onSuccess(deviceToken: String) {
                //注册成功会返回deviceToken deviceToken是推送消息的唯一标志
                Log.i("TAG", "注册成功：deviceToken：-------->  $deviceToken")
            }

            override fun onFailure(s: String, s1: String) {
                Log.e("TAG", "注册失败：-------->  s:$s,s1:$s1")
            }
        })
        //因为PackageName和appid不一致所以这是设置PackageName
        mPushAgent.resourcePackageName = "com.pro.zdp"
    }

    private fun initARouter() {
        if (BuildConfig.DEBUG) {
            ARouter.openLog()
            ARouter.openDebug()
        }
        ARouter.init(this)
    }

    private fun initPay() {
        val msgApi = WXAPIFactory.createWXAPI(instance, null)
        msgApi.registerApp(Constant.WXPAY_APPID)
    }

    private fun initIM() {
//        RongIMClient.init(instance, "")
    }
}