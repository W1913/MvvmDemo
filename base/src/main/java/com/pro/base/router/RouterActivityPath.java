package com.pro.base.router;

/**
 * 用于组件开发中，ARouter单Activity跳转的统一路径注册
 * 在这里注册添加路由路径，需要清楚的写好注释，标明功能界面
 *
 */

public class RouterActivityPath {
    /**
     * 直播
     */
    public static class Live {
        private static final String LIVE = "/live";
        /*用户详情*/
        public static final String PAGER_LIVE = LIVE + "/page";
    }
}
