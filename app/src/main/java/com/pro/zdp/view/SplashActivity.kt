package com.pro.zdp.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.blankj.utilcode.util.ToastUtils
import com.pro.zdp.MainActivity

/**
 * @author wjh
 * on      : 2020/7/28
 * description　:打开APP的统一过度页面 包括桌面打开图标 推送点击 第三方唤醒等
 */
class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startActivity(Intent(this, MainActivity::class.java))
    }

    override fun onStop() {
        super.onStop()
        finish()
    }
}