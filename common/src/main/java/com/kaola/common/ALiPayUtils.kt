package com.kaola.common

import android.app.Activity
import android.os.Message
import android.text.TextUtils
import com.alipay.sdk.app.PayTask
import kotlinx.coroutines.*
import java.lang.Runnable

/**
 * @author wjh
 * on      : 2020/7/23
 * description　:
 */
class ALiPayUtils {

    suspend fun pay(info: String, activity: Activity?) {
        var scope = CoroutineScope(Dispatchers.Main)
        var job = scope.launch(Dispatchers.Main) {
            var result = withContext(Dispatchers.IO) {
                val data = async { PayTask(activity) }
                data.await().payV2(info, true)
            }
            /**
             * 对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
             */
            /**
             * 对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
             */
            var payResult: PayResult? = PayResult(result)
            val resultInfo: String = payResult!!.result // 同步返回需要验证的信息
            val resultStatus: String = payResult.resultStatus
            // 判断resultStatus 为9000则代表支付成功
            // 判断resultStatus 为9000则代表支付成功
            if (TextUtils.equals(resultStatus, "9000")) {
                // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
            } else {
                // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
            }

        }
    }
}