package com.pro.base.callback.livedata.event

import java.util.*

/**
 * @author wjh
 * on      : 2020/7/17
 */

class Event<T>(content: T) {
    private var content: T?
    private var hasHandled = false
    private var isDelaying = false
    fun getContent(): T? {
        return if (!hasHandled) {
            hasHandled = true
            isDelaying = true
            val timer = Timer()
            val task: TimerTask = object : TimerTask() {
                override fun run() {
                    content = null
                    isDelaying = false
                }
            }
            timer.schedule(task, 1000)
            content
        } else if (isDelaying) {
            content
        } else {
            null
        }
    }

    init {
        this.content = content
    }
}