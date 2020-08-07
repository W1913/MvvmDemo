package com.kaola.home

/**
 * @author wjh
 * on      : 2020/7/29
 * description　:
 */

import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.pro.base.bean.BannerBean

import com.zhpan.bannerview.holder.ViewHolder

class HomeBannerViewHolder : ViewHolder<BannerBean> {
    override fun getLayoutId(): Int {
        return R.layout.banner_itemhome
    }

    override fun onBind(itemView: View, data: BannerBean?, position: Int, size: Int) {
        val img = itemView.findViewById<ImageView>(R.id.bannerhome_img)
        data?.let {
            Glide.with(img.context.applicationContext)
                .load(it.imagePath)
                .transition(DrawableTransitionOptions.withCrossFade(500))
                .into(img)
        }

    }

}
