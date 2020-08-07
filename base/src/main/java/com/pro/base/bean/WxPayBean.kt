package com.pro.base.bean

/**
 * @author wjh
 * on      : 2020/7/23
 * description　:
 */
data class WxPayBean(
    var appId: String,//微信开放平台审核通过的应用APPID
    var partnerId: String,//微信支付分配的商户号
    var prepayId: String,//微信返回的支付交易会话ID
//    var packages: String// 扩展字段 暂填写固定值Sign=WXPay
    var noncestr: String,//随机字符串,随机字符串，不长于32位。推荐随机数生成算法
    var sign: String,//签名，详见签名生成算法注意：签名方式一定要与统一下单接口使用的一致
    var timestamp: String//时间戳，请见接口规则-参数规定
)