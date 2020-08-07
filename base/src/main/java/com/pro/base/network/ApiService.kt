package com.pro.base.network

import com.pro.base.bean.ApiResponse
import com.pro.base.bean.BannerBean
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

/**
 * @author wjh
 * 时间　: 2019/12/23
 * description　: 网络API
 */
interface ApiService {

    companion object {
        const val SERVER_URL = "https://wanandroid.com/"
        const val IM_URL = "https://api-cn.ronghub.com/user/getToken.json"
    }

    /**
     * 注册
     */
    @FormUrlEncoded
    @POST("user/register")
    suspend fun register(
        @Field("username") username: String, @Field("password") pwd: String, @Field(
            "repassword"
        ) rpwd: String
    ): ApiResponse<Any>

    /**
     * 获取banner数据
     */
    @GET("banner/json")
    suspend fun getBanner(): ApiResponse<ArrayList<BannerBean>>

}