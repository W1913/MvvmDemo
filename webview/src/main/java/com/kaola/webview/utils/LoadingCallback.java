package com.kaola.webview.utils;

import android.content.Context;
import android.view.View;

import com.kingja.loadsir.callback.Callback;
import com.kaola.webview.R;

/**
 * Description:TODO
 * Create Time:2020/7/23 10:22
 * Author:wjh
 */

public class LoadingCallback extends Callback {

    @Override
    protected int onCreateView() {
        return R.layout.xiangxue_webview_layout_loading;
    }

    @Override
    public boolean getSuccessVisible() {
        return super.getSuccessVisible();
    }

    @Override
    protected boolean onReloadEvent(Context context, View view) {
        return true;
    }
}
