package com.kaola.common

import android.app.Activity
import com.pro.base.bean.WxPayBean
import com.pro.base.util.Constant
import com.tencent.mm.opensdk.modelpay.PayReq
import com.tencent.mm.opensdk.openapi.WXAPIFactory


/**
 * @author wjh
 * on      : 2020/7/23
 * description　:
 */
class WXPayUilts {
    /**
     * 调支付的方法
     *
     *
     * 注意： 每次调用微信支付的时候都会校验 appid 、包名 和 应用签名的。 这三个必须保持一致才能够成功调起微信
     *
     * @param wxPayBean
     */
    private fun startWXPay(activity: Activity, wxPayBean: WxPayBean) {
        val payRequest = PayReq()
        payRequest.appId = Constant.WXPAY_APPID
        payRequest.partnerId = wxPayBean.partnerId
        payRequest.prepayId = wxPayBean.prepayId
        payRequest.packageValue = "Sign=WXPay" //固定值
        payRequest.nonceStr = wxPayBean.noncestr
        payRequest.timeStamp = wxPayBean.timestamp
        payRequest.sign = wxPayBean.sign
        //发起请求，调起微信前去支付
        WXAPIFactory.createWXAPI(activity, null).sendReq(payRequest)
    }
}