package com.pro.base.router;

/**
 * 用于组件开发中，ARouter多Fragment跳转的统一路径注册
 * 在这里注册添加路由路径，需要清楚的写好注释，标明功能界面
 * Created by goldze on 2018/6/21
 */

public class RouterFragmentPath {
    /**
     * 主业务组件
     */
    public static class Main {
        private static final String MAIN = "/main";
        /*主业务界面*/
        public static final String PAGER_MAIN = MAIN +"/Main";
    }

    /**
     * 主页
     */
    public static class Home {
        private static final String HOME = "/home";
        /*登录界面*/
        public static final String PAGER_HOME = HOME + "/page";
    }
    /**
     * 分类
     */
    public static class Sort {
        private static final String SORT = "/sort";
        /*用户详情*/
        public static final String PAGER_SORT = SORT + "/page";
    }

    /**
     * 直播
     */
    public static class Live {
        private static final String LIVE = "/live";
        /*用户详情*/
        public static final String PAGER_LIVE = LIVE + "/page";
    }
    /**
     * 提醒
     */
    public static class Remind {
        private static final String REMIND = "/remind";
        /*用户详情*/
        public static final String PAGER_REMIND = REMIND + "/page";
    }

    /**
     * 用户组件
     */
    public static class My {
        private static final String MY = "/my";
        /*用户详情*/
        public static final String PAGER_MY = MY + "/UserDetail";
    }
}
