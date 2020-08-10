package com.kaola.live.sutoService;

import android.content.Context;
import android.content.Intent;

import com.google.auto.service.AutoService;
import com.kaola.common.autoService.LiveAtuoService;
import com.kaola.live.LiveActivity;

import java.util.ServiceLoader;

@AutoService(LiveAtuoService.class)
public class LiveServiceImpL implements LiveAtuoService {

    @Override
    public void StartLiveActivity(Context mContext) {
        Intent intent = new Intent(mContext, LiveActivity.class);
        mContext.startActivity(intent);
    }
}
